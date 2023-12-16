package main.java;

import java.util.Date;

public class SensorMessage {
    private int val;
    private Date timeStamp;

    public SensorMessage(int val, Date timeStamp){
        this.val = val;
        this.timeStamp = timeStamp;
    }

    public byte[] getByteArr(){
        StringBuilder str = new StringBuilder();
        if(val == -1){
            str.append("ALIVE at ").append(timeStamp);
        }else{
            str.append(val).append(" at ").append(timeStamp);
        }

        return str.toString().getBytes();
    }

    public int getVal() {
        return val;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }
}
