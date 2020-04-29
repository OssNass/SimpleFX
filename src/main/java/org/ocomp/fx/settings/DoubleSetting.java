package org.ocomp.fx.settings;

/**
 * Double setting type
 */
public class DoubleSetting extends Setting<Double> {
    public DoubleSetting(String name,Double defaultValue) {
        super(name,defaultValue);
    }

    @Override
    public Double fromString(String value) {
        return Double.parseDouble((value));
    }

    @Override
    public String fromValue(Double value) {
        return value.toString();
    }
}
