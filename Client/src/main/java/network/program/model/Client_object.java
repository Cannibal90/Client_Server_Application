package network.program.model;

import network.program.common.Message;

import java.io.*;
import java.net.Socket;
import java.util.*;

/**
 * This class implements a handle for Client user which is extended by Thread Interface.
 * This class is responsible for creating and managing the client.
 */
public class Client_object extends Thread implements Serializable {
    int port;
    String user_Login;

    /**
     * This function is a constructior. It set a specified port for socket
     * @param port number of specified port for socket connection
     */
    public Client_object(int port) {
        this.port = port;
    }

    /**
     * Sets login for new client
     * @param user_Login login user's nick
     */
    public void setUser_Login(String user_Login) {
        this.user_Login = user_Login;
    }

    /**
     * Sets specified port for Socket
     * @param port number of specified port for socket connection
     */
    public void setPort(int port) {
        this.port = port;
    }


    /**
     * This function turns on main thread which are responsible for creating Socket,
     * setting specified variables, sending and receiving data between server and client
     */
    @Override
    public void run() {
        try{
            //connecting with server
            Socket client_Socket = new Socket("localhost",port);
            //creating input and output for client
            ObjectOutputStream output_Stream = new ObjectOutputStream(client_Socket.getOutputStream());
            ObjectInputStream input_Stream = new ObjectInputStream(client_Socket.getInputStream());
            List<Message> receivedList = Collections.synchronizedList(new ArrayList<>());

            //creating first initialization message
            Message commonMessage = new Message(CommonObject.user_Login, CommonObject.user_Path);
            commonMessage.setMessageID(1);

            //setting up a local user list
            File dir = new File(CommonObject.user_Path);
            File[] files = dir.listFiles();
            for(int i=0;i<files.length;i++)
            {
                files[i] = new File(files[i].getName());
            }
            commonMessage.setFiles_List(Arrays.asList(files));
            receivedList.add(commonMessage);

            //run threads responsible for receiving and sending messages
            Thread client_receive = new Receive(input_Stream,receivedList);
            client_receive.setDaemon(true);
            Thread client_send = new Send(output_Stream,receivedList);
            client_send.setDaemon(true);


            client_send.start();
            client_receive.start();

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
