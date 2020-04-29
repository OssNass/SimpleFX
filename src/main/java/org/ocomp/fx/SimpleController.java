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
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.ocomp.fx.exceptions.SceneNotSetException;
import org.ocomp.fx.keyboard.KBSManager;
import org.ocomp.fx.keyboard.KBShortcut;
import org.ocomp.fx.keyboard.KBShortcutAnnotation;
import org.reflections.Reflections;

import java.io.IOException;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * The main class of the library, it is used as a controller for FXML files by extending
 * this class and annotating it using {@link ControllerInfo}
 * <p>
 * The FXML file main contoller must of type {@link Pane} or its descendents and has ID "root"
 * <p>
 * In order to add some of your own initialization code override {@link SimpleController#userInit()}
 * <p>
 * In order to get the stage an icon, please assign a value to the field {@link SimpleController#icon}
 * <p>
 * In order to set the title to the stage,
 * please add a line in the language line with key "STAGE.FXML file name without extension.TITLE"
 */
public abstract class SimpleController {
    /**
     * The language information
     */
    @FXML
    protected ResourceBundle resources;

    /**
     * The root pane where all the nodes are placed
     */
    @FXML
    protected Pane root;

    /**
     * The keyboard shortcut manager
     */
    protected KBSManager kbsm;

    /**
     * The scene where the root node is placed, please use {@link SimpleController#setScene(Scene)}
     */
    protected Scene scene;

    /**
     * The stage where everything is shown, please use (@link {@link SimpleController#setStage(Stage)}
     */
    protected Stage stage;

    /**
     * The icon of the stage, can be null
     */
    protected String icon;

    private String FXMLName;

    @FXML
    void initialize() {
        //crucial for RTL languages
        if (this.resources.getString("LANG.DIR").equalsIgnoreCase("RTL")) {
            this.root.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        } else {
            this.root.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        }
        kbsm = new KBSManager(root);
        System.out.printf(getClass().getSimpleName());
        userInit();

    }

    /**
     * Returns the root pane
     *
     * @return the root pane
     */
    public Pane getRoot() {
        return root;
    }

    /**
     * Return the current scene where the root pane is placed
     *
     * @return the current scene where the root pane is placed
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * Changes the scene where the root pane is placed, can be null.
     * <p>
     * When changing the scene, the root pane is first removed from the old scene, then placed on the new scene
     *
     * @param scene the new scene, can be null
     */
    public void setScene(Scene scene) {
        if (this.scene != null)
            this.scene.setRoot(null);
        this.scene = scene;
        if (this.scene != null) {
            this.scene.setRoot(root);
            if (ControlMaster.getControlMaster().getCSSPath() != null) {
                scene.getStylesheets().add(ControlMaster.getControlMaster().getCSSPath());
            }
        }
        if (this.stage != null)
            stage.setScene(this.scene);
    }

    /**
     * Returns the current stage on which the scene is displayed
     *
     * @return the current stage on which the scene is displayed
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * The new stage to display the scene on.
     * <p>
     * if the new stage is null, the stage is cleared.
     * When changing scenes and/or stages or possible changes are made automatically
     *
     * @param stage the new stage
     */
    public void setStage(Stage stage) {
        if (stage == null) {
            this.stage.setScene(null);
            this.stage.setOnShown(null);
        }
        this.stage = stage;
        if (this.stage != null) {
            setIcon();
            setTitle();
            //fixes a bug where the stage size is not calculated properly,
            // because nodes sizes are only calculated when they are shown on the screen
            this.stage.setOnShown(this::stageOnShow);
            this.stage.setScene(this.scene);
        }
    }

    /**
     * This function is used as an event handler to fix a problem when showing the stage and the size is wrong
     */
    protected void stageOnShow(WindowEvent event) {
        if (!this.stage.isMaximized() || !this.stage.isFullScreen()) {
            this.stage.sizeToScene();
            this.stage.setWidth(this.stage.getWidth());
            this.stage.setHeight(this.stage.getHeight());
        }
    }

    private void setTitle() {
        String titleKey = String.format("STAGE.%s.TITLE", getClass().getSimpleName());
        if (this.resources.containsKey(titleKey))
            stage.setTitle(this.resources.getString(titleKey));
    }

    private void setIcon() {
        if (this.icon != null) {
            this.stage.getIcons().add(new Image(getClass().getResourceAsStream(this.icon)));
        }
    }


    /**
     * Must be overridden by the user to implement there own initialization
     */
    protected abstract void userInit();
}
