package main.java.server;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerCommunicationHandler extends Thread{

    // TCP w/Gateway
    private Socket socket;
    private ServerSocket server;
    private DataInputStream in;
    private DataOutputStream out;

    public ServerCommunicationHandler(Socket socket) {
        this.socket = socket;
    }

    public void run(){

        try {

            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

            String line;

            while (true) {
                try {
                    line = in.readUTF();
                    System.out.println(line);

                }
                catch(IOException i) {
                    System.out.println(i);
                }
            }

        }
        catch(IOException i) {
            System.out.println(i);
        }
    }
}
