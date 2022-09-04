package client;

import Essentials.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Receiver implements Runnable
{
    private ObjectInputStream input;
    private Socket socket;
    private Message receiveMessage;

    public Receiver(Socket socket, ObjectInputStream input)
    {
        this.input = input;
        this.socket = socket;
    }

    @Override
    public void run()
    {
        while (socket.isConnected())
        {
            try
            {
                receiveMessage = (Message) input.readObject();
                System.out.println(receiveMessage.toString());
            }
            catch (ClassNotFoundException | IOException ignored)
            {
            }
        }
    }
}
