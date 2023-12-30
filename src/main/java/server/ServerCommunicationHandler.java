package main.java.server;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static main.java.AdvancedLogger.*;
import static main.java.AdvancedLogger.logInfo;

public class ServerCommunicationHandler extends Thread{

    // TCP w/Gateway
    private Socket socket;
    private Server server;
    private DataInputStream in;
    private DataOutputStream out;

    public ServerCommunicationHandler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    public void run(){

        try {

            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            out = new DataOutputStream(socket.getOutputStream());

            String msg;

            while (true) {
                try {
                    msg = in.readUTF();

                    String[] msgElements = msg.split(" ");

                    if(!msgElements[1].equals("DEVICE")) {

                        if (msgElements[0].equals("GET") && msgElements[2].equals("TEMP")) {
                            var timeStampArr = server.getTempMsgTimeStamp();
                            if (timeStampArr.size() == 0) {
                                if(server.isTempSensorOff()){
                                    out.writeUTF("TEMP SENSOR OFF VALUE NOT FOUND");
                                }else{
                                    out.writeUTF("TEMP SENSOR VALUE NOT FOUND");
                                }
                            } else {
                                var timeStamp = String.valueOf(timeStampArr.get(timeStampArr.size() - 1));
                                if(server.isTempSensorOff()){
                                    out.writeUTF("TEMP SENSOR OFF " + timeStamp.toUpperCase());

                                }else{
                                    out.writeUTF("TEMP SENSOR " + timeStamp.toUpperCase());
                                }
                            }

                        } else if (msgElements[0].equals("GET") && msgElements[2].equals("ALIVE")) {
                            var timeStampArr = server.getAliveMsgTimeStamp();
                            if (timeStampArr.size() == 0) {
                                if(server.isHumiditySensorOff()){
                                    out.writeUTF("HUMIDITY SENSOR OFF VALUE NOT FOUND");
                                }else {
                                    out.writeUTF("HUMIDITY SENSOR VALUE NOT FOUND");
                                }
                            } else {
                                var timeStamp = String.valueOf(timeStampArr.get(timeStampArr.size() - 1));
                                if(server.isHumiditySensorOff()){
                                    out.writeUTF("HUMIDITY SENSOR OFF " + timeStamp.toUpperCase());
                                }else {
                                    out.writeUTF("HUMIDITY SENSOR " + timeStamp.toUpperCase());
                                }
                            }

                        } else if (msgElements[0].equals("TEMP") && msgElements[2].equals("OFF")) {
                            server.setTempSensorOff(true);
                        } else if (msgElements[0].equals("HUMIDITY") && msgElements[2].equals("OFF")) {
                            server.setHumiditySensorOff(true);
                        } else {
                            // break down the message and store the info
                            server.breakDownMessageAndStore(msgElements);
                        }
                    }

                }
                catch(IOException i) {
                    logException(i);
                    throw new RuntimeException();
                }
            }

        }
        catch(IOException i) {
            logException(i);
            throw new RuntimeException();
        }
    }
}
