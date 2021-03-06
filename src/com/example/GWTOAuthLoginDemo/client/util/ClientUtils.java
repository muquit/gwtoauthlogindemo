package com.example.GWTOAuthLoginDemo.client.util;

import com.example.GWTOAuthLoginDemo.client.GWTOAuthLoginDemo;
import com.example.GWTOAuthLoginDemo.client.OAuth.OurCallbackUrl;
import com.example.GWTOAuthLoginDemo.client.OAuth.OurProtectedUrls;
import com.example.GWTOAuthLoginDemo.client.exception.OurException;
import com.example.GWTOAuthLoginDemo.client.model.Credential;
import com.example.GWTOAuthLoginDemo.client.resources.MyResources;
import com.example.GWTOAuthLoginDemo.client.resources.css.MyStylesCss;
import com.example.GWTOAuthLoginDemo.client.resources.images.MyImages;
import com.example.GWTOAuthLoginDemo.client.rpc.MyAsyncCallback;
import com.example.GWTOAuthLoginDemo.client.rpc.OAuthLoginService;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ClientUtils
{
    private final static String SESSION_ID_COOKIE         = "gwtgoogleoauthtest_session_id";
    private final static String AUTH_PROVIDER_COOKIE      = "gwtgoogleoauthtest_provider";
    private final static String AUTH_PROVIDER_NAME_COOKIE = "gwtgoogleoauthtest_provider_name";
    private final static String USERNAME_COOKIE           = "gwtgoogleoauthtest_user";
    private final static String REDIRECT_URL_COOKIE       = "gwtgoogleoauthtest_redirect_url";
    
    public final static int DIALOG_X_POSITION = 20;
    public final static int DIALOG_Y_POSITION = 110;

    
    public static final String SESSION_EXPIRED_MESSAGE = "Session has expired";
    
    private static String[] authProviders = 
    {
       "Facebook", "Google",   "Twitter",     "Yahoo!",
       "Flickr",   "ImgUr",    "LinkedIn",    "Windows Live",
       "Instagram","github",   "Vimeo",       "Evernote",
       "tumblr.", "foursquare","AWeber"
    };
    
    public final static int UNKNOWN  =     -1;
    public final static int DEFAULT  =     0;
    public final static int FACEBOOK =     1;
    public final static int GOOGLE   =     2;
    public final static int TWITTER  =     3;
    public final static int YAHOO    =     4;
    public final static int FLICKR   =     5;
    public final static int IMGUR   =      6;
    public final static int LINKEDIN =     7;
    public final static int WINDOWS_LIVE = 8;
    public final static int INSTAGRAM    = 9;
    public final static int GITHUB       = 10;
    public final static int VIMEO        = 11;
    public final static int EVERNOTE     = 12;
    public final static int TUMBLR       = 13;
    public final static int FOURSQUARE   = 14;
    public final static int AWEBER       = 15;
    
    public static String[] getAuthProviders()
    {
        return authProviders;
    }    
    
    public static int getAuthProvider(String authProviderName)
    {
        int authProvider = DEFAULT;
        authProviderName = authProviderName.toLowerCase();
        if (authProviderName.equals("facebook"))
            return FACEBOOK;
        else if (authProviderName.equals("google"))
            return GOOGLE;
        else if (authProviderName.equals("twitter"))
            return TWITTER;
        else if (authProviderName.equals("yahoo!"))
            return YAHOO;
        else if (authProviderName.equals("yahoo"))
            return YAHOO;
        else if (authProviderName.equals("flickr"))
            return FLICKR;
        else if (authProviderName.equals("imgur"))
            return IMGUR;
        else if (authProviderName.equals("linkedin"))
            return LINKEDIN;
        else if (authProviderName.equals("windows live"))
            return WINDOWS_LIVE;
        else if (authProviderName.equals("instagram"))
            return INSTAGRAM;
        else if (authProviderName.equals("github"))
            return GITHUB;
        else if (authProviderName.equals("vimeo"))
            return VIMEO;
        else if (authProviderName.equals("evernote"))
            return EVERNOTE;
        else if (authProviderName.equals("tumblr."))
            return TUMBLR;
        else if (authProviderName.equals("foursquare"))
            return FOURSQUARE;
        else if (authProviderName.equals("aweber"))
            return AWEBER;
        
        return authProvider;
    }
    
    public static String getAuthProviderName(int authProvider)
    {
        if (authProvider == FACEBOOK)
            return "Facebook";
        else if (authProvider == GOOGLE)
            return "Google";
        else if (authProvider == TWITTER)
            return "Twitter";
        else if (authProvider == YAHOO)
            return "Yahoo!";
        else if (authProvider == FLICKR)
            return "Flicker";
        else if (authProvider == IMGUR)
            return "ImgUr";
        else if (authProvider == LINKEDIN)
            return "LinkedIn";
        else if (authProvider == WINDOWS_LIVE)
            return "Windows Live";
        else if (authProvider == INSTAGRAM)
            return "Instagram";
        else if (authProvider == GITHUB)
            return "github";
        else if (authProvider == VIMEO)
            return "vimeo";
        else if (authProvider == EVERNOTE)
            return "Evernote";
        else if (authProvider == TUMBLR)
            return "tumblr.";
        else if (authProvider == FOURSQUARE)
            return "foursquare";
        else if (authProvider == AWEBER)
            return "AWeber";
        
        return "Default";
    }
    
    
    public static String getCallbackUrl()
    {
        return OurCallbackUrl.APP_CALLBACK_URL;
    }
    
    public static String getProctedResourceUrl(int authProvider)
    {
        switch(authProvider)
        {
            case FACEBOOK:
            {
                return OurProtectedUrls.FACEBOOK_PROTECTED_RESOURCE_URL;
            }
            
            case GOOGLE:
            {
                return OurProtectedUrls.GOOGLE_PROTECTED_RESOURSE_URL;
            }

            case TWITTER:
            {
                return OurProtectedUrls.TWITTER_PROTECTED_RESOURCE_URL;
            }
            
            case YAHOO:
            {
                return OurProtectedUrls.YAHOO_PROTECTED_RESOURCE_URL;
            }
            
            case LINKEDIN:
            {
                return OurProtectedUrls.LINKEDIN_PROTECTED_RESOURCE_URL;
            }
            
            case INSTAGRAM:
            {
                return OurProtectedUrls.INSTAGRAM_PROTECTED_RESOURCE_URL;
            }
            
            case IMGUR:
            {
                return OurProtectedUrls.IMGUR_PROTECTED_RESOURCE_URL;
            }
            
            case GITHUB:
            {
                return OurProtectedUrls.GITHUB_PROTECTED_RESOURCE_URL;
            }
            
            case FLICKR:
            {
                return OurProtectedUrls.FLICKR_PROTECTED_RESOURCE_URL;
            }
            
            case VIMEO:
            {
                return OurProtectedUrls.VIMEO_PROTECTED_RESOURCE_URL;
            }
            
            case WINDOWS_LIVE:
            {
                return OurProtectedUrls.LIVE_PROTECTED_RESOURCE_URL;
            }
            
            case TUMBLR:
            {
                return OurProtectedUrls.TUMBLR_PROTECTED_RESOURCE_URL;
            }
            
            case FOURSQUARE:
            {
                return OurProtectedUrls.FOURSQUARE_PROTECTED_RESOURCE_URL;
            }
            
            case AWEBER:
            {
                return OurProtectedUrls.AWEBER_PROTECTED_RESOURCE_URL;
            }
            
            default:
            {
                return null;
            }
        }
    }
    
    public static void clearCookies()
    {
        Cookies.removeCookie(SESSION_ID_COOKIE);
        Cookies.removeCookie(AUTH_PROVIDER_COOKIE);
        Cookies.removeCookie(AUTH_PROVIDER_NAME_COOKIE);
        Cookies.removeCookie(USERNAME_COOKIE);
        Cookies.removeCookie(REDIRECT_URL_COOKIE);
    }
    
    public static String getSessionIdFromCookie()
    {
        return Cookies.getCookie(SESSION_ID_COOKIE);
    }
    
    public static String getAuthProviderFromCookie()
    {
        return Cookies.getCookie(AUTH_PROVIDER_COOKIE);
    }
    
    public static int getAuthProviderFromCookieAsInt()
    {
       String authProviderStr = getAuthProviderFromCookie();
       int authProvider = UNKNOWN;
       if (authProviderStr != null)
       {
           try
           {
               authProvider = Integer.parseInt(authProviderStr);
               
           }
           catch(NumberFormatException e)
           {
               return UNKNOWN;
           }
       }
       
       return authProvider;
    }
    
    public static String getAuthProviderNameFromCookie()
    {
        return Cookies.getCookie(AUTH_PROVIDER_NAME_COOKIE);
    }
    
    public static String getUsernameFromCookie()
    {
        return Cookies.getCookie(USERNAME_COOKIE);
    }
    
    public static boolean alreadyLoggedIn()
    {
        if (getSessionIdFromCookie() != null)
            return true;
        return false;
    }
    
    public static void saveSessionId(String sessionId)
    {
        Cookies.setCookie(SESSION_ID_COOKIE,sessionId);
    }
    
    public static void saveAuthProvider(int authProvider)
    {
       Cookies.setCookie(AUTH_PROVIDER_COOKIE,Integer.toString(authProvider)); 
       String authProviderName = getAuthProviderName(authProvider);
       saveAuthProviderName(authProviderName);
    }
    
    public static void saveAuthProviderName(String authProviderName)
    {
       Cookies.setCookie(AUTH_PROVIDER_NAME_COOKIE,authProviderName); 
    }
    
    public static void saveUsername(String username)
    {
        Cookies.setCookie(USERNAME_COOKIE,username);
    }
    
    public static void saveRediretUrl(String url)
    {
        Cookies.setCookie(REDIRECT_URL_COOKIE,url);
    }
    
    public static String getRedirectUrlFromCookie()
    {
        return Cookies.getCookie(REDIRECT_URL_COOKIE);
    }
    
    public static void redirect(String url)
    {
        Window.Location.assign(url);
    }
    
    public static boolean redirected()
    {
        String authProvider = getAuthProviderFromCookie();
        if (authProvider == null)
        {
            return false;
        }
        
        if (Location.getParameter("code") != null) //facebook,google,github,windows live
            return true;
        
        if (Location.getParameter("oauth_token") != null) // twitter,yahoo,flickr
            return true;
        
        if (Location.getParameter("oauth_verifier") != null) // Flickr
            return true;
        
        String error = Location.getParameter("error");
        if (error != null)
        {
            String errorMessage = Location.getParameter("error_description");
            Window.alert("Error: " + error + ":" + errorMessage);
            reload();
            return false;
        }
        
        return false;
    }
    
    public static void reload()
    {
        String appUrl = getRedirectUrlFromCookie();
        int savedAuthProvider = getAuthProviderFromCookieAsInt();
        
        clearCookies();
        
        if (savedAuthProvider == DEFAULT || savedAuthProvider == UNKNOWN)
        {
            GWTOAuthLoginDemo.get().updateLoginStatus();
        }
 
        if (appUrl != null)
        {
            redirect(appUrl);
        }
   }
    
    public static Credential getCredential() throws OurException
    {
        String authProvider = getAuthProviderFromCookie();
        if (authProvider == null)
            return null;
        int ap = DEFAULT;
        
        try
        {
            ap = Integer.parseInt(authProvider);
        }
        catch(Exception e)
        {
            throw new OurException("Could not convert authProvider " + authProvider + " to Integer");
        }
        
        switch(ap)
        {
            case DEFAULT:
            {
                Credential credential = new Credential();
                credential.setAuthProvider(ap);
                return credential;
            }
            case FACEBOOK:
            case INSTAGRAM:
            case LINKEDIN:
            case IMGUR:
            case GITHUB:
            {
                Credential credential = new Credential();
                credential.setAuthProvider(ap);
                credential.setState(Location.getParameter("state"));
                credential.setVerifier(Location.getParameter("code"));
                return credential;
            }
            case GOOGLE:
            case WINDOWS_LIVE:
            case FOURSQUARE:
            {
                Credential credential = new Credential();
                credential.setAuthProvider(ap);
                credential.setVerifier(Location.getParameter("code"));
                return credential;
            }
            case TWITTER:
            case YAHOO:
            case FLICKR:
            case VIMEO:
            case TUMBLR:
            {
                Credential credential = new Credential();
                credential.setAuthProvider(ap);
                credential.setVerifier(Location.getParameter("oauth_verifier"));
                return credential;
            }
            
            default:
            {
                throw new OurException("ClientUtils.getCredential: Auth Provider " + authProvider + " Not implemented yet");
            }
        }
    }
    
    public static void handleException(Throwable caught)
    {
         if (caught.getMessage().equals(ClientUtils.SESSION_EXPIRED_MESSAGE))
        {
            showSessionExpires();
        }
        else
        {
            showGenericException(caught);

        }
    }
    
    public static void showSessionExpires()
    {
        Window.alert("Your session seems to have expired!\n" +  "You will be logged out");
        ClientUtils.reload();
    }
    
    public static void showGenericException(Throwable caught)
    {               
        String message = "Exception: " + caught;
        message += "\n";
        message += "Please Logout/reload the application";
        //String st = LogUtil.stackTraceToString(caught);
        //message += "Stack Trace:\n" + st;
        Window.alert(message);
    }
    public static void logout()
    {
        final String sessionId = ClientUtils.getSessionIdFromCookie();
        
        new MyAsyncCallback<Void>()
        {

            @Override
            public void onFailure(Throwable caught)
            {
                ClientUtils.reload(); // reload anyway otherwise we're toast! we will never be able to log out
            }

            @Override
            public void onSuccess(Void result)
            {
                GWTOAuthLoginDemo.get().log("Logged out.. clearing cookies");
                GWTOAuthLoginDemo.get().log("Redirecting to site url");
                ClientUtils.reload();
            }

            @Override
            protected void callService(AsyncCallback<Void> cb)
            {
                OAuthLoginService.Util.getInstance().logout(sessionId,cb);
            }
        }.go("Logging out..");
    }    
}

