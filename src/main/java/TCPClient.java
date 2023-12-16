package main.java;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class TCPClient {
    private Socket socket;
    private DataOutputStream out;
    private TemperatureSensor tempSensor;
    private String address;
    private int port;
    public TCPClient(TemperatureSensor tempSensor, String address, int port) throws IOException {
        socket = new Socket(address, port);
        System.out.println("Connected");
        this.tempSensor = tempSensor;
        this.address = address;
        this.port = port;
    }

    public void sendMessage(){
        try {
            out = new DataOutputStream(
                    socket.getOutputStream());
        } catch (IOException i) {
            System.out.println(i);
            return;
        }

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {
                    var temp = tempSensor.generateMessage();
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

}