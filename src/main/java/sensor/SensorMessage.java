package main.java.sensor;

import java.util.Date;

public class SensorMessage {
    private final SENSOR_TYPE type;
    private int val;
    private Date timeStamp;

    public SensorMessage(SENSOR_TYPE type, int val, Date timeStamp){
        this.type = type;
        this.val = val;
        this.timeStamp = timeStamp;
    }

    public SensorMessage(SENSOR_TYPE type){
        this.type = type;
    }

    public byte[] getByteArr(){
        StringBuilder str = new StringBuilder();
        if(val == -1){
            str.append(type.getName()).append(" ALIVE ").append(timeStamp.toString().toUpperCase());
        }else{
            str.append(type.getName()).append(" ").append(val).append(" ").append(timeStamp.toString().toUpperCase());
        }

        return str.toString().getBytes();
    }

    public String getString(){
        StringBuilder stringBuilder = new StringBuilder();
        String str = stringBuilder.append(type.getName()).append(" ").append(val).append(" ").append(timeStamp.toString().toUpperCase()).toString();
        return str;
    }

    public String sendTCPSensorInfo(){
        return "CONNECTED DEVICE " + type.getName();
    }

    public byte[] sendUDPSensorInfo(){
        StringBuilder str = new StringBuilder();
        str.append("CONNECTED DEVICE ").append(type.getName());

        return str.toString().getBytes();
    }

    public int getVal() {
        return val;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }
}
