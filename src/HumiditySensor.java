import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/*
    connected to gateway via UDP


 */
public class HumiditySensor {
    private final int minHumidity = 20;
    private final int maxHumidity = 30;
    private int lastMeasuredHumidity;
    private final String alive = "ALIVE";

    public HumiditySensor(){}

    public void sendHumidity(Gateway gateway){

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                int humidity = 0;
                Random rand = new Random();
                humidity = rand.nextInt(minHumidity, maxHumidity);
                lastMeasuredHumidity = humidity;
                if(humidity>80){
                    // TODO: send "humidity" with timestamp to Gateway

                }
            }
        };

        Timer timer = new Timer();

        timer.schedule(task, 0, 1000);

    }

    public void sendAlive(Gateway gateway){
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                //TODO: send "alive" message to gateway
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, 0, 3000);

    }

}
