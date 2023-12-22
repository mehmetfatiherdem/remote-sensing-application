package main.java.humidity;

import main.java.sensor.Sensor;

import java.net.*;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class HumidityGatewayHandler {
    private DatagramSocket socket;
    private InetAddress address;
    private int port;
    private Sensor sensor;

    public HumidityGatewayHandler(Sensor sensor, InetAddress address, int port) throws SocketException, UnknownHostException {
        this.port = port;
        socket = new DatagramSocket();
        this.address = address;
        this.sensor = sensor;
    }


    public void sendMessage() {

        HumiditySensorTimerTask sendValueTask = new HumiditySensorTimerTask(sensor, HUMIDITY_MESSAGE.VALUE, socket, address, port);
        HumiditySensorTimerTask sendAliveTask = new HumiditySensorTimerTask(sensor, HUMIDITY_MESSAGE.ALIVE, socket, address, port);

        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

        executor.scheduleAtFixedRate(sendValueTask, 0, 1, TimeUnit.SECONDS);
        executor.scheduleAtFixedRate(sendAliveTask, 0, 3, TimeUnit.SECONDS);


        //TODO: socket close logic??

    }


}
