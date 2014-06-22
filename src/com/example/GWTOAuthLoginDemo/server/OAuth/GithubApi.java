package com.example.GWTOAuthLoginDemo.server.OAuth;

import org.scribe.builder.api.DefaultApi20;
import org.scribe.extractors.AccessTokenExtractor;
import org.scribe.extractors.JsonTokenExtractor;
import org.scribe.model.OAuthConfig;
import org.scribe.model.Verb;
import org.scribe.utils.Preconditions;

import com.example.GWTOAuthLoginDemo.server.util.ServerUtils;

/**
 * 
 * @author muquit@muquit.com Dec 19, 2012 8:56:46 PM
 */
public class GithubApi extends DefaultApi20
{
    private static final String AUTHORIZATION_URL = "https://github.com/login/oauth/authorize?client_id=%s&redirect_uri=%s&scope=user&state=%s";
    @Override
    public String getAccessTokenEndpoint()
    {
        return "https://github.com/login/oauth/access_token";
    }
    
    @Override
    public Verb getAccessTokenVerb()
    {
        return Verb.POST;
    }
    
    /*
    @Override
    public AccessTokenExtractor getAccessTokenExtractor()
    {
        return new JsonTokenExtractor();
    }
    */

    @Override
    public String getAuthorizationUrl(OAuthConfig config)
    {
        Preconditions.checkValidUrl(config.getCallback(), "Must provide a valid url as callback for Instagram");
        
        String uuid = ServerUtils.makeRandomString();
        return String.format(AUTHORIZATION_URL,config.getApiKey(),config.getCallback(),uuid);
    }
}
