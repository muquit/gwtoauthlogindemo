package com.example.GWTOAuthLoginDemo.server.OAuth;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class InstagramToken
{
    private final static Log logger=LogFactory.getLog(InstagramToken.class);
    /*
        {
          "access_token":"263534762.8e52bda.5a03053395cc4d8ebb47a3ac43f45da2",
          "user":
          {
             "id":"263534762"
              "username":"mmqx1219",
              "bio":"",
              "website":"",
                ...
              "full_name":"mmqx2219",
          }
        }     
     */
    
    private String accessToken;
    private String userId;
    private String userName;
    private String fullName;
    private String rawResponse;
    
    public InstagramToken(String accessTokenJson) throws ParseException 
    {
        this.rawResponse = accessTokenJson;
        parseIntragramAccessTokenJson(accessTokenJson);
    }

    
    private void parseIntragramAccessTokenJson(String accessTokenJson) throws ParseException
    {
            JSONParser jsonParser = new JSONParser();
            Object obj=jsonParser.parse(accessTokenJson);
            JSONObject jsonObj = (JSONObject) obj;
            this.accessToken = (String) jsonObj.get("access_token");
            
            jsonObj = (JSONObject) jsonObj.get("user");
            this.userName = (String) jsonObj.get("username");
            this.userId = (String) jsonObj.get("id");
            this.fullName = (String) jsonObj.get("full_name");

    }
    
    public static InstagramToken parse(String accessTokenJson) throws ParseException
    {
        return new InstagramToken(accessTokenJson);
    }


    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId=userId;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName=userName;
    }

    public String getFullName()
    {
        return fullName;
    }

    public void setFullName(String fullName)
    {
        this.fullName=fullName;
    }

    public String getAcessToken()
    {
        return this.accessToken;
    }

    public void setAcessToken(String acessToken)
    {
        this.accessToken=acessToken;
    }

    public String getRawResponse()
    {
        return rawResponse;
    }

    public void setRawResponse(String rawResponse)
    {
        this.rawResponse=rawResponse;
    }
    
}
