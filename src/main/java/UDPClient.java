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

    public UDPClient(int port, HumiditySensor humiditySensor) throws SocketException, UnknownHostException {
        this.port = port;
        socket = new DatagramSocket();
        address = InetAddress.getLocalHost();
        this.humiditySensor = humiditySensor;
    }

    public void sendMessage() {

            TimerTask sendValueTask = new TimerTask() {
                @Override
                public void run() {


                    int humidity = humiditySensor.generateHumidity();

                    if (humiditySensor.isGreaterThanThreshold(humidity)) {
                        // like I wasn't virgin enough :_(
                        buf = new byte[]{
                                (byte) ((humidity & 0xFF000000) >> 24),
                                (byte) ((humidity & 0x00FF0000) >> 16),
                                (byte) ((humidity & 0x0000FF00) >> 8),
                                (byte) ((humidity & 0x000000FF)),

                        };

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

            Timer humidityTimer = new Timer();
            //humidityTimer.schedule(sendValueTask, 0, 1000);



            TimerTask sendAliveTask = new TimerTask() {
                @Override
                public void run() {
                    int aliveMsg = humiditySensor.generateAliveMessage();

                    buf = new byte[]{

                            (byte) ((aliveMsg & 0xFF000000) >> 24),
                            (byte) ((aliveMsg & 0x00FF0000) >> 16),
                            (byte) ((aliveMsg & 0x0000FF00) >> 8),
                            (byte) ((aliveMsg & 0x000000FF)),


                    };

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
