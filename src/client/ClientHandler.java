package client;

import Essentials.Message;
import Essentials.Type;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable
{
    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private String username;
    private static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Message inputMessage;


    public ClientHandler(Socket socket)
    {
        try
        {
            this.socket = socket;
            this.output = new ObjectOutputStream(socket.getOutputStream());
            this.input = new ObjectInputStream(socket.getInputStream());
            this.username = ((Message)input.readObject()).getSenderUsername();
            clientHandlers.add(this);

            sendToALl(new Message(username, Type.join));
        }
        catch (IOException | ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void run()
    {
        while (socket.isConnected())
        {
            try
            {
                inputMessage = (Message)input.readObject();

                if(inputMessage.getType() == Type.exit)
                {
                    closeEverything(socket, input, output);
                    sendToALl(inputMessage);
                }
                else
                {
                    sendToALl(inputMessage);
                }
            }
            catch (IOException | ClassNotFoundException ignored)
            {
                closeEverything(socket, input, output);
            }
        }
    }


    public void sendToALl(Message message)
    {
        try
        {
            if (message.getType() == Type.join || message.getType() == Type.exit)
            {
                for (ClientHandler clientHandler : clientHandlers)
                {
                    clientHandler.output.writeObject(message);
                }
            }
            else
            {
                for (ClientHandler clientHandler : clientHandlers)
                {
                    if(!(message.getSenderUsername().equals(clientHandler.username)))
                    {
                        clientHandler.output.writeObject(message);
                    }
                }
            }
        }
        catch (IOException e)
        {
            closeEverything(socket, input, output);
        }
    }

    public void removeClientHandler()
    {
        clientHandlers.remove(this);
    }

    public void closeEverything(Socket socket, ObjectInputStream input, ObjectOutputStream output)
    {
        removeClientHandler();
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
}
