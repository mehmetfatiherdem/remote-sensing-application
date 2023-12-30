package main.java;

import main.java.gateway.*;
import main.java.humidity.HumidityCommunicationHandler;
import main.java.humidity.HumiditySendInfoHandler;
import main.java.humidity.HumiditySendLastValueHandler;
import main.java.humidity.HumiditySensor;
import main.java.server.ServerCommunicationHandler;
import main.java.server.ServerRequestLastHumidityHandler;
import main.java.tcp.CreateTCPClient;
import main.java.tcp.CreateTCPServer;
import main.java.server.Server;
import main.java.temp.TempCommunicationHandler;
import main.java.temp.TempSendInfoHandler;
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

        // Gateway Listen UDP socket
        CreateUDPListener udpListenerGateway = new CreateUDPListener(Constants.listenerGatewaySenderSensorUDPPort);
        udpListenerGateway.start();

        // wait a little for gateway to start
        Thread.sleep(1000);

        if(udpListenerGateway.getSocket() != null){
            System.out.println("Gateway Listen UDP socket is up&running on port " + udpListenerGateway.getPort());
        }

        // Humidity Sensor Send udp socket
        CreateUDPSender udpSenderHumidity = new CreateUDPSender(localHostAddress, Constants.listenerGatewaySenderSensorUDPPort);
        udpSenderHumidity.start();

        // wait a little for sensor to start
        Thread.sleep(1000);

        if(udpSenderHumidity.getSocket() != null){
            System.out.println("Humidity sensor Send udp socket is ready on port " + udpSenderHumidity.getPort());
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

        // send info to server about the connected devices
        TempSendInfoHandler tempSendInfoHandler =
                new TempSendInfoHandler(tempSensor, tcpTemp.getSocket());
        tempSendInfoHandler.sendMessage();

        HumiditySendInfoHandler humiditySendInfoHandler =
                new HumiditySendInfoHandler(humiditySensor, udpSenderHumidity.getSocket(), udpSenderHumidity.getAddress(), udpSenderHumidity.getPort());
        humiditySendInfoHandler.sendMessage();

        GatewaySendTempSensorInfoToServerHandler gatewaySendTempSensorInfoToServerHandler =
                new GatewaySendTempSensorInfoToServerHandler(tcpClientGateway.getSocket(), tcpServerGateway.getSocket());
        gatewaySendTempSensorInfoToServerHandler.start();

        GatewaySendHumiditySensorInfoToServerHandler gatewaySendHumiditySensorInfoToServerHandler =
                new GatewaySendHumiditySensorInfoToServerHandler(udpListenerGateway.getSocket(), tcpClientGateway.getSocket());
        gatewaySendHumiditySensorInfoToServerHandler.start();


        Thread.sleep(1000); // add a delay here before starting to send the data


        //  initialize the client instances with the corresponding sensors
        HumidityCommunicationHandler humiditySensorClient =
                new HumidityCommunicationHandler(humiditySensor, udpSenderHumidity.getSocket(), udpSenderHumidity.getAddress(), udpSenderHumidity.getPort());
        TempCommunicationHandler tempSensorClient =
                new TempCommunicationHandler(tempSensor, tcpTemp.getSocket());

        // start sending the messages from the clients
        tempSensorClient.sendMessage();

        humiditySensorClient.sendMessage();

        GatewayTempHandler gatewayTempHandler =
                new GatewayTempHandler(tcpClientGateway.getSocket(), tcpServerGateway.getSocket());
        gatewayTempHandler.start();

        GatewayHumidityHandler gatewayHumidityHandler =
                new GatewayHumidityHandler(udpListenerGateway.getSocket(), tcpClientGateway.getSocket());
        gatewayHumidityHandler.start();

        ServerCommunicationHandler serverCommunicationHandler =
                new ServerCommunicationHandler(tcpServer.getSocket(), server);

        serverCommunicationHandler.start();

        // request the last sent temp value from the server
        RequestTemp requestTemp = new RequestTemp(tcpClientGateway.getSocket());
        requestTemp.request();

        // request the last sent ALIVE message from the server
        RequestAlive requestAlive = new RequestAlive(tcpClientGateway.getSocket());
        requestAlive.request();

        GatewayServerHandler gatewayServerHandler =
                new GatewayServerHandler(tcpClientGateway.getSocket(), gateway);
        gatewayServerHandler.start();

        // request last measured humidity value from the gateway

        Thread.sleep(9000);

        HumiditySendLastValueHandler humiditySendLastValueHandler =
                new HumiditySendLastValueHandler(humiditySensor, udpSenderHumidity.getSocket(), localHostAddress, udpListenerGateway.getPort());
        humiditySendLastValueHandler.start();

        GatewaySendLastHumidityValueHandler gatewaySendLastHumidityValueHandler =
                new GatewaySendLastHumidityValueHandler(udpListenerGateway.getSocket(), tcpClientGateway.getSocket());
        gatewaySendLastHumidityValueHandler.start();

        GatewayRequestLastHumidityHandler gatewayRequestLastHumidityHandler =
                new GatewayRequestLastHumidityHandler(udpListenerGateway.getSocket(), tcpClientGateway.getSocket(), localHostAddress, udpSenderHumidity.getPort());
        gatewayRequestLastHumidityHandler.start();

        ServerRequestLastHumidityHandler serverRequestLastHumidityHandler =
                new ServerRequestLastHumidityHandler(tcpServer.getSocket());
        serverRequestLastHumidityHandler.start();



    }
}