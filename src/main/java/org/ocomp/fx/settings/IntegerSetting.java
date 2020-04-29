package org.ocomp.fx.settings;

/**
 * Integer setting type
 */
public class IntegerSetting extends Setting<Integer>{
    public IntegerSetting(String name,Integer defaultValue) {
        super(name,defaultValue);
    }

    @Override
    public Integer fromString(String value) {
        return Integer.parseInt(value);
    }

    @Override
    public String fromValue(Integer value) {
        return value.toString();
    }
}
