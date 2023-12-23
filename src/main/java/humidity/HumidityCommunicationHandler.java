package main.java.humidity;

import main.java.sensor.Sensor;

import java.net.*;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class HumidityCommunicationHandler {
    private DatagramSocket socket;
    private Sensor sensor;
    private InetAddress address;

    public HumidityCommunicationHandler(Sensor sensor, DatagramSocket socket, InetAddress address) {
        this.sensor = sensor;
        this.socket = socket;
        this.address = address;
    }


    public void sendMessage() {

        HumiditySensorTimerTask sendValueTask = new HumiditySensorTimerTask(sensor, HUMIDITY_MESSAGE.VALUE, socket, address, socket.getPort());
        HumiditySensorTimerTask sendAliveTask = new HumiditySensorTimerTask(sensor, HUMIDITY_MESSAGE.ALIVE, socket, address, socket.getPort());

        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);

        executor.scheduleAtFixedRate(sendValueTask, 0, 1, TimeUnit.SECONDS);
        executor.scheduleAtFixedRate(sendAliveTask, 0, 3, TimeUnit.SECONDS);


        //TODO: socket close logic??

    }


}
