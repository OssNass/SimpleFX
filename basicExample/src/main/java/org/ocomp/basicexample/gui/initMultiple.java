package org.ocomp.basicexample.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.ocomp.fx.ContollerType;
import org.ocomp.fx.ControllerInfo;
import org.ocomp.fx.QuickActions;
import org.ocomp.fx.SimpleController;

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
