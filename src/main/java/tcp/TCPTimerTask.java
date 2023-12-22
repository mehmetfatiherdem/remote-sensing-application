package main.java.tcp;

import main.java.sensor.Sensor;

import java.io.DataOutputStream;
import java.io.IOException;

public class TCPTimerTask implements Runnable {
    private Sensor sensor;
    DataOutputStream out;
    public TCPTimerTask(Sensor sensor, DataOutputStream out){
        this.sensor = sensor;
        this.out = out;
    }

    @Override
    public void run() {
        try {
            var temp = sensor.generateMessage();
            out.writeUTF(temp.getString());
        } catch (IOException i) {
            System.out.println(i);
        }
    }
}
