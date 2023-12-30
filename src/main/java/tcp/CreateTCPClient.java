package main.java.tcp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import static main.java.AdvancedLogger.logException;

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
            logException(e);
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
