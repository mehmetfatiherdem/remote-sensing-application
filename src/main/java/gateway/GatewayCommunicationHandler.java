package main.java.gateway;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.Socket;

public class GatewayCommunicationHandler extends Thread{

    // TCP w/Server
    private Socket tcpSendToServerSocket;
    private DataOutputStream serverOut;

    // TCP w/Sensor
    private Socket tcpSensorSocket;
    private DataInputStream sensorIn;

    // UDP w/Sensor
    private DatagramSocket datagramSocket;
    private byte[] buf = new byte[65535];

    public GatewayCommunicationHandler(Socket tcpSendToServerSocket, Socket tcpSensorSocket, DatagramSocket datagramSocket) {
        this.tcpSendToServerSocket = tcpSendToServerSocket;
        this.tcpSensorSocket = tcpSensorSocket;
        this.datagramSocket = datagramSocket;

    }

    public void run(){

        try {

            // create byte stream to send info to the server about the sensors
            serverOut = new DataOutputStream(tcpSendToServerSocket.getOutputStream());

            // receive the data from the sensors
            sensorIn = new DataInputStream(new BufferedInputStream(tcpSensorSocket.getInputStream()));

            //TODO: send info to server about the sensors first

            String line;

            while (true) {
                try {
                    // read sensor message
                    while((line = sensorIn.readUTF()).length() != 0){
                        System.out.println(line);

                        // send it to the server
                        serverOut.writeUTF(line);

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
