package io.github.ossnass.basicexample.gui;

import io.github.ossnass.fx.ContollerType;
import io.github.ossnass.fx.ControllerInfo;
import io.github.ossnass.fx.QuickActions;
import io.github.ossnass.fx.SimpleController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


@ControllerInfo(Id = "InitMultiple",Type = ContollerType.MULTIPLE_INSTANCE,
FXMLFile = "/fxmls/initMultiple.fxml")
public class initMultiple extends SimpleController {
    @Override
    protected void userInit() {
        
    }

    @FXML
    private TextField txtMessage;

    @FXML
    private Button btnShowMessage;

    @FXML
    void btnShowMessageClick(ActionEvent event) {
        QuickActions.showInfoMessage(null,txtMessage.getText(),null,this);
    }

}
