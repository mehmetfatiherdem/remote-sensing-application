package main.java;

import main.java.gateway.Gateway;
import main.java.gateway.GatewayCommunicationHandler;
import main.java.humidity.HumiditySensor;
import main.java.server.ServerCommunicationHandler;
import main.java.temp.TemperatureSensor;
import main.java.server.Server;
import main.java.temp.TempCommunicationHandler;
import main.java.humidity.HumidityCommunicationHandler;
import main.java.utils.Constants;

import java.io.IOException;
import java.net.InetAddress;

public class Main {
    public static void main(String[] args) throws IOException {

        // initialize server
        Server server = Server.getInstance();

        // initialize the gateway
        Gateway gateway = Gateway.getInstance();

        //

        // returns 127.0.0.1
        var localHostAddress = InetAddress.getLocalHost();



        ServerCommunicationHandler serverCommunicationHandler =
                new ServerCommunicationHandler(Constants.serverGatewayTCPPort);

        serverCommunicationHandler.start();

        // min-max temp/humidity values
        int minTemp = 20;
        int maxTemp = 30;
        int minHumidity = 40;
        int maxHumidity = 90;

        // initialize the sensors
        TemperatureSensor tempSensor = new TemperatureSensor(minTemp, maxTemp);
        HumiditySensor humiditySensor = new HumiditySensor(minHumidity, maxHumidity);


        //  initialize the client instances with the corresponding sensors
        HumidityCommunicationHandler humiditySensorClient = new HumidityCommunicationHandler(humiditySensor, localHostAddress, Constants.gatewaySensorUDPPort);
        TempCommunicationHandler tempSensorClient = new TempCommunicationHandler(tempSensor, localHostAddress, Constants.gatewaySensorTCPPort);

        // start sending the messages from the clients
        tempSensorClient.sendMessage();
        humiditySensorClient.sendMessage();

        GatewayCommunicationHandler gatewayCommunicationHandler =
                new GatewayCommunicationHandler(Constants.serverGatewayTCPPort, Constants.gatewaySensorTCPPort,
                        Constants.gatewaySensorUDPPort, localHostAddress);

        gatewayCommunicationHandler.start();




    }
}