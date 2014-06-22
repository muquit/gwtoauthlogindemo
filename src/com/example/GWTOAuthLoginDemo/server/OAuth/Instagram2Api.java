package com.example.GWTOAuthLoginDemo.server.OAuth;

import org.scribe.builder.api.DefaultApi20;
import org.scribe.extractors.AccessTokenExtractor;
import org.scribe.extractors.JsonTokenExtractor;
import org.scribe.model.OAuthConfig;
import org.scribe.model.OAuthConstants;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuth20ServiceImpl;
import org.scribe.oauth.OAuthService;
import org.scribe.utils.OAuthEncoder;
import org.scribe.utils.Preconditions;

import com.example.GWTOAuthLoginDemo.server.util.ServerUtils;

/**
 * We are using server side flow
 * @author muquit@muquit.com
 * Ref: http://instagram.com/developer/authentication/#
 * After updating to scribe-java 1.3.6, it stopped working.
 * Updated code similar to Google2Api.java
 */
public class Instagram2Api extends DefaultApi20
{
    private static final String AUTHORIZATION_URL = "https://api.instagram.com/oauth/authorize/?client_id=%s&redirect_uri=%s&state=%s&grant_type=authorization_code&response_type=code";
    private static final String SCOPED_AUTHORIZATION_URL = AUTHORIZATION_URL + "&scope=%s";

    @Override
    public String getAccessTokenEndpoint()
    {
        return "https://api.instagram.com/oauth/access_token";
    }
    
    @Override
    public Verb getAccessTokenVerb()
    {
        return Verb.POST;
    }
    
    @Override
    public AccessTokenExtractor getAccessTokenExtractor()
    {
        return new JsonTokenExtractor();
    }

    @Override
    public String getAuthorizationUrl(OAuthConfig config)
    {
        Preconditions.checkValidUrl(config.getCallback(), "Must provide a valid url as callback for Instagram");
        String state = ServerUtils.makeRandomString();
        if (config.hasScope())
        {
            return String.format(SCOPED_AUTHORIZATION_URL, config.getApiKey(), OAuthEncoder.encode(config.getCallback()),state,OAuthEncoder.encode(config.getScope()));
        }
        else
        {
            return String.format(AUTHORIZATION_URL, config.getApiKey(), OAuthEncoder.encode(config.getCallback()),state);
        }
    }
    
    @Override
    public OAuthService createService(OAuthConfig config)
    {
        return new InstagramOauthService(this,config);
    }
    
    /* Jun-14-2014 */
    /* followed code in Google2Api.java */
    private class InstagramOauthService extends OAuth20ServiceImpl
    {
        private DefaultApi20 api;
        private OAuthConfig config;

        public InstagramOauthService(DefaultApi20 api,OAuthConfig config)
        {
            super(api,config);
            this.api = api;
            this.config = config;
        }
        
        @Override
        public Token getAccessToken(Token requestToken, Verifier verifier)
        {
            OAuthRequest request = new OAuthRequest(api.getAccessTokenVerb(), api.getAccessTokenEndpoint());
            switch (api.getAccessTokenVerb())
            {
                case POST:
                {
                    request.addBodyParameter(OAuthConstants.CLIENT_ID, config.getApiKey());
                    request.addBodyParameter(OAuthConstants.CLIENT_SECRET, config.getApiSecret());
                    request.addBodyParameter(OAuthConstants.CODE, verifier.getValue());
                    request.addBodyParameter(OAuthConstants.REDIRECT_URI, config.getCallback());
                    request.addBodyParameter("grant_type","authorization_code");
                    break;
                }
                case GET:
                default:
                {
                    request.addQuerystringParameter(OAuthConstants.CLIENT_ID, config.getApiKey());
                    request.addQuerystringParameter(OAuthConstants.CLIENT_SECRET, config.getApiSecret());
                    request.addQuerystringParameter(OAuthConstants.CODE, verifier.getValue());
                    request.addQuerystringParameter(OAuthConstants.REDIRECT_URI, config.getCallback());
                    request.addBodyParameter("grant_type","authorization_code");
                    if(config.hasScope()) request.addQuerystringParameter(OAuthConstants.SCOPE, config.getScope());
                }
            }
            Response response = request.send();
            return api.getAccessTokenExtractor().extract(response.getBody());
        }

    }
}
