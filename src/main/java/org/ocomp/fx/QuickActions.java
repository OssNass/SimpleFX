package org.ocomp.fx;

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

