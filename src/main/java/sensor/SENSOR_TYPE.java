package main.java.sensor;

public enum SENSOR_TYPE {
    TEMPERATURE("Temperature Sensor"),
    HUMIDITY("Humidity Sensor");

    private final String name;
    SENSOR_TYPE(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
}
