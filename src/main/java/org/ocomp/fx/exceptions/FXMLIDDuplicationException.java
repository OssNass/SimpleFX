package org.ocomp.fx.exceptions;

/**
 * This exception is thrown if and only if the ID of a controller is duplicated
 */
public class FXMLIDDuplicationException extends RuntimeException {
    private static final String msg = "%s is duplicated";

    public FXMLIDDuplicationException(String Id) {
        super(String.format(msg, Id));
    }
}
