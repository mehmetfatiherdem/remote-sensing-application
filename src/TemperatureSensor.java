import java.util.Random;

/*
    connected to gateway via TCP

 */
public class TemperatureSensor {
    private final int minTemp = 20;
    private final int maxTemp = 30;
    private int lastMeasuredTemp;
    public TemperatureSensor(){}
    public int generateTemp(){
        Random rand = new Random();
        int temp = rand.nextInt(minTemp, maxTemp);
        lastMeasuredTemp = temp;
        return temp;
    }


}
