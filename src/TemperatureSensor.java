import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/*
    connected to gateway via TCP

 */
public class TemperatureSensor {
    private final int minTemp = 20;
    private final int maxTemp = 30;
    private int lastMeasuredTemp;
    public TemperatureSensor(){}
    public void sendTemp(Gateway gateway){

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                int temp = 0;
                Random rand = new Random();
                temp = rand.nextInt(minTemp, maxTemp);
                lastMeasuredTemp = temp;
                // TODO: send "temp" with timestamp to Gateway
            }
        };

        Timer timer = new Timer();

        timer.schedule(task, 0, 1000);

    }

}
