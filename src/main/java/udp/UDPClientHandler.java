package main.java.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;


public class UDPClientHandler extends Thread{
    private DatagramSocket socket;
    private byte[] buf = new byte[65535];

    public UDPClientHandler(int port) throws SocketException {
        socket = new DatagramSocket(port);
        System.out.println("UDP server init at port " + socket.getLocalPort());
    }

    public void run() {

        while (true) {
            DatagramPacket packet = new DatagramPacket(buf, buf.length);

            try {
                socket.receive(packet);
                System.out.println(byteToStr(buf));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            buf = new byte[65535];

            //send to serve logic here??
        }
        //TODO: socket close here?
    }

    public StringBuilder byteToStr(byte[] a) {
        if (a == null) return null;

        StringBuilder ret = new StringBuilder();

        int i = 0;
        while(a[i] != 0){
            ret.append((char)a[i++]);
        }

        return ret;
    }

}
