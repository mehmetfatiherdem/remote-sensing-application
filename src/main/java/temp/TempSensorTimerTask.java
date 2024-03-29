package main.java.temp;

import main.java.sensor.Sensor;

import java.io.DataOutputStream;
import java.io.IOException;

import static main.java.AdvancedLogger.logException;

public class TempSensorTimerTask implements Runnable {
    private Sensor sensor;
    DataOutputStream out;
    public TempSensorTimerTask(Sensor sensor, DataOutputStream out){
        this.sensor = sensor;
        this.out = out;
    }

    @Override
    public void run() {
        try {
            var temp = sensor.generateMessage();
            out.writeUTF(temp.getString());
        } catch (IOException i) {
            logException(i);
        }
    }
}
