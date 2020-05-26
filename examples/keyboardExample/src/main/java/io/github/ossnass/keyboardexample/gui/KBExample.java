package io.github.ossnass.keyboardexample.gui;

import io.github.ossnass.fx.ContollerType;
import io.github.ossnass.fx.ControllerInfo;
import io.github.ossnass.fx.SimpleController;

@ControllerInfo(Id = "KBEMain",FXMLFile = "/fxmls/kbmain.fxml",Type = ContollerType.SINGLE_INSTANCE_ON_STARTUP)
public class KBExample extends SimpleController {
    @Override
    protected void userInit() {
        
    }
}
