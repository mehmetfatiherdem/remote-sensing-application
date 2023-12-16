package main.java;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Gateway gateway = new Gateway(5000, 4445);

        TemperatureSensor tempSensor = new TemperatureSensor();
        TCPClient socket = new TCPClient(tempSensor, "127.0.0.1", 5000);
        socket.sendMessage();

        HumiditySensor humiditySensor = new HumiditySensor();
        UDPClient udpClient = new UDPClient(4445, humiditySensor);

        udpClient.sendMessage();


    }
}