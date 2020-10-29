package network.program.common;

import java.io.File;
import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Class which is used to sending data between client and server.
 * It implements Serializable interface
 */
public class Message implements Serializable {


    int messageID;
    List<File> files_List;
    List<File> Server_List;
    List<String> user_List;
    String user_Login;
    String user_Path;
    byte[] bytes;
    int File_counter;
    String File_name;
    String shareUser;
    int stop_flag;

    /**
     * Creating object responsible for storing information
     * @param user_Login login user's nick
     * @param user_Path Path of local user files
     */
    public Message(String user_Login, String user_Path){
        this.user_Login = user_Login;
        this.user_Path = user_Path;
    }

    public String getShareUser() {
        return shareUser;
    }

    public void setShareUser(String shareUser) {
        this.shareUser = shareUser;
    }

    public int getStop_flag() {
        return stop_flag;
    }

    public void setStop_flag(int stop_flag) {
        this.stop_flag = stop_flag;
    }

    public int getFile_counter() {
        return File_counter;
    }

    public void setFile_counter(int file_counter) {
        File_counter = file_counter;
    }

    public String getFile_name() {
        return File_name;
    }

    public void setFile_name(String file_name) {
        File_name = file_name;
    }

    public List<File> getServer_List() {
        return Server_List;
    }

    public void setServer_List(List<File> server_List) {
        Server_List = server_List;
    }

    public int getMessageID() {
        return messageID;
    }

    public void setMessageID(int messageID) {
        this.messageID = messageID;
    }

    public List<File> getFiles_List() {
        return files_List;
    }

    public void setFiles_List(List<File> files_List) {
        this.files_List = files_List;
    }

    public List<String> getUser_List() {
        return user_List;
    }

    public void setUser_List(List<String> user_List) {
        this.user_List = user_List;
    }

    public String getUser_Login() {
        return user_Login;
    }

    public void setUser_Login(String user_Login) {
        this.user_Login = user_Login;
    }

    public String getUser_Path() {
        return user_Path;
    }

    public void setUser_Path(String user_Path) {
        this.user_Path = user_Path;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }


    /**
     * Function which calculate difference between client and server file lists
     * @param x variable responsible for choosing right calculation
     * @param Path path to server files
     */
    public void FileDiff(int x, String Path){

        String clientPath = Path + user_Login;
        File dir = new File(clientPath);
        File[] files = dir.listFiles();
        for(int i=0;i<files.length;i++)
        {
            files[i] = new File(files[i].getName());
        }

        List<File> newServerList = new LinkedList<File>(Arrays.asList(files));
        List<File> newClientList = new LinkedList<File>(files_List);


        //for server - local client files - server client's files
        if(x == 1){
            newClientList.removeAll(newServerList);
            Server_List = newClientList;
            File_counter = newClientList.size();
        }
        else{//for client - server client's files - local client files
            newServerList.removeAll(newClientList);
            Server_List = newServerList;
            File_counter = newServerList.size();
        }
    }
}
