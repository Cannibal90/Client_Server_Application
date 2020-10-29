package network.program.model;
import network.program.common.Message;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.util.*;

/**
 * Class responsible for sending messages and files. It is extended by Thread interface.
 * Communication is based on ObjectOutputStream
 */
public class Send extends Thread {
    ObjectOutputStream outputStream;
    List<Message> receivedList;

    /**
     * Creating object responsible for sending data
     * @param outputStream object representing stream to sending objects
     * @param receivedList list with received messages for processing
     */
    public Send(ObjectOutputStream outputStream, List<Message> receivedList){
        this.outputStream = outputStream;
        this.receivedList = receivedList;
    }

    /**
     * This function reads messages from list, process them and next send to server.
     * Processed message is deleted from list
     */

    @Override
    public void run() {
        while(true) {


            while (!receivedList.isEmpty()){

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //getting first message from list, getting user list and variable
                // responsible for changing label
                Message message = receivedList.get(0);
                CommonObject.setUser_List(message.getUser_List());
                CommonObject.setLabelInfo(message.getMessageID());
                if(message.getMessageID() == 1){
                    //sending initialize message
                    try {
                        outputStream.writeObject(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else if(message.getMessageID() == 2){
                    //sending difference in files from local client folder to server client's folder
                    if(message.getFile_counter()>0){

                        String path = message.getUser_Path() +"\\"+message.getServer_List().get(0).toString();
                        File file = new File(path);
                        try{
                            byte[] fileContent = Files.readAllBytes(file.toPath());
                            message.setBytes(fileContent);
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }

                    try {
                        outputStream.writeObject(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else if(message.getMessageID() == 3){
                    //downloading difference in files from server client's folder
                    //to local client folder
                    if(message.getFile_counter()>0 && message.getStop_flag()!=1){
                        String path = message.getUser_Path()+"\\"+message.getServer_List().get(0).toString();
                        try{
                            FileOutputStream fos = new FileOutputStream(path);
                            fos.write(message.getBytes());
                            fos.close();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                        message.getServer_List().remove(0);
                        message.setFile_counter(message.getFile_counter()-1);
                    }
                    else{ message.setStop_flag(0); }

                    message.setFiles_List(CommonObject.getFiles_List());
                    try {
                        outputStream.writeObject(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if(CommonObject.getMessageID()==4){
                    //sending message responsible for sharing files between two clients
                    Message mess = new Message(CommonObject.getUser_Login(),CommonObject.getUser_Path());
                    mess.setMessageID(4);
                    mess.setFiles_List(CommonObject.getFiles_List());
                    mess.setUser_List(CommonObject.getUser_List());
                    mess.setFile_name(CommonObject.getFile_name());
                    mess.setShareUser(CommonObject.getShareUser());
                    CommonObject.setMessageID(0);

                    try {
                        outputStream.writeObject(mess);
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
