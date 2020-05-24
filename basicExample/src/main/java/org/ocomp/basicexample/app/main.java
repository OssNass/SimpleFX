package org.ocomp.basicexample.app;

import javafx.application.Application;
import javafx.stage.Stage;
import org.ocomp.fx.ControlMaster;
import org.ocomp.fx.SimpleController;

public class main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        ControlMaster.getControlMaster().initControlMaster("en-us.lang",null);
        SimpleController mainWindow=ControlMaster.getControlMaster().getInstance("initOnceStartup");
        mainWindow.setStage(stage);
        stage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
