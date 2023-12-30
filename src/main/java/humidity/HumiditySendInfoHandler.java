package main.java.humidity;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class HumiditySendInfoHandler {
    private DatagramSocket socket;
    private HumiditySensor humiditySensor;
    private InetAddress address;
    private int port;

    public HumiditySendInfoHandler(HumiditySensor humiditySensor, DatagramSocket socket, InetAddress address, int port) {
        this.humiditySensor = humiditySensor;
        this.socket = socket;
        this.address = address;
        this.port = port;
    }


    public void sendMessage() {

        HumiditySensorToGatewaySensorInfoTimerTask sensorToGatewaySensorInfoTimerTask =
                new HumiditySensorToGatewaySensorInfoTimerTask(humiditySensor, socket, address, port);
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

        executor.schedule(sensorToGatewaySensorInfoTimerTask, 0, TimeUnit.SECONDS);


        //TODO: socket close logic??

    }
}
