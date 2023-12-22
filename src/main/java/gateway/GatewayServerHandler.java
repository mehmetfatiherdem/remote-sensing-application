package main.java.gateway;

import main.java.CreateServerGatewayTCPConnectionService;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramSocket;

public class GatewayServerHandler extends Thread{

    // TCP w/Server
    private CreateServerGatewayTCPConnectionService tcpServerService;
    private DataInputStream serverIn;

    // TCP w/Sensor
    private CreateGatewaySensorTCPConnectionService tcpSensorService;
    private DataInputStream sensorIn;

    // UDP w/Sensor
    private DatagramSocket datagramSocket;
    private byte[] buf = new byte[65535];

    public GatewayServerHandler(int tcpServerPort, int tcpSensorPort, int udpSensorPort) throws IOException {

        tcpServerService = CreateServerGatewayTCPConnectionService.getInstance(tcpServerPort);
        System.out.println("Gateway Waiting for the Server at port " + tcpServerService.getServer().getLocalPort());

        tcpSensorService = CreateGatewaySensorTCPConnectionService.getInstance(tcpSensorPort);
        System.out.println("Gateway Waiting for a TCP Sensor at port " + tcpSensorService.getServer().getLocalPort());


        datagramSocket = new DatagramSocket(udpSensorPort);
        System.out.println("Gateway datagram socket waiting for UDP client at port " + datagramSocket.getLocalPort());


    }

    public void run(){

        try {

            System.out.println("Gateway accepted TCP Sensor at port " + tcpSensorService.getServer().getLocalPort());


            sensorIn = new DataInputStream(new BufferedInputStream(tcpSensorService.getSocket().getInputStream()));

            String line;

            while (true) {
                try {
                    line = sensorIn.readUTF();
                    System.out.println(line);

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
