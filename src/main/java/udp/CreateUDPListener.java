package main.java.udp;

import java.net.DatagramSocket;
import java.net.SocketException;

import static main.java.AdvancedLogger.logException;

public class CreateUDPListener extends Thread{
    private DatagramSocket socket;
    private int port;

    public CreateUDPListener(int port) {
        this.port = port;

    }

    public void run() {

        try {
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
            logException(e);
            throw new RuntimeException(e);
        }
    }

    public DatagramSocket getSocket() {
        return socket;
    }

    public int getPort() {
        return port;
    }
}
