package main.java.humidity;

import main.java.sensor.Sensor;

import java.net.*;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class HumidityCommunicationHandler {
    private DatagramSocket socket;
    private Sensor sensor;
    private InetAddress address;
    private int port;

    public HumidityCommunicationHandler(Sensor sensor, DatagramSocket socket, InetAddress address, int port) {
        this.sensor = sensor;
        this.socket = socket;
        this.address = address;
        this.port = port;
    }


    public void sendMessage() {

        HumiditySensorTimerTask sendValueTask = new HumiditySensorTimerTask(sensor, HUMIDITY_MESSAGE.VALUE, socket, address, port);
        HumiditySensorTimerTask sendAliveTask = new HumiditySensorTimerTask(sensor, HUMIDITY_MESSAGE.ALIVE, socket, address, port);

        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

        executor.scheduleAtFixedRate(sendValueTask, 0, 1, TimeUnit.SECONDS);
        executor.scheduleAtFixedRate(sendAliveTask, 0, 8, TimeUnit.SECONDS); //TODO: change period to 3 before sending


        //TODO: socket close logic??

    }


}
