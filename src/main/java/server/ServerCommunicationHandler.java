package main.java.server;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

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
                    System.out.println("gateway signal to server ===>" + msg);

                    String[] msgElements = msg.split(" ");

                    if(msgElements[0].equals("GET") && msgElements[2].equals("TEMP")){
                        var timeStampArr = server.getTempMsgTimeStamp();
                        if(timeStampArr.size() == 0){
                            out.writeUTF("Temperature Sensor");
                        }else{
                            var timeStamp = String.valueOf(timeStampArr.get(timeStampArr.size() - 1));
                            out.writeUTF("Temperature Sensor " + timeStamp);
                        }

                    } else if(msgElements[2].equals("OFF")){
                        server.setTempSensorOff(true);
                    }
                    else{
                        // break down the message and store the info
                        server.breakDownMessageAndStore(msgElements);
                    }

                }
                catch(IOException i) {
                    System.out.println(i);
                }
            }

        }
        catch(IOException i) {
            System.out.println(i);
        }
    }
}
