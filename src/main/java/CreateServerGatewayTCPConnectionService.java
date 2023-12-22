package main.java;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class CreateServerGatewayTCPConnectionService {
    private static CreateServerGatewayTCPConnectionService instance;
    private Socket socket;
    private ServerSocket server;
    private int port;

    private CreateServerGatewayTCPConnectionService(int port) throws IOException {
        server = new ServerSocket(port);
        socket = getServer().accept();

    }

    public static CreateServerGatewayTCPConnectionService getInstance(int port) throws IOException {
        if(instance == null){
            instance = new CreateServerGatewayTCPConnectionService(port);
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
