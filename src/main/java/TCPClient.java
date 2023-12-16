package main.java;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

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

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {
                    var temp = sensor.generateMessage();
                    out.writeUTF("Temperature Sensor: " + temp.getVal() + " at " + temp.getTimeStamp() + " on port " + socket.getPort());
                } catch (IOException i) {
                    System.out.println(i);
                }

            }
        };

        Timer timer = new Timer();
        timer.schedule(task, 0, 1000);

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