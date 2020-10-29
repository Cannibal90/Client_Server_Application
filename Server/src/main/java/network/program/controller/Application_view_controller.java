package network.program.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import network.program.model.CommonObject;

import java.io.*;

public class Application_view_controller {

    public void setFile_label(String file_label) {
        File_label.setText(file_label);
    }

    public void setUser_label(String user_label) {
        User_label.setText(user_label);
    }

    @FXML
    private Label File_label;

    @FXML
    private Label User_label;

    @FXML
    private ListView<File> Files;

    @FXML
    private ListView<String> Users;

    @FXML
    private Button Exit;

    private void refreshUsers() {

        while(true){
            Platform.runLater(()->{
                try {
                    ObservableList<String> list = FXCollections.observableArrayList(CommonObject.getUser_List());
                    Users.setItems(list);
                } catch (NullPointerException ignored) {}
            });
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void initialize(){
        Thread refreshThread = new Thread(this::refreshUsers);
        refreshThread.start();
        Exit.addEventFilter(ActionEvent.ACTION, actionEvent -> {
            Platform.exit();
            System.exit(0);

        });

        Users.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            String path = "C:\\Users\\Testo\\Desktop\\STUDIA\\IV semestr\\PO2\\Server_Folder\\";
            if(Users.getSelectionModel().getSelectedItem()!=null)
            {
                String selectedPath = path + Users.getSelectionModel().getSelectedItem();
                File dir = new File(selectedPath);
                File[] files = dir.listFiles();
                for(int i=0;i<files.length;i++)
                {
                    files[i] = new File(files[i].getName());
                }
                ObservableList<File> f = FXCollections.observableArrayList(files);

                Files.setItems(f);
            }
        });
    }
}
