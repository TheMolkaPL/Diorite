package diorite.impl.connection.packets.play.in;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import diorite.BlockFace;
import diorite.BlockLocation;
import diorite.impl.connection.EnumProtocol;
import diorite.impl.connection.EnumProtocolDirection;
import diorite.impl.connection.packets.PacketClass;
import diorite.impl.connection.packets.PacketDataSerializer;
import diorite.impl.connection.packets.play.PacketPlayInListener;

@PacketClass(id = 0x07, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.SERVERBOUND)
public class PacketPlayInBlockDig implements PacketPlayIn
{
    private BlockDigAction action;
    private BlockLocation blockLocation;
    private BlockFace     blockFace;

    public PacketPlayInBlockDig()
    {
    }

    public PacketPlayInBlockDig(final BlockDigAction action)
    {
        this.action = action;
        this.blockLocation = BlockLocation.ZERO;
    }

    public PacketPlayInBlockDig(final BlockDigAction action, final BlockLocation blockLocation, final BlockFace blockFace)
    {
        this.action = action;
        this.blockLocation = blockLocation;
        this.blockFace = blockFace;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.action = BlockDigAction.values()[data.readByte()];
        this.blockLocation = data.readBlockLocation();
        this.blockFace = toBlockFace(data.readByte());
    }

    @Override
    public void writePacket(final PacketDataSerializer data) throws IOException
    {
        data.writeByte(this.action.ordinal());
        data.writeBlockLocation(this.blockLocation);
        data.writeByte((this.blockFace == null) ? -1 : fromBlockFace(this.blockFace));
    }

    public BlockDigAction getAction()
    {
        return this.action;
    }

    public void setAction(final BlockDigAction action)
    {
        this.action = action;
    }

    public BlockLocation getBlockLocation()
    {
        return this.blockLocation;
    }

    public void setBlockLocation(final BlockLocation blockLocation)
    {
        this.blockLocation = blockLocation;
    }

    public BlockFace getBlockFace()
    {
        return this.blockFace;
    }

    public void setBlockFace(final BlockFace blockFace)
    {
        this.blockFace = blockFace;
    }

    @Override
    public void handle(final PacketPlayInListener listener)
    {
        listener.handle(this);
    }

    protected static BlockFace toBlockFace(final byte b)
    {
        switch (b)
        {
            case 0:
                return BlockFace.DOWN;
            case 1:
                return BlockFace.UP;
            case 2:
                return BlockFace.NORTH;
            case 3:
                return BlockFace.SOUTH;
            case 4:
                return BlockFace.WEST;
            case 5:
                return BlockFace.EAST;
            default:
                return null;
        }
    }

    protected static byte fromBlockFace(final BlockFace b)
    {
        switch (b)
        {

            case NORTH:
                return 2;
            case EAST:
                return 5;
            case SOUTH:
                return 3;
            case WEST:
                return 4;
            case UP:
                return 1;
            case DOWN:
                return 0;
            default:
                return -1;
        }
    }

    public enum BlockDigAction
    {
        START_DIG,
        CANCEL_DIG,
        FINISH_DIG,
        DROP_ITEM_STACK,
        DROP_ITEM,
        SHOT_ARROW_OR_EAT
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("action", this.action).append("blockLocation", this.blockLocation).append("blockFace", this.blockFace).toString();
    }
}
