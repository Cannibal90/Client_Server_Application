package network.program.apt;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import network.program.controller.Application_view_controller;
import network.program.model.Client_object;
import network.program.model.CommonObject;



public class Main extends Application {
    public static void main(String[] args) {

        String path = "C:\\Users\\Testo\\Desktop\\STUDIA\\IV semestr\\PO2\\Local_Folder\\"+args[0];
        CommonObject arg = new CommonObject(args[0],path);

        Client_object client = new Client_object(1235);
        Thread clientThread = new Thread(client);
        clientThread.setDaemon(true);
        clientThread.start();

        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader handller = new FXMLLoader(getClass().getResource("/application_view.fxml"));
        Pane view = handller.load();
        Application_view_controller controller = handller.getController();
        controller.setFile_label("Files");
        controller.setUser_label("Users");
        controller.setOperation("Checking...");
        Scene scene = new Scene(view);
        stage.setScene(scene);
        stage.setTitle("Client_application");
        stage.show();
    }
}
