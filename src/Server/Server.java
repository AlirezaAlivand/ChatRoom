package Server;

import client.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server
{
    private ServerSocket serverSocket;

    public Server(ServerSocket serverSocket)
    {
        this.serverSocket = serverSocket;
    }

    public void start()
    {
        try
        {
            while (!serverSocket.isClosed())
            {
                Socket socket = serverSocket.accept();

                ClientHandler clientHandler = new ClientHandler(socket);

                System.out.println("A new Client has connected!");

                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        }
        catch (IOException ignored)
        {

        }
    }
}
