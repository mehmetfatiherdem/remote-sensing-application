package main.java.humidity;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class HumiditySendInfoHandler {
    private DatagramSocket socket;
    private HumiditySensor sensor;
    private InetAddress address;
    private int port;

    public HumiditySendInfoHandler(HumiditySensor sensor, DatagramSocket socket, InetAddress address, int port) {
        this.sensor = sensor;
        this.socket = socket;
        this.address = address;
        this.port = port;
    }


    public void sendMessage() {

        HumiditySensorToGatewaySensorInfoTimerTask sensorToGatewaySensorInfoTimerTask =
                new HumiditySensorToGatewaySensorInfoTimerTask(sensor, socket, address, port);
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

        executor.schedule(sensorToGatewaySensorInfoTimerTask, 0, TimeUnit.SECONDS);


        //TODO: socket close logic??

    }
}
