module Server {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    exports network.program.apt to javafx.graphics;
    opens network.program.controller to javafx.fxml;
}