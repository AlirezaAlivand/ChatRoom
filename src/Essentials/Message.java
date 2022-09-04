package Essentials;

import java.io.IOException;
import java.io.Serializable;



public class Message implements Serializable
{
    private String content;
    private String userNameSender;
    private Type type;

    public Message(String userNameSender, Type type)
    {
        this.content = "";
        this.userNameSender = userNameSender;
        this.type = type;
    }

    public Message(String content, String userNameSender, Type type)
    {
        this.content = content;
        this.userNameSender = userNameSender;
        this.type = type;
    }

    public Type getType()
    {
        return type;
    }

    public void setType(Type type)
    {
        this.type = type;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getSenderUsername()
    {
        return userNameSender;
    }

    public void setUserNameSender(String userNameSender)
    {
        this.userNameSender = userNameSender;
    }



    @Override
    public String toString()
    {
        if(type.equals(Type.join))
        {
            return ConsoleColors.BLUE_BOLD + userNameSender + " is joined :)" + ConsoleColors.RESET;
        }
        else if(type.equals(Type.exit))
        {
            return ConsoleColors.RED_BOLD + userNameSender + " is exited -_-" + ConsoleColors.RESET;
        }
        else
        {
            return ConsoleColors.GREEN + userNameSender + " : " + content + ConsoleColors.RESET;
        }
    }
}
