package main.java.gateway;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import static main.java.AdvancedLogger.*;
import static main.java.AdvancedLogger.logInfo;

public class GatewayServerHandler extends Thread{

    // TCP w/Server
    private Gateway gateway;
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;


    public GatewayServerHandler(Socket socket, Gateway gateway) {
        this.socket = socket;
        this.gateway = gateway;

    }

    public void run(){

        try {

            // receive the data from the server
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

            // create byte stream to send info to the server about the sensors
            out = new DataOutputStream(socket.getOutputStream());


            String msg;

            while (true) {
                try {
                    // read server message
                    msg = in.readUTF();
                    // logInfo("temp timestamp coming from the server: " + msg);

                    String[] msgArr = msg.split(" ");
                    if(!msgArr[2].equals("OFF")){
                        if(msgArr[0].equals("TEMP")){
                            if(msgArr[2].equals("VALUE") && msgArr[3].equals("NOT") && msgArr[4].equals("FOUND")){

                                // send temp sensor message to the server
                                logInfo("TEMP SENSOR OFF");
                                out.writeUTF("TEMP SENSOR OFF");

                            } else if(gateway.isTempSensorOff(msgArr[2] + " " +
                                    msgArr[3] + " " + msgArr[4] + " " + msgArr[5] + " " + msgArr[6] + " " +
                                    msgArr[7])){
                                // send temp sensor message to the server
                                logInfo("TEMP SENSOR OFF");
                                out.writeUTF("TEMP SENSOR OFF");
                            }
                        } if(msgArr[0].equals("HUMIDITY")){
                            if(msgArr[2].equals("VALUE") && msgArr[3].equals("NOT") && msgArr[4].equals("FOUND")){

                                // send temp sensor message to the server
                                logInfo("HUMIDITY SENSOR OFF");
                                out.writeUTF("HUMIDITY SENSOR OFF");

                            } else if(gateway.isHumiditySensorOff(msgArr[2] + " " +
                                    msgArr[3] + " " + msgArr[4] + " " + msgArr[5] + " " + msgArr[6] + " " +
                                    msgArr[7])){
                                // send temp sensor message to the server
                                logInfo("HUMIDITY SENSOR OFF");
                                out.writeUTF("HUMIDITY SENSOR OFF");
                            }
                        }
                    }

                }
                catch(IOException i) {
                    logException(i);
                    throw new RuntimeException(i);
                }
            }

        }
        catch(IOException i) {
            logException(i);
            throw new RuntimeException(i);
        }
    }
}
