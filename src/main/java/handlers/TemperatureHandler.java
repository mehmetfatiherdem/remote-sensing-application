package main.java.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.java.server.Server;
import main.java.utils.HttpUtils;

import java.io.IOException;
import java.util.ArrayList;

public class TemperatureHandler implements HttpHandler {
    private Server server;

    public TemperatureHandler(Server server) {
        this.server = server;
    }
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Get the latest temperature values from the server
        ArrayList<Integer> latestTemperatureValues = server.getTempVal();

        // Generate HTML content for temperature with actual data
        StringBuilder htmlResponse = new StringBuilder("<html><body><h1>Temperature Data</h1><p>Latest Temperature Values:</p><ul>");

        for (Integer temperatureValue : latestTemperatureValues) {
            htmlResponse.append("<li>").append(temperatureValue).append("</li>");
        }

        htmlResponse.append("</ul></body></html>");

        // Send HTML response
        HttpUtils.sendHtmlResponse(exchange, htmlResponse.toString());
    }

}