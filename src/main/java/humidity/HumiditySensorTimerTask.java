package main.java.humidity;

import main.java.sensor.Sensor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class HumiditySensorTimerTask implements Runnable {
    private Sensor sensor;
    private HUMIDITY_MESSAGE msg;
    private DatagramSocket socket;
    private InetAddress address;
    private int port;
    private byte[] buf;
    public HumiditySensorTimerTask(Sensor sensor, HUMIDITY_MESSAGE msg, DatagramSocket socket, InetAddress address, int port){
        this.sensor = sensor;
        this.msg = msg;
        this.socket = socket;
        this.address = address;
        this.port = port;
    }
    @Override
    public void run() {
        if(msg == HUMIDITY_MESSAGE.ALIVE){
            buf = ((HumiditySensor)sensor).generateAliveMessage().getByteArr();

        } else if(msg == HUMIDITY_MESSAGE.VALUE){
            var humidity = sensor.generateMessage();
            if (((HumiditySensor)sensor).isGreaterThanThreshold(humidity.getVal())) {
                buf = humidity.getByteArr();
            }else{
                return; // When buf this case holds to be true it breaks everything
                        // due to buf being null, so I just return to be safe
            }
        }

        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);
        try {
            socket.send(packet);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
