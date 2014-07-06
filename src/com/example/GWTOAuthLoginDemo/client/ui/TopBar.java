package com.example.GWTOAuthLoginDemo.client.ui;
import com.example.GWTOAuthLoginDemo.client.resources.MyResources;
import com.example.GWTOAuthLoginDemo.client.resources.css.MyStylesCss;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class TopBar extends Composite
{
    private HorizontalPanel horizontalPanel;

    private Anchor logoutAnchor;

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

    public static MyStylesCss css = MyResources.INSTANCE.css();
    public TopBar()
    {
        horizontalPanel=new HorizontalPanel();
        horizontalPanel.setStyleName("topBar");
        initWidget(horizontalPanel);
        horizontalPanel.setWidth("100%");
        setHorizontalPanelSpacing(6);
//        horizontalPanel.setStyleName(css.paddedHorizontalPanel());
        
        logoutAnchor = new Anchor("Logout");
        logoutAnchor.setWordWrap(false);
        logoutAnchor.setVisible(false);
        horizontalPanel.add(logoutAnchor);
        
        lblWelcome=new HTML();
        horizontalPanel.add(lblWelcome);
    }
}
