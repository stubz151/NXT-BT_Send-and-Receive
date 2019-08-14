import lejos.nxt.LCD;
import lejos.nxt.Motor;
import java.io.DataInputStream;
import java.io.IOException;
public class BTThread extends Thread {
    private DataInputStream dis;
    private boolean runner = true;
    public BTThread(DataInputStream input) {
        this.dis = input;
    }
    @Override
    public void run() {
        while (runner)
        {
            handleInput();
        }
    }
    private void handleInput() {
        try {
          int choice = dis.readInt ();
            switch (choice) {
                case 1:
                    LCD.drawString ("Forward", 0, 0);
                    LCD.refresh ();
                    Motor.A.setSpeed(360);
                    Motor.B.setSpeed(360);
                    Motor.A.rotate (720, true);
                    Motor.B.rotate (720, true);
                    break;
                case 2:
                    LCD.drawString ("Backwards", 0, 0);
                    LCD.refresh ();
                    Motor.A.setSpeed(360);
                    Motor.B.setSpeed(360);
                    Motor.A.rotate (-720, true);
                    Motor.B.rotate (-720, true);
                    break;
                case 3:
                    LCD.drawString ("Right", 0, 0);
                    LCD.refresh ();
                    Motor.A.setSpeed(360);
                    Motor.B.setSpeed(360);
                    Motor.A.rotate (-505, true);
                    Motor.B.rotate (505, true);
                    break;
                case 4:
                    LCD.drawString ("LEFT", 0, 0);
                    LCD.refresh ();
                    Motor.A.setSpeed(360);
                    Motor.B.setSpeed(360);
                    Motor.A.rotate (508, true);
                    Motor.B.rotate (-508, true);
                    break;
                case -1:
                    LCD.drawString ("EXIT", 0, 0);
                    LCD.refresh ();
                    LCD.clear ();
                    LCD.drawString ("closing", 0, 0);
                    LCD.refresh ();
                    runner = false;
                    break;

            }
        } catch (IOException e) {
            e.printStackTrace ();
        }


    }
}
