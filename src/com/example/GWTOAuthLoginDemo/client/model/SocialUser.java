package com.example.GWTOAuthLoginDemo.client.model;

import java.io.Serializable;

/**
 * The object we send back and forth between client and server.
 * Note: More information is stored in server side in ServersideSession
 * object.
 * @author muquit@muquit.com
 */
public class SocialUser implements Serializable
{
    private static final long serialVersionUID=1011L;
    private String sessionId;
    
    private String email;
    private String json;
    
    /* must be named exactly as JSON google returns -- starts */
    private String id;
    private String name;        // full name
    private String given_name;  // first name
    private String family_name; // last name
    private String gender;      // same on Yahoo   
    private String link;
    private String locale;
    /* must be named exactly as JSON google returns -- ends */
    
    /* Yahoo --starts */
    private String guid; // is it always the same for the user??
    private String givenName;
    private String familyName;
    private String nickname;
    private String location;
    private String birthdate;
    private String timeZone;
    private String lang;
    private String relationShipStatus;
    private int    displayAge;
    /* Yahoo --ends */
    
    /* Linkedin -starts */
    private String firstName;
    private String lastName;
    private String headline;
    /* Linkedin -starts */
    
    
    /*
     Google returns JSON like:
     
      {
          "id": "116397076041912827850", 
          "name": "Muhammad Muquit", 
          "given_name": "Muhammad", 
          "family_name": "Muquit", 
          "link": "https://plus.google.com/116397076041912827850", 
          "gender": "male", 
          "locale": "en-US"
       }
     */
    /*
     * http://developer.yahoo.com/social/rest_api_guide/extended-profile-resource.html#
     Yahoo returns JSON like:
{
  "profile": {
    "uri": "http:\/\/social.yahooapis.com\/v1\/user\/ECUFIYO7BLY5FOV54XAPEQDC3Y\/profile",
    "guid": "ECUFIYO7BLY5FOAPEQDC3Y",
    "birthYear": 1969,
    "created": "2010-01-23T13:07:10Z",
    "displayAge": 43,
    "gender": "M",
    "image": {
      "height": 192,
      "imageUrl": "http:\/\/l.yimg.com\/a\/i\/identity2\/profile_192c.png",
      "size": "192x192",
      "width": 192
    },
    "location": "Philadelphia, Pennsylvania",
    "memberSince": "2006-08-04T13:27:58Z",
    "nickname": "jdoe",
    "profileUrl": "http:\/\/profile.yahoo.com\/ECUFIYO7BLY5FOV54XAPEQDC3Y",
    "searchable": false,
    "updated": "2011-04-16T07:28:00Z",
    "isConnected": false
  }
}
     */
    
    
    
    public String getSessionId()
    {
        return sessionId;
    }
    public void setSessionId(String sessionId)
    {
        this.sessionId=sessionId;
    }
    public String getEmail()
    {
        return email;
    }
    public void setEmail(String email)
    {
        this.email=email;
    }
    public String getJson()
    {
        return json;
    }
    public void setJson(String json)
    {
        this.json=json;
    }
    public String getId()
    {
        return id;
    }
    public void setId(String id)
    {
        this.id=id;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name=name;
    }
    public String getGiven_name()
    {
        return given_name;
    }
    public void setGiven_name(String given_name)
    {
        this.given_name=given_name;
    }
    public String getFamily_name()
    {
        return family_name;
    }
    public void setFamily_name(String family_name)
    {
        this.family_name=family_name;
    }
    public String getGender()
    {
        return gender;
    }
    public void setGender(String gender)
    {
        this.gender=gender;
    }
    public String getLink()
    {
        return link;
    }
    public void setLink(String link)
    {
        this.link=link;
    }
    public String getLocale()
    {
        return locale;
    }
    public void setLocale(String locale)
    {
        this.locale=locale;
    }
    public String getGuid()
    {
        return guid;
    }
    public void setGuid(String guid)
    {
        this.guid=guid;
    }
    public String getGivenName()
    {
        return givenName;
    }
    public void setGivenName(String givenName)
    {
        this.givenName=givenName;
    }
    public String getFamilyName()
    {
        return familyName;
    }
    public void setFamilyName(String familyName)
    {
        this.familyName=familyName;
    }
    public String getNickname()
    {
        return nickname;
    }
    public void setNickname(String nickname)
    {
        this.nickname=nickname;
    }
    public String getLocation()
    {
        return location;
    }
    public void setLocation(String location)
    {
        this.location=location;
    }
    public String getBirthdate()
    {
        return birthdate;
    }
    public void setBirthdate(String birthdate)
    {
        this.birthdate=birthdate;
    }
    public String getTimeZone()
    {
        return timeZone;
    }
    public void setTimeZone(String timeZone)
    {
        this.timeZone=timeZone;
    }
    public String getLang()
    {
        return lang;
    }
    public void setLang(String lang)
    {
        this.lang=lang;
    }
    public String getRelationShipStatus()
    {
        return relationShipStatus;
    }
    public void setRelationShipStatus(String relationShipStatus)
    {
        this.relationShipStatus=relationShipStatus;
    }
    public int getDisplayAge()
    {
        return displayAge;
    }
    public void setDisplayAge(int displayAge)
    {
        this.displayAge=displayAge;
    }
    
    /* linkedin */ 
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
    public String getHeadline()
    {
        return headline;
    }
    public void setHeadline(String headline)
    {
        this.headline=headline;
    }
    /* linkedin */ 
}

