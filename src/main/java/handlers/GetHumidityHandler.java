package main.java.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.java.server.Server;
import main.java.utils.HttpUtils;

import java.io.IOException;


public class GetHumidityHandler implements HttpHandler {
    private Server server;

    public GetHumidityHandler(Server server) {
        this.server = server;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Get the last measured humidity value from the server
        int lastHumidityValue = 75; //TODO: Implement getLastHumidityValue() method and call it in here //server.getLastHumidityValue();

        // Generate HTML content for the last measured humidity value
        String htmlResponse = "<html><body><h1>Last Measured Humidity Value</h1><p>" + lastHumidityValue + "</p></body></html>";

        // Send HTML response
        HttpUtils.sendHtmlResponse(exchange, htmlResponse);
    }
}