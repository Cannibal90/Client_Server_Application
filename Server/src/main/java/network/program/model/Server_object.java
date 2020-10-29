package network.program.model;

import network.program.common.Message;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class implements a handle for Server which is extended by Thread Interface.
 * This class is responsible for creating serverSocket and Socket for each client user.
 */
public class Server_object extends Thread implements Serializable  {
    int port;

    /**
     * This function is a constructior. It set a specified port for socket
     * @param port number of specified port for socket connection
     */
    public Server_object(int port) {
        this.port = port;
    }

    /**
     * This function turns on main thread which are responsible for creating Socket,
     * setting specified variables, sending and receiving data between server and clients
     */
    @Override
    public void run()  {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            while(true){
                //accepting new client
                Socket client_Socket = serverSocket.accept();
                //creating objects for client
                ObjectInputStream client_Input = new ObjectInputStream(client_Socket.getInputStream());
                ObjectOutputStream client_Output = new ObjectOutputStream(client_Socket.getOutputStream());
                List<Message> receivedList = Collections.synchronizedList(new ArrayList<>());

                //run threads responsible for receiving and sending messages for each client
                Thread client_receive = new Receive(client_Input,receivedList);
                client_receive.setDaemon(true);

                Thread client_send = new Send(client_Output,receivedList);
                client_send.setDaemon(true);


                client_receive.start();
                client_send.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
