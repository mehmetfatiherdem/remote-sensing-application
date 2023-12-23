package main.java.gateway;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Date;

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
                    System.out.println("temp timestamp coming from the server: " + msg);

                    String[] msgArr = msg.split(" ");
                    if(msgArr[0].equals("Temperature")){
                        if(msgArr.length == 2){

                            // send temp sensor message to the server
                            out.writeUTF("TEMP SENSOR OFF at " + new Date());

                        } else if(gateway.isTempSensorOff(msgArr[2] + " " +
                                msgArr[3] + " " + msgArr[4] + " " + msgArr[5] + " " + msgArr[6] + " " +
                                msgArr[7])){
                            // send temp sensor message to the server
                            out.writeUTF("TEMP SENSOR OFF at " + new Date());
                        }
                    }


                }
                catch(IOException i) {
                    throw new RuntimeException(i);
                }
            }

        }
        catch(IOException i) {
            System.out.println(i);
        }
    }
}
