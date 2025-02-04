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

package org.diorite.material.data.drops;

import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.entity.Entity;
import org.diorite.inventory.item.ItemStack;
import org.diorite.utils.math.DioriteRandom;
import org.diorite.world.Block;

public class PossibleFixedDrop extends PossibleDrop
{
    protected final int amount;

    public PossibleFixedDrop(final ItemStack itemStack, final int amount)
    {
        super(itemStack);
        this.amount = amount;
    }

    public PossibleFixedDrop(final ItemStack itemStack)
    {
        super(itemStack);
        this.amount = 1;
    }

    @Override
    public void simulateDrop(final Entity entity, final DioriteRandom rand, final Set<ItemStack> drops, final ItemStack usedTool, final Block block)
    {
        if (this.amount == 0)
        {
            return;
        }
        if (this.amount == 1)
        {
            drops.add(this.getItemStack());
            return;
        }
        for (int i = 0; i < this.amount; i++)
        {
            final ItemStack itemStack = this.getItemStack();
            if ((itemStack.getMaterial().getId() != 0) && (itemStack.getAmount() != 0))
            {
                drops.add(itemStack);
            }
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("amount", this.amount).toString();
    }
}
