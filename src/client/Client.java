package client;

import java.io.*;
import java.net.Socket;

public class Client
{
    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private String username;
    private Receiver receiver;
    private Sender sender;
    private Thread senderThread;
    private Thread receiverThread;

    public Client(Socket socket, String username)
    {
        try
        {
            this.socket = socket;
            this.output = new ObjectOutputStream(socket.getOutputStream());
            this.input = new ObjectInputStream(socket.getInputStream());
            this.username = username;
            this.sender = new Sender(socket, output, input, username);
            this.receiver = new Receiver(socket, input);
        }
        catch (IOException e)
        {
            closeEverything(socket, input, output);
        }
    }

    public void run()
    {
        senderThread = new Thread(sender);
        receiverThread = new Thread(receiver);

        senderThread.start();
        receiverThread.start();
    }

    public static void closeEverything(Socket socket, ObjectInputStream input, ObjectOutputStream output)
    {
        try
        {
            if (input != null)
            {
                input.close();
            }
            if (output != null)
            {
                output.close();
            }
            if (socket != null)
            {
                socket.close();
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }



}
