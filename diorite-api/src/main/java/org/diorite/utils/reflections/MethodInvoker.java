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

package org.diorite.utils.reflections;

import java.lang.reflect.Method;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Class used to access/invoke previously prepared methods,
 * methods used by this class must be accessible.
 */
public class MethodInvoker
{
    protected final Method method;

    /**
     * Construct new invoker for given method, it don't check its accessible status.
     *
     * @param method method to wrap.
     */
    public MethodInvoker(final Method method)
    {
        this.method = method;
    }

    /**
     * Invoke method and create new object.
     *
     * @param target    target object, use null for static fields.
     * @param arguments arguments for method.
     *
     * @return method invoke result.
     */
    public Object invoke(final Object target, final Object... arguments)
    {
        try
        {
            return this.method.invoke(target, arguments);
        } catch (final Exception e)
        {
            throw new RuntimeException("Cannot invoke method " + this.method, e);
        }
    }

    /**
     * @return wrapped method.
     */
    public Method getMethod()
    {
        return this.method;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("method", this.method).toString();
    }
}
