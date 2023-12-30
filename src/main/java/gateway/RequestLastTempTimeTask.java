package main.java.gateway;

import java.io.DataOutputStream;
import java.io.IOException;

import static main.java.AdvancedLogger.logException;

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
            logException(i);

        }
    }
}
