package main.java.temp;

import main.java.sensor.Sensor;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TempSendInfoHandler {
    private Socket socket;
    private TemperatureSensor sensor;

    public TempSendInfoHandler(TemperatureSensor sensor, Socket socket) {
        this.sensor = sensor;
        this.socket = socket;
    }

    public void sendMessage(){
        DataOutputStream out;
        try {
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException i) {
            System.out.println(i);
            return;
        }

        TempSensorToGatewaySensorInfoTimerTask tempSensorToGatewaySensorInfoTimerTask =
                   new TempSensorToGatewaySensorInfoTimerTask(sensor, out);

        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

        executor.schedule(tempSensorToGatewaySensorInfoTimerTask, 0, TimeUnit.SECONDS);

        //Close connection logic??
    }

    public Socket getSocket() {
        return socket;
    }

    public Sensor getSensor() {
        return sensor;
    }
}
