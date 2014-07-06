package com.example.GWTOAuthLoginDemo.client.ui;

import com.example.GWTOAuthLoginDemo.client.resources.MyResources;
import com.example.GWTOAuthLoginDemo.client.resources.images.MyImages;
import com.example.GWTOAuthLoginDemo.client.util.ClientUtils;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Image;

/**
 * Adapted from:
 * http://turbomanage.wordpress.com/2009/10/22/how-to-show-a-loading-pop-up-in-your-gwt-app/
 * busy.gif is generated from: http://www.ajaxload.info/
 * 
 * @author muquit@muquit.com Nov 27, 2012 7:53:42 PM
 */
public class BusyPanel extends PopupPanel
{
    private final FlowPanel container = new FlowPanel();
    private String title = "Please wait..";
    private Grid grid;
    
    public static MyImages images = MyResources.INSTANCE.images();
    public BusyPanel()
    {
        setStyleName("busy-PopupPanel");
       setGlassEnabled(true);
       SafeUri imageUri = images.busy16IconImageData().getSafeUri();
       final Image busyImage = new Image(imageUri);
       grid = new Grid(1,2);
       grid.setWidget(0,0,busyImage);
       this.container.add(grid);
       add(this.container);
    }
    
    public void setTitle(String message)
    {
        if (message == null)
            grid.setText(0,1,this.title);
        else
            grid.setText(0,1,message);
    }
    
    public void startProcessing(String message)
    {
        //center();
        setPopupPosition(ClientUtils.DIALOG_X_POSITION,ClientUtils.DIALOG_Y_POSITION);
        setTitle(message);
        show();
    }
    
    public void stopProcessing()
    {
        hide();
    }
    
    public void showWidget()
    {
        startProcessing(title);
    }

}
