package main.java.udp;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
public class CreateUDPSender extends Thread{
    private DatagramSocket socket;
    private InetAddress address;
    private int port;

    public CreateUDPSender(InetAddress address, int port) {
        this.port = port;
        this.address = address;
    }


    public void run() {
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    public DatagramSocket getSocket() {
        return socket;
    }

    public InetAddress getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }
}
