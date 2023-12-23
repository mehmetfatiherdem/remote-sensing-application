package main.java.temp;

import main.java.sensor.SENSOR_TYPE;
import main.java.sensor.Sensor;

/*
    connected to gateway via TCP

 */
public class TemperatureSensor extends Sensor {

    public TemperatureSensor(int minVal, int maxVal){
        super(minVal, maxVal);
    }

    @Override
    public SENSOR_TYPE getType() {
        return SENSOR_TYPE.TEMPERATURE;
    }

}
