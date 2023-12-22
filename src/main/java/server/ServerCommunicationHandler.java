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

    public ServerCommunicationHandler(int port) throws IOException {

        server = new ServerSocket(port);
        System.out.println("Server waiting for a TCP Gateway at port " + server.getLocalPort());

        // Gateway connected to Server
        socket = server.accept();
        System.out.println("Server accepted the gateway with TCP at " + server.getLocalPort());

    }

    public void run(){

        try {

            // temperature sensor is connected to the gateway
            socket = server.accept();
            System.out.println("Gateway accepted a TCP Sensor at port " + server.getLocalPort());

            // send info to the server about the sensors
            out = new DataOutputStream(socket.getOutputStream());

            // create a task



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
