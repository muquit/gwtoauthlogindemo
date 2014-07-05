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
package com.example.GWTOAuthLoginDemo.client;
import java.util.Date;

import com.allen_sauer.gwt.log.client.Log;
import com.example.GWTOAuthLoginDemo.client.model.Credential;
import com.example.GWTOAuthLoginDemo.client.model.SocialUser;
import com.example.GWTOAuthLoginDemo.client.resources.MyResources;
import com.example.GWTOAuthLoginDemo.client.resources.images.MyImages;
import com.example.GWTOAuthLoginDemo.client.rpc.MyAsyncCallback;
import com.example.GWTOAuthLoginDemo.client.rpc.OAuthLoginService;
import com.example.GWTOAuthLoginDemo.client.ui.AppScreen;
import com.example.GWTOAuthLoginDemo.client.ui.LoginScreen;
import com.example.GWTOAuthLoginDemo.client.ui.MessageDialog;
import com.example.GWTOAuthLoginDemo.client.util.ClientUtils;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.TextArea;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GWTOAuthLoginDemo implements EntryPoint
{
    private final String VERSION = "1.72";
    private final String VERSION_STRING = "GWTOAuthLoginDemo dev v" + VERSION;
    private final String VERSION_STRING_PLAIN = "GWTOAuthLoginDemo v" +  VERSION;
    private final String WELCOME_STRING = "Welcome to " + VERSION_STRING;
    
    private static GWTOAuthLoginDemo singleton;
    
   
    AppScreen appScreen           = new AppScreen();
    LoginScreen loginScreen       = appScreen.getLoginScreen();
    MessageDialog messageDialog   = new MessageDialog(VERSION_STRING_PLAIN);
    
    public static GWTOAuthLoginDemo get()
    {
        return singleton;
    }
    
    public void onModuleLoad()
    {
        singleton = this;
        Log.info("Loading app..");
        setupMainScreenHandlers();
        setupLoginScreenHandlers();
        showApp();
        handleRedirect();
        updateLoginStatus();
    }
    
    public void updateLoginStatus()
    {
        // if there is a client side session show, Logout link
        if (ClientUtils.alreadyLoggedIn())
        {
            //log("Already logged in..showing Logout anchor");
            showLogoutAnchor();
        }
        else
        {
            showLoginScreen();
        }
        updateLoginLabel();
    }
    
    private void showLogoutAnchor()
    {
        appScreen.getTopBar().showLogoutAnchor();
        appScreen.getLoginScreen().setVisible(false);
    }
    
    private void showLoginScreen()
    {
        appScreen.getLoginScreen().setVisible(true);
        updateWelcomeLabel(WELCOME_STRING);
    }
    
    private void updateLoginLabel()
    {
        MyImages images = MyResources.INSTANCE.images();
        String name = ClientUtils.getUsernameFromCookie();
        String authProviderName = ClientUtils.getAuthProviderNameFromCookie();
        int authProvider = ClientUtils.getAuthProviderFromCookieAsInt();
        
        SafeUri safeUri = null;
        if (name != null)
        {
            String labelStr = "Welcome " + "<font color=\"#006600\">"+ authProviderName + "</font>" + " user " + "<font color=\"#006600\">" + name + "</font>";
            updateWelcomeLabel(labelStr);
            String tooltip = "You logged in using ";
            switch(authProvider)
            {
                case ClientUtils.FACEBOOK:
                {
                    safeUri = images.facebookIconImageData().getSafeUri();
                    tooltip += "Facebook";
                    break;
                }
                case ClientUtils.GOOGLE:
                {
                    safeUri = images.googleIconImageData().getSafeUri();
                    tooltip += "Google";
                    break;
                }
                case ClientUtils.TWITTER:
                {
                    safeUri = images.twitterIconImageData().getSafeUri();
                    tooltip += "Twitter";
                    break;
                }
                
                case ClientUtils.YAHOO:
                {
                    safeUri = images.yahooIconImageData().getSafeUri();
                    tooltip += "Yahoo";
                    break;
                }
                
                case ClientUtils.LINKEDIN:
                {
                    safeUri = images.linkedinIconImageData().getSafeUri();
                    tooltip += "Linkedin";
                    break;
                }
                
                case ClientUtils.INSTAGRAM:
                {
                    safeUri = images.instagramIconImageData().getSafeUri();
                    tooltip += "Instagram";
                    break;
                }
                
                case ClientUtils.IMGUR:
                {
                    safeUri = images.imgurIconImageData().getSafeUri();
                    tooltip += "ImgUr";
                    break;
                }
                
                case ClientUtils.GITHUB:
                {
                    safeUri = images.githubIconImageData().getSafeUri();
                    tooltip += "github";
                    break;
                }
                
                case ClientUtils.FLICKR:
                {
                    safeUri = images.flickrIconImageData().getSafeUri();
                    tooltip += "Flickr";
                    break;
                }
                
                case ClientUtils.VIMEO:
                {
                    safeUri = images.vimeoIconImageData().getSafeUri();
                    tooltip += "Vimeo";
                    break;
                }
                
                case ClientUtils.WINDOWS_LIVE:
                {
                    safeUri = images.liveIconImageData().getSafeUri();
                    tooltip += "Microsoft Live Connect";
                    break;
                }
                
                case ClientUtils.TUMBLR:
                {
                    safeUri = images.tumblrIconImageData().getSafeUri();
                    tooltip += "tumblr.";
                    break;
                }
                
                case ClientUtils.FOURSQUARE:
                {
                    safeUri = images.foursquareIconImageData().getSafeUri();
                    tooltip += "foursquare";
                    break;
                }
                
                case ClientUtils.DEFAULT:
                {
                    // No images at the moment
                    break;
                }
                
                default:
                {
                    safeUri = null;
                    break;
                }
            }
            if (safeUri != null)
            {
                appScreen.getAuthProviderImage().setUrl(safeUri);
                appScreen.getAuthProviderImage().setSize("24px","24px");
                appScreen.getAuthProviderImage().setTitle(tooltip);
            }
        }
        else
        {
            updateWelcomeLabel(WELCOME_STRING);
        }
    }
    
    private void updateWelcomeLabel(String labelStr)
    {
        //appScreen.getLblWelcome().setText(labelStr);
        //appScreen.getLblWelcome().setHTML(labelStr);
        appScreen.updateWelcomeMessage(labelStr);
    }
 
    private void handleRedirect()
    {
        if (ClientUtils.redirected())
        {
            if (!ClientUtils.alreadyLoggedIn())
            {
                verifySocialUser();
            }
        }
        else
        {
            //Window.alert("No redirection..");
        }
        updateLoginStatus();        
    }
    
    private void verifySocialUser()
    {
        final String authProviderName = ClientUtils.getAuthProviderNameFromCookie();
        final int authProvider = ClientUtils.getAuthProviderFromCookieAsInt();
        log("Verifying " + authProviderName + " user ...");
        final String userName = loginScreen.getUsernameTextBox().getText();
        final String password = loginScreen.getPasswordTextBox().getText();
        
        if (authProvider == ClientUtils.DEFAULT)
        {
            if (userName.length() == 0)
            {
                Window.alert("Username is empty");
                return;
            }
            if (password.length() == 0)
            {
                Window.alert("Password is emtpy");
                return;
            }
            else
            {
                /*
                if (loginDialog == null)
                {
                    // we're using login screen
                    showApp(APPSCREEN_MAIN);
                }
                */
 
            }
            
        }
       
        new MyAsyncCallback<SocialUser>()
        {

            @Override
            public void onSuccess(SocialUser result)
            {
                ClientUtils.saveSessionId(result.getSessionId());
                
                String name = "";
                if (result.getName() != null)
                {
                    name = result.getName();
                }
                else if (result.getNickname() != null) // yahoo
                {
                    name = result.getNickname();
                }
                else if (result.getFirstName() != null) // linkedin
                {
                    name = result.getFirstName();
                    String lastName = result.getLastName();
                    if (lastName != null)
                    {
                        name = name + " " + lastName;
                    }
                }
                        
                log(authProviderName + " user '" + name + "' is verified!\n" + result.getJson());
                ClientUtils.saveUsername(name);
                updateLoginStatus();
            }

            @Override
            protected void callService(AsyncCallback<SocialUser> cb)
            {
                try
                {
                    final Credential credential = ClientUtils.getCredential();
                    if (credential == null)
                    {
                        log("verifySocialUser: Could not get credential for " + authProvider + " user");
                        return;
                    }
 
                   if (authProvider == ClientUtils.DEFAULT)
                   {
                       credential.setLoginName(userName);
                       credential.setPassword(password);
                   }
                    
                    OAuthLoginService.Util.getInstance().verifySocialUser(credential,cb);
                }
                catch (Exception e)
                {
                    Window.alert(e.getMessage());
                }
            }

            @Override
            public void onFailure(Throwable caught)
            {
                Window.alert("Coult not verify" + authProvider + " user." + caught);
            }
        }.go("Verifying " + authProviderName + " user..");
        
        /*
        AsyncCallback<SocialUser> callback = new AsyncCallback<SocialUser>()
        {

            @Override
            public void onFailure(Throwable caught)
            {
                Window.alert("Coult not verify" + authProvider + " user." + caught);
            }

            @Override
            public void onSuccess(SocialUser result)
            {
                log(authProvider + " user is verified");
                ClientUtils.saveSessionId(result.getSessionId());
                ClientUtils.saveUsername(result.getName());
                updateLoginStatus();
            }
        };
        OAuthLoginService.Util.getInstance().verifySocialUser(credential,callback);
        */
    }
    

    private void show(final String s)
    {
        Window.alert(s);
    }
    
    private void setupMainScreenHandlers()
    {
        /*
        appScreen.getLoginAnchor().addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
                showLoginScreen();
            }
        });
        */
        
        appScreen.getTopBar().getLogoutAnchor().addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
                log("Calling logout()");
                ClientUtils.logout();
            }
        });
        
        appScreen.getBtnMe().addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
                getMeObject();
            }
        });
        
        appScreen.getBtnAccessToken().addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
                getAccessToken();
            }
        });
        
        appScreen.getBtnClear().addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
                appScreen.getTextArea().setText("");
            }
        });
        
        appScreen.getBtnAbout().addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
                showAboutMessage();
            }
        });
    }
    
    private void setupLoginScreenHandlers()
    {
        /*
        appScreenLogin.getHomeAnchor().addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
                showApp(APPSCREEN_MAIN);
            }
        });
        */
        
        loginScreen.getFacebookImage().addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
               final int authProvider = ClientUtils.getAuthProvider("Facebook");
               getAuthorizationUrl(authProvider);
            }
        });
        
        loginScreen.getGoogleImage().addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
               final int authProvider = ClientUtils.getAuthProvider("Google");
               getAuthorizationUrl(authProvider);
            }
        });
        
        loginScreen.getTwitterImage().addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
               final int authProvider = ClientUtils.getAuthProvider("Twitter");
               getAuthorizationUrl(authProvider);
            }
        });
        
        loginScreen.getYahooImage().addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
               final int authProvider = ClientUtils.getAuthProvider("Yahoo!");
               getAuthorizationUrl(authProvider);
            }
        });
        
        loginScreen.getLinkedinImage().addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
               final int authProvider = ClientUtils.getAuthProvider("Linkedin");
               getAuthorizationUrl(authProvider);
            }
        });
        
        loginScreen.getInstagramImage().addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
               final int authProvider = ClientUtils.getAuthProvider("Instagram");
               getAuthorizationUrl(authProvider);
            }
        });
        
        loginScreen.getVimeoImage().addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
               final int authProvider = ClientUtils.getAuthProvider("Vimeo");
               getAuthorizationUrl(authProvider);
            }
        });
                
        /* cert does not match name.. so not supporting at this time
        loginScreen.getImgurImage().addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
               hideLoginDialog();
               final int authProvider = ClientUtils.getAuthProvider("Imgur");
               notImplementedYet("Login using ImagUr is not implemented yet!");
               //getAuthorizationUrl(authProvider);
            }
        });
        */
        
        loginScreen.getGithubImage().addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
               final int authProvider = ClientUtils.getAuthProvider("github");
               getAuthorizationUrl(authProvider);
            }
        });
        
        loginScreen.getFlickrImage().addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
               final int authProvider = ClientUtils.getAuthProvider("flickr");
               getAuthorizationUrl(authProvider);
            }
        });
        
        loginScreen.getLiveImage().addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
               final int authProvider = ClientUtils.getAuthProvider("Windows Live");
               getAuthorizationUrl(authProvider);
            }
        });
        
        loginScreen.getTumblrImage().addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
               final int authProvider = ClientUtils.getAuthProvider("tumblr.");
               getAuthorizationUrl(authProvider);
            }
        });
        
        loginScreen.getFoursquareImage().addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
               final int authProvider = ClientUtils.getAuthProvider("foursquare");
               getAuthorizationUrl(authProvider);
            }
        });

        /*
         * ImgUr API is super buggy. dropping it.
         */
        /*
        loginScreen.getImgurImage().addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
               hideLoginDialog();
               final int authProvider = ClientUtils.getAuthProvider("imgur");
               getAuthorizationUrl(authProvider);
            }
        });       
        */
        /*
         * AWeber requires paid registration to test the API, hence not
         * supported at this time
         */
        /*
        loginScreen.getAweberImage().addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
               hideLoginDialog();
               final int authProvider = ClientUtils.getAuthProvider("aweber");
               getAuthorizationUrl(authProvider);
            }
        });
        */
        
        loginScreen.getPasswordTextBox().addKeyPressHandler(new KeyPressHandler()
        {
            @Override
            public void onKeyPress(KeyPressEvent event)
            {
                if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER)
                {
                    ClientUtils.clearCookies(); 
                    ClientUtils.saveAuthProvider(ClientUtils.DEFAULT);
                    verifySocialUser();
                }
            }
        });
                        
        loginScreen.getBtnLogin().addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
                
                // clear all cookes first
                ClientUtils.clearCookies(); 
                // save the auth provider to cookie
                ClientUtils.saveAuthProvider(ClientUtils.DEFAULT);
                
                verifySocialUser();
                
            }
        });
        
    }
    
    private void notImplementedYet(String html)
    {
        showMessage(html);
    }
    
    private void getAccessToken()
    {        
        final String authProviderName = ClientUtils.getAuthProviderNameFromCookie();
        final int authProvider = ClientUtils.getAuthProviderFromCookieAsInt();
        if (authProvider == ClientUtils.DEFAULT)
        {
            log("Access Token is not available for " + authProviderName + " provider");
            return;
        }
        final String sessionId = ClientUtils.getSessionIdFromCookie();
        if (sessionId == null)
        {
            String html = "Please Login first to get access to your protected resource";
            showMessage(html);
            return;
        }
        log("Got session id in client: " + sessionId);
        
        new MyAsyncCallback<String>()
        {

            @Override
            public void onFailure(Throwable caught)
            {
                ClientUtils.handleException(caught);
            }


            @Override
            public void onSuccess(String result)
            {
                log("Access token:");
                log(result);
            }

            @Override
            protected void callService(AsyncCallback<String> cb)
            {
                OAuthLoginService.Util.getInstance().getAccessToken(sessionId,cb);
            }
        }.go("Getting Access Token...");
        
    }
    
    private void getMeObject()
    {
        final String sessionId = ClientUtils.getSessionIdFromCookie();
        if (sessionId == null)
        {
            String html = "Please Login first to get access to your protected resource";
            showMessage(html);
            return;
        }
        log("Got session id in client: " + sessionId);
            
        new MyAsyncCallback<SocialUser>()
        {

            @Override
            public void onSuccess(SocialUser result)
            {
                if (result == null)
                {
                   Window.alert("Could not receive User"); 
                }
                else
                {
                    String authProvider = ClientUtils.getAuthProviderNameFromCookie();
                    String json = result.getJson();
                    if (json != null)
                    {
                        log(authProvider + " user retrieved...\n" + json);
                    }
                    else
                    {
                        log("No JSON available for " + authProvider + " user");
                        
                    }
                }
            }
            
            @Override
            protected void callService(AsyncCallback<SocialUser> cb)
            {
                OAuthLoginService.Util.getInstance().fetchMe(sessionId,cb);
            }

            @Override
            public void onFailure(Throwable caught)
            {
                ClientUtils.handleException(caught);
            }
        }.go("Getting Protected resource..");
        
        /*
        AsyncCallback<SocialUser> callback = new AsyncCallback<SocialUser>()
        {

            @Override
            public void onFailure(Throwable caught)
            {

                if (caught.getMessage().equals(ClientUtils.SESSION_EXPIRED_MESSAGE))
                {
                    showSessionExpires();
                }
                else
                {
                    showGenericException(caught.getMessage());

                }
            }

            @Override
            public void onSuccess(SocialUser result)
            {
                if (result == null)
                {
                   Window.alert("Could not receive User"); 
                }
                else
                {
                    String authProvider = ClientUtils.getAuthProviderNameFromCookie();
                    log(authProvider + " user retrieved...");
                    String json = result.getJson();
                    if (json != null)
                    {
                        log(json);
                    }
                }
            }
        };       
        OAuthLoginService.Util.getInstance().fetchMe(sessionId,callback);
        */
    }
    
    private void getAuthorizationUrl(final int authProvider)
    {
        String authProviderName = ClientUtils.getAuthProviderName(authProvider);
        final String callbackUrl = ClientUtils.getCallbackUrl();
        log("Getting authorization url");
        
        final Credential credential = new Credential();
        credential.setRedirectUrl(callbackUrl);
        credential.setAuthProvider(authProvider);
        
        new MyAsyncCallback<String>()
        {
            @Override
            public void onSuccess(String result)
            {
                String authorizationUrl = result;
                log("Authorization url: " + authorizationUrl);
                
                
                // clear all cookes first
                ClientUtils.clearCookies(); 
                
                // save the auth provider to cookie
                ClientUtils.saveAuthProvider(authProvider);
                
                // save the redirect url to a cookie as well
                // we need to redirect there after logout
                ClientUtils.saveRediretUrl(callbackUrl);
                
                //Window.alert("Redirecting to: " + authorizationUrl);
                ClientUtils.redirect(authorizationUrl);
            }
            
            @Override
            protected void callService(AsyncCallback<String> cb)
            {
                OAuthLoginService.Util.getInstance().getAuthorizationUrl(credential,cb);
            }

            @Override
            public void onFailure(Throwable caught)
            {
                ClientUtils.handleException(caught);
            }
        }.go("Getting Authorization URL from " + authProviderName + "...");
                
        /*
        AsyncCallback<String> callback = new AsyncCallback<String>()
        {

            @Override
            public void onFailure(Throwable caught)
            {
                Window.alert("Exception: " + caught);
            }

            @Override
            public void onSuccess(String result)
            {
                String authorizationUrl = result;
                log("Authorization url: " + authorizationUrl);
                
                // clear all cookes first
                ClientUtils.clearCookies(); // XXX
                
                // save the auth provider to cookie
                ClientUtils.saveAuthProvider(authProvider);
                
                // save the redirect url to a cookie as well
                // we need to redirect there after logout
                ClientUtils.saveRediretUrl(callbackUrl);
                
                ClientUtils.redirect(authorizationUrl);
            }
        };
        OAuthLoginService.Util.getInstance().getAuthorizationUrl(credential,callback);
        */
    }
    
    public void log(String msg)
    {
       if (appScreen == null)
       {
           return;
       }
       TextArea logTextArea = appScreen.getTextArea();
       Date d = new Date();
       String t = d.toString() + ": " + msg;
       int cW = logTextArea.getCharacterWidth();
       String currentText = logTextArea.getText();
       if (currentText.length() > 0)
       {
           logTextArea.setText(currentText + "\n" + t);
       }
       else
       {
           logTextArea.setText(t);
           
       }
       appScreen.getScrollPanel().scrollToBottom();
       Log.debug(msg);
    }
    
    public void showMessage(String html)
    {
        messageDialog.setContent(html);
    }
    
    public void showAboutMessage()
    {
        String gwtLink = "https://developers.google.com/web-toolkit/";
        gwtLink = "<a href=\"" + gwtLink + "\" target=\"_new\">GWT</a>";
        // I always have to google to find where to register the app
        // So I'll add the links here as of July-04-2014
        String scribe = "<a href=\"https://github.com/fernandezpablo85/scribe-java\" target=\"_new\">scribe-java</a> 1.3.6";
        String oauth = "<a href=\"http://oauth.net/\" target=\"_new\">OAuth</a>";
        String facebook = "<a href=\"https://developers.facebook.com\" target=\"_new\">Facebook</a> (Extended from " + scribe +")";
        String google = "<a href=\"https://console.developers.google.com/\" target=\"_new\">Google</a> (Extended from " + scribe +")";
        String twitter = "<a href=\"https://dev.twitter.com/\" target=\"_new\">Twitter</a>";
        String yahoo = "<a href=\"https://developer.yahoo.com/oauth/\" target=\"_new\">Yahoo!</a>";
        String linkedin = "<a href=\"https://developer.linkedin.com/\" target=\"_new\">Linkedin</a> (Extended from " + scribe + ")";
        String instagram = "<a href=\"http://instagram.com/developer/\" target=\"_new\">Instagram</a> (Extended from " + scribe + ")";
        String vimeo = "<a href=\"https://developer.vimeo.com/\" target=\"_new\">Vimeo</a>";
        String github = "<a href=\"https://developer.github.com/\" target=\"_new\">github</a> (Extended from " + scribe + ")";
        String flickr = "<a href=\"https://www.flickr.com/services/developer/\" target=\"_new\">flickr</a>";
        String microsoft = "<a href=\"https://account.live.com/developers/applications/create?tou=1\" target=\"_new\">Microsoft Live Connect</a>";
        String tumblr = "<a href=\"http://www.tumblr.com/developers\" target=\"_new\">tumblr</a>";
        String foursquare = "<a href=\"https://developer.foursquare.com/\" target=\"_new\">foursquare</a>";

        final String aboutMessage = 
            "A " + gwtLink + " application to demonstrate Login using " + oauth + " protocol."  +
            "<br>" +
            "This App uses Server side flow of OAuth. Click on the provider name to" + 
            "<br>" +
            "go to the provider's developer page, where an app can be registered." + 
            "<br>" +
            "The links are correct as of Jul-04-2014." +
            "<br>" +
            "<font color=\"#ff0000\">The app does not ask for any private profile information.</font>" +
            "<br><br>" +
            "Supported OAuth Providers are: " + 
            "<ul>" + 
            "<li>" + facebook + "<li>" + google + "<li>" + instagram + "<li>" + linkedin + "<li>" + microsoft +
            "<li>" + twitter + "<li>" + vimeo + "<li>" + yahoo + "<li>" + flickr + "<li>" + foursquare + "<li>" + github + 
            "<li>" + tumblr + 
            "</ul>" +
            "--" +
            "<br>" +
            "Muhammad Muquit, <a href=\"http://www.muquit.com/\" target=\"_new\">http://www.muquit.com/</a>";    
        
        showMessage(aboutMessage);
    }
    
    private void showApp()
    {
        Window.setMargin("0px");
        RootLayoutPanel rootLayoutPanel = RootLayoutPanel.get();
        rootLayoutPanel.clear();
        appScreen.setWidth("100%");
        rootLayoutPanel.add(appScreen);
    }
}
