package main.java.server;

import java.io.IOException;
import java.util.ArrayList;

public class Server {
    private static Server instance;

    /*
        TODO: Well the PDF says all the values will be stored in the server
        TODO: am not sure if we should just use field for this or a txt file
        TODO: so for now lets keep this
     */
    private final ArrayList<String> messages = new ArrayList<>();
    public Server() {

    }

    public static Server getInstance() throws IOException {
        if (instance == null) {
            instance = new Server();
        }
        return instance;
    }

    public ArrayList<String> getMessages() {
        return messages;
    }
    public void storeMessage(String msg){
        this.messages.add(msg);
    }
}
