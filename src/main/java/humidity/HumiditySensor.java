package main.java.humidity;

import main.java.sensor.SENSOR_TYPE;
import main.java.sensor.Sensor;
import main.java.sensor.SensorMessage;

import java.util.Date;

/*
    connected to gateway via UDP


 */
public class HumiditySensor extends Sensor {

    public HumiditySensor(int minVal, int maxVal){
        super(minVal, maxVal);
    }

    @Override
    public SENSOR_TYPE getType() {
        return SENSOR_TYPE.HUMIDITY;
    }

    public boolean isGreaterThanThreshold(int humidity){
        return humidity > 80;
    }
    public int generateAliveValue(){
        return -1; // -1 means alive in our context since we will be sending byte[] in UDP connections
        // dealing with strings makes our life harder
    }
    public SensorMessage generateAliveMessage(){
        return new SensorMessage(getType(), generateAliveValue(), new Date());
    }

    public SensorMessage generateLastValueMessage(){
        return new SensorMessage(getType(), getLastMeasuredVal());
    }

}
