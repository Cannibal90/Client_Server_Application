package network.program.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import network.program.model.CommonObject;
import java.io.File;
import java.util.Arrays;


public class Application_view_controller {

    public void setFile_label(String file_label) {
        File_label.setText(file_label);
    }

    public void setUser_label(String user_label) {
        User_label.setText(user_label);
    }

    public void setOperation(String operation) {
        Operation.setText(operation);
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
    private Button Share;

    @FXML
    private Button Exit;

    @FXML
    private Label Operation;

    @FXML
    private Label User_name;

    private void refreshUsers() {
        while(true){
            Platform.runLater(()->{
                try {
                    ObservableList<String> list = FXCollections.observableArrayList(CommonObject.getUser_List());
                    Users.setItems(list);
                }catch (NullPointerException ignored){}
            });

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    private void refreshFiles(){
        while(true){
            Platform.runLater(()->{
                File dir = new File(CommonObject.user_Path);
                File[] files = dir.listFiles();
                for(int i=0;i<files.length;i++)
                {
                    files[i] = new File(files[i].getName());
                }
                ObservableList<File> f = FXCollections.observableArrayList(files);
                CommonObject.setFiles_List(Arrays.asList(files));
                Files.setItems(f);

                if(CommonObject.getLabelInfo() == 2){
                    Operation.setText("Sending...");
                }
                else if(CommonObject.getLabelInfo() == 3){
                    Operation.setText("Downloading...");
                }
                else{
                    Operation.setText("Checking...");
                }
            });
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void initialize(){
        User_name.setText(CommonObject.getUser_Login());
        Thread refreshThread = new Thread(this::refreshUsers);
        Thread refreshFileThread = new Thread(this::refreshFiles);
        refreshThread.start();
        refreshFileThread.start();

        //Exit button
        Exit.addEventFilter(ActionEvent.ACTION, actionEvent -> {
            Platform.exit();
            System.exit(0);
        });

        //Share button

        Share.addEventFilter(ActionEvent.ACTION, actionEvent -> {
            if(Users.getSelectionModel().getSelectedItem()!=null && Files.getSelectionModel().getSelectedItem()!=null && Users.getSelectionModel().getSelectedItem().toString()!=CommonObject.getUser_Login()){
                CommonObject.setMessageID(4);
                CommonObject.setShareUser(Users.getSelectionModel().getSelectedItem());
                CommonObject.setFile_name(Files.getSelectionModel().getSelectedItem().toString());
            }
        });
    }
}
