package main.java.tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class CreateTCPServer extends Thread{
    private Socket socket;
    private ServerSocket server;
    private int port;

    public CreateTCPServer(int port){
        this.port = port;
    }

    public void run(){
        try{
            server = new ServerSocket(port);
            socket = server.accept();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public ServerSocket getServer() {
        return server;
    }

    public int getPort() {
        return port;
    }
}
