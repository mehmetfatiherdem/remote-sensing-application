package main.java;

import java.io.IOException;
import java.net.InetAddress;

public class Main {
    public static void main(String[] args) throws IOException {

        // initialize the gateway
        Gateway gateway = Gateway.getInstance(5000, 4445);

        // initialize the sensors
        TemperatureSensor tempSensor = new TemperatureSensor(20, 30);
        HumiditySensor humiditySensor = new HumiditySensor(40, 90);

        // returns 127.0.0.1
        var localHostAddress = InetAddress.getLocalHost();

        // 2 different ports for UDP and TCP
        int udpPort = 4445;
        int tcpPort = 5000;

        //  initialize the client instances with the corresponding sensors
        UDPClient humiditySensorClient = new UDPClient(humiditySensor, localHostAddress, udpPort);
        TCPClient tempSensorClient = new TCPClient(tempSensor, localHostAddress, tcpPort);

        // start sending the messages from the clients
        tempSensorClient.sendMessage();
        humiditySensorClient.sendMessage();


    }
}