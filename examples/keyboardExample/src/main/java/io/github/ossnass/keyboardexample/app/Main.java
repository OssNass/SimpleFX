package io.github.ossnass.keyboardexample.app;

import io.github.ossnass.fx.ControlMaster;
import io.github.ossnass.fx.SimpleController;
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        ControlMaster.getControlMaster().initControlMaster("en-us.lang", null);
        SimpleController kbmain=ControlMaster.getControlMaster().getInstance("KBEMain");
        kbmain.setStage(stage);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
