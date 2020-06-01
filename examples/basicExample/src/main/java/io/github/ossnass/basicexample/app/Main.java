package io.github.ossnass.basicexample.app;

import io.github.ossnass.fx.ControlMaster;
import io.github.ossnass.fx.SimpleController;
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        ControlMaster.getControlMaster().initControlMaster("en-us.lang",null);
        SimpleController mainWindow=ControlMaster.getControlMaster().getController("initOnceStartup");
        mainWindow.setStage(stage);
        stage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
