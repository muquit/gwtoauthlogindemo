package com.example.GWTOAuthLoginDemo.server.OAuth;
import org.scribe.builder.api.FacebookApi;
import org.scribe.model.OAuthConfig;
import org.scribe.utils.OAuthEncoder;
import org.scribe.utils.Preconditions;

import com.example.GWTOAuthLoginDemo.server.util.ServerUtils;

/**
 * just add state to the authorization URL
 * @author muquit
 */
public class FacebookApiWithState extends FacebookApi
{
    private static final String AUTHORIZE_URL="https://www.facebook.com/dialog/oauth?client_id=%s&redirect_uri=%s&state=%s";
    private static final String SCOPED_AUTHORIZE_URL=AUTHORIZE_URL + "&scope=%s";

    public FacebookApiWithState()
    {
        super();
    }

    @Override
    public String getAuthorizationUrl(OAuthConfig config)
    {
        Preconditions.checkValidUrl(config.getCallback(), "Must provide a valid url as callback. Facebook does not support OOB");
        String state=ServerUtils.makeRandomString();
        // Append scope if present
        if (config.hasScope())
        {
            return String.format(SCOPED_AUTHORIZE_URL, config.getApiKey(), OAuthEncoder.encode(config.getCallback()), state, OAuthEncoder.encode(config.getScope()));
        }
        else
        {
            return String.format(AUTHORIZE_URL, config.getApiKey(), OAuthEncoder.encode(config.getCallback()),state);
        }
    }
}
