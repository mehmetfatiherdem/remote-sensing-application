import java.util.Date;

public class SensorMessage {

    private SENSOR_TYPE sourceType;
    private int val;
    private String msg;
    private Date timeStamp;

    public SensorMessage(SENSOR_TYPE sourceType, int val, Date timeStamp){
        this.sourceType = sourceType;
        this.val = val;
        this.timeStamp = timeStamp;
    }

    public SensorMessage(SENSOR_TYPE sourceType, String msg){
        this.sourceType = sourceType;
        this.msg = msg;
    }

    public int getVal() {
        return val;
    }

    public String getMsg() {
        return msg;
    }

    public SENSOR_TYPE getSourceType() {
        return sourceType;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }
}
