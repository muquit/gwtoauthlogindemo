package com.example.GWTOAuthLoginDemo.client.exception;

import java.io.Serializable;

/**
 * @author muquit@muquit.com Aug 19, 2012 10:04:36 AM
 */
public class OurException extends Exception implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String message;
    public OurException()
    {
    }

    public OurException(String message)
    {
        super(message);
        this.message=message;
    }

    public OurException(Throwable cause)
    {
        super(cause);
        if (cause != null)
        {
            if (this.message == null)
            {
                this.message=cause.getMessage();
            }
        }
    }

    public OurException(String message,Throwable cause)
    {
        super(message,cause);
        this.message=message;
    }

    public String getMessage()
    {
        return(message);
    }
    
}
