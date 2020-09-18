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

import io.github.ossnass.fx.exceptions.NotAnnotatedException;
import io.github.ossnass.fx.keyboard.KBSManager;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.geometry.NodeOrientation;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.ResourceBundle;

/**
 * The main class of the library, it is used as a controller for FXML files by extending
 * this class and annotating it using {@link ControllerInfo}
 * <p>
 * The FXML file main contoller must of type {@link Pane} or its descendents and has ID "root"
 * <p>
 * In order to add some of your own initialization code override {@link SimpleController#userInit()}
 * <p>
 * In order to get the stage an icon, please assign a value to the field {@link ControllerInfo#Icon()}
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
     * The info annotation of this controller
     */
    protected ControllerInfo info;

    /**
     * Creates a new simple contoller
     */
    public SimpleController() {
        if (!this.getClass().isAnnotationPresent(ControllerInfo.class))
            throw new NotAnnotatedException(getClass().getName());
        info = getClass().getAnnotation(ControllerInfo.class);
    }

    @FXML
    void initialize() {
        //critical for RTL languages
        if (this.resources.getString("LANG.DIR").equalsIgnoreCase("RTL")) {
            this.root.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        } else {
            this.root.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        }
        kbsm = new KBSManager(root);
        userInit();
    }

    /**
     * Return the Id of the FXML file
     *
     * @return the Id of the FXML file
     */
    public String getId() {
        return info.Id();
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
            if (!ControlMaster.getControlMaster().getCSSes().isEmpty()) {
                ControlMaster.getControlMaster().getCSSes().forEach(url -> scene.getStylesheets().add(url));
            }
            if (!info.CSS().isEmpty())
                scene.getStylesheets().add(info.CSS());
            ControlMaster.getControlMaster().addChangeListener(this::onChanged);
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
            this.stage.setOnHidden(null);
        }
        this.stage = stage;
        if (this.stage != null) {
            loadIcon();
            setTitle();
            //fixes a bug where the stage size is not calculated properly,
            // because nodes sizes are only calculated when they are shown on the screen
            this.stage.setOnShown(this::stageOnShow);
            this.stage.setOnHidden(this::onStageCloseUser);
            this.stage.setScene(this.scene);
        }
    }

    /**
     * This function is used as an event handler to fix a problem when showing the stage and the size is wrong
     *
     * @param event the window event
     */
    private void stageOnShow(WindowEvent event) {
        if (!this.stage.isMaximized() || !this.stage.isFullScreen()) {
            this.stage.sizeToScene();
            this.stage.setWidth(this.stage.getWidth());
            this.stage.setHeight(this.stage.getHeight());
        }
        onStageShowUser();
    }


    private void stageOnClose(WindowEvent event) {
        if (info.Type() == ContollerType.MULTIPLE_INSTANCE)
            ControlMaster.getControlMaster().removeChangeListener(this::onChanged);
        onStageCloseUser(event);
    }

    /**
     * Must be overridden by the user to implement there own code that execute when the stage is closed.
     * <p>
     * Only called when there is a stage to close
     */
    protected void onStageCloseUser(WindowEvent event) {

    }

    private void setTitle() {
        String titleKey = String.format("STAGE.%s.TITLE", getId());
        if (this.resources.containsKey(titleKey))
            stage.setTitle(this.resources.getString(titleKey));
    }

    private void loadIcon() {
        if (this.info.Icon() != null) {
            try {
                this.stage.getIcons().add(new Image(ResourceManager.getURL(this.info.Icon()).toExternalForm()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Must be overridden by the user to implement there own initialization.
     */
    protected abstract void userInit();

    /**
     * Must be overridden by the user to implement there own code that execute when the stage is shown.
     * <p>
     * Only called when there is a stage to show
     */
    protected void onStageShowUser() {
    }


    /**
     * Used to track changes in the css list
     * <p>
     * *********************************
     * *<b>MUST NOT BE USED BY USER</b>*
     * *********************************
     *
     * @param c the change in the css list
     */
    void onChanged(ListChangeListener.Change<? extends String> c) {
        while (c.next()) {
            if (c.wasAdded())
                c.getAddedSubList().forEach(url -> scene.getStylesheets().add(url));
            else if (c.wasRemoved())
                c.getRemoved().forEach(url -> scene.getStylesheets().remove(url));
        }
    }
}
