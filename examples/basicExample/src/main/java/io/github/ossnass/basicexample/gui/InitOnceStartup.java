package io.github.ossnass.basicexample.gui;

import io.github.ossnass.fx.ContollerType;
import io.github.ossnass.fx.ControlMaster;
import io.github.ossnass.fx.ControllerInfo;
import io.github.ossnass.fx.SimpleController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

@ControllerInfo(Id = "initOnceStartup", FXMLFile = "/fxmls/initOnStartup.fxml"
        , Type = ContollerType.SINGLE_INSTANCE_ON_STARTUP)
public class InitOnceStartup extends SimpleController {
    @FXML
    private TextField txtAlways;

    @FXML
    private Button btnOnceOnDemand;

    @FXML
    private Button btnMulti;


    @FXML
    void btnMultiClick(ActionEvent event) {
        try {
            SimpleController onDemand= ControlMaster.getControlMaster().getController("InitMultiple");
            Stage newStage=new Stage();
            onDemand.setStage(newStage);
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnOnceOnDemandClick(ActionEvent event) {
        try {
            SimpleController onDemand= ControlMaster.getControlMaster().getController("InitOnceOnDemand");
            if(onDemand.getStage()==null)
                onDemand.setStage(new Stage());
            onDemand.getStage().show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void userInit() {

    }

    protected void onStageShowUser() {
        
    }
}
