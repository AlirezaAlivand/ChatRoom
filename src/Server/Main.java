package Server;

import java.io.IOException;
import java.net.ServerSocket;

public class Main
{
    public static void main(String[] args)
    {
        try
        {
            ServerSocket serverSocket = new ServerSocket(10000);
            Server server = new Server(serverSocket);
            server.start();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
