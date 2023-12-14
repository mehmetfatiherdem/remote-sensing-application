package main.java;

import java.io.IOException;
import java.net.*;
import java.util.Timer;
import java.util.TimerTask;

public class UDPClient {
    private DatagramSocket socket;
    private InetAddress address;
    private byte[] buf;
    private HumiditySensor humiditySensor;

    public UDPClient(HumiditySensor humiditySensor) throws SocketException, UnknownHostException {
        socket = new DatagramSocket();
        address = InetAddress.getByName("localhost");
        this.humiditySensor = humiditySensor;
    }

    public void sendMessage() throws IOException {
        TimerTask sendValueTask = new TimerTask() {
            @Override
            public void run() {

                int humidity = humiditySensor.generateHumidity();

                if(humiditySensor.isGreaterThanThreshold(humidity)){
                    // like I wasn't virgin enough :_(
                    buf = new byte[]{
                            (byte)((humidity & 0xFF000000) >> 24),
                            (byte)((humidity & 0x00FF0000) >> 16),
                            (byte)((humidity & 0x0000FF00) >> 8),
                            (byte)((humidity & 0x000000FF)),
                    };

                    DatagramPacket packet
                            = new DatagramPacket(buf, buf.length, address, 4445);
                    try {
                        socket.send(packet);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    packet = new DatagramPacket(buf, buf.length);
                    try {
                        socket.receive(packet);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    String received = new String(
                            packet.getData(), 0, packet.getLength());
                }


            }
        };

        TimerTask sendAliveTask = new TimerTask() {
            @Override
            public void run() {

                buf = humiditySensor.generateAliveMessage().getBytes();

                DatagramPacket packet
                        = new DatagramPacket(buf, buf.length, address, 4445);
                try {
                    socket.send(packet);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                packet = new DatagramPacket(buf, buf.length);
                try {
                    socket.receive(packet);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                String received = new String(
                        packet.getData(), 0, packet.getLength());
            }
        };



        Timer humidityTimer = new Timer();
        humidityTimer.schedule(sendValueTask, 0, 1000);
        humidityTimer.schedule(sendAliveTask, 0, 3000);

    }

    //TODO: socket close logic??


}
