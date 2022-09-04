package client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        String username;
        Socket socket;

        System.out.println("Please enter your name : ");
        username = scanner.nextLine();

        try
        {
            socket = new Socket("127.0.0.1", 10000);
            Client client = new Client(socket, username);
            client.run();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}