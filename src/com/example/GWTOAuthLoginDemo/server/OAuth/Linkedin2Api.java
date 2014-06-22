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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.example.GWTOAuthLoginDemo.server.util.ServerUtils;

/**
 * @author muquit@muquit.com Jun-19-2014
 * https://developer.linkedin.com/documents/authentication
 */

public class Linkedin2Api extends DefaultApi20
{
    private static final String AUTHORIZATION_URL = "https://www.linkedin.com/uas/oauth2/authorization?client_id=%s&redirect_uri=%s&state=%s&response_type=code";
    private static final String SCOPED_AUTHORIZATION_URL = AUTHORIZATION_URL + "&scope=%s";
    private static final Log logger=LogFactory .getLog(Linkedin2Api.class);

    @Override
    public String getAccessTokenEndpoint()
    {
        return "https://www.linkedin.com/uas/oauth2/accessToken";
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
        String uuid = ServerUtils.makeRandomString();
        String aUrl = null;
        if (config.hasScope())
        {
            aUrl =  String.format(SCOPED_AUTHORIZATION_URL, config.getApiKey(), OAuthEncoder.encode(config.getCallback()),uuid,OAuthEncoder.encode(config.getScope()));
        }
        else
        {
            aUrl = String.format(AUTHORIZATION_URL, config.getApiKey(), OAuthEncoder.encode(config.getCallback()),uuid);
        }
        logger.info("Autorization URL: " + aUrl);
        return aUrl;
    }
    
    @Override
    public OAuthService createService(OAuthConfig config)
    {
        return new LinkedInOauthService(this,config);
    }
    
    /* Jun-14-2014 */
    /* followed code in Google2Api.java */
    private class LinkedInOauthService extends OAuth20ServiceImpl
    {
        private DefaultApi20 api;
        private OAuthConfig config;

        public LinkedInOauthService(DefaultApi20 api,OAuthConfig config)
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
