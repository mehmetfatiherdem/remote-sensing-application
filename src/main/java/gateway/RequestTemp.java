package main.java.gateway;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static main.java.AdvancedLogger.logException;

public class RequestTemp {

    private Socket socket;

    public RequestTemp(Socket socket) {
        this.socket = socket;
    }

    public void request(){
        DataOutputStream out;
        try {
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException i) {
            logException(i);
            throw new RuntimeException();
        }

        RequestLastTempTimeTask task = new RequestLastTempTimeTask(out);

        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

        executor.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);

        //Close connection logic??
    }

    public Socket getSocket() {
        return socket;
    }

}
