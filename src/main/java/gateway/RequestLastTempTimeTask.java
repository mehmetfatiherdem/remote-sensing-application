package main.java.gateway;

import main.java.sensor.Sensor;

import java.io.DataOutputStream;
import java.io.IOException;

public class RequestLastTempTimeTask implements Runnable {
    DataOutputStream out;
    public RequestLastTempTimeTask(DataOutputStream out){
        this.out = out;
    }

    @Override
    public void run() {
        try {;
            out.writeUTF("GET LAST TEMP TIME"); //TODO: discuss this message w/bros
        } catch (IOException i) {
            System.out.println(i);
        }
    }
}
