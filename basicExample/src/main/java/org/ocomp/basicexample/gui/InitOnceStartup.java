package org.ocomp.basicexample.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.ocomp.fx.ContollerType;
import org.ocomp.fx.ControlMaster;
import org.ocomp.fx.ControllerInfo;
import org.ocomp.fx.SimpleController;
import org.ocomp.fx.exceptions.FXMLNotFoundException;

import javax.management.StandardEmitterMBean;
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
            SimpleController onDemand= ControlMaster.getControlMaster().getInstance("InitMultiple");
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
            SimpleController onDemand= ControlMaster.getControlMaster().getInstance("InitOnceOnDemand");
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
}
