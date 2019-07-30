
import lejos.pc.comm.NXTCommLogListener;
import lejos.pc.comm.NXTConnector;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        NXTConnector conn = new NXTConnector();
        conn.addLogListener (new NXTCommLogListener () {
            @Override
            public void logEvent(String s) {
                System.out.println ("BTSend Log.Listener: " + s);
            }

            @Override
            public void logEvent(Throwable throwable) {
                System.out.println("BTSend Log.listener - stack trace: ");
                throwable.printStackTrace();
            }
        });
        boolean connected = conn.connectTo("btspp://");
        if (!connected)
        {
            System.err.println("Failed to connect to any NXT");
            System.exit(1);
        }
        System.out.println ("connected obtaining streams");
        DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
        DataInputStream dis = new DataInputStream(conn.getInputStream());
        Boolean doLoop = true;
        //simple loop gives choice, sends choice. hopefully robot does choice.
        while(doLoop)
        {
            BufferedReader buffer=new BufferedReader(new InputStreamReader (System.in));
            System.out.println ("THESE ARE YOUR CHOICES HUMAN");
            System.out.println ("1 move forward, 2 move back, 3 right, 4 left, -1 exit");
            String line=buffer.readLine();
            int choice = Integer.parseInt (line);
            switch (choice){
                case RobotCommands.MOVE_FORWARD:
                    System.out.println ("Moving Forward");
                    try {
                        dos.writeInt (1);
                        dos.flush ();
                    } catch (IOException e) {
                        System.out.println("IO Exception writing bytes:");
                        System.out.println(e.getMessage());
                        e.printStackTrace ();
                    }
                    break;
                case RobotCommands.MOVE_BACK:
                    System.out.println ("Moving Back");
                    try {
                        dos.writeInt (2);
                        dos.flush ();
                    } catch (IOException e) {
                        System.out.println("IO Exception writing bytes:");
                        System.out.println(e.getMessage());
                        e.printStackTrace ();
                    }
                    break;
                case RobotCommands.TURN_LEFT:
                    System.out.println ("Turning Left");
                    try {
                        dos.writeInt (RobotCommands.TURN_LEFT);
                        dos.flush ();
                    } catch (IOException e) {
                        System.out.println("IO Exception writing bytes:");
                        System.out.println(e.getMessage());
                        e.printStackTrace ();
                    }
                    break;
                case RobotCommands.TURN_RIGHT:
                    System.out.println ("Turning right");
                    try {
                        dos.writeInt (RobotCommands.TURN_RIGHT);
                        dos.flush ();
                    } catch (IOException e) {
                        System.out.println("IO Exception writing bytes:");
                        System.out.println(e.getMessage());
                        e.printStackTrace ();
                    }
                    break;
                case RobotCommands.CMD_EXIT:
                    System.out.println ("Exiting");
                    try {
                        dos.writeInt (RobotCommands.CMD_EXIT);
                        dos.flush ();
                        doLoop=false;
                    } catch (IOException e) {
                        System.out.println("IO Exception writing bytes:");
                        System.out.println(e.getMessage());
                        e.printStackTrace ();
                    }
                    break;

            }
            }
        dis.close();
        dos.close();
        conn.close();


    }

}
