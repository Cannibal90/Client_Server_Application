package network.program.model;

import network.program.common.Message;

import java.io.*;

import java.util.List;

/**
 * Class responsible for receiving messages. It is extended by Thread interface.
 * Communication is based on ObjectInputStream
 */
public class Receive extends Thread {
    ObjectInputStream inputStream;
    List<Message> synch_list;

    /**
     * Creating object responsible for receiving data
     * @param inputStream Object stream input for specified client. It's responsible for receiving message form server
     * @param synch_list list which stores messages from server
     */
    public Receive(ObjectInputStream inputStream, List<Message> synch_list){
        this.synch_list = synch_list;
        this.inputStream = inputStream;
    }

    /**
     * Main thread class receiving messages from server and adding them to list
     */
    @Override
    public void run() {
        while(true){
            try{
                Message receivedMessage = (Message) inputStream.readObject();
                synch_list.add(receivedMessage);
            }catch (IOException | ClassNotFoundException e){
                e.printStackTrace();
            }
        }

    }

    }

