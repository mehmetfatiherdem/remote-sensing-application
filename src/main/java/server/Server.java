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
    private final ArrayList<String> tempMessages = new ArrayList<>();
    private final ArrayList<String> aliveMessages = new ArrayList<>();
    private final ArrayList<String> humidityMessages = new ArrayList<>();
    public Server() {

    }

    public static Server getInstance() throws IOException {
        if (instance == null) {
            instance = new Server();
        }
        return instance;
    }

    public ArrayList<String> getTempMessages() {
        return tempMessages;
    }

    public ArrayList<String> getAliveMessages() {
        return aliveMessages;
    }

    public ArrayList<String> getHumidityMessages() {
        return humidityMessages;
    }

    public void addTempMessage(String msg){
        tempMessages.add(msg);
    }
    public void addAliveMessage(String msg){
        aliveMessages.add(msg);
    }
    public void addHumidityMessage(String msg){
        humidityMessages.add(msg);
    }
}
