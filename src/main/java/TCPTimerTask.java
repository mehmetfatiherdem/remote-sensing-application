package main.java;

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
            out.writeUTF(sensor.toString().substring(10, 27) + " " + temp.getVal() + " at " + temp.getTimeStamp());
        } catch (IOException i) {
            System.out.println(i);
        }
    }
}
