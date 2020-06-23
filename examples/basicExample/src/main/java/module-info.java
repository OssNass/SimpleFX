module basicExample {
    requires javafx.controls;
    requires javafx.fxml;
    requires simplefx;
    opens io.github.ossnass.languageexample.gui to javafx.fxml;
    opens io.github.ossnass.languageexample to simplefx;
    exports io.github.ossnass.basicexample.app;
}