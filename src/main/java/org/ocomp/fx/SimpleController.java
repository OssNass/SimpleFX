/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2019 the original author or authors.
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.ocomp.fx;

import javafx.fxml.FXML;
import javafx.geometry.NodeOrientation;
import javafx.scene.layout.Pane;
import org.ocomp.fx.keyboard.KBSManager;

import java.util.ResourceBundle;

/**
 * The main class of the library, it is used as a controller for FXML files by extending
 * this class and annotating it using {@link ControllerInfo}
 * <p>
 * The FXML file main contoller must of type {@link Pane} or its descendents and has ID "root"
 * <p>
 * In order to add some of your own initialization code override {@link SimpleController#userInit()}
 */
public abstract class SimpleController {
    @FXML
    protected ResourceBundle resources;
    @FXML
    protected Pane root;

    protected KBSManager kbsm;

    @FXML
    void initialize() {
        if (this.resources.getString("LANG.DIR").equalsIgnoreCase("RTL")) {
            this.root.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        } else {
            this.root.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        }
        kbsm = new KBSManager(root);

    }

    public Pane getRoot() {
        return root;
    }

    protected abstract void userInit();
}
