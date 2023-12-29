package main.java.humidity;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class HumiditySensorToGatewaySensorInfoTimerTask implements Runnable{
    private HumiditySensor sensor;
    private DatagramSocket socket;
    private InetAddress address;
    private int port;
    private byte[] buf;
    public HumiditySensorToGatewaySensorInfoTimerTask(HumiditySensor sensor, DatagramSocket socket, InetAddress address, int port){
        this.sensor = sensor;
        this.socket = socket;
        this.address = address;
        this.port = port;
    }
    @Override
    public void run() {
        buf = sensor.generateInfoMessage().sendUDPSensorInfo();

        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);
        try {
            socket.send(packet);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
