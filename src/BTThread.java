
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
    private UltrasonicSensor sonicForward = new UltrasonicSensor(SensorPort.S1); // sensor forward
    private UltrasonicSensor sonicBackward = new UltrasonicSensor(SensorPort.S4); //sensor backward
    private ArrayList<Integer> workingCommands; //list of commands to work with.
    private Boolean loopRunning =false;
    public BTThread(DataInputStream input) {
        this.dis = input;
    }
    @Override
    public void run() {
        while (runner)
        {
            loopRunning=false;
            workingCommands = arrayBuilder();
            LCD.drawString("Got array ", 0, 0);
            LCD.refresh();
            try {
                handleInput();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LCD.drawString("finished array", 0, 0);
            LCD.refresh();
            }
            return;
    }

    private void handleInput() throws InterruptedException {
       for(int i =0 ; i< workingCommands.size(); i ++)
       {
           //loops through array, if a or b is moving it waits for them to stop before moving onto the next command.
                while(loopRunning || Motor.A.isMoving() || Motor.B.isMoving())
                {
                    Thread.sleep(5);
                }

           //567 represents an int that shutdown the app and closes the connection. is send on the android side when quit is pressed.
           int choice = workingCommands.get(i);
                if (choice == 567) {
                    runner = false;
                    break;
                }
                else {
                    //does the move
                   move(choice);
                }
       }
    }


    private void move(int choice)
    {
        int distanceToTravel =850; //this variable needs to be played with to determine the correct distance each move should take, recommend using a seperate program called MovementTester to do so.

        switch(choice) {
            //forward,
            case 55: {
                if (sonicForward.getDistance() < 20) {
                    LCD.drawString("Blocked", 0, 0);
                    LCD.refresh();
                    break;
                }
                LCD.drawString("Forward", 0, 0);
                LCD.refresh();
                Motor.A.setSpeed(360);
                Motor.B.setSpeed(360);
                Motor.A.rotate(distanceToTravel, true);
                Motor.B.rotate(distanceToTravel, true);
                break;
            }
                //repeat forward 4 times
            case 109: {
                loopRunning=true;
                for (int i = 0; i < 4; i++) {
                    if (sonicForward.getDistance() < 20) {
                        LCD.drawString("Blocked", 0, 0);
                        LCD.refresh();
                        i = 4;
                        break;
                    }
                    LCD.drawString("Forward", 0, 0);
                    LCD.refresh();
                    Motor.A.setSpeed(360);
                    Motor.B.setSpeed(360);
                    Motor.A.rotate(distanceToTravel, true);
                    Motor.B.rotate(distanceToTravel, true);
                    while (Motor.A.isMoving() || Motor.B.isMoving()) {
                        ;
                    }
                }
                loopRunning=false;
                break;
            }
                //repeat forward 3 times
            case 107: {
                loopRunning=true;
                for (int i = 0; i < 3; i++) {
                    if (sonicForward.getDistance() < 20) {
                        LCD.drawString("Blocked", 0, 0);
                        LCD.refresh();
                        i = 3;
                        break;
                    }
                    LCD.drawString("Forward", 0, 0);
                    LCD.refresh();
                    Motor.A.setSpeed(360);
                    Motor.B.setSpeed(360);
                    Motor.A.rotate(distanceToTravel, true);
                    Motor.B.rotate(distanceToTravel, true);
                    while (Motor.A.isMoving() || Motor.B.isMoving()) {
                        ;
                    }

                }
                loopRunning=false;
                break;
            }
                //turn right, notice how motor and b are rotated at same speed just reverse angles.
            case 87: {
                LCD.drawString("Right", 0, 0);
                LCD.refresh();
                Motor.A.setSpeed(360);
                Motor.B.setSpeed(360);
                Motor.A.rotate(-550, true);
                Motor.B.rotate(550, true);
                while (Motor.A.isMoving() || Motor.B.isMoving()) {
                    ;
                }
                break;
            }
            case 79: {
                //turn left
                LCD.drawString("LEFT", 0, 0);
                LCD.refresh();
                Motor.A.setSpeed(360);
                Motor.B.setSpeed(360);
                Motor.A.rotate(550, true);
                Motor.B.rotate(-550, true);
                while (Motor.A.isMoving() || Motor.B.isMoving()) {
                    ;
                }
                break;
            }
            case 47:
                //backwards
            {
                if (sonicBackward.getDistance() < 20) {
                    LCD.drawString("Blocked", 0, 0);
                    LCD.refresh();
                    break;
                }
                LCD.drawString("Backwards", 0, 0);
                LCD.refresh();
                Motor.A.setSpeed(360);
                Motor.B.setSpeed(360);
                Motor.A.rotate(-distanceToTravel, true);
                Motor.B.rotate(-distanceToTravel, true);
                break;
            }
            //while not blocked move forwards
            case 155: {
                loopRunning=true;
                boolean blocked = false;
                Motor.A.setSpeed(360);
                Motor.B.setSpeed(360);
                while (!blocked) {
                    if (sonicForward.getDistance() < 20) {
                        blocked = true;
                        LCD.drawString("Blocked", 0, 0);
                        LCD.refresh();
                        break;
                    }
                    LCD.drawString("Forward Loop", 0, 0);
                    LCD.refresh();
                    Motor.A.rotate(distanceToTravel, true);
                    Motor.B.rotate(distanceToTravel, true);
                    while (Motor.A.isMoving() || Motor.B.isMoving()) {
                        ;
                    }
                }
                loopRunning=false;
                break;
            }
            case 91:
                //while not blocked at the back move backwards
            {
                loopRunning=true;
                boolean blockedBack = false;
                Motor.A.setSpeed(360);
                Motor.B.setSpeed(360);
                while (!blockedBack) {
                    if (sonicBackward.getDistance() < 10) {
                        blockedBack = true;
                        LCD.drawString("Blocked", 0, 0);
                        LCD.refresh();
                        break;
                    }

                    LCD.drawString("Backwards Loop", 0, 0);
                    LCD.refresh();
                    Motor.A.rotate(-distanceToTravel, true);
                    Motor.B.rotate(-distanceToTravel, true);
                    while (Motor.A.isMoving() || Motor.B.isMoving()) {
                        ;
                    }
                }
                loopRunning=false;
                break;
            }

            case 569: {
                LCD.drawString("Ending", 0, 0);
                LCD.refresh();
                LCD.clear();
                break;
            }
            case -1: {
                break;
            }
            default: {
                LCD.drawString("Start", 0, 0);
                LCD.refresh();
                break;
            }
        }
    }
    //Takes the input stream and builds array, it keeps listening till -1 is sent
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
