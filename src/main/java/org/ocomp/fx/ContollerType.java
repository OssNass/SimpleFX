package org.ocomp.fx;

/**
 * The Type of the controller
 */
public enum ContollerType {
    /**
     * Initialize the controller once at startup, on every call to {@link ControlMaster#getInstance} returns the same instance
     */
    SINGLE_INSTANCE_ON_STARTUP,
    /**
     * Initialize the controller once on demand, on every call to {@link ControlMaster#getInstance} returns the same instance
     */
    SINGLE_INSTANCE_ON_DEMAND,
    /**
     * Initialize the controller on demand, on every call to {@link ControlMaster#getInstance} returns a new instance
     */
    MULTIPLE_INSTANCE;
}
