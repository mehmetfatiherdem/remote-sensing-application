package main.java;// A Java program for a Server
import java.net.*;
import java.io.*;

public class TCPClientHandler extends Thread {
    //initialize socket and input stream
    private Socket socket;
    private ServerSocket server;
    private DataInputStream in;
    public TCPClientHandler(int port) throws IOException {
        server = new ServerSocket(port);
        System.out.println("Server started");

        System.out.println("Waiting for a client ...");
    }

    public void run(){
        // starts server and waits for a connection
        try {
            socket = server.accept();
            System.out.println("Client accepted");

            // takes input from the client socket
            in = new DataInputStream(
                    new BufferedInputStream(socket.getInputStream()));

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

    // constructor with port


}