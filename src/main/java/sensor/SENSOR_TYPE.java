package main.java.sensor;

public enum SENSOR_TYPE {
    TEMPERATURE("TEMP SENSOR"),
    HUMIDITY("HUMIDITY SENSOR");

    private final String name;
    SENSOR_TYPE(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
}
