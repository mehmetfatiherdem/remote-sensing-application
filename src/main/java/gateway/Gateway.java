package main.java.gateway;

import java.io.IOException;
import java.net.SocketException;

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
    private static Gateway instance;

    private Gateway(){
    }

    public static Gateway getInstance() throws IOException {
        if (instance == null) {
            instance = new Gateway();
        }
        return instance;
    }

    public void handleUDPClient(int port) throws SocketException {
        GatewayHumidityHandler gatewayHumidityHandler = new GatewayHumidityHandler(port);
        gatewayHumidityHandler.start();
    }

    public void handleTCPClient(int port) throws IOException {
        GatewayTempHandler gatewayTempHandler = new GatewayTempHandler(port);
        gatewayTempHandler.start();
    }

}