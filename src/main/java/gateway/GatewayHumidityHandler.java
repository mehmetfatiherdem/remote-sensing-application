package main.java.gateway;

import main.java.utils.Helpers;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;


public class GatewayHumidityHandler extends Thread{
    private DatagramSocket socket;
    private byte[] buf = new byte[65535];

    public GatewayHumidityHandler(int port) throws SocketException {
        socket = new DatagramSocket(port);
        System.out.println("Gateway datagram socket waiting for UDP sensor client at port " + socket.getLocalPort());
    }

    public void run() {

        while (true) {
            DatagramPacket packet = new DatagramPacket(buf, buf.length);

            try {
                socket.receive(packet);
                System.out.println(Helpers.ByteToStr(buf));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            buf = new byte[65535];

            //send to serve logic here??
        }
        //TODO: socket close here?
    }

}
