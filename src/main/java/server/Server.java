package main.java.server;

import main.java.utils.Helpers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class Server {
    private static Server instance;

    /*
        TODO: Well the PDF says all the values will be stored in the server
        TODO: am not sure if we should just use field for this or a txt file
        TODO: so for now lets keep this
     */
    private final ArrayList<Integer> tempVal = new ArrayList<>();
    private final ArrayList<Date> tempMsgTimeStamp = new ArrayList<>();
    private final ArrayList<Date> aliveMsgTimeStamp = new ArrayList<>();
    private final ArrayList<Integer> humidityVal = new ArrayList<>();
    private final ArrayList<Date> humidityMsgTimeStamp = new ArrayList<>();

    private boolean isTempSensorOff = false;
    private boolean isHumiditySensorOff = false;

    public Server() {

    }

    public static Server getInstance() throws IOException {
        if (instance == null) {
            instance = new Server();
        }
        return instance;
    }

    public void breakDownMessageAndStore(String[] msgElements){

        String timeStampString = msgElements[3] + " " +
                msgElements[4] + " " + msgElements[5] +
                " " + msgElements[6] + " " + msgElements[7] +
                " " + msgElements[8];

        Date timeStamp = Helpers.strToDate(timeStampString);

        if(msgElements[0].equals("Temperature")){
            int tempVal = Integer.parseInt(msgElements[2]);
            addTempVal(tempVal);
            addTempMsgTimeStamp(timeStamp);

        } else if (msgElements[0].equals("Humidity")) {
            if(msgElements[2].equals("ALIVE")){
                addAliveMsgTimeStamp(timeStamp);
            }else{
                int humidityVal = Integer.parseInt(msgElements[2]);
                addHumidityVal(humidityVal);
                addHumidityMsgTimeStamp(timeStamp);
            }
        }
    }

    public ArrayList<Integer> getTempVal() {
        return tempVal;
    }

    public ArrayList<Date> getAliveMsgTimeStamp() {
        return aliveMsgTimeStamp;
    }

    public ArrayList<Integer> getHumidityVal() {
        return humidityVal;
    }

    public ArrayList<Date> getTempMsgTimeStamp() {
        return tempMsgTimeStamp;
    }

    public ArrayList<Date> getHumidityMsgTimeStamp() {
        return humidityMsgTimeStamp;
    }

    public void addTempVal(int val){
        tempVal.add(val);
    }
    public void addHumidityVal(int val){
        humidityVal.add(val);
    }
    public void addHumidityMsgTimeStamp(Date timestamp){
        humidityMsgTimeStamp.add(timestamp);
    }

    public void addTempMsgTimeStamp(Date timestamp){
        tempMsgTimeStamp.add(timestamp);
    }

    public void addAliveMsgTimeStamp(Date timestamp){
        aliveMsgTimeStamp.add(timestamp);
    }

    public boolean isTempSensorOff() {
        return isTempSensorOff;
    }

    public void setTempSensorOff(boolean tempSensorOff) {
        isTempSensorOff = tempSensorOff;
    }

    public boolean isHumiditySensorOff() {
        return isHumiditySensorOff;
    }

    public void setHumiditySensorOff(boolean humiditySensorOff) {
        isHumiditySensorOff = humiditySensorOff;
    }
}
