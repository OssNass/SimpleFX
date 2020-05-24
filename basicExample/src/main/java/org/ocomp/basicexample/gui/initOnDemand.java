package org.ocomp.basicexample.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.ocomp.fx.ContollerType;
import org.ocomp.fx.ControllerInfo;
import org.ocomp.fx.QuickActions;
import org.ocomp.fx.SimpleController;

@ControllerInfo(Id = "InitOnceOnDemand",Type = ContollerType.SINGLE_INSTANCE_ON_DEMAND,
FXMLFile = "/fxmls/initOnDemand.fxml")
public class initOnDemand  extends SimpleController {
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
