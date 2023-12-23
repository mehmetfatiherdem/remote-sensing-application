package main.java.temp;

import main.java.sensor.Sensor;
import main.java.sensor.SensorToGatewaySensorInfoTimerTask;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TempCommunicationHandler{
    private Socket socket;
    private Sensor sensor;

    public TempCommunicationHandler(Sensor sensor, Socket socket) {
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

        TempSensorTimerTask task = new TempSensorTimerTask(sensor, out);
        //SensorToGatewaySensorInfoTimerTask sensorToGatewaySensorInfoTimerTask =
        //           new SensorToGatewaySensorInfoTimerTask(sensor, out);

        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

        // executor.schedule(sensorToGatewaySensorInfoTimerTask, 0, TimeUnit.SECONDS);

        executor.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);

        //Close connection logic??
    }

    public Socket getSocket() {
        return socket;
    }

    public Sensor getSensor() {
        return sensor;
    }

}