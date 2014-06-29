package com.example.GWTOAuthLoginDemo.server.OAuth;

import java.util.logging.Logger;

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
 * @author muquit@muquit.com
 * Jun-29-2014
 */
public class ImgUr2Api extends DefaultApi20
{
    private static final String AUTHORIZATION_URL = "https://api.imgur.com/oauth2/authorize?response_type=code&client_id=%s&state=%s";
    private static final Logger logger=Logger.getLogger(ImgUr2Api.class.getName());

    @Override
    public String getAccessTokenEndpoint()
    {
        return "https://api.imgur.com/oauth2/token";
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
        Preconditions.checkValidUrl(config.getCallback(), "Must provide a valid url as callback for ImgUr");
        String state = ServerUtils.makeRandomString();
        return String.format(AUTHORIZATION_URL, config.getApiKey(),state);
    }
    
    @Override
    public OAuthService createService(OAuthConfig config)
    {
        return new ImgUrOauthService(this,config);
    }
    
    private class ImgUrOauthService extends OAuth20ServiceImpl
    {
        private DefaultApi20 api;
        private OAuthConfig config;

        public ImgUrOauthService(DefaultApi20 api,OAuthConfig config)
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
                    logger.info("getAccessToken: POST");
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
                    logger.info("getAccessToken: GET");
                    request.addQuerystringParameter(OAuthConstants.CLIENT_ID, config.getApiKey());
                    request.addQuerystringParameter(OAuthConstants.CLIENT_SECRET, config.getApiSecret());
                    request.addQuerystringParameter(OAuthConstants.CODE, verifier.getValue());
                    request.addQuerystringParameter(OAuthConstants.REDIRECT_URI, config.getCallback());
                    request.addBodyParameter("grant_type","authorization_code");
                }
            }
            Response response = request.send();
            return api.getAccessTokenExtractor().extract(response.getBody());
        }

    }
}
