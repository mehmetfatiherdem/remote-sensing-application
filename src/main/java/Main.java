package main.java;

import java.io.IOException;
import java.net.InetAddress;

public class Main {
    public static void main(String[] args) throws IOException {

        // 2 different ports for UDP and TCP
        int udpPort = 4445;
        int tcpPort = 5000;

        // initialize the gateway
        Gateway gateway = Gateway.getInstance(tcpPort, udpPort);

        // min-max temp/humidity values
        int minTemp = 20;
        int maxTemp = 30;
        int minHumidity = 40;
        int maxHumidity = 90;

        // initialize the sensors
        TemperatureSensor tempSensor = new TemperatureSensor(minTemp, maxTemp);
        HumiditySensor humiditySensor = new HumiditySensor(minHumidity, maxHumidity);

        // returns 127.0.0.1
        var localHostAddress = InetAddress.getLocalHost();

        //  initialize the client instances with the corresponding sensors
        UDPClient humiditySensorClient = new UDPClient(humiditySensor, localHostAddress, udpPort);
        TCPClient tempSensorClient = new TCPClient(tempSensor, localHostAddress, tcpPort);

        // start sending the messages from the clients
        tempSensorClient.sendMessage();
        humiditySensorClient.sendMessage();


    }
}