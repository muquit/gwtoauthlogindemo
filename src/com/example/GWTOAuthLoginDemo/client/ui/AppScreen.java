package com.example.GWTOAuthLoginDemo.client.ui;

import com.allen_sauer.gwt.log.client.Log;
import com.example.GWTOAuthLoginDemo.client.resources.MyResources;
import com.example.GWTOAuthLoginDemo.client.resources.css.MyStylesCss;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.HasVerticalAlignment;



public class AppScreen extends Composite
{
    private VerticalPanel verticalPanel;
    
    private FlexTable       flexTable;
    private TextArea        textArea;
    private HorizontalPanel horizontalPanel;
    private Button          btnClear;
    private Button          btnMe;
    private ScrollPanel     scrollPanel;
    private Button          btnAccessToken;
    private Label           lblLoggedInAs;
    private Image           authProviderImage;
    private Button          btnAbout;
    private DeckPanel       deckPanel;
    private LoginScreen     loginScreen;
    
    private TopBar          topBar;
    
    
    public TextArea getTextArea()
    {
        return textArea;
    }

    public Button getBtnClear()
    {
        return btnClear;
    }


    public Button getBtnMe()
    {
        return btnMe;
    }

    public ScrollPanel getScrollPanel()
    {
        return scrollPanel;
    }


    public Button getBtnAccessToken()
    {
        return btnAccessToken;
    }


    public void setAuthProviderImage(Image authProviderImage)
    {
        this.authProviderImage=authProviderImage;
    }


    public Image getAuthProviderImage()
    {
        return authProviderImage;
    }


    public Button getBtnAbout()
    {
        return btnAbout;
    }
    
    public void updateWelcomeMessage(String html)
    {
        topBar.setWelcomeLabel(html);
    }
    
    public TopBar getTopBar()
    {
        return topBar;
    }

    public LoginScreen getLoginScreen()
    {
        return loginScreen;
    }
    
    public void showAppScreenWithLoginScreen()
    {
 //       deckPanel.showWidget(0);
    }
    public void showAppScreenWithoutLoginScreen()
    {
        Log.info("Visible widget: " + deckPanel.getVisibleWidget());
 //       deckPanel.showWidget(1);
        Log.info("Visible widget: " + deckPanel.getVisibleWidget());
    }

    public static MyStylesCss css = MyResources.INSTANCE.css();
    public AppScreen()
    {
        verticalPanel = new VerticalPanel();
        initWidget(verticalPanel);
        verticalPanel.setWidth("100%");
        verticalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
        verticalPanel.setSpacing(0);
        
        topBar = new TopBar();
        verticalPanel.add(topBar);
        
        loginScreen = new LoginScreen();
        verticalPanel.add(loginScreen);
        
        flexTable = new FlexTable();
        flexTable.setWidth("100%");
        
        int row = 0; int col = 0;
        
        verticalPanel.add(flexTable);

//        loginAnchor = new Anchor("Login");
//        loginAnchor.setTitle("Please Login to see protected resource");
//        loginAnchor.setWordWrap(false);
//       deckPanel.add(loginAnchor);
        
//        logoutAnchor = new Anchor("Logout");
//        logoutAnchor.setWordWrap(false);
//        deckPanel.add(logoutAnchor);
//        deckPanel.showWidget(0);
        
//        row = 0; col = 0;
//        flexTable.setWidget(row, col, deckPanel);
        
//        deckPanel.showWidget(0);
        
        row = 0; col = 0;
        horizontalPanel = new HorizontalPanel();
        horizontalPanel.setSpacing(4);
        flexTable.setWidget(row, col, horizontalPanel);
        
        btnMe = new Button("me");
        btnMe.removeStyleName("gwt-Button");
        btnMe.setStyleName("button");
        btnMe.addStyleName("blue");
        
        horizontalPanel.add(btnMe);
        btnMe.setWidth("110px");
        btnMe.setStyleName(css.buttonsStyle());
        
        btnAccessToken = new Button("Access Token");
        horizontalPanel.add(btnAccessToken);
        btnAccessToken.setWidth("110px");
        btnAccessToken.setStyleName(css.buttonsStyle());
        
        btnClear = new Button("Clear");
        btnClear.setText("Clear Log");
        horizontalPanel.add(btnClear);
        btnClear.setWidth("110px");
        btnClear.setStyleName(css.buttonsStyle());
        
        lblLoggedInAs = new Label("                            ");
        lblLoggedInAs.setWordWrap(false);
        horizontalPanel.add(lblLoggedInAs);
        
        btnAbout = new Button("About...");
        horizontalPanel.add(btnAbout);
        btnAbout.setWidth("110px");
        btnAbout.setStyleName(css.buttonsStyle());
        
        
        authProviderImage = new Image("1x1.png");
        authProviderImage.setAltText("Logged in using");
        horizontalPanel.add(authProviderImage);
        
        row = 1; col = 0;
        scrollPanel = new ScrollPanel();
        scrollPanel.scrollToBottom();
        flexTable.setWidget(row, col, scrollPanel);
        scrollPanel.setWidth("100%");
        
        textArea = new TextArea();
        scrollPanel.setWidget(textArea);
        textArea.setSize("99%", "400px");
        flexTable.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
        flexTable.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);


 
    }

    public HorizontalPanel getHorizontalPanel()
    {
        return horizontalPanel;
    }
}
