import java.util.Random;

/*
    connected to gateway via UDP


 */
public class HumiditySensor {
    private final int minHumidity = 20;
    private final int maxHumidity = 30;
    private int lastMeasuredHumidity;
    private final String alive = "ALIVE";

    public HumiditySensor(){}
    public int generateHumidity(){
        Random rand = new Random();
        int humidity = rand.nextInt(minHumidity, maxHumidity);
        lastMeasuredHumidity = humidity;
        return humidity;
    }
    public boolean isGreaterThanThreshold(int humidity){
        return humidity > 80;
    }
    public String generateAliveMessage(){
        return  alive;
    }

}
