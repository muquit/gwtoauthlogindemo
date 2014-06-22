package com.example.GWTOAuthLoginDemo.server.model;

import java.io.Serializable;

import org.scribe.model.Token;

/**
 * We store this in the HTTP session object in server side.
 * Clients do not have access to it.
 * @author muquit@muquit.com Nov-17-2012 first cut
 * 
 * For Google App Engine, must be implemented as Serializable
 */
@SuppressWarnings("serial")
public class ServersideSession implements Serializable
{
    private String sessionId;
    private String code;
    private String state;
    private String expires;
    private int    expiresInt;
    private String expiresGMT;
    
    private String name;
    private String firstName;
    private String lastName;
    private String gender;
    private String email;
    private String jsonString;
    private String authProviderName = "Default";
    private String protectedResourceUrl;
    private String yahooGuid;
    private int    authProvider;
    
    private Token accessToken;
    private Token requestToken;
    
    
    public Token getRequestToken()
    {
        return requestToken;
    }

    public void setRequestToken(Token requestToken)
    {
        this.requestToken=requestToken;
    }

    public String getProtectedResourceUrl()
    {
        return protectedResourceUrl;
    }

    public void setProtectedResourceUrl(String protectedResourceUrl)
    {
        this.protectedResourceUrl=protectedResourceUrl;
    }

    public String getYahooGuid()
    {
        return yahooGuid;
    }

    public void setYahooGuid(String yahooGuid)
    {
        this.yahooGuid=yahooGuid;
    }


    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name=name;
    }

    public int getExpiresInt()
    {
        return expiresInt;
    }

    public void setExpiresInt(int expiresInt)
    {
        this.expiresInt=expiresInt;
    }

    public String getExpiresGMT()
    {
        return expiresGMT;
    }

    public void setExpiresGMT(String expiresGMT)
    {
        this.expiresGMT=expiresGMT;
    }

    public String getAuthProviderName()
    {
        return authProviderName;
    }

    public void setAuthProviderName(String authProviderName)
    {
        this.authProviderName=authProviderName;
    }

    public int getAuthProvider()
    {
        return authProvider;
    }

    public void setAuthProvider(int authProvider)
    {
        this.authProvider=authProvider;
    }

    public String getSessionId()
    {
        return sessionId;
    }
    public void setSessionId(String sessionId)
    {
        this.sessionId=sessionId;
    }
    
    
    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code=code;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state=state;
    }



    public Token getAccessToken()
    {
        return accessToken;
    }

    public void setAccessToken(Token accessToken)
    {
        this.accessToken=accessToken;
    }

    public String getExpires()
    {
        return expires;
    }
    public void setExpires(String expires)
    {
        this.expires=expires;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName=firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName=lastName;
    }

    public String getGender()
    {
        return gender;
    }

    public void setGender(String gender)
    {
        this.gender=gender;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email=email;
    }

    public String getJsonString()
    {
        return jsonString;
    }

    public void setJsonString(String jsonString)
    {
        this.jsonString=jsonString;
    }
    
}
