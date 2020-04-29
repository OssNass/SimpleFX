package org.ocomp.fx.settings;

/**
 * Character setting type
 */
public class CharSetting extends Setting<Character> {

    public CharSetting(String name,Character defaultValue) {
        super(name,defaultValue);
    }

    @Override
    public Character fromString(String value) {
        return value.charAt(0);
    }

    @Override
    public String fromValue(Character value) {
        return value+"";
    }
}
