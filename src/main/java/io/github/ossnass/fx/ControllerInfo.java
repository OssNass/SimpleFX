/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2020 Ossama Nasser
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.ossnass.fx;

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
     * @return A string representing the path to the FXML GUI file location with in the application resources folder.
     */
    String FXMLFile();

    /**
     * The type of the controller, used to determine how to load it.
     *
     * @return The type of the controller
     */
    ContollerType Type() default ContollerType.SINGLE_INSTANCE_ON_DEMAND;

    /**
     * The name of the icon in the resources dir for the stage, has to be resource bound.
     *
     * Optional.
     * @return The name of the icon in the resources dir for the stage
     */
    String Icon() default "";

    /**
     * If you have a special CSS for this controller, has to be resource bound.
     *
     * Optional
     * @return a special CSS for this controller, empty string if otherwise
     */
    String CSS() default "";
}
