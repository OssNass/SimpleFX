module simplefx {
    exports io.github.ossnass.fx;
    requires javafx.fxml;
    requires javafx.controls;
    requires io.github.classgraph;
    opens io.github.ossnass.fx to javafx.fxml;
}