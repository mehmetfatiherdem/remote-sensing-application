package main.java;

import java.io.IOException;
import java.net.InetAddress;

public class Main {
    public static void main(String[] args) throws IOException {

        // initialize the gateway
        Gateway gateway = Gateway.getInstance(5000, 4445);

        // initialize the sensors
        TemperatureSensor tempSensor = new TemperatureSensor();
        HumiditySensor humiditySensor = new HumiditySensor();

        // returns 127.0.0.1
        var localHostAddress = InetAddress.getLocalHost();

        //  initialize the client instances with the corresponding sensors
        UDPClient humiditySensorClient = new UDPClient(humiditySensor, localHostAddress, 4445);
        TCPClient tempSensorClient = new TCPClient(tempSensor, localHostAddress, 5000);

        // start sending the messages from the clients
        tempSensorClient.sendMessage();
        humiditySensorClient.sendMessage();


    }
}