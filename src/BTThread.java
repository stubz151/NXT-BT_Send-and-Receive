
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;


public class BTThread extends Thread {
    private DataInputStream dis;
    private boolean runner = true;
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

        for (int i =0 ; i< workingCommands.size();i++)
        {
                int choice = workingCommands.get(i);
                if (choice ==567)
                {
                    runner = false;
                    break;
                }
                else
                {
                    moveThread mThread = new moveThread(choice);
                    mThread.run();
                    try {
                        mThread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
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
