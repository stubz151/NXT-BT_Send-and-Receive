import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.NXT;
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
        //Attemps to start connection.
        try {
           btc = Bluetooth.waitForConnection ();
            LCD.drawString (waiting, 0, 0);
            LCD.refresh ();
            LCD.clear ();
            LCD.drawString (connected, 0, 0);
            LCD.refresh ();
            dis=btc.openDataInputStream ();
            //when the input stream has been obtained it starts the thread to handle it.
            BTThread bthread = new BTThread (dis);
            bthread.run ();
            //waits for thread to be over before moving on.
            bthread.join (500);
            // DataOutputStream dos = btc.openDataOutputStream ();
        } catch (InterruptedException e )
        {
            System.out.println(e.getLocalizedMessage());
        }
        finally {
            dis.close ();
            if (btc != null )
            {
                btc.close();
            }

        }
    }

}
