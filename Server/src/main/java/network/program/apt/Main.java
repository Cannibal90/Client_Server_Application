package network.program.apt;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import network.program.controller.Application_view_controller;
import network.program.model.CommonObject;
import network.program.model.Server_object;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Main extends Application {
    public static void main(String[] args) {

        Server_object server_object = new Server_object(1235);
        List<String> newL = Collections.synchronizedList(new ArrayList<>());
        CommonObject x = new CommonObject(newL);

        Thread server = new Thread(server_object);
        server.setDaemon(true);
        server.start();
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader handller = new FXMLLoader(getClass().getResource("/application_view.fxml"));
        Pane view = handller.load();
        Application_view_controller controller = handller.getController();
        controller.setFile_label("Files");
        controller.setUser_label("Users");
        Scene scene = new Scene(view);
        stage.setScene(scene);
        stage.setTitle("Server_application");
        stage.show();

    }
}
