package main.java.gateway;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class GatewayCommunicationHandler extends Thread{

    // TCP w/Server
    private Socket tcpSendToServerSocket;
    private Gateway gateway;
    private InetAddress address;
    private DataInputStream serverIn;
    private DataOutputStream serverOut;

    // TCP w/Sensor
    private Socket tcpSensorSocket;
    private ServerSocket tcpSensorServer;
    private DataInputStream sensorIn;

    // UDP w/Sensor
    private DatagramSocket datagramSocket;
    private byte[] buf = new byte[65535];

    public GatewayCommunicationHandler(int tcpServerPort, int tcpSensorPort, int udpSensorPort, InetAddress address) throws IOException {

        this.address = address;

        tcpSensorServer = new ServerSocket(tcpSensorPort);
        System.out.println("Gateway waiting for a TCP Sensor at port " + tcpSensorServer.getLocalPort());

        datagramSocket = new DatagramSocket(udpSensorPort);
        System.out.println("Gateway datagram socket is ready for UDP client at port " + datagramSocket.getLocalPort());

        // Gateway connected to Server
        tcpSendToServerSocket = new Socket(address, tcpServerPort);
        System.out.println("Gateway Connected to server with TCP at " + tcpSendToServerSocket.getLocalPort());

    }

    public void run(){

        try {

            // temperature sensor is connected to the gateway
            tcpSensorSocket = tcpSensorServer.accept();
            System.out.println("Gateway accepted a TCP Sensor at port " + tcpSensorServer.getLocalPort());

            // send info to the server about the sensors
            serverOut = new DataOutputStream(tcpSendToServerSocket.getOutputStream());

            // create a task to send info to the server once it is connected

            SendSensorInfoTask sendSensorInfoTask = new SendSensorInfoTask();

            ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

            executor.schedule(sendSensorInfoTask, 0, TimeUnit.SECONDS);

            // receive the data from the sensors handle them and signal the server


            sensorIn = new DataInputStream(new BufferedInputStream(tcpSensorSocket.getInputStream()));

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
