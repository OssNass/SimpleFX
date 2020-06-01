package io.github.ossnass.languageexample.app;

import io.github.ossnass.fx.ControlMaster;
import io.github.ossnass.fx.SimpleController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        ControlMaster.getControlMaster().initControlMaster("en-us.lang", null);
//        ControlMaster.getControlMaster().initControlMaster("ar-sy.lang",null);
//        ControlMaster.getControlMaster().initControlMaster("ar-eg.lang",null);
        SimpleController LangMain = ControlMaster.getControlMaster().getController("LangMain");
        LangMain.setStage(stage);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
