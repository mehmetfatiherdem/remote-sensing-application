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


}
