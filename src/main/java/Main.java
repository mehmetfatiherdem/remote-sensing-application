package main.java;

import main.java.gateway.Gateway;
import main.java.gateway.GatewayCommunicationHandler;
import main.java.humidity.HumidityCommunicationHandler;
import main.java.humidity.HumiditySensor;
import main.java.server.ServerCommunicationHandler;
import main.java.tcp.CreateTCPClient;
import main.java.tcp.CreateTCPServer;
import main.java.server.Server;
import main.java.temp.TempCommunicationHandler;
import main.java.temp.TemperatureSensor;
import main.java.udp.CreateUDPListener;
import main.java.udp.CreateUDPSender;
import main.java.utils.Constants;

import java.io.IOException;
import java.net.InetAddress;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        // initialize server
        Server server = Server.getInstance();

        // initialize the gateway
        Gateway gateway = Gateway.getInstance();

        //

        // returns 127.0.0.1
        var localHostAddress = InetAddress.getLocalHost();

        // Server TCP connection waiting for Gateway
        CreateTCPServer tcpServer = new CreateTCPServer(Constants.serverGatewayTCPPort);
        tcpServer.start();

        // wait a little for server to start
        Thread.sleep(1000);

        if(tcpServer.getServer() != null){
            System.out.println("Server up&running on port " + tcpServer.getPort());
        }

        if(tcpServer.getSocket() == null){
            System.out.println("Server is waiting for a Client....");
        }

        // Gateway TCP connection waiting for Sensor
        CreateTCPServer tcpServerGateway = new CreateTCPServer(Constants.gatewaySensorTCPPort);
        tcpServerGateway.start();

        // wait a little for gateway to start
        Thread.sleep(1000);

        if(tcpServerGateway.getServer() != null){
            System.out.println("Gateway waiting for TCP Sensor Client on port " + tcpServerGateway.getPort());
        }

        // Temperature sensor
        CreateTCPClient tcpTemp = new CreateTCPClient(localHostAddress, Constants.gatewaySensorTCPPort);
        tcpTemp.start();

        // wait a little for sensor to start
        Thread.sleep(1000);

        if(tcpServerGateway.getSocket() != null){
            System.out.println("Temperature Sensor is connected to the gateway on port " + tcpServerGateway.getPort());
        }

        // Gateway UDP socket
        CreateUDPListener udpGateway = new CreateUDPListener(Constants.gatewaySensorUDPPort);
        udpGateway.start();

        // wait a little for gateway to start
        Thread.sleep(1000);

        if(udpGateway.getSocket() != null){
            System.out.println("Gateway UDP socket is up&running on port " + udpGateway.getPort());
        }

        // Humidity Sensor udp socket
        CreateUDPSender udpHumidity = new CreateUDPSender(localHostAddress, Constants.gatewaySensorUDPPort);
        udpHumidity.start();

        // wait a little for sensor to start
        Thread.sleep(1000);

        if(udpHumidity.getSocket() != null){
            System.out.println("Humidity sensor udp socket is ready on port " + udpHumidity.getPort());
        }

        // TCP client socket of Gateway to signal server
        CreateTCPClient tcpClientGateway = new CreateTCPClient(localHostAddress, Constants.serverGatewayTCPPort);
        tcpClientGateway.start();

        // wait a little for gateway to start
        Thread.sleep(1000);

        if(tcpServer.getSocket() != null){
            System.out.println("Gateway is connected to the server on port " + tcpServer.getPort());
        }


        // min-max temp/humidity values
        int minTemp = 20;
        int maxTemp = 30;
        int minHumidity = 40;
        int maxHumidity = 90;

        // initialize the sensors
        TemperatureSensor tempSensor = new TemperatureSensor(minTemp, maxTemp);
        HumiditySensor humiditySensor = new HumiditySensor(minHumidity, maxHumidity);


        //  initialize the client instances with the corresponding sensors
        //HumidityCommunicationHandler humiditySensorClient =
                //new HumidityCommunicationHandler(humiditySensor, localHostAddress, Constants.gatewaySensorUDPPort);
        TempCommunicationHandler tempSensorClient =
                new TempCommunicationHandler(tempSensor, tcpTemp.getSocket());

        // start sending the messages from the clients
        tempSensorClient.start();
        Thread.sleep(1000);
        //humiditySensorClient.sendMessage();


        GatewayCommunicationHandler gatewayCommunicationHandler =
                new GatewayCommunicationHandler(tcpClientGateway.getSocket(), tcpTemp.getSocket(),
                        udpHumidity.getSocket());
        gatewayCommunicationHandler.start();

        Thread.sleep(1000);

        ServerCommunicationHandler serverCommunicationHandler =
                new ServerCommunicationHandler(tcpServer.getSocket());

        serverCommunicationHandler.start();
    }
}