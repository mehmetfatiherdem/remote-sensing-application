package main.java;

import java.util.Date;

public class SensorMessage {

    private SENSOR_TYPE sourceType;
    private int msg;
    private Date timeStamp;

    public SensorMessage(SENSOR_TYPE sourceType, int msg, Date timeStamp){
        this.sourceType = sourceType;
        this.msg = msg;
        this.timeStamp = timeStamp;
    }

    public int getMsg() {
        return msg;
    }

    public SENSOR_TYPE getSourceType() {
        return sourceType;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }
}
