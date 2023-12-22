package main.java.sensor;

import java.io.DataOutputStream;
import java.io.IOException;

public class SensorToGatewaySensorInfoTimerTask implements Runnable{

    private Sensor sensor;
    DataOutputStream out;

    public SensorToGatewaySensorInfoTimerTask(Sensor sensor, DataOutputStream out) {
        this.sensor = sensor;
        this.out = out;
    }

    @Override
    public void run() {
        try {
            var info = sensor.getType().getName();
            out.writeUTF(info);
        } catch (IOException i) {
            System.out.println(i);
        }
    }
}
