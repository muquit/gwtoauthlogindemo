package com.example.GWTOAuthLoginDemo.client.ui;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class AppScreenLogin extends Composite
{
    private VerticalPanel verticalPanel;
    private TopBar topBar;
    private LoginScreen loginScreen = new LoginScreen();
    private HorizontalPanel horizontalPanel;
    private Anchor homeAnchor;
    
    public void updateWecomeLabel(String html)
    {
        topBar.setWelcomeLabel(html);
    }
    
    public LoginScreen getLoginScreen()
    {
        return loginScreen;
    }
    
    public void resetLoginParams()
    {
        loginScreen.resetLoginParams();
    }
    
    public Anchor getHomeAnchor()
    {
        return homeAnchor;
    }

    public AppScreenLogin()
    {
        
        verticalPanel = new VerticalPanel();
        initWidget(verticalPanel);
        verticalPanel.setWidth("100%");
        
        topBar = new TopBar();
        verticalPanel.add(topBar);
        
        horizontalPanel = new HorizontalPanel();
        horizontalPanel.setSpacing(4);
        verticalPanel.add(horizontalPanel);
        
        homeAnchor = new Anchor("Home");
        horizontalPanel.add(homeAnchor);
        
        verticalPanel.add(loginScreen);
    }
}
