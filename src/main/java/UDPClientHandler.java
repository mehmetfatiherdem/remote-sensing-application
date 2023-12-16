package main.java;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.Date;

public class UDPClientHandler extends Thread{
    private DatagramSocket socket;
    private byte[] buf = new byte[65535];

    public UDPClientHandler(int port) throws SocketException {
        socket = new DatagramSocket(port);
        System.out.println("udp server init at port " + socket.getLocalPort());
    }

    public void run() {

        while (true) {
            DatagramPacket packet
                    = new DatagramPacket(buf, buf.length);
            try {
                socket.receive(packet);
                System.out.println("Humidity sensor: " + byteToStr(buf) + " at " + new Date() + " at port " + socket.getLocalPort());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            buf = new byte[65535];

            //send to serve logic here??
        }
        //TODO: socket close here?
    }

    //FIXME: This is intimidating
    public StringBuilder byteToStr(byte[] a) {
        if (a == null) return null;
        StringBuilder ret = new StringBuilder();
        int byteArrVal = ByteBuffer.wrap(a).getInt();
        if(byteArrVal == -1)
            ret.append("ALIVE");
        else
            ret.append(byteArrVal);
        return ret;
    }

}
