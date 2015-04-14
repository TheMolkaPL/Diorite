package org.diorite.material.blocks.wooden.wood.fencegate;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.blocks.wooden.WoodType;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "DarkOakFenceGate" and all its subtypes.
 */
public class DarkOakFenceGate extends WoodenFenceGate
{
    // TODO: auto-generated class, implement other types (sub-ids).	
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;	
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__DARK_OAK_FENCE_GATE__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__DARK_OAK_FENCE_GATE__HARDNESS;

    public static final DarkOakFenceGate DARK_OAK_FENCE_GATE = new DarkOakFenceGate();

    private static final Map<String, DarkOakFenceGate>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<DarkOakFenceGate> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected DarkOakFenceGate()
    {
        super("DARK_OAK_FENCE_GATE", 186, "minecraft:fark_oak_fence_gate", "DARK_OAK_FENCE_GATE", WoodType.DARK_OAK);
    }

    @Override
    public float getBlastResistance()
    {
        return BLAST_RESISTANCE;
    }

    @Override
    public float getHardness()
    {
        return HARDNESS;
    }

    @Override
    public DarkOakFenceGate getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public DarkOakFenceGate getType(final int id)
    {
        return getByID(id);
    }

    public static DarkOakFenceGate getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static DarkOakFenceGate getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final DarkOakFenceGate element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        DarkOakFenceGate.register(DARK_OAK_FENCE_GATE);
    }
}
