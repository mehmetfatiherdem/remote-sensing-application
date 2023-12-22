package main.java;

import main.java.sensor.HumiditySensor;
import main.java.sensor.TemperatureSensor;
import main.java.tcp.TCPClient;
import main.java.udp.UDPClient;
import main.java.utils.Constants;

import java.io.IOException;
import java.net.InetAddress;

public class Main {
    public static void main(String[] args) throws IOException {

        // initialize the gateway
        Gateway gateway = Gateway.getInstance(Constants.gatewaySensorTCPPort, Constants.udpPort);

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
        UDPClient humiditySensorClient = new UDPClient(humiditySensor, localHostAddress, Constants.udpPort);
        TCPClient tempSensorClient = new TCPClient(tempSensor, localHostAddress, Constants.gatewaySensorTCPPort);

        // start sending the messages from the clients
        tempSensorClient.sendMessage();
        humiditySensorClient.sendMessage();


    }
}