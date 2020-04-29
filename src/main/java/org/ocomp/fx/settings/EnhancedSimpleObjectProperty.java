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
