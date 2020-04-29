package org.ocomp.fx.settings;

/**
 * Byte setting type
 */
public class ByteSetting extends Setting<Byte> {

    public ByteSetting(String name, Byte defaultValue) {
        super(name, defaultValue);
    }

    @Override
    public Byte fromString(String value) {
        return Byte.valueOf(value);
    }

    @Override
    public String fromValue(Byte value) {
        return value.toString();
    }
}
