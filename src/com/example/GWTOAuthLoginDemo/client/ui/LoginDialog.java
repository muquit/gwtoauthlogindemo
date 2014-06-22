package com.example.GWTOAuthLoginDemo.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LoginDialog extends DialogBox
{
    private VerticalPanel verticalPanel;
    private LoginScreen loginScreen = new LoginScreen();
    
    public LoginScreen getLoginScreen()
    {
        return loginScreen;
    }

    public LoginDialog()
    {
        setHTML("Please Login");
        
        verticalPanel = new VerticalPanel();
        setWidget(verticalPanel);
        verticalPanel.setSize("100%", "100%");
        
        loginScreen = new LoginScreen();
        verticalPanel.add(loginScreen);
        
        FlexTable flexTable = new FlexTable();
        verticalPanel.add(flexTable);
        
        Button closeButton = new Button("Cancel");
        flexTable.setWidget(0,0,closeButton);
        closeButton.addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
                hide();
            }
        });
        
        setGlassEnabled(true);
        setAnimationEnabled(true);
    }
    
    public void show()
    {
        loginScreen.getUsernameTextBox().setText("");
        loginScreen.getPasswordTextBox().setText("");
        super.show();
    }
}
