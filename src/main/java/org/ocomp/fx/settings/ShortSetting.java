package org.ocomp.fx.settings;

/**
 * Short setting type
 */
public class ShortSetting extends Setting<Short> {
    public ShortSetting(String name,Short defaultValue) {
        super(name,defaultValue);
    }

    @Override
    public Short fromString(String value) {
        return Short.parseShort(value);
    }

    @Override
    public String fromValue(Short value) {
        return value.toString();
    }
}
