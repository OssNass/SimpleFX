package org.ocomp.fx.settings;

/**
 * Srting setting type
 */
public class StringSetting extends Setting<String> {

    public StringSetting(String name, String defaultValue) {
        super(name, defaultValue);
    }

    @Override
    public String fromString(String value) {
        return value;
    }

    @Override
    public String fromValue(String value) {
        return value;
    }
}
