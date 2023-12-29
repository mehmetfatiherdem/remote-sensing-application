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
            str.append(type.getName()).append(" ALIVE ").append(timeStamp);
        }else{
            str.append(type.getName()).append(" ").append(val).append(" ").append(timeStamp);
        }

        return str.toString().getBytes();
    }

    public String getString(){
        StringBuilder stringBuilder = new StringBuilder();
        String str = stringBuilder.append(type.getName()).append(" ").append(val).append(" ").append(timeStamp).toString();
        return str;
    }

    public String sendTCPSensorInfo(){
        return "CONNECTED DEVICE INFO: " + type.getName();
    }

    public byte[] sendUDPSensorInfo(){
        StringBuilder str = new StringBuilder();
        str.append("CONNECTED DEVICE INFO: ").append(type.getName());

        return str.toString().getBytes();
    }

    public int getVal() {
        return val;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }
}
