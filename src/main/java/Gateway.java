package main.java;

import java.io.IOException;

/*
    reads values from sensors and sends them with timestamps
    to the server

    monitoring:
        time sensor fails to send anything
        for 3 secs -> "TEMP SENSOR OFF" to the server

        humidity sensor fails to send "ALIVE"
        msg for more than 7 secs -> "HUMIDITY SENSOR OFF"
        to the server

     DOES NOT HAVE A STORAGE UNIT!!!!!

 */
public class Gateway {
    private static Gateway instance;
    private int tcpPort;
    private int udpPort;

    public Gateway(int tcpPort, int udpPort) throws IOException {

        this.tcpPort = tcpPort;
        this.udpPort = udpPort;

        UDPClientHandler udpClientHandler = new UDPClientHandler(udpPort);
        udpClientHandler.start();

        TCPClientHandler tcpClientHandler = new TCPClientHandler(tcpPort);
        tcpClientHandler.start();

    }

    public static Gateway getInstance(int tcpPort, int udpPort) throws IOException {
        if (instance == null) {
            instance = new Gateway(tcpPort, udpPort);
        }
        return instance;
    }

    public static Gateway getInstance() {
        return instance;
    }

    public int getTcpPort() {
        return tcpPort;
    }

    public int getUdpPort() {
        return udpPort;
    }
}
