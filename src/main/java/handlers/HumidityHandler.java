package main.java.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.java.server.Server;
import main.java.utils.HttpUtils;

import java.io.IOException;
import java.util.ArrayList;

public class HumidityHandler implements HttpHandler {
    private Server server;

    public HumidityHandler(Server server) {
        this.server = server;
    }
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Get the latest humidity values from the server
        ArrayList<Integer> latestHumidityValues = server.getHumidityVal();

        // Generate HTML content for humidity with actual data
        StringBuilder htmlResponse = new StringBuilder("<html><body><h1>Humidity Data</h1><p>Latest Humidity Values:</p><ul>");

        for (Integer humidityValue : latestHumidityValues) {
            htmlResponse.append("<li>").append(humidityValue).append("</li>");
        }

        htmlResponse.append("</ul></body></html>");

        // Send HTML response
        HttpUtils.sendHtmlResponse(exchange, htmlResponse.toString());
    }

}