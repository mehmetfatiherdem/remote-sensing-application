package main.java.gateway;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

public class GatewayRequestLastHumidityHandler extends Thread {

    // TCP w/Server
    private Socket tcpSendToServerSocket;

    private DataInputStream serverIn;

    // UDP w/sensor
    private DatagramSocket socket;
    private byte[] buf;
    private final InetAddress address;
    private int port;


    public GatewayRequestLastHumidityHandler(DatagramSocket socket, Socket tcpSendToServerSocket, InetAddress address, int port) {
        this.socket = socket;
        this.tcpSendToServerSocket = tcpSendToServerSocket;
        this.address = address;
        this.port = port;
    }

    public void run() {

        try {

            serverIn = new DataInputStream(new BufferedInputStream(tcpSendToServerSocket.getInputStream()));

            String msg;

            while (true) {


                try {

                    msg = serverIn.readUTF();

                    String[] msgArr = msg.split(" ");

                    if(msgArr[0].equals("GET") && msgArr[1].equals("LAST") && msgArr[2].equals("HUMIDITY")){
                        // request last measured humidity from the humidity sensor
                        buf = msg.getBytes();
                        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);

                        socket.send(packet);
                    }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }



                buf = new byte[65535];

                //send to serve logic here??
            }

            //TODO: socket close here?

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
