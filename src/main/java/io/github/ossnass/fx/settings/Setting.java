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

package io.github.ossnass.fx.settings;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;

/**
 * This class represents the setting you wish to store.\
 * <p>
 * You must inherit this class and override the {@link Setting#fromString(String)} and {@link Setting#fromValue(Object)}
 * functions in order to use it
 *
 * @param <T> the type of data stored in the setting
 */
public abstract class Setting<T> {
    private EnhancedSimpleObjectProperty<T> data;
    private String name;
    private boolean saveChange = true;

    /**
     * Creates a new setting
     *
     * @param name         the name of the setting, cannot be null or empty string
     * @param defaultValue the default value of the setting, can be null
     */
    public Setting(String name, T defaultValue) {
        if (name == null || name.trim().equals(""))
            throw new IllegalArgumentException("Setting name cannot be null or empty string");
        this.name = name;
        data = new EnhancedSimpleObjectProperty<>();
        data.setDefaultValue(defaultValue);
        data.addListener(this::storeChanges);
        SettingsManager.getManager().addSetting(this);
    }

    /**
     * Must override.
     * <p>
     * Converts string value to the type of data stored
     *
     * @param value the string value to covert
     * @return the setting value in data type
     */
    public abstract T fromString(String value);

    /**
     * Must override
     * <p>
     * Converts from setting data type to string
     *
     * @param value the value of the setting in data type
     * @return the string representation of the setting value
     */
    public abstract String fromValue(T value);

    private void storeChanges(ObservableValue<? extends T> observable, T oldValue, T newValue) {
        if (saveChange) {
            String storeValue = fromValue(newValue);
            SettingsManager.getManager().storeSettings(name, storeValue);
        } else
            saveChange = true;
    }

    /**
     * Returns the name of the setting, please note that the setting name is set only once
     *
     * @return the name of the setting
     */
    public String getName() {
        return name;
    }


    /**
     * Returns the setting data
     *
     * @return the setting data
     */
    public T getData() {
        return data.get();
    }

    /**
     * Returns the settings as a {@link SimpleObjectProperty}
     *
     * @return the settings as a {@link SimpleObjectProperty}
     */
    public SimpleObjectProperty<T> dataProperty() {
        return data;
    }

    /**
     * Changes the value of the setting
     *
     * @param data the new value of the setting
     */
    public void setData(T data) {
        this.setData(data, true);
    }

    /**
     * Internal function must not ve called by the user
     *
     * @param data
     * @param saveChange
     */
    void setData(T data, boolean saveChange) {
        this.saveChange = saveChange;
        this.data.set(data);
    }
}
