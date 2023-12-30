package main.java.humidity;

import main.java.utils.Helpers;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class HumiditySendLastValueHandler extends Thread {

    // UDP w/gateway
    private HumiditySensor sensor;
    private DatagramSocket socket;
    private byte[] bufIn = new byte[65535];
    private byte[] bufOut;
    private final InetAddress address;
    private final int port;


    public HumiditySendLastValueHandler(HumiditySensor sensor, DatagramSocket socket, InetAddress address, int port) {
        this.sensor = sensor;
        this.socket = socket;
        this.address = address;
        this.port = port;
    }

    public void run() {

        while (true) {

            DatagramPacket packetIn = new DatagramPacket(bufIn, bufIn.length);


            try {
                socket.receive(packetIn);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            var msg = Helpers.ByteToStr(bufIn);

            var msgArr = msg.toString().split(" ");

            if(msgArr[0].equals("GET") && msgArr[1].equals("LAST") && msgArr[2].equals("HUMIDITY")){
                // request last measured humidity from the humidity sensor

                bufOut = sensor.generateLastValueMessage().getLastHumidityByteArr();
                DatagramPacket packetOut = new DatagramPacket(bufOut, bufOut.length, address, port);

                try {
                    socket.send(packetOut);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }


            bufIn = new byte[65535];


        }

    }

}