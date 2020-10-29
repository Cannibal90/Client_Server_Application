package network.program.model;

import network.program.common.Message;

import java.io.File;
import java.util.List;

/**
 * Helping class imitating Message class. It is used to setting ora getting variables from API
 *
 */
public class CommonObject {


    public static List<Message> receivedList;
    public static int messageID;
    public static int labelInfo;
    public static List<File> files_List;
    public static List<String> user_List;
    public static List<File> Server_List;
    public static String user_Login;
    public static String user_Path;
    public static byte[] bytes;
    public static String shareUser;
    public static String File_name;

    /**
     * Creating object used to setting and getting API variables
     * @param user_Login client user's nick
     * @param user_Path path to local client folder
     */
    public CommonObject(String user_Login,String user_Path){
        this.user_Path = user_Path;
        this.user_Login = user_Login;
    }

    public static int getLabelInfo() {
        return labelInfo;
    }

    public static void setLabelInfo(int labelInfo) {
        CommonObject.labelInfo = labelInfo;
    }

    public static List<Message> getReceivedList() {
        return receivedList;
    }

    public static void setReceivedList(List<Message> receivedList) {
        CommonObject.receivedList = receivedList;
    }
    public static String getShareUser() {
        return shareUser;
    }

    public static void setShareUser(String shareUser) {
        CommonObject.shareUser = shareUser;
    }

    public static String getFile_name() {
        return File_name;
    }

    public static void setFile_name(String file_name) {
        File_name = file_name;
    }

    public static List<File> getServer_List() {
        return Server_List;
    }

    public static void setServer_List(List<File> server_List) {
        Server_List = server_List;
    }

    public static int getMessageID() {
        return messageID;
    }

    public static void setMessageID(int messageID) {
        CommonObject.messageID = messageID;
    }

    public static List<File> getFiles_List() {
        return files_List;
    }

    public static void setFiles_List(List<File> files_List) {
        CommonObject.files_List = files_List;
    }

    public static List<String> getUser_List() {
        return user_List;
    }

    public static void setUser_List(List<String> user_List) {
        CommonObject.user_List = user_List;
    }
    public static String getUser_Login() {
        return user_Login;
    }

    public static void setUser_Login(String user_Login) {
        CommonObject.user_Login = user_Login;
    }

    public static String getUser_Path() {
        return user_Path;
    }

    public static void setUser_Path(String user_Path) {
        CommonObject.user_Path = user_Path;
    }

    public static byte[] getBytes() {
        return bytes;
    }

    public static void setBytes(byte[] bytes) {
        CommonObject.bytes = bytes;
    }
}
