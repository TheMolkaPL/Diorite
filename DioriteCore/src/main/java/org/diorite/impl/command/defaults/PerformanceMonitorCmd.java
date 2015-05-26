package org.diorite.impl.command.defaults;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import org.diorite.impl.command.SystemCommandImpl;
import org.diorite.impl.multithreading.input.ChatThread;
import org.diorite.impl.multithreading.input.CommandsThread;
import org.diorite.command.CommandPriority;
import org.diorite.command.sender.CommandSender;

public class PerformanceMonitorCmd extends SystemCommandImpl
{
    public static final int ONE_MiB = 1_048_576;

    public PerformanceMonitorCmd()
    {
        super("performanceMonitor", Pattern.compile("(performance(Monitor|)|perf)", Pattern.CASE_INSENSITIVE), CommandPriority.LOW);
        this.setCommandExecutor((sender, command, label, matchedPattern, args) -> {
            final Runtime rt = Runtime.getRuntime();
            if (args.has(0) && args.asString(0).equalsIgnoreCase("-dump"))
            {
                int classSize = 0;
                int methodSize = 0;
                int threadSize = 0;
                Map<Thread, StackTraceElement[]> allStackTraces = new LinkedHashMap<>(Thread.getAllStackTraces());

                for (Iterator<Entry<Thread, StackTraceElement[]>> iterator = allStackTraces.entrySet().iterator(); iterator.hasNext(); )
                {
                    final Entry<Thread, StackTraceElement[]> entry = iterator.next();
                    Thread t = entry.getKey();
                    boolean add = true;
                    if (args.length() > 1)
                    {
                        add = false;
                        for (int i = 1; i < args.length(); i++)
                        {
                            if (t.getName().toLowerCase().contains(args.asString(i).toLowerCase()))
                            {
                                add = true;
                                break;
                            }
                        }
                    }
                    if (! add)
                    {
                        iterator.remove();
                        continue;
                    }

                    if (t.getName().length() > threadSize)
                    {
                        threadSize = t.getName().length();
                    }

                    StackTraceElement[] sts = entry.getValue();
                    for (StackTraceElement st : sts)
                    {
                        if (st.getClassName().length() > classSize)
                        {
                            classSize = st.getClassName().length();
                        }
                        if (st.getMethodName().length() > methodSize)
                        {
                            methodSize = st.getMethodName().length();
                        }
                    }
                }
                final StringBuilder sb = new StringBuilder(2048);
                sb.append("\n&c====== &3Thread dump &c======\n");
                for (Entry<Thread, StackTraceElement[]> entry : allStackTraces.entrySet())
                {
                    Thread t = entry.getKey();
                    StackTraceElement[] sts = entry.getValue();
                    sb.append("&7[&3").append(t.getName()).append("&7]");
                    for (int j = t.getName().length(); j < methodSize; j++)
                    {
                        sb.append(' ');
                    }
                    sb.append(" (&3").append(t.getId()).append("&7)");
                    for (int j = Long.toString(t.getId()).length(); j < 5; j++)
                    {
                        sb.append(' ');
                    }
                    sb.append(" State: &3").append(t.getState()).append(" &7 priority=").append(t.getPriority()).append(" \n");
                    for (StackTraceElement st : sts)
                    {
                        boolean isOwn = st.getClassName().contains("diorite");
                        if (isOwn)
                        {
                            sb.append("    &c&l== &r&3");
                        }
                        else
                        {
                            sb.append("    &7&l-- &r&3");
                        }
                        sb.append(st.getClassName());
                        for (int j = st.getClassName().length(); j < classSize; j++)
                        {
                            sb.append(' ');
                        }
                        sb.append(" &c->&3 ").append(st.getMethodName());
                        for (int j = st.getMethodName().length(); j < methodSize; j++)
                        {
                            sb.append(' ');
                        }
                        sb.append("&7[&3");
                        if (st.isNativeMethod())
                        {
                            sb.append("NATIVE");
                        }
                        else
                        {
                            int line = st.getLineNumber();
                            if (line == - 1)
                            {
                                sb.append(" NULL ");
                            }
                            else
                            {
                                sb.append(line);
                                for (int j = Integer.toString(st.getLineNumber()).length(); j < 6; j++)
                                {
                                    sb.append(' ');
                                }
                            }
                        }
                        sb.append("&7]").append(" \n");
                    }
                    sb.append(" \n");
//                    sb.append("&c=========================\n\n");
                }
                sb.append(" ");
                if (sender.isConsole())
                {
                    sender.sendSimpleColoredMessage(sb.toString());
                }
                else
                {
                    sender.getServer().sendConsoleSimpleColoredMessage(sb.toString());
                    final String str = sb.toString().replaceAll("(\\u0020)+", " ");
                    sender.sendSimpleColoredMessage(StringUtils.split(str, '\n'));
                }
                return;
            }
            boolean gc = args.has(0) && args.asString(0).equalsIgnoreCase("-gc");
            boolean memOnly = args.has(0) && args.asString(0).equalsIgnoreCase("-mem");
            if (gc)
            {
                this.display(sender, true);
                rt.gc();
            }
            this.display(sender, memOnly || gc);
        });
    }

    private void display(final CommandSender sender, final boolean onlyMem)
    {
        final Runtime rt = Runtime.getRuntime();
        final long maxMemTemp = rt.maxMemory();
        final long allocated = rt.totalMemory();
        final long free = rt.freeMemory();
        final StringBuilder sb = new StringBuilder(512);
        sb.append("&7====== &3Performance Monitor &7======\n");
        sb.append("&7  == &3Memory &7==\n");
        sb.append("&7    Used: &3").append(((allocated - free) / ONE_MiB)).append(" &7MiB");
        sb.append("&7    (Free: &3").append((free / ONE_MiB)).append(" &7MiB)\n");
        sb.append("&7    Allocated: &3").append((allocated / ONE_MiB)).append(" &7MiB\n");
        if (maxMemTemp != Long.MAX_VALUE)
        {
            sb.append("&7    Max: &3").append((maxMemTemp / ONE_MiB)).append(" &7MiB");
            sb.append("&7    (Not Allocated: &3").append(((maxMemTemp - allocated) / ONE_MiB)).append(" &7MiB)");
        }
        else
        {
            sb.append("&7    No memory limit.");
        }
        if (! onlyMem)
        {
            sb.append('\n');
            sb.append("&7  == &3CPU &7==\n");
            sb.append("&7    Available Processors: &3").append(rt.availableProcessors()).append("\n");
            sb.append("&7  == &3Diorite &7==\n");
            sb.append("&7    Waiting chat actions: &3").append(ChatThread.getActionsSize()).append("\n");
            sb.append("&7    Waiting commands actions: &3").append(CommandsThread.getActionsSize());
        }
        sb.append('\n');
        sender.sendSimpleColoredMessage(sb.toString());
    }
}
