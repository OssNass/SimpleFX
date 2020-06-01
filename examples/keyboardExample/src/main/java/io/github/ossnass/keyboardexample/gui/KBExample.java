package io.github.ossnass.keyboardexample.gui;

import io.github.ossnass.fx.ContollerType;
import io.github.ossnass.fx.ControllerInfo;
import io.github.ossnass.fx.QuickActions;
import io.github.ossnass.fx.SimpleController;
import io.github.ossnass.fx.keyboard.KBShortcut;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCombination;

@ControllerInfo(Id = "KBEMain",FXMLFile = "/fxmls/kbmain.fxml",Type = ContollerType.SINGLE_INSTANCE_ON_STARTUP)
public class KBExample extends SimpleController {
    @FXML
    private Button btnA;

    @FXML
    private Button btnB;

    @FXML
    private Button btnC;

    @FXML
    void btnAClick(ActionEvent event) {
        QuickActions.showInfoMessage(null,"Info Message","Info body",this);
    }

    @FXML
    void btnBClick(ActionEvent event) {
        QuickActions.showWarningMessage(null,"Warning Message","Warning body",this);
    }

    @FXML
    void btnCClick(ActionEvent event) {
        QuickActions.showErrorMessage(null,"Error Message","Error body",this);
    }
    @Override
    protected void userInit() {
        kbsm.addKeyboardShortcut("Info",new KBShortcut<ActionEvent>(btnA, KeyCombination.valueOf("ctrl+a"),this::btnAClick));
        kbsm.addKeyboardShortcut("Warning",new KBShortcut<ActionEvent>(btnB, KeyCombination.valueOf("ctrl+b"),this::btnBClick));
        kbsm.addKeyboardShortcut("Error",new KBShortcut<ActionEvent>(btnC, KeyCombination.valueOf("ctrl+c"),this::btnCClick));
    }

    @Override
    protected void onStageShowUser() {
        
    }
}
