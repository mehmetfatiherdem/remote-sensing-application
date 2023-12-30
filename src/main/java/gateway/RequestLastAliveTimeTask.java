package main.java.gateway;

import java.io.DataOutputStream;
import java.io.IOException;

import static main.java.AdvancedLogger.logException;

public class RequestLastAliveTimeTask implements Runnable{
    DataOutputStream out;
    public RequestLastAliveTimeTask(DataOutputStream out){
        this.out = out;
    }

    @Override
    public void run() {
        try {
            out.writeUTF("GET LAST ALIVE TIME");
        } catch (IOException i) {
            logException(i);
            throw new RuntimeException();
        }
    }
}
