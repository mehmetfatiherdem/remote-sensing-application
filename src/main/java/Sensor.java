package main.java;

import java.util.Date;
import java.util.Random;

public abstract class Sensor {
    private final int minVal;
    private final int maxVal;
    private int lastMeasuredVal;

    public Sensor(int minVal, int maxVal){
        this.minVal = minVal;
        this.maxVal = maxVal;
    }
    public int generateValue(){
        Random rand = new Random();
        int value = rand.nextInt(minVal, maxVal);
        lastMeasuredVal = value;
        return value;
    }

    public SensorMessage generateMessage(){
        return new SensorMessage(generateValue(), new Date());
    }

    public abstract SENSOR_TYPE getType();

    public int getMinVal() {
        return minVal;
    }

    public int getMaxVal() {
        return maxVal;
    }

    public int getLastMeasuredVal() {
        return lastMeasuredVal;
    }
}
