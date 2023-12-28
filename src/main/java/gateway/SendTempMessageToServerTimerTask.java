package main.java.gateway;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import static main.java.AdvancedLogger.logException;

public class SendTempMessageToServerTimerTask implements Runnable {
    DataInputStream in;
    DataOutputStream out;
    public SendTempMessageToServerTimerTask(DataInputStream in, DataOutputStream out){
        this.in = in;
        this.out = out;
    }

    @Override
    public void run() {
        try {
            var msg = in.readUTF();
            out.writeUTF(msg);
        } catch (IOException i) {
            logException(i);
            System.out.println(i);
        }
    }
}
