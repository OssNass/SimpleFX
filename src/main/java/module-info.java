module simplefx {
    exports io.github.ossnass.fx;
    requires javafx.fxml;
    requires javafx.controls;
    opens io.github.ossnass.fx to javafx.fxml;
}