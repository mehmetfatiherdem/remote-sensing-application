package main.java;

import java.io.IOException;

public class CreateTCPConnectionService {
    private Gateway gateway;
    private Server server;

    public CreateTCPConnectionService(Gateway gateway, Server server){
        this.gateway = gateway;
        this.server = server;
    }

    public void init() throws IOException {

        //TCPHandler tcpHandler = new TCPHandler(Constants.gatewaySensorTCPPort, Constants.serverGatewayTCPPort);
        //tcpHandler.start();
    }
}
