package main.java.gateway;

import main.java.CreateServerGatewayTCPConnectionService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class CreateGatewaySensorTCPConnectionService {
    private static CreateGatewaySensorTCPConnectionService instance;
    private Socket socket;
    private ServerSocket server;
    private int port;

    private CreateGatewaySensorTCPConnectionService(int port) throws IOException {
        server = new ServerSocket(port);
        socket = getServer().accept();

    }

    public static CreateGatewaySensorTCPConnectionService getInstance(int port) throws IOException {
        if(instance == null){
            instance = new CreateGatewaySensorTCPConnectionService(port);
        }

        return instance;
    }

    public Socket getSocket() {
        return socket;
    }

    public ServerSocket getServer() {
        return server;
    }

    public int getPort() {
        return port;
    }
}
