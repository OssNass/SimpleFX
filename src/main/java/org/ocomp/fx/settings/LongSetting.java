package org.ocomp.fx.settings;

/**
 * Long setting type
 */
public class LongSetting extends Setting<Long> {

    public LongSetting(String name,Long defaultValue) {
        super(name,defaultValue);
    }

    @Override
    public Long fromString(String value) {
        return Long.parseLong(value);
    }

    @Override
    public String fromValue(Long value) {
        return value.toString();
    }
}
