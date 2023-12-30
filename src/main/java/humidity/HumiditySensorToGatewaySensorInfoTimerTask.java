package main.java.humidity;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class HumiditySensorToGatewaySensorInfoTimerTask implements Runnable{
    private HumiditySensor humiditySensor;
    private DatagramSocket socket;
    private InetAddress address;
    private int port;
    private byte[] buf;
    public HumiditySensorToGatewaySensorInfoTimerTask(HumiditySensor humiditySensor, DatagramSocket socket, InetAddress address, int port){
        this.humiditySensor = humiditySensor;
        this.socket = socket;
        this.address = address;
        this.port = port;
    }
    @Override
    public void run() {
        buf = humiditySensor.generateInfoMessage().sendUDPSensorInfo();

        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);
        try {
            socket.send(packet);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
