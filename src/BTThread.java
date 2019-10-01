
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;


public class BTThread extends Thread {
    private DataInputStream dis;
    private boolean runner = true;
    private UltrasonicSensor sonicForward = new UltrasonicSensor(SensorPort.S1);
    private UltrasonicSensor sonicBackward = new UltrasonicSensor(SensorPort.S4);
    private ArrayList<Integer> workingCommands;

    public BTThread(DataInputStream input) {
        this.dis = input;
    }
    @Override
    public void run() {
        while (runner)
        {
        workingCommands = arrayBuilder();
        handleInput();
        }
        return;
    }

    private void handleInput() {

       for(int i =0 ; i< workingCommands.size(); i ++)
       {
                while(Motor.A.isMoving() || Motor.B.isMoving())
                {
                ;
                }
           try {
               Thread.sleep(500);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           int choice = workingCommands.get(i);

                if (choice == 567) {
                    runner = false;
                    break;
                }
                else {
                   move(choice);
                }
           try {
               Thread.sleep(1000);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       }
    }

    private void move(int choice)
    {
        switch(choice) {
            case 55:
                if (sonicForward.getDistance()<10)
                {
                    LCD.drawString("Blocked", 0, 0);
                    LCD.refresh();
                    break;
                }
                LCD.drawString("Forward", 0, 0);
                LCD.refresh();
                Motor.A.setSpeed(360);
                Motor.B.setSpeed(360);
                Motor.A.rotate(720, true);
                Motor.B.rotate(720, true);
                break;
            case 87:
                LCD.drawString("Right", 0, 0);
                LCD.refresh();
                Motor.A.setSpeed(360);
                Motor.B.setSpeed(360);
                Motor.A.rotate(-505, true);
                Motor.B.rotate(505, true);
                break;
            case 79:
                LCD.drawString("LEFT", 0, 0);
                LCD.refresh();
                Motor.A.setSpeed(360);
                Motor.B.setSpeed(360);
                Motor.A.rotate(508, true);
                Motor.B.rotate(-508, true);
                break;
            case 47:
                if (sonicForward.getDistance()<10)
                {
                    LCD.drawString("Blocked", 0, 0);
                    LCD.refresh();
                    break;
                }
                LCD.drawString("Backwards", 0, 0);
                LCD.refresh();
                Motor.A.setSpeed(360);
                Motor.B.setSpeed(360);
                Motor.A.rotate(-720, true);
                Motor.B.rotate(-720, true);
                break;

            case 155:
                boolean blocked=false;
                Motor.A.setSpeed(360);
                Motor.B.setSpeed(360);
                while(!blocked)
                {
                    if (sonicForward.getDistance()<10)
                    {
                        blocked=true;
                        LCD.drawString("Blocked", 0, 0);
                        LCD.refresh();
                        break;
                    }
                    LCD.drawString("ForwardLoop", 0, 0);
                    LCD.refresh();
                    Motor.A.rotate(720, true);
                    Motor.B.rotate(720, true);
                }
                break;
            case 91:
                boolean blockedBack=false;
                Motor.A.setSpeed(360);
                Motor.B.setSpeed(360);
                while(!blockedBack)
                {
                    if (sonicBackward.getDistance()<10)
                    {
                        blockedBack=true;
                        LCD.drawString("Blocked", 0, 0);
                        LCD.refresh();
                        break;
                    }
                    LCD.drawString("BackwardsLoop", 0, 0);
                    LCD.refresh();
                    Motor.A.rotate(-720, true);
                    Motor.B.rotate(-720, true);
                }
                break;

            case 569:
                LCD.drawString("Ending", 0, 0);
                LCD.refresh();
                LCD.clear();
                break;
            case -1:
                break;
            default:
                LCD.drawString("Start", 0, 0);
                LCD.refresh();
                break;
        }
    }

    private ArrayList<Integer> arrayBuilder()
    {
        ArrayList<Integer> commands = new ArrayList<>();
        while (true)
        {
                try {
                int choice = dis.readInt();
                commands.add(choice);
                if (choice==-1)
                {
                    return commands;
                }

            }catch (IOException e) {
                    e.printStackTrace();
        }

        }

    }
}
