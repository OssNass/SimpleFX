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

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;


/**
 * This class provides an abstraction for creating and showing message and confirmation boxes,
 * for more control please use {@link Alert} and in order to maintain the node orientation,
 * please consider using this line {@code alert.dialogPaneProperty().get().setNodeOrientation(NDE_ORIENTATION);}
 */
public class QuickActions {
    /**
     * Shows an error message
     * @param title the title of the message,if null then the title is loaded from the language file under the key {@link LanguageKeys#MESSAGE_TITLE_ERROR}
     * @param header the header of the message can be null
     * @param content the body of the message can be null
     * @param owner the owner of the message, if not null, the message will have the same node orientation of the owner
     */
    public static void showErrorMessage(String title, String header, String content, SimpleController owner) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if (owner != null && owner.getRoot() != null) {
            alert.dialogPaneProperty().get().setNodeOrientation(owner.getRoot().getNodeOrientation());
            alert.initOwner(owner.getStage());
        }
        if (title == null)
            title = ControlMaster.getControlMaster().getLanguage().getString("MESSAGE.TITLE.ERROR");
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Shows an info message
     * @param title the title of the message,if null then the title is loaded from the language file under the key {@link LanguageKeys#MESSAGE_TITLE_INFO}
     * @param header the header of the message can be null
     * @param content the body of the message can be null
     * @param owner the owner of the message, if not null, the message will have the same node orientation of the owner
     */
    public static void showInfoMessage(String title, String header, String content, SimpleController owner) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (owner.getRoot() != null) {
            alert.dialogPaneProperty().get().setNodeOrientation(owner.getRoot().getNodeOrientation());
            alert.initOwner(owner.getStage());
        }
        if (title == null)
            title = ControlMaster.getControlMaster().getLanguage().getString("MESSAGE.TITLE.INFO");
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }

    /**
     * Shows a confirmation dialog
     * @param title the title of the message,if null then the title is loaded from the language file under the key {@link LanguageKeys#MESSAGE_TITLE_CONFIRM}
     * @param header the header of the message can be null
     * @param content the body of the message can be null
     * @param owner the owner of the message, if not null, the message will have the same node orientation of the owner
     * @return the button pressed, see {@link ButtonType} for more info
     */

    public static ButtonType showConfirmationMessage(String title, String header, String content, SimpleController owner) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        if (owner.getRoot() != null) {
            alert.dialogPaneProperty().get().setNodeOrientation(owner.getRoot().getNodeOrientation());
            alert.initOwner(owner.getStage());
        }
        if (title == null)
            title = ControlMaster.getControlMaster().getLanguage().getString("MESSAGE.TITLE.CONFIRM");
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
        return alert.getResult();
    }

    /**
     * Shows a warning message
     * @param title the title of the message,if null then the title is loaded from the language file under the key {@link LanguageKeys#MESSAGE_TITLE_WARNING}
     * @param header the header of the message can be null
     * @param content the body of the message can be null
     * @param owner the owner of the message, if not null, the message will have the same node orientation of the owner
     */
    public static void showWarningMessage(String title, String header, String content, SimpleController owner) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        if (owner.getRoot() != null) {
            alert.dialogPaneProperty().get().setNodeOrientation(owner.getRoot().getNodeOrientation());
            alert.initOwner(owner.getStage());
        }
        if (title == null)
            title = ControlMaster.getControlMaster().getLanguage().getString("MESSAGE.TITLE.WARNING");
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }
}

