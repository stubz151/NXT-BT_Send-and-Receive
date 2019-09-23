import lejos.nxt.LCD;
import lejos.nxt.Motor;

public class moveThread extends Thread {
    private int choice;

    public moveThread(int var1) {
        this.choice = var1;
    }

    public void run() {
        switch(this.choice) {
            case 157:
                LCD.drawString("Forward", 0, 0);
                LCD.refresh();
                Motor.A.setSpeed(360);
                Motor.B.setSpeed(360);
                Motor.A.rotate(720, true);
                Motor.B.rotate(720, true);
                break;
            case 205:
                LCD.drawString("Right", 0, 0);
                LCD.refresh();
                Motor.A.setSpeed(360);
                Motor.B.setSpeed(360);
                Motor.A.rotate(-505, true);
                Motor.B.rotate(505, true);
                break;
            case 279:
                LCD.drawString("LEFT", 0, 0);
                LCD.refresh();
                Motor.A.setSpeed(360);
                Motor.B.setSpeed(360);
                Motor.A.rotate(508, true);
                Motor.B.rotate(-508, true);
                break;
            case 327:
                LCD.drawString("Backwards", 0, 0);
                LCD.refresh();
                Motor.A.setSpeed(360);
                Motor.B.setSpeed(360);
                Motor.A.rotate(-720, true);
                Motor.B.rotate(-720, true);
                break;
            case 569:
                LCD.drawString("Ending", 0, 0);
                LCD.refresh();
                LCD.clear();
            case -1:
                LCD.drawString("Restarting", 0, 0);
                LCD.refresh();
                LCD.clear();
        }

       return;
    }
}