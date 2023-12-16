package main.java;

import java.io.IOException;
import java.net.*;
import java.util.Timer;
import java.util.TimerTask;

public class UDPClient {
    private DatagramSocket socket;
    private InetAddress address;
    private int port;
    private byte[] buf;
    private HumiditySensor humiditySensor;

    public UDPClient(HumiditySensor humiditySensor, InetAddress address, int port) throws SocketException, UnknownHostException {
        this.port = port;
        socket = new DatagramSocket();
        this.address = address;
        this.humiditySensor = humiditySensor;
    }

    public void sendMessage() {

            TimerTask sendValueTask = new TimerTask() {
                @Override
                public void run() {


                    var humidity = humiditySensor.generateMessage();

                    if (humiditySensor.isGreaterThanThreshold(humidity.getVal())) {

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
                    buf = humiditySensor.generateAliveMessage().getByteArr();

                    DatagramPacket packet
                            = new DatagramPacket(buf, buf.length, address, port);
                    try {
                        socket.send(packet);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            };


            Timer aliveTimer = new Timer();
            aliveTimer.schedule(sendAliveTask, 0, 3000);
            aliveTimer.schedule(sendValueTask, 0, 1000);


        //TODO: socket close logic??

    }
}
