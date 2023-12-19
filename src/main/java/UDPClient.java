package main.java;

import java.net.*;
import java.util.Timer;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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

        UDPTimerTask sendValueTask = new UDPTimerTask(sensor, HUMIDITY_MESSAGE.VALUE, socket, address, port);
        UDPTimerTask sendAliveTask = new UDPTimerTask(sensor, HUMIDITY_MESSAGE.ALIVE, socket, address, port);

        ScheduledThreadPoolExecutor threadPool
                = new ScheduledThreadPoolExecutor(2);

        threadPool.scheduleAtFixedRate(sendValueTask, 0, 1, TimeUnit.SECONDS);
        threadPool.scheduleAtFixedRate(sendAliveTask, 0, 3, TimeUnit.SECONDS);

        Timer timer = new Timer();
        timer.schedule(sendAliveTask, 0, 3000);
        timer.schedule(sendValueTask, 0, 1000);

        threadPool.shutdown();

        //TODO: socket close logic??

    }


}
