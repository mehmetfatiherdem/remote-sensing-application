package main.java.temp;

import main.java.sensor.Sensor;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static main.java.AdvancedLogger.logException;

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
            logException(i);
            System.out.println(i);
            return;
        }

        TempSensorTimerTask task = new TempSensorTimerTask(sensor, out);

        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

        executor.scheduleAtFixedRate(task, 0, 4, TimeUnit.SECONDS); //TODO: change period to 1 before sending

        //Close connection logic??
    }

    public Socket getSocket() {
        return socket;
    }

    public Sensor getSensor() {
        return sensor;
    }

}