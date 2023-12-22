package main.java.gateway;
import java.net.*;
import java.io.*;

public class GatewayTempHandler extends Thread {
    private Socket socket;
    private ServerSocket server;
    private DataInputStream in;
    public GatewayTempHandler(int port) throws IOException {
        server = new ServerSocket(port);

        System.out.println("Gateway Waiting for a TCP client at port " + server.getLocalPort());
    }

    public void run(){

        try {
            socket = server.accept();
            System.out.println("Gateway accepted TCP Client at port " + server.getLocalPort());


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