package org.ocomp.fx.exceptions;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This exception is thrown when calling {@link org.ocomp.fx.SimpleController#setStage(Stage)}
 * when the scene has not been set or set null in {@link org.ocomp.fx.SimpleController#setScene(Scene)}
 */
public class SceneNotSetException extends RuntimeException {
    public SceneNotSetException() {
        super("Cannot set stage when the scene is null");
    }
}
