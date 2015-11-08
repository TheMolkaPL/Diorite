/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Diorite (by Bartłomiej Mazur (aka GotoFinal))
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.diorite.material.blocks;

import java.util.Map;

import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing 'Enchanting Table' block material in minecraft. <br>
 * ID of block: 116 <br>
 * String ID of block: minecraft:enchanting_table <br>
 * Hardness: 5 <br>
 * Blast Resistance 6000
 */
@SuppressWarnings("JavaDoc")
public class EnchantingTableMat extends BlockMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final EnchantingTableMat ENCHANTING_TABLE = new EnchantingTableMat();

    private static final Map<String, EnchantingTableMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<EnchantingTableMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected EnchantingTableMat()
    {
        super("ENCHANTING_TABLE", 116, "minecraft:enchanting_table", "ENCHANTING_TABLE", (byte) 0x00, 5, 6_000);
    }

    protected EnchantingTableMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
    }

    @Override
    public EnchantingTableMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public EnchantingTableMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of EnchantingTable sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of EnchantingTable or null
     */
    public static EnchantingTableMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of EnchantingTable sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of EnchantingTable or null
     */
    public static EnchantingTableMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final EnchantingTableMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public EnchantingTableMat[] types()
    {
        return EnchantingTableMat.enchantingTableTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static EnchantingTableMat[] enchantingTableTypes()
    {
        return byID.values(new EnchantingTableMat[byID.size()]);
    }

    static
    {
        EnchantingTableMat.register(ENCHANTING_TABLE);
    }
}
