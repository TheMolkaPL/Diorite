package org.diorite.material.blocks.wooden;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Bookshelf" and all its subtypes.
 */
public class BookshelfMat extends BlockMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__BOOKSHELF__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__BOOKSHELF__HARDNESS;

    public static final BookshelfMat BOOKSHELF = new BookshelfMat();

    private static final Map<String, BookshelfMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<BookshelfMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected BookshelfMat()
    {
        super("BOOKSHELF", 47, "minecraft:bookshelf", "BOOKSHELF", (byte) 0x00);
    }

    protected BookshelfMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
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
    public BookshelfMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public BookshelfMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of Bookshelf sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Bookshelf or null
     */
    public static BookshelfMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Bookshelf sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Bookshelf or null
     */
    public static BookshelfMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final BookshelfMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.name(), element);
    }

    @Override
    public BookshelfMat[] types()
    {
        return BookshelfMat.bookshelfTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static BookshelfMat[] bookshelfTypes()
    {
        return byID.values(new BookshelfMat[byID.size()]);
    }

    static
    {
        BookshelfMat.register(BOOKSHELF);
    }
}
