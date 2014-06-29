package com.example.GWTOAuthLoginDemo.client.OAuth;

public class OurProtectedUrls
{
    public OurProtectedUrls(){}
    public static final String FACEBOOK_PROTECTED_RESOURCE_URL  = "https://graph.facebook.com/me";
    public static final String GOOGLE_PROTECTED_RESOURSE_URL    = "https://www.googleapis.com/oauth2/v1/userinfo";
    public static final String TWITTER_PROTECTED_RESOURCE_URL   = "https://api.twitter.com/1.1/account/verify_credentials.json";

    // %s is guid and the caller must replace
    public static final String YAHOO_PROTECTED_RESOURCE_URL     = "https://social.yahooapis.com/v1/user/%s/profile?format=json";

//    public static final String YAHOO_PROTECTED_RESOURCE_URL     = "https://social.yahooapis.com/v1/me/%s";
        
    public static final String LINKEDIN_PROTECTED_RESOURCE_URL  = "https://api.linkedin.com/v1/people/~?oauth2_access_token=%s&format=json";

    // %s is userId and access token and the caller must replace
    public static final String INSTAGRAM_PROTECTED_RESOURCE_URL = "https://api.instagram.com/v1/users/%s/?access_token=%s";

//    public static final String IMGUR_PROTECTED_RESOURCE_URL     = "http://api.imgur.com/2/account.json";
    // %s is returned username and must be replaced by the caller
    public static final String IMGUR_PROTECTED_RESOURCE_URL     = "https://api.imgur.com/3/account/%s";
    public static final String GITHUB_PROTECTED_RESOURCE_URL    = "https://api.github.com/user?access_token=%s";
    public static final String LIVE_PROTECTED_RESOURCE_URL      = "https://apis.live.net/v5.0/me";
    public static final String FLICKR_PROTECTED_RESOURCE_URL    = "https://api.flickr.com/services/rest?format=json&nojsoncallback=1&method=flickr.test.login";
    public static final String VIMEO_PROTECTED_RESOURCE_URL     = "https://vimeo.com/api/rest/v2?format=json&method=vimeo.people.getInfo";
    public static final String TUMBLR_PROTECTED_RESOURCE_URL    = "https://api.tumblr.com/v2/user/info";

    // caller must replace %s with access token
    public static final String FOURSQUARE_PROTECTED_RESOURCE_URL = "https://api.foursquare.com/v2/users/self?v=20131016&oauth_token=%s";

    // Jun-29-2014
    public static final String AWEBER_PROTECTED_RESOURCE_URL     = "https://api.aweber.com/1.0/accounts/";
    

}
