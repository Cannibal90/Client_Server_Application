package network.program.model;

import network.program.common.Message;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Class responsible for sending messages and files. It is extended by Thread interface.
 * Communication is based on ObjectOutputStream
 */
public class Send extends Thread {
    ObjectOutputStream outputStream;
    List<Message> receivedList;
    String server_Path = "C:\\Users\\Testo\\Desktop\\STUDIA\\IV semestr\\PO2\\Server_Folder\\";

    /**
     * Creating object responsible for sending data
     * @param outputStream object representing stream to sending objects
     * @param receivedList list with received messages for processing
     */
    Send(ObjectOutputStream outputStream, List<Message> receivedList){
        this.outputStream = outputStream;
        this.receivedList = receivedList;
    }

    /**
     * This function reads messages from list, process them and next send to specified client.
     * Processed message is deleted from list
     */
    @Override
    public void run() {

        while (true){

            while (!receivedList.isEmpty()){

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //getting first message form list, setting Users list
                Message message = receivedList.get(0);
                List<String> Users = Collections.synchronizedList(new ArrayList<>());
                CommonObject.getUser_List().forEach(x->Users.add(x));
                message.setUser_List(Users);

                if(message.getMessageID() == 1){
                    //receiving initialize message
                    //if client doesn't exist, create new server folder for him
                    try {
                        String client_Directory = server_Path+"\\"+message.getUser_Login();
                        Files.createDirectory(Paths.get(client_Directory));
                    } catch (IOException ignore) {
                    }
                    CommonObject.getUser_List().add(message.getUser_Login());
                    message.setMessageID(2);
                    //difference betweend server client's files and local client files
                    message.FileDiff(1,server_Path);

                    try {
                        outputStream.writeObject(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                else if(message.getMessageID() == 2){
                    //downloading difference in files from local client folder to server client's folder
                    if(message.getFile_counter()>0){
                        String path = server_Path+message.getUser_Login()+"\\"+message.getServer_List().get(0).toString();
                        try{
                            FileOutputStream fos = new FileOutputStream(path);
                            fos.write(message.getBytes());
                            fos.close();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                        message.getServer_List().remove(0);
                    }

                    if(message.getFile_counter()==0){
                        message.FileDiff(2,server_Path);
                        message.setMessageID(3);
                        message.setStop_flag(1);
                    }else {
                        message.setFile_counter(message.getFile_counter()-1);
                    }

                    try {
                        outputStream.writeObject(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else if(message.getMessageID() == 3){
                    //sending  difference in files from server client's folder to local client folder
                    if(message.getFile_counter()>0){
                        String path = server_Path + message.getUser_Login() + "\\" + message.getServer_List().get(0).toString();
                        File file = new File(path);
                        try{
                            byte[] fileContent = Files.readAllBytes(file.toPath());
                            message.setBytes(fileContent);
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }

                    if(message.getFile_counter() == 0){
                        message.FileDiff(1,server_Path);
                        message.setMessageID(2);
                    }

                    try {
                        outputStream.writeObject(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else if(message.getMessageID() == 4){
                    //moving file on server from one client to another to share it between them
                    String path = server_Path + message.getUser_Login() + "\\" + message.getFile_name();
                    String path1 = server_Path+message.getShareUser()+"\\"+message.getFile_name();
                    File file = new File(path);
                    try{
                        byte[] fileContent = Files.readAllBytes(file.toPath());
                        FileOutputStream fos = new FileOutputStream(path1);
                        fos.write(fileContent);
                        fos.close();
                    }catch (IOException e){
                        e.printStackTrace();
                    }

                    message.setMessageID(3);
                    message.setStop_flag(1);
                    try {
                        outputStream.writeObject(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                //deleting processed message
                receivedList.remove(message);
            }

        }
        }
    }

