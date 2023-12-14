package main.java;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

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
    public Gateway(int tcpPort, int udpPort) throws SocketException {
        // TCP connection between Temperature Sensor and Gateway
        TCPServer tcpServer = new TCPServer(tcpPort);

        UDPServer udpServer = new UDPServer(udpPort);
        udpServer.start();
    }

    public static void main(String[] args) throws IOException {
        Gateway gateway = new Gateway(5000, 4445);

        HumiditySensor humiditySensor = new HumiditySensor();
        UDPClient udpClient = new UDPClient(humiditySensor);

        udpClient.sendMessage();

    }


}
