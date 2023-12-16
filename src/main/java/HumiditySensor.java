package main.java;

import java.util.Random;

/*
    connected to gateway via UDP


 */
public class HumiditySensor {
    private final int minHumidity = 40;
    private final int maxHumidity = 90;
    private int lastMeasuredHumidity;

    public HumiditySensor(){}
    public int generateHumidity(){
        Random rand = new Random();
        int humidity = rand.nextInt(minHumidity, maxHumidity);
        lastMeasuredHumidity = humidity;
        return humidity;
    }
    public boolean isGreaterThanThreshold(int humidity){
        return humidity > 80;
    }
    public int generateAliveMessage(){
        return -1; // -1 means alive in our context since we will be sending byte[] in UDP connections
        // dealing with strings makes our life harder
    }

    public int getMinHumidity() {
        return minHumidity;
    }

    public int getMaxHumidity() {
        return maxHumidity;
    }

    public int getLastMeasuredHumidity() {
        return lastMeasuredHumidity;
    }
}
