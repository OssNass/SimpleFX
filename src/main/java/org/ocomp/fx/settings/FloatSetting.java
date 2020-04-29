package org.ocomp.fx.settings;

/**
 * Float setting type
 */
public class FloatSetting extends Setting<Float> {
    public FloatSetting(String name, Float defaultValue) {
        super(name, defaultValue);
    }

    @Override
    public Float fromString(String value) {
        return Float.valueOf(value);
    }

    @Override
    public String fromValue(Float value) {
        return value.toString();
    }

}
