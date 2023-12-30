package main.java.temp;

import main.java.sensor.Sensor;

import java.io.DataOutputStream;
import java.io.IOException;

import static main.java.AdvancedLogger.logException;

public class TempSensorToGatewaySensorInfoTimerTask implements Runnable{

    private Sensor sensor;
    DataOutputStream out;

    public TempSensorToGatewaySensorInfoTimerTask(Sensor sensor, DataOutputStream out) {
        this.sensor = sensor;
        this.out = out;
    }

    @Override
    public void run() {
        try {
            var info = sensor.generateInfoMessage().sendTCPSensorInfo();
            out.writeUTF(info);
        } catch (IOException i) {
            logException(i);
            System.out.println(i);
        }
    }
}
