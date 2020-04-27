package org.ocomp.fx;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
/**
 * Provides extra information about each controller for the library.
 *
 * Id: must be unique, indicates the ID of the controller, and is used to differentiate between different controllers.
 *
 * FXMLFile: a string representing the path to the FXML GUI file location with in the application resources folder.
 *
 * Type: The type of the controller, used to determine how to load it. Default value is {@link ContollerType#SINGLE_INSTANCE_ON_DEMAND}
 *
 * Icon: A string representing the path to the icon PNG file location with in the application resources folder, used with {@link javafx.stage.Stage}, the default value is empty string.
 */
public @interface ControllerInfo {
    /**
     * Must be unique, indicates the ID of the controller, and is used to differentiate between different controllers
     *
     * @return
     */
    String Id();

    /**
     * A string representing the path to the FXML GUI file location with in the application resources folder.
     *
     * @return
     */
    String FXMLFile();

    /**
     * The type of the controller, used to determine how to load it.
     *
     * @return
     */
    ContollerType Type() default ContollerType.SINGLE_INSTANCE_ON_DEMAND;

    /**
     * A string representing the path to the icon PNG file location with in the application resources folder,
     * used with {@link javafx.stage.Stage}.
     *
     * @return
     */
    String Icon() default "";
}
