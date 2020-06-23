module simplefx {
    exports io.github.ossnass.fx;
    exports io.github.ossnass.fx.exceptions;
    exports io.github.ossnass.fx.keyboard;
    exports io.github.ossnass.fx.settings;
    requires javafx.fxml;
    requires javafx.controls;
    requires io.github.classgraph;
    opens io.github.ossnass.fx to javafx.fxml;
}