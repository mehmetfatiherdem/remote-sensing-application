package main.java;

import main.java.gateway.Gateway;
import main.java.humidity.HumiditySensor;
import main.java.temp.TemperatureSensor;
import main.java.server.Server;
import main.java.temp.TempGatewayHandler;
import main.java.humidity.HumidityGatewayHandler;
import main.java.utils.Constants;

import java.io.IOException;
import java.net.InetAddress;

public class Main {
    public static void main(String[] args) throws IOException {

        // initialize server
        Server server = Server.getInstance();

        // initialize the gateway
        Gateway gateway = Gateway.getInstance();

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
        HumidityGatewayHandler humiditySensorClient = new HumidityGatewayHandler(humiditySensor, localHostAddress, Constants.gatewaySensorUDPPort);
        TempGatewayHandler tempSensorClient = new TempGatewayHandler(tempSensor, localHostAddress, Constants.gatewaySensorTCPPort);

        // start sending the messages from the clients
        tempSensorClient.sendMessage();
        humiditySensorClient.sendMessage();


    }
}