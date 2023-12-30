package main.java.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerRequestLastHumidityHandler extends Thread{

    // TCP w/Gateway
    private Socket socket;
    private DataOutputStream out;


    public ServerRequestLastHumidityHandler(Socket socket) {
        this.socket = socket;

    }

    public void run(){

        try {

            // create byte stream to request last humidity value from the gateway
            out = new DataOutputStream(socket.getOutputStream());

            out.writeUTF("GET LAST HUMIDITY");

        }
        catch(IOException i) {
            System.out.println(i);
        }
    }
}
