package io.github.ossnass.fx.exceptions;

public class NotAnnotatedException extends RuntimeException{

    public NotAnnotatedException(String controllerClass) {
        super(controllerClass+" is not annotated with ControllerInfo");
    }
}
