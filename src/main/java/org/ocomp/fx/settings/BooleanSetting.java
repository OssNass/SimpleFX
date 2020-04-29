package org.ocomp.fx.settings;

/**
 * Boolean setting type
 */
public class BooleanSetting extends Setting<Boolean> {
    public BooleanSetting(String name,boolean defaultValue) {
        super(name,defaultValue);
    }

    @Override
    public Boolean fromString(String value) {
        return Boolean.parseBoolean(value);
    }

    @Override
    public String fromValue(Boolean value) {
        return value.toString();
    }
}
