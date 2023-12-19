package main.java;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Timer;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TCPClient {
    private Socket socket;
    private Sensor sensor;
    private InetAddress address;
    private int port;
    public TCPClient(Sensor sensor, InetAddress address, int port) throws IOException {
        this.address = address;
        this.port = port;
        this.sensor = sensor;

        socket = new Socket(address, port);
        System.out.println("Connected");
    }

    public void sendMessage(){
        DataOutputStream out;
        try {
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException i) {
            System.out.println(i);
            return;
        }

        TCPTimerTask task = new TCPTimerTask(sensor, out);

        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

        executor.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);

        //Close connection logic??
    }

    public Socket getSocket() {
        return socket;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public InetAddress getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }
}