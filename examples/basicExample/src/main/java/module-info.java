module basicExample {
    requires javafx.controls;
    requires javafx.fxml;
    requires simplefx;
    opens io.github.ossnass.basicexample.gui to javafx.fxml;
    opens io.github.ossnass.basicexample to simplefx;
    exports io.github.ossnass.basicexample.app;
}