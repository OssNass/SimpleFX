package io.github.ossnass.basicexample.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import io.github.ossnass.fx.ContollerType;
import io.github.ossnass.fx.ControllerInfo;
import io.github.ossnass.fx.QuickActions;
import io.github.ossnass.fx.SimpleController;

@ControllerInfo(Id = "InitOnceOnDemand",Type = ContollerType.SINGLE_INSTANCE_ON_DEMAND,
FXMLFile = "/fxmls/initOnDemand.fxml")
public class initOnDemand  extends SimpleController {
    @Override
    protected void userInit() {
        
    }

    protected void onStageShowUser() {
        
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
