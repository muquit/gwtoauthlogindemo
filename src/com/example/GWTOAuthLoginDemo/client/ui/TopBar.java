package com.example.GWTOAuthLoginDemo.client.ui;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class TopBar extends Composite
{
    private HorizontalPanel horizontalPanel;

//    private Anchor loginAnchor;
    private Anchor logoutAnchor;
//    private DeckPanel deckPanel;

    private HTML lblWelcome;

    public HorizontalPanel getHorizontalPanel()
    {
        return horizontalPanel;
    }

    public HTML getLblWelcome()
    {
        return lblWelcome;
    }

    public int getHorizontalPanelSpacing()
    {
        return horizontalPanel.getSpacing();
    }

    public void setHorizontalPanelSpacing(int spacing)
    {
        horizontalPanel.setSpacing(spacing);
    }
    
    public void setWelcomeLabel(String html)
    {
        lblWelcome.setHTML(html);
    }

    public Anchor getLogoutAnchor()
    { 
        return logoutAnchor;
    }
    public void showLogoutAnchor()
    { 
        logoutAnchor.setVisible(true);
    }

    public void hideLogoutAnchor()
    {
        logoutAnchor.setVisible(false);
    }

    /*
     * public Anchor getLoginAnchor() { return loginAnchor; }
     * 
     * 
     */
    public TopBar()
    {
        horizontalPanel=new HorizontalPanel();
        horizontalPanel.setStyleName("topBar");
        initWidget(horizontalPanel);
        horizontalPanel.setWidth("100%");
        setHorizontalPanelSpacing(3);
        
        /****/
//        deckPanel = new DeckPanel(); horizontalPanel.add(deckPanel);
//        horizontalPanel.add(deckPanel);
          
//        loginAnchor = new Anchor("Login"); loginAnchor.setWordWrap(false);
//        deckPanel.add(loginAnchor);
          
//        logoutAnchor = new Anchor("Logout"); logoutAnchor.setWordWrap(false);
//        deckPanel.add(logoutAnchor);
//        deckPanel.showWidget(0);
        /****/
        logoutAnchor = new Anchor("Logout");
        logoutAnchor.setWordWrap(false);
        logoutAnchor.setVisible(false);
        horizontalPanel.add(logoutAnchor);
        
        lblWelcome=new HTML();
        horizontalPanel.add(lblWelcome);
        /*
         * deckPanel = new DeckPanel(); horizontalPanel.add(deckPanel);
         * 
         * loginAnchor = new Anchor("Login"); loginAnchor.setWordWrap(false);
         * deckPanel.add(loginAnchor);
         * 
         * logoutAnchor = new Anchor("Logout"); logoutAnchor.setWordWrap(false);
         * deckPanel.add(logoutAnchor);
         */
        // showLoginAnchor();
    }
}
