package main.java.utils;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public class HttpUtils {

    public static void sendHtmlResponse(HttpExchange exchange, String htmlResponse) throws IOException {
        // Set content type to HTML
        exchange.getResponseHeaders().set("Content-Type", "text/html");

        // Set response status and length
        exchange.sendResponseHeaders(200, htmlResponse.length());

        // Write HTML response to the output stream
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(htmlResponse.getBytes());
        }
    }
}
