package org.ocomp.fx.exceptions;

import org.ocomp.fx.ControlMaster;

/**
 * This is exception is thrown by {@link ControlMaster#getInstance(String)} if the ID doesn't match any already registered controllers in any type
 */
public class FXMLNotFoundException extends Exception {
    private static final String msg="FXML for ID %s not found";

    public FXMLNotFoundException(String ID) {
        super(String.format(msg,ID));
    }
}
