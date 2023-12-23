package main.java.tcp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class CreateTCPClient extends Thread{
    private Socket socket;
    private InetAddress address;
    private int port;
    public CreateTCPClient(InetAddress address, int port) {
        this.address = address;
        this.port = port;
    }

    public void run(){

        try {
            socket = new Socket(address, port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public Socket getSocket() {
        return socket;
    }

    public InetAddress getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }
}
