import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class BTReceive {
    public static void main(String[] args) throws IOException, InterruptedException {
        String connected = "Connected";
        String waiting = "Waiting...";
        String startThread = "StartingInputThread....";
        LCD.drawString (waiting, 0, 0);
        LCD.refresh ();
        DataInputStream dis = null;
        BTConnection btc = null;
        try {
           btc = Bluetooth.waitForConnection ();
            LCD.drawString (waiting, 0, 0);
            LCD.refresh ();
            LCD.clear ();
            LCD.drawString (connected, 0, 0);
            LCD.refresh ();
            dis=btc.openDataInputStream ();
            BTThread bthread = new BTThread (dis);
            LCD.refresh ();
            Thread.sleep (200);
            bthread.run ();
            bthread.join ();
            // DataOutputStream dos = btc.openDataOutputStream ();

        } catch (InterruptedException e )
        {
            System.out.println(e.getLocalizedMessage());
        }
        finally {
            dis.close ();
            if ( btc != null ) btc.close();

        }
    }
public void HandleInput()
{
     /*
            while(comms)
                {
                    //crashed,check out threads

                    int choice = dis.readInt ();

                    LCD.drawString ("The choice is "+ choice, 0, 0);
                    LCD.refresh ();
                    switch (choice){
                        case 1:
                            Motor.A.rotate(720,true);
                            Motor.B.rotate(720,true);
                            LCD.drawString ("Forward", 0, 0);
                            LCD.refresh ();
                            dos.writeInt (0);
                            dos.flush ();
                            break;
                        case 2:
                            LCD.drawString ("Backwards", 0, 0);
                            LCD.refresh ();

                            Motor.A.rotate(-720,true);
                            Motor.B.rotate(-720,true);
                            break;
                        case 3:
                            LCD.drawString ("Right", 0, 0);
                            LCD.refresh ();
                            Motor.B.rotateTo(90);
                            break;
                        case 4:
                            LCD.drawString ("LEFT", 0, 0);
                            LCD.refresh ();
                            Motor.A.rotateTo(90);
                            break;
                        case -1:
                            LCD.drawString ("EXIT", 0, 0);
                            LCD.refresh ();
                            comms=false;
                            keeplooping=false;
                            dis.close();
                            dos.close();
                            Thread.sleep(100); // wait for data to drain
                            LCD.clear();
                            LCD.drawString(closing,0,0);
                            LCD.refresh();
                            btc.close();
                            LCD.clear();
                            break;

                    }
                }
                }
    */
}
}