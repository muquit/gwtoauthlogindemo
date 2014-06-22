package com.example.GWTOAuthLoginDemo.client.ui;

import com.example.GWTOAuthLoginDemo.client.resources.MyResources;
import com.example.GWTOAuthLoginDemo.client.resources.css.MyStylesCss;
import com.example.GWTOAuthLoginDemo.client.resources.images.MyImages;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class LoginScreen extends Composite
{
    private VerticalPanel verticalPanel;
    private Grid grid;
    private FlexTable flexTableLeft;
    private FlexTable flexTableRight;
    
    private Image image;
    private Image facebookImage;
    private Image googleImage;
    private Image twitterImage;
    private Image linkedinImage;
    private Image yahooImage;
    private Image vimeoImage;
    private Image githubImage;
    private Image instagramImage;
    private Image flickrImage;
    private Image liveImage;
    private Image tumblrImage;
    private Image foursquareImage;
    
    private TextBox usernameTextBox;
    private PasswordTextBox passwordTextBox;
    
    public static MyStylesCss css = MyResources.INSTANCE.css();
    public static MyImages images = MyResources.INSTANCE.images();
    
    private HorizontalPanel horizontalPanel;
    private Button btnLogin;
    
    
    public VerticalPanel getVerticalPanel()
    {
        return verticalPanel;
    }


    public Grid getGrid()
    {
        return grid;
    }

    public FlexTable getFlexTableLeft()
    {
        return flexTableLeft;
    }



    public FlexTable getFlexTableRight()
    {
        return flexTableRight;
    }



    public Image getImage()
    {
        return image;
    }



    public Image getFacebookImage()
    {
        return facebookImage;
    }



    public Image getGoogleImage()
    {
        return googleImage;
    }



    public Image getTwitterImage()
    {
        return twitterImage;
    }



    public Image getLinkedinImage()
    {
        return linkedinImage;
    }



    public Image getYahooImage()
    {
        return yahooImage;
    }



    public Image getVimeoImage()
    {
        return vimeoImage;
    }



    public Image getGithubImage()
    {
        return githubImage;
    }

    public HorizontalPanel getHorizontalPanel()
    {
        return horizontalPanel;
    }



    public Button getBtnLogin()
    {
        return btnLogin;
    }



    public Image getInstagramImage()
    {
        return instagramImage;
    }

    public Image getFlickrImage()
    {
        return flickrImage;
    }


    public TextBox getUsernameTextBox()
    {
        return usernameTextBox;
    }


    public PasswordTextBox getPasswordTextBox()
    {
        return passwordTextBox;
    }
    
    public void resetLoginParams()
    {
        getUsernameTextBox().setText("");
        getPasswordTextBox().setText("");
    }


    public Image getLiveImage()
    {
        return liveImage;
    }


    public Image getTumblrImage()
    {
        return tumblrImage;
    }


    public Image getFoursquareImage()
    {
        return foursquareImage;
    }


    public LoginScreen()
    {
        
        verticalPanel = new VerticalPanel();
        verticalPanel.setWidth("100%");
        verticalPanel.setSpacing(4);
        initWidget(verticalPanel);
        
        HorizontalAlignmentConstant hal = HasHorizontalAlignment.ALIGN_LEFT;
        HorizontalAlignmentConstant har = HasHorizontalAlignment.ALIGN_RIGHT;
        
        grid = new Grid(1, 2);
        verticalPanel.add(grid);
        
        DecoratorPanel dp = new DecoratorPanel();
        
        String labelStyle = css.loginWithLabelStyle();
        String imageStyle = css.authProviderIconsStyle();
        
        /* left side */
        flexTableLeft = new FlexTable();
        dp.add(flexTableLeft);
        grid.setWidget(0, 0, dp);
        
        Label loginWithLabel = new Label("Login With");
        loginWithLabel.setWordWrap(false);
        loginWithLabel.setStyleName(labelStyle);
        flexTableLeft.setWidget(0,0,loginWithLabel);
        
        // row 1
        int r; int c;
        r = 1; c = 0;
        facebookImage = new Image(images.facebookIconImageData().getSafeUri());
        image = facebookImage;
        image.setStyleName(imageStyle);
        image.setTitle("Login with Facebook");
        flexTableLeft.setWidget(r,c,image);
        
        r = 1; c = 1;
        googleImage = new Image(images.googleIconImageData().getSafeUri());
        image = googleImage;
        image.setStyleName(imageStyle);
        image.setTitle("Login with Google");
        flexTableLeft.setWidget(r,c,image);
        flexTableLeft.getCellFormatter().setHorizontalAlignment(r,c,har);
        
        r = 1; c = 2;
        twitterImage = new Image(images.twitterIconImageData().getSafeUri());
        image = twitterImage;
        image.setStyleName(imageStyle);
        image.setTitle("Login with Twitter");
        flexTableLeft.setWidget(r,c,image);
        flexTableLeft.getCellFormatter().setHorizontalAlignment(r,c,har);
        
        r = 1; c = 3;
        yahooImage = new Image(images.yahooIconImageData().getSafeUri());
        image = yahooImage;
        image.setStyleName(imageStyle);
        image.setTitle("Login with Yahoo!");
        flexTableLeft.setWidget(r,c,image);
        flexTableLeft.getCellFormatter().setHorizontalAlignment(r,c,har);
        
        // row 2
        r = 2; c = 0;
        linkedinImage = new Image(images.linkedinIconImageData().getSafeUri());
        image = linkedinImage;
        image.setStyleName(imageStyle);
        image.setTitle("Login with LinkedIn");
        flexTableLeft.setWidget(r,c,image);
        flexTableLeft.getCellFormatter().setHorizontalAlignment(r,c,har);
        
        r = 2; c = 1;
        instagramImage = new Image(images.instagramIconImageData().getSafeUri());
        image = instagramImage;
        image.setStyleName(imageStyle);
        image.setTitle("Login with Instagram");
        flexTableLeft.setWidget(r,c,image);
        flexTableLeft.getCellFormatter().setHorizontalAlignment(r,c,har);       
        
        r = 2; c = 2;
        vimeoImage = new Image(images.vimeoIconImageData().getSafeUri());
        image = vimeoImage;
        image.setStyleName(imageStyle);
        image.setTitle("Login with Vimeo");
        flexTableLeft.setWidget(r,c,image);
        flexTableLeft.getCellFormatter().setHorizontalAlignment(r,c,har);
        
        r = 2; c = 3;
        githubImage = new Image(images.githubIconImageData().getSafeUri());
        image = githubImage;
        image.setStyleName(imageStyle);
        image.setTitle("Login with github");
        flexTableLeft.setWidget(r,c,image);
        flexTableLeft.getCellFormatter().setHorizontalAlignment(r,c,har);
        
        // row 3
        r = 3; c = 0;
        flickrImage = new Image(images.flickrIconImageData().getSafeUri());
        image = flickrImage;
        image.setStyleName(imageStyle);
        image.setTitle("Login with flickr");
        flexTableLeft.setWidget(r,c,image);
        flexTableLeft.getCellFormatter().setHorizontalAlignment(r,c,har);
        
        r = 3; c = 1;
        liveImage = new Image(images.liveIconImageData().getSafeUri());
        image = liveImage;
        image.setStyleName(imageStyle);
        image.setTitle("Login with Microsoft Live Connect");
        flexTableLeft.setWidget(r,c,image);
        flexTableLeft.getCellFormatter().setHorizontalAlignment(r,c,har);
        flexTableLeft.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_RIGHT);
        flexTableLeft.getFlexCellFormatter().setColSpan(0, 0, 4);
        flexTableLeft.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
        
        r = 3; c = 2;
        tumblrImage = new Image(images.tumblrIconImageData().getSafeUri());
        image = tumblrImage;
        image.setStyleName(imageStyle);
        image.setTitle("Login with tumblr.");
        flexTableLeft.setWidget(r,c,image);
        flexTableLeft.getCellFormatter().setHorizontalAlignment(r,c,har);
        flexTableLeft.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_RIGHT);
        flexTableLeft.getFlexCellFormatter().setColSpan(0, 0, 4);
        flexTableLeft.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
        
        r = 3; c = 3;
        foursquareImage = new Image(images.foursquareIconImageData().getSafeUri());
        image = foursquareImage;
        image.setStyleName(imageStyle);
        image.setTitle("Login with foursquare");
        flexTableLeft.setWidget(r,c,image);
        flexTableLeft.getCellFormatter().setHorizontalAlignment(r,c,har);
        flexTableLeft.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_RIGHT);
        flexTableLeft.getFlexCellFormatter().setColSpan(0, 0, 4);
        flexTableLeft.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
        
        /********************** right side *******************/
        dp = new DecoratorPanel();
        flexTableRight = new FlexTable();
        dp.add(flexTableRight);
        grid.setWidget(0, 1, dp);
        
        loginWithLabel = new Label("Login With Default Account");
        loginWithLabel.setWordWrap(false);
        loginWithLabel.setStyleName(css.loginWithLabelStyle());
        r = 0; c = 0;
        flexTableRight.setWidget(r,c,loginWithLabel);
        flexTableRight.getCellFormatter().setHorizontalAlignment(r,c,hal);
        
        r = 1; c = 0;
        Label userNameLabel = new Label("Username:");
        userNameLabel.setWordWrap(false);
        flexTableRight.setWidget(r,c,userNameLabel);
        flexTableRight.getCellFormatter().setHorizontalAlignment(r,c,har);
        
        r = 1; c = 1;
        usernameTextBox = new TextBox();
        flexTableRight.setWidget(r,c,usernameTextBox);
        usernameTextBox.setWidth("175px");
        flexTableRight.getCellFormatter().setHorizontalAlignment(r,c,hal);
        
        r = 2; c = 0;
        Label passwordLabel = new Label("Password:");
        passwordLabel.setWordWrap(false);
        flexTableRight.setWidget(r,c,passwordLabel);
        flexTableRight.getCellFormatter().setHorizontalAlignment(r,c,har);
        
        r = 2; c = 1;
        passwordTextBox = new PasswordTextBox();
        flexTableRight.setWidget(r,c,passwordTextBox);
        passwordTextBox.setWidth("175px");
        flexTableRight.getCellFormatter().setHorizontalAlignment(r,c,hal);
        
        r = 3; c = 0;
        
        
        flexTableRight.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
        flexTableRight.getFlexCellFormatter().setColSpan(0, 0, 2);
        
        horizontalPanel = new HorizontalPanel();
        flexTableRight.setWidget(3, 0, horizontalPanel);
        horizontalPanel.setSpacing(8);
        
        btnLogin = new Button("Login");
        btnLogin.setStyleName(css.buttonsStyle());
        horizontalPanel.add(btnLogin);
        
        HTML l = new HTML("&nbsp; <i>(Use \"test\" and \"secret\")</i>");
        horizontalPanel.add(l);
        
        
        flexTableRight.getFlexCellFormatter().setColSpan(3, 0, 2);
        grid.getCellFormatter().setVerticalAlignment(0, 1, HasVerticalAlignment.ALIGN_TOP);
        grid.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
    }
}
