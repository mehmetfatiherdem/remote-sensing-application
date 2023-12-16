package main.java;

import java.io.IOException;
import java.net.*;
import java.util.Timer;
import java.util.TimerTask;

public class UDPClient {
    private DatagramSocket socket;
    private InetAddress address;
    private int port;
    private Sensor sensor;
    private byte[] buf;

    public UDPClient(Sensor sensor, InetAddress address, int port) throws SocketException, UnknownHostException {
        this.port = port;
        socket = new DatagramSocket();
        this.address = address;
        this.sensor = sensor;
    }


    public void sendMessage() {
        TimerTask sendValueTask = new TimerTask() {
            @Override
            public void run() {

                var humidity = sensor.generateMessage();

                if (((HumiditySensor)sensor).isGreaterThanThreshold(humidity.getVal())) {

                    buf = humidity.getByteArr();

                    DatagramPacket packet
                            = new DatagramPacket(buf, buf.length, address, port);
                    try {
                        socket.send(packet);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

        };
        TimerTask sendAliveTask = new TimerTask() {
            @Override
            public void run() {
                buf = ((HumiditySensor)sensor).generateAliveMessage().getByteArr();

                DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);
                try {
                    socket.send(packet);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        };


        Timer timer = new Timer();
        timer.schedule(sendAliveTask, 0, 3000);
        timer.schedule(sendValueTask, 0, 1000);



        //TODO: socket close logic??

    }


}
