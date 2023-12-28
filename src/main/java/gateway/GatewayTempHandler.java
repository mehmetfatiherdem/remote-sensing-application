package main.java.gateway;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static main.java.AdvancedLogger.*;

public class GatewayTempHandler extends Thread{

    // TCP w/Server
    private Socket tcpSendToServerSocket;
    private DataOutputStream serverOut;

    // TCP w/Sensor
    private Socket tcpSensorSocket;
    private DataInputStream sensorIn;


    public GatewayTempHandler(Socket tcpSendToServerSocket, Socket tcpSensorSocket) {
        this.tcpSendToServerSocket = tcpSendToServerSocket;
        this.tcpSensorSocket = tcpSensorSocket;

    }

    public void run(){

        try {

            // receive the data from the sensors
            sensorIn = new DataInputStream(new BufferedInputStream(tcpSensorSocket.getInputStream()));

            // create byte stream to send info to the server about the sensors
            serverOut = new DataOutputStream(tcpSendToServerSocket.getOutputStream());

            //TODO: send info to server about the sensors first

            String msg;

            while (true) {
                try {
                    // read temp sensor message
                    msg = sensorIn.readUTF();

                    // send temp sensor message to the server

                    serverOut.writeUTF(msg);
                    logInfo(msg);


                }
                catch(IOException i) {
                    logException(i);
                    throw new RuntimeException(i);
                }
            }

        }
        catch(IOException i) {
            System.out.println(i);
            logException(i);
        }
    }
}
