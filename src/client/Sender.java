package client;

import Essentials.Message;
import Essentials.Type;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Sender implements Runnable
{
    private ObjectOutputStream output;
    private Socket socket;
    private Scanner scanner;
    private String contact;
    private String username;
    private Message sendMessage;
    private ObjectInputStream input;

    public Sender(Socket socket, ObjectOutputStream output, ObjectInputStream input, String username)
    {
        this.input = input;
        this.username = username;
        this.output = output;
        this.socket = socket;
        scanner = new Scanner(System.in);
    }

    @Override
    public void run()
    {
        try
        {
            sendMessage = new Message(username, Type.join);
            output.writeObject(sendMessage);

            while (socket.isConnected())
            {
                contact = scanner.nextLine();

                if(contact.equals("#exit"))
                {
                    sendMessage = new Message(username, Type.exit);
                    output.writeObject(sendMessage);
                    Client.closeEverything(socket, input, output);
                    System.exit(0);
                }
                else
                {
                    sendMessage = new Message(contact, username, Type.normal);
                    output.writeObject(sendMessage);
                }
            }
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
