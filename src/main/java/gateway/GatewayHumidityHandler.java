package main.java.gateway;

import main.java.utils.Helpers;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;

import static main.java.AdvancedLogger.*;


public class GatewayHumidityHandler extends Thread {

    // TCP w/Server
    private Socket tcpSendToServerSocket;
    private DataOutputStream serverOut;

    // UDP w/sensor
    private DatagramSocket socket;
    private byte[] buf = new byte[65535];

    public GatewayHumidityHandler(DatagramSocket socket, Socket tcpSendToServerSocket) {
        this.socket = socket;
        this.tcpSendToServerSocket = tcpSendToServerSocket;
    }

    public void run() {

        try {
            // create byte stream to send info to the server about the sensors
            serverOut = new DataOutputStream(tcpSendToServerSocket.getOutputStream());

            while (true) {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);

                try {
                    socket.receive(packet);
                } catch (IOException e) {
                    logException(e);
                    throw new RuntimeException(e);

                }

                String str = Helpers.ByteToStr(buf).toString();

                serverOut.writeUTF(str);
                logInfo(str);


                buf = new byte[65535];

                //send to serve logic here??
            }

            //TODO: socket close here?

        } catch (IOException e) {
            logException(e);
            throw new RuntimeException(e);
        }


    }
}
