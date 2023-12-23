package main.java.gateway;

import main.java.sensor.Sensor;

import java.io.DataOutputStream;
import java.io.IOException;

public class GatewayToServerSensorInfoTimerTask implements Runnable {
    //FIXME: this is copy paste so will be removed
    private Sensor sensor;
    DataOutputStream out;
    public GatewayToServerSensorInfoTimerTask(Sensor sensor, DataOutputStream out){
        this.sensor = sensor;
        this.out = out;
    }

    public GatewayToServerSensorInfoTimerTask(){

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
