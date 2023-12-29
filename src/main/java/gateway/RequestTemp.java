package main.java.gateway;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
            System.out.println(i);
            return;
        }

        RequestLastTempTimeTask task = new RequestLastTempTimeTask(out);

        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

        executor.scheduleAtFixedRate(task, 3, 3, TimeUnit.SECONDS);

        //Close connection logic??
    }

    public Socket getSocket() {
        return socket;
    }

}
