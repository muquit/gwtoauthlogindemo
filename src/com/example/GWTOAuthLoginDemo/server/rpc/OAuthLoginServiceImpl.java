/*******************************************************************************
 * Copyright 2011 Google Inc. All Rights Reserved.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.example.GWTOAuthLoginDemo.server.rpc;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.FacebookApi;
import org.scribe.builder.api.FlickrApi;
import org.scribe.builder.api.Foursquare2Api;
import org.scribe.builder.api.ImgUrApi;
import org.scribe.builder.api.LiveApi;
import org.scribe.builder.api.TumblrApi;
import org.scribe.builder.api.TwitterApi;
import org.scribe.builder.api.VimeoApi;
import org.scribe.builder.api.YahooApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import com.allen_sauer.gwt.log.client.LogUtil;
import com.example.GWTOAuthLoginDemo.client.exception.OurException;
import com.example.GWTOAuthLoginDemo.client.model.Credential;
import com.example.GWTOAuthLoginDemo.client.model.SocialUser;
import com.example.GWTOAuthLoginDemo.client.rpc.OAuthLoginService;
import com.example.GWTOAuthLoginDemo.client.util.ClientUtils;
import com.example.GWTOAuthLoginDemo.server.OAuth.FacebookApiWithState;
import com.example.GWTOAuthLoginDemo.server.OAuth.GithubApi;
import com.example.GWTOAuthLoginDemo.server.OAuth.Google2Api;
import com.example.GWTOAuthLoginDemo.server.OAuth.Instagram2Api;
import com.example.GWTOAuthLoginDemo.server.OAuth.InstragramToken;
import com.example.GWTOAuthLoginDemo.server.OAuth.Linkedin2Api;
import com.example.GWTOAuthLoginDemo.server.model.ServersideSession;
import com.example.GWTOAuthLoginDemo.server.util.OAuthParams;
import com.example.GWTOAuthLoginDemo.server.util.ServerUtils;






// GWT has similar class. 
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class OAuthLoginServiceImpl extends RemoteServiceServlet implements
        OAuthLoginService
{
    //private final String SESSION                = "GWTOAuthLoginDemo_session";
    private final String SESSION_ID             = "GWTOAuthLoginDemo_sessionid";
    private final String SESSION_REQUEST_TOKEN  = "GWTOAuthLoginDemo_request_token";
    private final String SESSION_NONCE          = "GWTOAuthLoginDemo_nonce";
    private final String SESSION_PROTECTED_URL  = "GWTOAuthLoginDemo_protected_url";
    private final String SESSION_ACCESS_TOKEN   = "GWTOAuthLoginDemo_access_token";
    private final String SESSION_YAHOO_GUID     = "GWTOAuthLoginDemo_yahoo_guid";
    private final String SESSION_AUTH_PROVIDER = "GWTOAuthLoginDemo_auth_provider";

    private final String DEFAULT_USERNAME = "test";
    private final String DEFAULT_PASSWORD = "secret";
    private final String DEFAULT_JSON = "{" +
            "\n" +
            "  \":username:\" " + "\"" + DEFAULT_USERNAME + "\""+
            "\n" +
            "}";

    private String getDefaultUsername()
    {
        return DEFAULT_USERNAME;
    }
    private String getDefaultPassword()
    {
        return DEFAULT_PASSWORD;
    }

    private String getDefaultJson()
    {
        return DEFAULT_JSON;
    }

    private static final Log logger=LogFactory .getLog(OAuthLoginServiceImpl.class);

    private OAuthService getOAuthService(int authProvider) throws OurException
    {
        OAuthService service = null;
        switch(authProvider)
        {
            case ClientUtils.FACEBOOK:
            {
                service = new ServiceBuilder()
                .provider(FacebookApiWithState.class)
                .apiKey(OAuthParams.FACEBOOK_APP_ID)
                .apiSecret(OAuthParams.FACEBOOK_APP_SECRET)
                .callback(ClientUtils.getCallbackUrl())
                .build();
                break;
            }

            case ClientUtils.GOOGLE:
            {
                /*
                service = new ServiceBuilder()
                    .provider(GoogleApi20.class)
                    .apiKey(ServerUtils.GOOGLE_APP_ID)
                    .apiSecret(ServerUtils.GOOGLE_APP_SECRET)
                    .scope(ServerUtils.GOOGLE_SCOPE)
                    .callback(ClientUtils.getCallbackUrl())
                    .grantType(OAuthConstants.AUTHORIZATION_CODE)
                    .build();
                 */

                /*
                 ** Used to use Google oauth 2.0 api form a 
                 ** forked version of scribe. Now we use 
                 ** scribe 1.3.6 and extended Google API 
                 ** from yincrash. The Google2Api.java is in
                 ** Google2Api.java in server/OAuth directory
                 ** Jun-08-2014
                 *
                 */
                service = new ServiceBuilder()
                .provider(Google2Api.class)
                .apiKey(OAuthParams.GOOGLE_APP_ID)
                .apiSecret(OAuthParams.GOOGLE_APP_SECRET)
                .scope(OAuthParams.GOOGLE_SCOPE)
                .callback(ClientUtils.getCallbackUrl())
                .build();
                break;
            }

            case ClientUtils.TWITTER:
            {
                service = new ServiceBuilder()
                .provider(TwitterApi.class)
                .apiKey(OAuthParams.TWITTER_APP_ID)
                .apiSecret(OAuthParams.TWITTER_APP_SECRET)
                .callback(ClientUtils.getCallbackUrl())
                .build();
                break;
            }
            case ClientUtils.YAHOO:
            {
                /* 
                 * Note: yahoo does not support custom port in redirect URL.
                 * So we won't be able to test the app in localhost.
                 */
                service = new ServiceBuilder()
                    .provider(YahooApi.class)
                    .apiKey(OAuthParams.YAHOO_APP_ID)
                    .apiSecret(OAuthParams.YAHOO_APP_SECRET)
                    .callback(ClientUtils.getCallbackUrl())
                    .build();
                break;
            }
            
            case ClientUtils.LINKEDIN:
            {
                /*
                service = new ServiceBuilder()
                    .provider(LinkedInApi.class)
                    .apiKey(ServerUtils.LINKEDIN_APP_ID)
                    .apiSecret(ServerUtils.LINKEDIN_APP_SECRET)
                    .callback(ClientUtils.getCallbackUrl())
                    .build();
                */
                service = new ServiceBuilder()
                    .provider(Linkedin2Api.class)
                    .apiKey(OAuthParams.LINKEDIN_APP_ID)
                    .apiSecret(OAuthParams.LINKEDIN_APP_SECRET)
                    .callback(ClientUtils.getCallbackUrl())
                    .build();
                break;
            }
            
            case ClientUtils.INSTAGRAM:
            {
                /*
                service = new ServiceBuilder()
                .provider(InstagramApi.class)
                    .apiKey(ServerUtils.INSTAGRAM_APP_ID)
                    .apiSecret(ServerUtils.INSTAGRAM_APP_SECRET)
                    .grantType(OAuthConstants.AUTHORIZATION_CODE)
                    .callback(ClientUtils.getCallbackUrl())
                    .build();
                    */
                /* Jun-14-2014, using scribe 1.3.6 */
                service = new ServiceBuilder()
                    .provider(Instagram2Api.class)
                    .apiKey(OAuthParams.INSTAGRAM_APP_ID)
                    .apiSecret(OAuthParams.INSTAGRAM_APP_SECRET)
                    .callback(ClientUtils.getCallbackUrl())
                    .build();
                
                break;
            }
            
            case ClientUtils.GITHUB:
            {
                service = new ServiceBuilder()
                .provider(GithubApi.class)
                    .apiKey(OAuthParams.GITHUB_APP_ID)
                    .apiSecret(OAuthParams.GITHUB_APP_SECRET)
                    .callback(ClientUtils.getCallbackUrl())
                    .build();
                break;
                
            }
            
            case ClientUtils.IMGUR:
            {
                service = new ServiceBuilder()
                .provider(ImgUrApi.class)
                    .apiKey(ServerUtils.IMGUR_APP_ID)
                    .apiSecret(ServerUtils.IMGUR_APP_SECRET)
                    .callback(ClientUtils.getCallbackUrl())
                    .build();
                break;
            }
            
            case ClientUtils.FLICKR:
            {
                service = new ServiceBuilder()
                .provider(FlickrApi.class)
                    .apiKey(OAuthParams.FLICKR_APP_ID)
                    .apiSecret(OAuthParams.FLICKR_APP_SECRET)
                    .callback(ClientUtils.getCallbackUrl())
                    .build();
                break;
            }
            
            case ClientUtils.VIMEO:
            {
                service = new ServiceBuilder()
                .provider(VimeoApi.class)
                    .apiKey(OAuthParams.VIMEO_APP_ID)
                    .apiSecret(OAuthParams.VIMEO_APP_SECRET)
                    .callback(ClientUtils.getCallbackUrl())
                    .build();
                break;
            }
            
            case ClientUtils.WINDOWS_LIVE:
            {
                // a Scope must be specified
                service = new ServiceBuilder()
                .provider(LiveApi.class)
                    .apiKey(OAuthParams.WINDOWSLIVE_APP_ID)
                    .apiSecret(OAuthParams.WINDOWSLIVE_APP_SECRET)
                    .callback(ClientUtils.getCallbackUrl())
                    .scope("wl.basic")
                    .build();
                break;
            }
            
            case ClientUtils.TUMBLR:
            {
                service = new ServiceBuilder()
                .provider(TumblrApi.class)
                    .apiKey(OAuthParams.TUMBLRLIVE_APP_ID)
                    .apiSecret(OAuthParams.TUMBLRLIVE_APP_SECRET)
                    .callback(ClientUtils.getCallbackUrl())
                    .build();
                break;
            }
            
            case ClientUtils.FOURSQUARE:
            {
                service = new ServiceBuilder()
                .provider(Foursquare2Api.class)
                    .apiKey(ServerUtils.FOURSQUARE_APP_ID)
                    .apiSecret(ServerUtils.FOURSQUARE_APP_SECRET)
                    .callback(ClientUtils.getCallbackUrl())
                    .build();
                break;
            }
            
            default:
            {
                return null;
            }
            
        }
        return service;
    }
    

    @Override
    public String getAuthorizationUrl(Credential credential) throws OurException
    {
        logger.info("callback url: " + credential.getRedirectUrl());
        String authorizationUrl = null;
        Token requestToken = null;
        
        int authProvider = credential.getAuthProvider();
        
        OAuthService service = getOAuthService(authProvider);
        if (service == null)
        {
            throw new OurException("Could not build OAuthService");
        }
        
//            authProvider == ClientUtils.LINKEDIN   ||
        
        if (authProvider == ClientUtils.TWITTER    ||
            authProvider == ClientUtils.YAHOO      ||
            authProvider == ClientUtils.FLICKR     ||
            authProvider == ClientUtils.IMGUR      ||
            authProvider == ClientUtils.TUMBLR     ||
            authProvider == ClientUtils.VIMEO)
        {
            String authProviderName = ClientUtils.getAuthProviderName(authProvider);
            logger.info(authProviderName + " requires Request token first.. obtaining..");
            try
            {
                requestToken = service.getRequestToken();
                logger.info("Got request token: " + requestToken);
                // we must save in the session. It will be required to
                // get the access token
                saveRequestTokenToSession(requestToken);
            }
            catch(Exception e)
            {
                System.out.println("MMMM exception: " + e);
                
                String stackTrace = stackTraceToString(e);
                throw new OurException("Could not get request token for " + authProvider + " " + stackTrace);
            }
            
        }
 
        logger.info("Getting Authorization url...");
        try
        {
            authorizationUrl = service.getAuthorizationUrl(requestToken);
            logger.info("Authorization URL" + authorizationUrl);
            // if the provider supports "state", save it to session
            if (authProvider == ClientUtils.LINKEDIN   ||
                authProvider == ClientUtils.GITHUB     ||
                authProvider == ClientUtils.FACEBOOK   ||
                authProvider == ClientUtils.INSTAGRAM)
            {
                logger.info("Auth URL should have state in QS");
                logger.info("Extract state from URL");
                String state = ServerUtils.getQueryStringValueFromUrl(authorizationUrl,"state");
                if (state != null)
                {
                    logger.info("state: " + state);
                    logger.info("Save state to session");
                    saveStateToSession(state);
                }
            }
        }
        catch(Exception e)
        {
            logger.error("Exception caught: " + e);
            String st = LogUtil.stackTraceToString(e);
            throw new OurException("Could not get Authorization url: " + st);
        }
        
        if (authProvider == ClientUtils.FLICKR)
        {
            authorizationUrl += "&perms=read";
        }
        logger.info("Returning: " + authorizationUrl);
       
        return authorizationUrl;
    }
    
    public static String stackTraceToString(Throwable caught)
    {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement e : caught.getStackTrace())
        {
            sb.append(e.toString()).append("\n");
        }
        return sb.toString();
    }    
    
    private void saveRequestTokenToSession(Token requestToken) throws OurException
    {
        HttpSession session = getHttpSession();
        if (session == null)
        {
            throw new OurException(ClientUtils.SESSION_EXPIRED_MESSAGE);
        }
        /*
        ServersideSession sss = getServersideSession();
        if (sss == null)
        {
            sss = new ServersideSession();
            sss.setRequestToken(requestToken);
            session.setAttribute(SESSION,sss);
        }
        else
        {
            sss.setRequestToken(requestToken);
        }
        */
        session.setAttribute(SESSION_REQUEST_TOKEN,requestToken);
    }

    private void saveStateToSession(String state) throws OurException
    {
        HttpSession session = getHttpSession();
        if (session == null)
        {
            throw new OurException(ClientUtils.SESSION_EXPIRED_MESSAGE);
        }
        /*
        ServersideSession sss = getServersideSession();
        if (sss == null)
        {
            sss = new ServersideSession();
            sss.setState(state);
            session.setAttribute(SESSION,sss);
        }
        else
        {
            sss.setState(state);
        }
        */
        session.setAttribute(SESSION_NONCE,state);
    }
    
    private void saveSessionIdToSession(String sessionId) throws OurException
    {
        HttpSession session = getHttpSession();
        if (session == null)
        {
            throw new OurException(ClientUtils.SESSION_EXPIRED_MESSAGE);
        }
        session.setAttribute(SESSION_ID,sessionId);
        /*
        ServersideSession sss = getServersideSession();
        if (sss == null)
        {
            sss = new ServersideSession();
            sss.setSessionId(sessionId);
            session.setAttribute(SESSION,sss);
        }
        else
        {
            sss.setSessionId(sessionId);
        }
        */
    }
    
    private void saveProtectedResourceUrlToSession(String url) throws OurException
    {
        HttpSession session = getHttpSession();
        if (session == null)
        {
            throw new OurException(ClientUtils.SESSION_EXPIRED_MESSAGE);
        }
        /*
        ServersideSession sss = getServersideSession();
        if (sss == null)
        {
            sss = new ServersideSession();
            sss.setProtectedResourceUrl(url);
            session.setAttribute(SESSION,sss);
        }
        else
        {
            sss.setProtectedResourceUrl(url);
        }
        */
        session.setAttribute(SESSION_PROTECTED_URL,url);
    }
    
    private void saveAuthProviderToSession(int authProvider) throws OurException
    {
        HttpSession session = getHttpSession();
        if (session == null)
        {
            throw new OurException(ClientUtils.SESSION_EXPIRED_MESSAGE);
        }
        /*
        ServersideSession sss = getServersideSession();
        if (sss == null)
        {
            sss = new ServersideSession();
            sss.setAuthProvider(authProvider);
            session.setAttribute(SESSION,sss);
        }
        else
        {
            sss.setAuthProvider(authProvider);
        }
        */
        session.setAttribute(SESSION_AUTH_PROVIDER,authProvider);
    }
    
    private void saveAccessTokenToSession(Token accessToken) throws OurException
    {
        HttpSession session = getHttpSession();
        if (session == null)
        {
            throw new OurException(ClientUtils.SESSION_EXPIRED_MESSAGE);
        }
        /*
        ServersideSession sss = getServersideSession();
        if (sss == null)
        {
            sss = new ServersideSession();
            sss.setAccessToken(accessToken);
            session.setAttribute(SESSION,sss);
        }
        else
        {
            sss.setAccessToken(accessToken);
        }
        */
        session.setAttribute(SESSION_ACCESS_TOKEN,accessToken);
        
    }
    
    private void saveYahooGuidToSession(String guid) throws OurException
    {
        HttpSession session = getHttpSession();
        if (session == null)
        {
            throw new OurException(ClientUtils.SESSION_EXPIRED_MESSAGE);
        }
        /*
        ServersideSession sss = getServersideSession();
        if (sss == null)
        {
            sss = new ServersideSession();
            sss.setYahooGuid(guid);
            session.setAttribute(SESSION,sss);
        }
        else
        {
            sss.setYahooGuid(guid);
        }
        */
        session.setAttribute(SESSION_YAHOO_GUID,guid);
    }
    
    private String getYahooGuidFromSession() throws OurException
    {
        HttpSession session = getHttpSession();
        if (session == null)
        {
            throw new OurException(ClientUtils.SESSION_EXPIRED_MESSAGE);
        }
        /*
        ServersideSession sss = getServersideSession();
        if (sss != null)
        {
            return sss.getYahooGuid();
        }
        return null;
        */
        return (String) session.getAttribute(SESSION_YAHOO_GUID);
    }
    
    private String getProtectedResourceUrlFromSession() throws OurException
    {
        HttpSession session = getHttpSession();
        if (session == null)
        {
            throw new OurException(ClientUtils.SESSION_EXPIRED_MESSAGE);
        }
        /*
        ServersideSession sss = getServersideSession();
        if (sss != null)
            return sss.getProtectedResourceUrl();
        
        return null;
        */
        return (String) session.getAttribute(SESSION_PROTECTED_URL);
    }
    
    private String getStateFromSession() throws OurException
    {
        HttpSession session = getHttpSession();
        if (session == null)
        {
            throw new OurException(ClientUtils.SESSION_EXPIRED_MESSAGE);
        }
        /*
        ServersideSession sss = getServersideSession();
        if (sss != null)
            return sss.getState();
        
        return null;
        */
        return (String) session.getAttribute(SESSION_NONCE);
    }
    
    private String getSessionIdFromSession() throws OurException
    {
        HttpSession session = getHttpSession();
        if (session == null)
        {
            throw new OurException(ClientUtils.SESSION_EXPIRED_MESSAGE);
        }
        /*
        ServersideSession sss = getServersideSession();
        if (sss != null)
            return sss.getSessionId();
            */
        String sessionId = (String) session.getAttribute(SESSION_ID);
        
        return sessionId;
    }
    
    private Token getRequestTokenFromSession() throws OurException
    {
        HttpSession session = getHttpSession();
        if (session == null)
        {
            throw new OurException(ClientUtils.SESSION_EXPIRED_MESSAGE);
        }
        /*
        ServersideSession sss = getServersideSession();
        if (sss != null)
            return sss.getRequestToken();
        
        return null;
        */
        return (Token) session.getAttribute(SESSION_REQUEST_TOKEN);
    }

    private String makeRandomString()
    {
        UUID uuid=UUID.randomUUID();
        return uuid.toString();
    }
    
    private SocialUser getSocialUser(Token accessToken,int authProvider) throws OurException
    {
        logger.info("Token: " + accessToken + " Provider: " + authProvider);
        OAuthService service = getOAuthService(authProvider);
        
        
        String url = getProtectedResourceUrlFromSession();
        OAuthRequest request = new OAuthRequest(Verb.GET,url);
        // sign the request
        service.signRequest(accessToken,request);
        Response response = request.send();
        String json = response.getBody();
        SocialUser socialUser = getSocialUserFromJson(json,authProvider);
        return socialUser;
    }

    @Override
    public SocialUser verifySocialUser(Credential credential) throws OurException
    {
        int authProvider = credential.getAuthProvider();
        logger.info("authProvider: " + authProvider);
        String authProviderName = ClientUtils.getAuthProviderName(authProvider);
        logger.info("Verifying social usr from " + authProviderName);
        
        Token requestToken              = null;
        InstragramToken instragramToken = null;
        String yahooGuid                = null;
        String protectedResourceUrl = ClientUtils.getProctedResourceUrl(authProvider);
        
        if (authProvider == ClientUtils.FACEBOOK  || 
            authProvider == ClientUtils.INSTAGRAM ||
            authProvider == ClientUtils.LINKEDIN)
        {
            logger.info("Verifying state: " + credential.getState());
            verifyState(credential.getState()); 
        }  
        
        // some providers requires request token to get access token
        /*
        switch(authProvider)
        {
            case ClientUtils.TWITTER:
            case ClientUtils.YAHOO:
            case ClientUtils.LINKEDIN:
            case ClientUtils.FLICKR:
            case ClientUtils.IMGUR:
            case ClientUtils.VIMEO:
            case ClientUtils.TUMBLR:
            {
                requestToken = getRequestTokenFromSession();
                if (requestToken == null)
                {
                    throw new OurException("Could not retrieve Request Token form HTTP session");
                }
                break;
            }
        }
        */
        
        /* if there is any request token in session, get it */
        requestToken = getRequestTokenFromSession();
        
        OAuthService service = null;
        Verifier verifier    = null;
        Token accessToken    = null;
        
        /* Get Access Token */
        if (authProvider != ClientUtils.DEFAULT)
        {
            service = getOAuthService(authProvider);
            verifier = new Verifier(credential.getVerifier());
            logger.info("Requesting access token with requestToken: " + requestToken);
            logger.info("verifier=" + verifier);
            try
            {
                accessToken = service.getAccessToken(requestToken,verifier);
                if (accessToken == null)
                {
                    logger.error("Could not get Access Token for " + authProviderName);
                    throw new OurException("Could not get Access Token");
                }
            }
            catch (Exception e)
            {
                logger.info("Exception received getting Access Token: " + e);
                throw new OurException("Exception received getting Access Token: " + e);
            }
            logger.info("Got the access token: " + accessToken);
            logger.info(" Token: " + accessToken.getToken());
            logger.info(" Secret: " + accessToken.getSecret());
            logger.info(" Raw: " + accessToken.getRawResponse());
        }
        else
        {
            /*
            ** Default provider.
            ** The info will probably come from database. Password will 
            ** probably some kind of salted hash. We're just hard coding
            ** "test" and "secret" for the demo.
            */
            logger.info("Handing default loign..");
            String username = credential.getLoginName();
            String password = credential.getPassword();
            if (username == null)
            {
                throw new OurException("Default Username can not be empty");
            }
            if (password == null)
            {
                throw new OurException("Default Password not be empty");
            }
            if (username.equals(getDefaultUsername()) && password.equals(getDefaultPassword()))
            {
                
            }
            else
            {
                throw new OurException("Please use " + getDefaultUsername() + "  and " + getDefaultPassword() + " as Default Credential!");
            }
            
            
        }
        
        if (authProvider == ClientUtils.INSTAGRAM)
        {
            try
            {
                instragramToken = InstragramToken.parse(accessToken.getRawResponse());
            } catch (ParseException e)
            {
                throw new OurException("Could not parse " + authProviderName + " Json AccessToken");
            }
            logger.info("Getting Instragram Access Token");
            logger.info(" access token" + instragramToken.getAcessToken());
            logger.info(" userId: " + instragramToken.getUserId());
            logger.info(" full name: " + instragramToken.getFullName());
            logger.info(" username: " + instragramToken.getFullName());
            logger.info(" raw: " + instragramToken.getRawResponse());
            
            // replace userId and access token in protected resource url
            protectedResourceUrl = ClientUtils.getProctedResourceUrl(authProvider);
            logger.info("Instragram protected resource url: " + protectedResourceUrl);
            protectedResourceUrl = String.format(protectedResourceUrl, instragramToken.getUserId(),instragramToken.getAcessToken());
            logger.info("Instragram protected resource url: " + protectedResourceUrl);
        }
        
        if (authProvider == ClientUtils.GITHUB     ||
            authProvider == ClientUtils.FOURSQUARE ||
            authProvider == ClientUtils.LINKEDIN)
        {
            protectedResourceUrl = String.format(protectedResourceUrl,accessToken.getToken());
        }
        
        logger.info("Protected resource url: " + protectedResourceUrl);
        
        if (authProvider == ClientUtils.YAHOO)
        {
            //throw new OurException("Not implemented for yahoo yet!)");
            /* we need to replace <GUID> */
            yahooGuid = getQueryStringValue(accessToken.getRawResponse(),"xoauth_yahoo_guid");
            if (yahooGuid == null)
            {
                throw new OurException("Could not get Yahoo GUID from Query String");
            }
            // must save it to session. we'll use to get the user profile
            saveYahooGuidToSession(yahooGuid);
            
            protectedResourceUrl = ClientUtils.getProctedResourceUrl(authProvider);
            protectedResourceUrl = String.format(protectedResourceUrl,yahooGuid);
            logger.info("Yahoo protected resource url: " + protectedResourceUrl);
        }
        
        // make session id
        String sessionId = makeRandomString();
        
        // must save session id to session
        saveSessionIdToSession(sessionId);
        
        // must save authProvider to session
        saveAuthProviderToSession(authProvider);
        
 
        SocialUser socialUser = null;
        if (authProvider != ClientUtils.DEFAULT)
        {
            // must save acess token to session
            saveAccessTokenToSession(accessToken);
            
            // must save the protected resource url to session
            saveProtectedResourceUrlToSession(protectedResourceUrl);
            
            // now request protected resource
            logger.info("Getting protected resource");
            logger.info("Protected resource url: " + protectedResourceUrl);
            try
            {
                OAuthRequest request = new OAuthRequest(Verb.GET,protectedResourceUrl);
                service.signRequest(accessToken,request);
                
                Response response = request.send();
                logger.info("Status code: " + response.getCode());
                logger.info("Body: " + response.getBody());
                
                String json = response.getBody();
                socialUser = getSocialUserFromJson(json,authProvider);
            }
            catch(Exception e)
            {
                logger.error("Could not retrieve protected resource: " + e);
                throw new OurException("Could not retrieve protected resource: " + e);
            }
        }
        else
        {
            socialUser = new SocialUser();
            socialUser.setName(getDefaultUsername());
                         
            socialUser.setJson(getDefaultJson());
        }
        socialUser.setSessionId(sessionId);
        
        return socialUser;
    }
    
    private Token getAccessTokenFromSession() throws OurException
    {
        HttpSession session = getHttpSession();
        if (session == null)
        {
            throw new OurException(ClientUtils.SESSION_EXPIRED_MESSAGE);
        }
        /*
        ServersideSession sss = getServersideSession();
        if (sss != null)
        {
            return sss.getAccessToken();
        }
        return null;
        */
        return (Token) session.getAttribute(SESSION_ACCESS_TOKEN);
 
    }
    
    private int getAuthProviderFromSession() throws OurException
    {
        HttpSession session = getHttpSession();
        if (session == null)
        {
            throw new OurException(ClientUtils.SESSION_EXPIRED_MESSAGE);
        }
        /*
        ServersideSession sss = getServersideSession();
        if (sss != null)
            return sss.getAuthProvider();
        */
       return (Integer) session.getAttribute(SESSION_AUTH_PROVIDER);
    }
    
    private SocialUser getSocialUserFromJson(String json, int authProvider) throws OurException
    {
        String authProviderName = ClientUtils.getAuthProviderName(authProvider); 
        logger.info("Auth provider: " + authProviderName);
        
        JSONParser jsonParser = new JSONParser();
        Object obj = null;
        SocialUser socialUser = new SocialUser();
        json = ServerUtils.prettyPrintJsonString(json);
        switch(authProvider)
        {
            case ClientUtils.FACEBOOK:
            {
                /* --Facebook--
                { 
                  "id":"537157209",
                  "name":"Muhammad Muquit",
                  "first_name":"Muhammad",
                  "last_name":"Muquit",
                  "link":"http:\/\/www.facebook.com\/muhammad.muquit",
                  "username":"muhammad.muquit",
                  "gender":"male",
                  "timezone":-5,"locale":"en_US",
                  "verified":true,
                  "updated_time":"2012-11-10T23:13:04+0000"}
                 }
                */
                try
                {
                    obj = jsonParser.parse(json);
                    JSONObject jsonObj = (JSONObject) obj;
                
                
                    socialUser.setName((String) jsonObj.get("name"));
                    socialUser.setFirstName((String) jsonObj.get("first_name"));
                    socialUser.setLastName((String) jsonObj.get("last_name"));
                    socialUser.setGender((String)jsonObj.get("gender"));
                    
                    socialUser.setJson(json);
                    
                    return socialUser;
                }
                catch (ParseException e)
                {
                  throw new OurException("Could not parse JSON data from " + authProviderName + ":" + e.getMessage());
                }
            }
                
            case ClientUtils.YAHOO:
            {
                /* --YAHOO--
                   http://developer.yahoo.com/social/rest_api_guide/extended-profile-resource.html#
                {
                     "profile": {
                       "uri": "http:\/\/social.yahooapis.com\/v1\/user\/ECUFIYO7BLY5FOV54XAPEQDC3Y\/profile",
                       "guid": "ECUFIYO7BLY5FOAPEQDC3Y",
                       "birthYear": 1969,
                       "created": "2010-01-23T13:07:10Z",
                       "displayAge": 89,
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
                try
                {
                    obj = jsonParser.parse(json);
                    JSONObject jsonObj = (JSONObject) obj;
                    // get profile object
                    JSONObject jsonObjPeople = (JSONObject) jsonObj.get("profile");
                
                    socialUser.setJson(json);
                    
                    socialUser.setNickname((String) jsonObjPeople.get("nickname"));
                    socialUser.setGender((String) jsonObjPeople.get("gender"));
                    socialUser.setFirstName((String) jsonObjPeople.get("givenName"));
                    socialUser.setLastName((String) jsonObjPeople.get("familyName"));
                    
                    return socialUser;
                }
                catch (ParseException e)
                {
                  throw new OurException("Could not parse JSON data from " + authProviderName + ":" + e.getMessage());
                }
            }
            
            case ClientUtils.GOOGLE:
            {
               /* --Google--
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
 
                try
                {
                    obj = jsonParser.parse(json);
                    JSONObject jsonObj = (JSONObject) obj;
                
                    socialUser.setJson(json);
                
                    socialUser.setName((String) jsonObj.get("name"));
                    socialUser.setFirstName((String) jsonObj.get("given_name"));
                    socialUser.setLastName((String) jsonObj.get("family_name"));
                    socialUser.setGender((String)jsonObj.get("gender"));
                    
                    return socialUser;
                }
                catch (ParseException e)
                {
                  throw new OurException("Could not parse JSON data from " + authProviderName + ":" + e.getMessage());
                }
            }
            
            case ClientUtils.LINKEDIN:
            {
                /* --Linkedin--
                {
                    "firstName": "Muhammad",
                    "headline": "Sr. Software Engineer at British Telecom",
                    "lastName": "Muquit",
                 }
                 */
                try
                {
                    obj = jsonParser.parse(json);
                    JSONObject jsonObj = (JSONObject) obj;
                
                    socialUser.setJson(json);
                
                    socialUser.setFirstName((String) jsonObj.get("firstName"));
                    socialUser.setLastName((String) jsonObj.get("lastName"));
                    
                    return socialUser;
                }
                catch (ParseException e)
                {
                  throw new OurException("Could not parse JSON data from " + authProviderName + ":" + e.getMessage());
                }
            }
            
            case ClientUtils.TWITTER:
            {
               /* --Twitter --
                {
                    "id":955924206,
                    "contributors_enabled":false,
                    "profile_use_background_image":true,
                    "time_zone":"Eastern Time (US & Canada)",
                    "following":false,
                    "friends_count":3,
                    "profile_text_color": "333333",
                    "geo_enabled":false,
                    "created_at":"Sun Nov 18 17:54:22 +0000 2012",
                    "utc_offset":-18000,
                    "follow_request_sent":false,
                    "name":"Muhammad Muquit",
                    "id_str":"955924206",
                    "default_profile_image":true,
                    "verified":false,
                    "profile_sidebar_border_color":"C0DEED",
                    "url":null,
                    "favourites_count":0,
                    ..
                    "lang":"en",
                    "profile_background_color":"C0DEED",
                    "screen_name":"mmqt2012",
                    ..
                  }
               */
                
                try
                {
                    obj = jsonParser.parse(json);
                    JSONObject jsonObj = (JSONObject) obj;
                
                    socialUser.setJson(json);
                
                    socialUser.setName((String) jsonObj.get("name"));
                    socialUser.setGender((String)jsonObj.get("gender"));
                    
                    return socialUser;
                }
                catch (ParseException e)
                {
                  throw new OurException("Could not parse JSON data from " + authProviderName + ":" + e.getMessage());
                }
            }
            
            case ClientUtils.INSTAGRAM:
            {
                /* -- Instragram -- 
                {
                    "data": {
                        "id": "1574083",
                        "username": "snoopdogg",
                        "full_name": "Snoop Dogg",
                        "profile_picture": "http://distillery.s3.amazonaws.com/profiles/profile_1574083_75sq_1295469061.jpg",
                        "bio": "This is my bio",
                        "website": "http://snoopdogg.com",
                        "counts": {
                            "media": 1320,
                            "follows": 420,
                            "followed_by": 3410
                        }
                }                
                */

                try
                {
                    logger.info("Instragram JSON: " + json);
                   obj = jsonParser.parse(json);
                    JSONObject jsonObj = (JSONObject) obj;
                    // get profile object
                    JSONObject jsonObjData = (JSONObject) jsonObj.get("data");
                
                    socialUser.setJson(json);
                    socialUser.setName((String) jsonObjData.get("username"));
                    
                    return socialUser;
 
                }
                catch (ParseException e)
                {
                  throw new OurException("Could not parse JSON data from " + authProviderName + ":" + e.getMessage());
                }
                
            }
            
            case ClientUtils.GITHUB:
            {
                /* -- github  --
                {
                    "plan":{
                       "private_repos":0,
                       "space":307200,
                       "name":"free",
                       "collaborators":0
                    },
                    "followers":0,
                    "type":"User",
                    "events_url":"https://api.github.com/users/oauthdemo2012/events{/privacy}",
                    "owned_private_repos":0,
                    "public_gists":0,
                    "avatar_url":"https://secure.gravatar.com/avatar/e0cb08c2b353cc1c3022dc65ebd060d1?d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png",
                    "received_events_url":"https://api.github.com/users/oauthdemo2012/received_events",
                    "private_gists":0,
                    "disk_usage":0,
                    "url":"https://api.github.com/users/oauthdemo2012",
                    "followers_url":"https://api.github.com/users/oauthdemo2012/followers",
                    "login":"oauthdemo2012",
                    "created_at":"2012-12-20T01:36:36Z",
                    "following_url":"https://api.github.com/users/oauthdemo2012/following",
                    "organizations_url":"https://api.github.com/users/oauthdemo2012/orgs",
                    "following":0,
                    "starred_url":"https://api.github.com/users/oauthdemo2012/starred{/owner}{/repo}",
                    "collaborators":0,
                    "public_repos":0,
                    "repos_url":"https://api.github.com/users/oauthdemo2012/repos",
                    "gists_url":"https://api.github.com/users/oauthdemo2012/gists{/gist_id}",
                    "id":3085592,
                    "total_private_repos":0,
                    "html_url":"https://github.com/oauthdemo2012",
                    "subscriptions_url":"https://api.github.com/users/oauthdemo2012/subscriptions",
                    "gravatar_id":"e0cb08c2b353cc1c3022dc65ebd060d1"
               }                
               */
                logger.info("github JSON: " + json);
                try
                {
                   obj = jsonParser.parse(json);
                    JSONObject jsonObj = (JSONObject) obj;
                
                    socialUser.setJson(json);
                    socialUser.setName((String) jsonObj.get("login"));
                    
                    return socialUser;
 
                }
                catch (ParseException e)
                {
                  throw new OurException("Could not parse JSON data from " + authProviderName + ":" + e.getMessage());
                }
            }
            
            case ClientUtils.FLICKR:
            {
                /* -- flickr -- 
                {
                    "user": {
                      "id": "91390211@N06",
                      "username": {
                        "_content": "oauthdemo2012"
                      }
                    },
                    "stat": "ok"
                } 
                */
                logger.info("Flickr JSON: " + json);
                try
                {
                   obj = jsonParser.parse(json);
                   JSONObject jsonObj = (JSONObject) obj;
                   JSONObject jsonObjUser = (JSONObject) jsonObj.get("user");
                   JSONObject jsonObjUsername = (JSONObject) jsonObjUser.get("username");
                   socialUser.setName((String) jsonObjUsername.get("_content"));
                   socialUser.setJson(json);
                    
                  return socialUser;
                }
                catch (ParseException e)
                {
                  throw new OurException("Could not parse JSON data from " + authProviderName + ":" + e.getMessage());
                }
            }
            
            case ClientUtils.VIMEO:
            {
                /* --Vimeo starts --
                {
                    "generated_in": "0.0698",
                    "stat": "ok",
                    "person": {
                        "created_on": "2012-12-22 23:37:55",
                        "id": "15432968",
                        "is_contact": "0",
                        "is_plus": "0",
                        "is_pro": "0",
                        "is_staff": "0",
                        "is_subscribed_to": "0",
                        "username": "user15432968",
                        "display_name": "oauthdemo2012",
                        "location": "",
                        "url": [
                            ""
                        ],
                        .....
                    }
                } 
                */
                logger.info("Vimeo JSON: " + json);
                try
                {
                   obj = jsonParser.parse(json);
                   JSONObject jsonObj = (JSONObject) obj;
                   JSONObject jsonObjPerson = (JSONObject) jsonObj.get("person");
                   String userName = (String) jsonObjPerson.get("username");
                   String displayName = (String) jsonObjPerson.get("display_name");
                   
                   if (displayName != null)
                   {
                       socialUser.setName(displayName);
                   }
                   else if (userName != null)
                   {
                       socialUser.setName(userName);
                   }
                   else
                   {
                       socialUser.setName("Unknown");
                   }
                   socialUser.setJson(json);
                    
                  return socialUser;
                }
                catch (ParseException e)
                {
                  throw new OurException("Could not parse JSON data from " + authProviderName + ":" + e.getMessage());
                }
            }
            
            case ClientUtils.WINDOWS_LIVE:
            {
                /* Windows Live --starts --
                {
                    "id" : "contact.c1678ab4000000000000000000000000",
                    "first_name" : "Roberto",
                    "last_name" : "Tamburello",
                    "name" : "Roberto Tamburello",
                    "gender" : "male",
                    "locale" : "en_US"
                } 
                */
                logger.info("Windows Live JSON: " + json);
                try
                {
                   obj = jsonParser.parse(json);
                   JSONObject jsonObj = (JSONObject) obj;
                   JSONObject jsonErrorObj = (JSONObject) jsonObj.get("error");
                   if (jsonErrorObj != null)
                   {
                       /*
                       {
                           "error": {
                               "code": "request_token_too_many",
                               "message": "The request includes more than one access token. Only one access token is allowed."
                           }
                       }
                       */
                       String message = (String) jsonErrorObj.get("message");
                       throw new OurException("Error: " + message);
                   }
                   socialUser.setName((String) jsonObj.get("name"));
                   socialUser.setLastName((String) jsonObj.get("last_name"));
                   socialUser.setFirstName((String) jsonObj.get("first_name"));
                   socialUser.setJson(json);
                    
                  return socialUser;
                }
                catch (ParseException e)
                {
                  throw new OurException("Could not parse JSON data from " + authProviderName + ":" + e.getMessage());
                }
            }
            
            case ClientUtils.TUMBLR:
            {
                /* tumblr. -- 
                {
                    "meta": {
                      "status": 200,
                      "msg": "OK"
                    },
                    "response": {
                      "user": {
                        "name": "oauthdemo2012",
                        "likes": 0,
                        "following": 1,
                        "default_post_format": "html",
                        "blogs": [
                          {
                            "name": "oauthdemo2012",
                            "url": "http:\/\/oauthdemo2012.tumblr.com\/",
                            "followers": 0,
                            "primary": true,
                            "title": "Untitled",
                            "description": "",
                            "admin": true,
                            "updated": 0,
                            "posts": 0,
                            "messages": 0,
                            "queue": 0,
                            "drafts": 0,
                            "share_likes": true,
                            "ask": false,
                            "tweet": "N",
                            "facebook": "N",
                            "facebook_opengraph_enabled": "N",
                            "type": "public"
                          }
                        ]
                      }
                    }
                  }
                  */
                logger.info("tumblr JSON: " + json);
                try
                {
                   obj = jsonParser.parse(json);
                   JSONObject jsonObj = (JSONObject) obj;
                   JSONObject jsonObjResponse = (JSONObject) jsonObj.get("response");
                   JSONObject jsonObjUser = (JSONObject) jsonObjResponse.get("user");
                   String userName = (String) jsonObjUser.get("name");
                   socialUser.setName(userName);
                   socialUser.setJson(json);
                    
                  return socialUser;
                }
                catch (ParseException e)
                {
                  throw new OurException("Could not parse JSON data from " + authProviderName + ":" + e.getMessage());
                }
            }
            
            case ClientUtils.FOURSQUARE:
            {
                
                
                /* foursquare --         
                {
                    "meta": {
                        "code": 200,
                        "errorType": "deprecated",
                        "errorDetail": "Please provide an API version to avoid future errors.See http://bit.ly/vywCav"
                    },
                    "notifications": [
                        {
                            "type": "notificationTray",
                            "item": {
                                "unreadCount": 0
                            }
                        }
                    ],
                    "response": {
                        "user": {
                            "id": "43999331",
                            "firstName": "OAuth",
                            "lastName": "Demo",
                            "gender": "none",
                            "relationship": "self",
                            "photo": "https://foursquare.com/img/blank_boy.png",
                            "friends": {
                                "count": 0,
                                "groups": [
                                    {
                                        "type": "friends",
                                        "name": "Mutual friends",
                                        "count": 0,
                                        "items": []
                                    },
                                    {
                                        "type": "others",
                                        "name": "Other friends",
                                        "count": 0,
                                        "items": []
                                    }
                                ]
                            },
                            ......
                        }
                    }
                }
                */
                try
                {
                   obj = jsonParser.parse(json);
                   JSONObject jsonObj = (JSONObject) obj;
                   JSONObject jsonObjResponse = (JSONObject) jsonObj.get("response");
                   JSONObject jsonObjUser = (JSONObject) jsonObjResponse.get("user");
                   String firstName = (String) jsonObjUser.get("firstName");
                   String lastName = (String) jsonObjUser.get("lastName");
                   if (firstName != null && lastName != null)
                   {
                       socialUser.setName(firstName + " " + lastName);
                   }
                   else
                   {
                       socialUser.setName("UNKNOWN");
                   }
                   socialUser.setJson(json);
                    
                  return socialUser;
                }
                catch (ParseException e)
                {
                  throw new OurException("Could not parse JSON data from " + authProviderName + ":" + e.getMessage());
                }
            }
 
            default:
            {
                throw new OurException("Unknown Auth Provider: " + authProviderName);
            }
        }
        
       
        /*
         * We don't use Gson() anymore as it choked on nested Facebook JSON data
         * Dec-03-2012
         */
        
        /*
        // map json to SocialUser
        try
        {
            Gson gson = new Gson();
            SocialUser user = gson.fromJson(json,SocialUser.class);
            // pretty print json
            //gson = new GsonBuilder().setPrettyPrinting().create();
            //String jsonPretty = gson.toJson(json);
            user.setJson(json);
            return user;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new OurException("Could not map userinfo JSON to SocialUser class: " + e);
        }
        */
    }
 
    private void verifyState(String state) throws OurException
    {
        String stateInSession = getStateFromSession();
        if (stateInSession == null)
        {
            throw new OurException("Could not find state in session");
        }
        if (!stateInSession.equals(state))
        {
            throw new OurException("State mismatch in session, expected: " + stateInSession + " Passed: " + state);
        }
    }

    private HttpSession getHttpSession()
    {
        return getThreadLocalRequest().getSession();
    }

    private HttpSession validateSession(String sessionId) throws OurException
    {
        if (sessionId == null)
            throw new OurException("Session Id can not be empty");
        HttpSession session=getHttpSession();
        if (session == null)
        {
            throw new OurException(ClientUtils.SESSION_EXPIRED_MESSAGE);
        }
        /*
        ServersideSession ssSession=getServersideSession();
        if (ssSession == null)
        {
            throw new OurException(ClientUtils.SESSION_EXPIRED_MESSAGE);
        }
        if (sessionId.equals(ssSession.getSessionId()))
        {
            return session;
        }
        */
        String savedSessionId = getSessionIdFromSession();
        if (sessionId.equals(savedSessionId))
        {
            return session;
        }
        throw new OurException("Session Id mismatch: expected " + "'" + sessionId + "'" + " Found: " + "'" + savedSessionId + "'");
    }
    
    /*
    private ServersideSession getServersideSession()
    {
        HttpSession session = getHttpSession();
        if (session != null)
        {
            ServersideSession ssSession = (ServersideSession) session.getAttribute(SESSION);
            return ssSession;
        }
        return null;
    }
    */

    @Override
    public void logout(String sessionId) throws OurException
    {
        /* don't bother to validate.. just invalidate already */
//        validateSession(sessionId);
        
        getHttpSession().invalidate();
        logger.info("Invalidated HTTP session");
        
    }
    

    @Override
    public SocialUser fetchMe(String sessionId) throws OurException
    {
        validateSession(sessionId);
        int authProvider = getAuthProviderFromSession();
        if (authProvider == ClientUtils.DEFAULT)
        {
            SocialUser user = new SocialUser();
            user.setName(getDefaultUsername());
            user.setJson(getDefaultJson());
            return user;
        }
        Token accessToken = getAccessTokenFromSession();
        SocialUser user = getSocialUser(accessToken,authProvider);
        
        return user;
    }
    
    
    private static Map<String,String> parseQueryString(String qs)
    {
        String[] ps = qs.split("&");
        Map<String,String> map = new HashMap<String,String>();
        
        for (String p: ps )
        {
            String k = p.split("=")[0];
            String v = p.split("=")[1];
            map.put(k,v);
        }
        return map;
    }
    private String getQueryStringValue(String qs,String name)
    {
        Map<String,String> map = parseQueryString(qs);
        return map.get(name);
    }

    @Override
    public String getAccessToken(String sessionId) throws OurException
    {
        validateSession(sessionId);
        Token accessToken = getAccessTokenFromSession();
        if (accessToken == null)
        {
            throw new OurException("Could not find Access Token in HTTP Session");
        }
        return accessToken.getRawResponse();
    }

}
