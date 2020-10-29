package network.program.model;

import network.program.common.Message;

import java.io.*;
import java.util.List;

/**
 * Class responsible for receiving messages. It is extended by Thread interface.
 * Communication is based on ObjectInputStream
 */
public class Receive extends Thread {
    List<Message> receivedList;
    ObjectInputStream inputStream;

    /**
     * Creating object responsible for receiving data
     * @param inputStream Object stream input for specified client. It's responsible for receiving message form client
     * @param receivedList list which stores messages from client
     */
    public Receive(ObjectInputStream inputStream, List<Message> receivedList) {
        this.inputStream = inputStream;
        this.receivedList = receivedList;
    }

    /**
     * Main thread class receiving messages from client and adding them to list
     */
    @Override
    public void run() {
        while(true){
            try {
                Message receivedMessage = (Message) inputStream.readObject();
                receivedList.add(receivedMessage);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
