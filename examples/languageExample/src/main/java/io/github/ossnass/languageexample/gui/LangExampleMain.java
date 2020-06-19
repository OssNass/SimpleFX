package io.github.ossnass.languageexample.gui;

import io.github.ossnass.fx.ContollerType;
import io.github.ossnass.fx.ControllerInfo;
import io.github.ossnass.fx.QuickActions;
import io.github.ossnass.fx.SimpleController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

@ControllerInfo(Id = "LangMain", FXMLFile = "io.github.ossnass.languageexample/langexample.fxml", Type = ContollerType.SINGLE_INSTANCE_ON_STARTUP)
public class LangExampleMain extends SimpleController {
    @Override
    protected void userInit() {

    }

    @Override
    protected void onStageShowUser() {
        
    }

    @FXML
    private Button btnMessage;

    @FXML
    void btnMessageClick(ActionEvent event) {
        QuickActions.showInfoMessage(null,
                resources.getString("InfoHeader"),
                resources.getString("InfoBody"),
                this);
    }
}
