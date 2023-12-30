package main.java;

import main.java.gateway.*;
import main.java.humidity.HumidityCommunicationHandler;
import main.java.humidity.HumiditySendInfoHandler;
import main.java.humidity.HumiditySensor;
import main.java.server.Server;
import main.java.server.ServerCommunicationHandler;
import main.java.tcp.CreateTCPClient;
import main.java.tcp.CreateTCPServer;
import main.java.temp.TempCommunicationHandler;
import main.java.temp.TempSendInfoHandler;
import main.java.temp.TemperatureSensor;
import main.java.udp.CreateUDPListener;
import main.java.udp.CreateUDPSender;
import main.java.utils.Constants;

import java.io.IOException;
import java.net.InetAddress;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;

import static main.java.AdvancedLogger.*;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        initLogger();

        logInfo("Application starting...");
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
            logInfo("Server up&running on port " + tcpServer.getPort());
        }

        if(tcpServer.getSocket() == null){
            logInfo("Server is waiting for a Client....");
        }

        // Gateway TCP connection waiting for Sensor
        CreateTCPServer tcpServerGateway = new CreateTCPServer(Constants.gatewaySensorTCPPort);
        tcpServerGateway.start();

        // wait a little for gateway to start
        Thread.sleep(1000);

        if(tcpServerGateway.getServer() != null){
            logInfo("Gateway waiting for TCP Sensor Client on port " + tcpServerGateway.getPort());
        }

        // Temperature sensor
        CreateTCPClient tcpTemp = new CreateTCPClient(localHostAddress, Constants.gatewaySensorTCPPort);
        tcpTemp.start();

        // wait a little for sensor to start
        Thread.sleep(1000);

        if(tcpServerGateway.getSocket() != null){
            logInfo("Temperature Sensor is connected to the gateway on port " + tcpServerGateway.getPort());
        }

        // Gateway UDP socket
        CreateUDPListener udpGateway = new CreateUDPListener(Constants.gatewaySensorUDPPort);
        udpGateway.start();

        // wait a little for gateway to start
        Thread.sleep(1000);

        if(udpGateway.getSocket() != null){
            logInfo("Gateway UDP socket is up&running on port " + udpGateway.getPort());
        }

        // Humidity Sensor udp socket
        CreateUDPSender udpHumidity = new CreateUDPSender(localHostAddress, Constants.gatewaySensorUDPPort);
        udpHumidity.start();

        // wait a little for sensor to start
        Thread.sleep(1000);

        if(udpHumidity.getSocket() != null){
            logInfo("Humidity sensor udp socket is ready on port " + udpHumidity.getPort());
        }

        // TCP client socket of Gateway to signal server
        CreateTCPClient tcpClientGateway = new CreateTCPClient(localHostAddress, Constants.serverGatewayTCPPort);
        tcpClientGateway.start();

        // wait a little for gateway to start
        Thread.sleep(1000);

        if(tcpServer.getSocket() != null){
            logInfo("Gateway is connected to the server on port " + tcpServer.getPort());
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
                new HumiditySendInfoHandler(humiditySensor, udpHumidity.getSocket(), udpHumidity.getAddress(), udpHumidity.getPort());
        humiditySendInfoHandler.sendMessage();

        GatewaySendTempSensorInfoToServerHandler gatewaySendTempSensorInfoToServerHandler =
                new GatewaySendTempSensorInfoToServerHandler(tcpClientGateway.getSocket(), tcpServerGateway.getSocket());
        gatewaySendTempSensorInfoToServerHandler.start();

        GatewaySendHumiditySensorInfoToServerHandler gatewaySendHumiditySensorInfoToServerHandler =
                new GatewaySendHumiditySensorInfoToServerHandler(udpGateway.getSocket(), tcpClientGateway.getSocket());
        gatewaySendHumiditySensorInfoToServerHandler.start();


        Thread.sleep(1000); // add a delay here before starting to send the data


        //  initialize the client instances with the corresponding sensors
        HumidityCommunicationHandler humiditySensorClient =
                new HumidityCommunicationHandler(humiditySensor, udpHumidity.getSocket(), udpHumidity.getAddress(), udpHumidity.getPort());
        TempCommunicationHandler tempSensorClient =
                new TempCommunicationHandler(tempSensor, tcpTemp.getSocket());

        // start sending the messages from the clients
        tempSensorClient.sendMessage();

        humiditySensorClient.sendMessage();

        GatewayTempHandler gatewayTempHandler =
                new GatewayTempHandler(tcpClientGateway.getSocket(), tcpServerGateway.getSocket());
        gatewayTempHandler.start();

        GatewayHumidityHandler gatewayHumidityHandler =
                new GatewayHumidityHandler(udpGateway.getSocket(), tcpClientGateway.getSocket());
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
        logInfo("Application exiting...");
    }
    private static void initLogger() {
        logInfo("Initializing logger...");

        try {
            LOGGER.setLevel(Level.INFO);

            // Create a console handler
            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setLevel(Level.INFO);
            LOGGER.addHandler(consoleHandler);

            // Create a file handler with a custom formatter
            FileHandler fileHandler = new FileHandler("logfile.log");
            fileHandler.setLevel(Level.INFO);
            fileHandler.setFormatter(new SimpleFormatter()); // Use a SimpleFormatter for human-readable logs
            LOGGER.addHandler(fileHandler);

            logInfo("Logger initialized successfully.");
        } catch (SecurityException | IOException e) {
            logInfo("SecurityException during logger initialization: " + e.getMessage());
        }
    }

}