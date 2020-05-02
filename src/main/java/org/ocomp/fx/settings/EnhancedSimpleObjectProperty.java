/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2020 Ossama Nasser
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

package org.ocomp.fx.settings;

import javafx.beans.property.SimpleObjectProperty;

/**
 * A modified version of {@link SimpleObjectProperty} that allows the property to fall back to default value
 * when setting it with the value {@code null}
 *
 * @param <T> The type of data stored
 */
public class EnhancedSimpleObjectProperty<T> extends SimpleObjectProperty<T> {
    private T defaultValue;

    /**
     * Returns the default value of the property
     *
     * @return the default value of the property
     */
    public T getDefaultValue() {
        return defaultValue;
    }

    /**
     * Returns the value of the property, if the value is {@code null}, it returns the default value
     *
     * @return the value of the property, if the value is {@code null}, it returns the default value
     */
    public T get() {
        if (super.get() == null || super.get().toString().isEmpty())
            return defaultValue;
        return super.get();
    }

    /**
     * Returns the value of the property, if the value is {@code null}, it returns the default value
     *
     * @return the value of the property, if the value is {@code null}, it returns the default value
     */
    public T getValue() {
        if (super.getValue() == null || super.getValue().toString().trim().isEmpty())
            return defaultValue;
        return super.getValue();
    }

    /**
     * Changes the value of the property, if the new value is null, it falls back to the default value
     *
     * @param newValue the new value of the property
     */
    public void set(T newValue) {
        if (newValue == null || newValue.toString().trim().isEmpty())
            super.set(defaultValue);
        else
            super.set(newValue);
    }

    /**
     * Changes the value of the property, if the new value is null, it falls back to the default value
     *
     * @param newValue the new value of the property
     */
    public void setValue(T newValue) {
        if (newValue == null || newValue.toString().trim().isEmpty())
            super.setValue(defaultValue);
        else
            super.setValue(newValue);
    }

    /**
     * Changes the default value of the property
     *
     * @param defaultValue the new default value of the property
     */
    public void setDefaultValue(T defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * Simple constrictor
     */
    public EnhancedSimpleObjectProperty() {
    }

    /**
     * Inherits {@link SimpleObjectProperty#SimpleObjectProperty()}
     *
     * @param initialValue
     */
    public EnhancedSimpleObjectProperty(T initialValue) {
        super(initialValue);
    }

    /**
     * Inherits {@link SimpleObjectProperty#SimpleObjectProperty(Object, String)}
     *
     * @param bean
     * @param name
     */
    public EnhancedSimpleObjectProperty(Object bean, String name) {
        super(bean, name);
    }

    /**
     * Inherits {@link SimpleObjectProperty#SimpleObjectProperty(Object, String, Object)}
     *
     * @param bean
     * @param name
     * @param initialValue
     */
    public EnhancedSimpleObjectProperty(Object bean, String name, T initialValue) {
        super(bean, name, initialValue);
    }

}
