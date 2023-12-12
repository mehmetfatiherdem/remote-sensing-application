import java.util.ArrayList;
/*
    reads values from sensors and sends them with timestamps
    to the server

    monitoring:
        time sensor fails to send anything
        for 3 secs -> "TEMP SENSOR OFF" to the server

        humidity sensor fails to send "ALIVE"
        msg for more than 7 secs -> "HUMIDITY SENSOR OFF"
        to the server

     DOES NOT HAVE A STORAGE UNIT!!!!!

 */
public class Gateway {
    public Gateway(){}

    public void listenSensor(ArrayList<SensorMessage> msg){

        for (SensorMessage m: msg) {
            switch (m.getSourceType()) {
                case TEMPERATURE -> {
                    System.out.println("Temp sensor sensed => " + m.getVal());
                    //TODO:
                }
                case HUMIDITY -> System.out.println("Humidity sensor sensed => " + m.getVal() + " or => " + m.getMsg());
                default -> System.out.println("invalid sensor");

                //TODO: implement a log functionality
            }
        }

    }

}
