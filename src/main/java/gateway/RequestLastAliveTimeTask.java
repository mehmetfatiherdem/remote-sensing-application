package main.java.gateway;

import java.io.DataOutputStream;
import java.io.IOException;

public class RequestLastAliveTimeTask implements Runnable{
    DataOutputStream out;
    public RequestLastAliveTimeTask(DataOutputStream out){
        this.out = out;
    }

    @Override
    public void run() {
        try {;
            out.writeUTF("GET LAST ALIVE TIME"); //TODO: discuss this message w/bros
        } catch (IOException i) {
            System.out.println(i);
        }
    }
}
