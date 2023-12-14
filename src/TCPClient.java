import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class TCPClient {
    private Socket socket;
    private DataOutputStream out;

    public TCPClient(TemperatureSensor tempSensor, String address, int port) {
        try {
            socket = new Socket(address, port);
            System.out.println("Connected");
            out = new DataOutputStream(
                    socket.getOutputStream());
        } catch (IOException i) {
            System.out.println(i);
            return;
        }

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                    try {
                        var temp = tempSensor.generateMessage();
                        out.writeUTF("Temperature Sensor: " + temp.getVal() + " at " + temp.getTimeStamp());
                    } catch (IOException i) {
                        System.out.println(i);
                    }

            }
        };

        Timer timer = new Timer();
        timer.schedule(task, 0, 1000);

        //Close connection logic??


    }

    public static void main(String[] args) {
        TemperatureSensor tempSensor = new TemperatureSensor();
        TCPClient socket = new TCPClient(tempSensor, "127.0.0.1", 5000);


    }
}