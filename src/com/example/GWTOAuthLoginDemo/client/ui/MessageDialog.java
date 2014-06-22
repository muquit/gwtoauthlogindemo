package com.example.GWTOAuthLoginDemo.client.ui;

import com.example.GWTOAuthLoginDemo.client.resources.MyResources;
import com.example.GWTOAuthLoginDemo.client.resources.css.MyStylesCss;
import com.example.GWTOAuthLoginDemo.client.util.ClientUtils;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.ScrollPanel;

/**
 * 
 * @author muquit@muquit.com Dec 22, 2012 7:02:22 PM
 */
public class MessageDialog extends DialogBox
{
    private VerticalPanel verticalPanel;
    private HTML htmlDialogBox;
    private Button btnClose;
    private ScrollPanel scrollPanel;
    
    private static MyStylesCss css = MyResources.INSTANCE.css();
    
    public VerticalPanel getVerticalPanel()
    {
        return verticalPanel;
    }

    public HTML getHtmlDialogBox()
    {
        return htmlDialogBox;
    }

    public Button getBtnClose()
    {
        return btnClose;
    }
    
    public void setContent(String html)
    {
        html += "<br><br><hr width=\"50%\" align=\"left\">";
        htmlDialogBox.setHTML(html);
        setGlassEnabled(true);
        setAnimationEnabled(true);
        setPopupPosition(ClientUtils.DIALOG_X_POSITION,ClientUtils.DIALOG_Y_POSITION);
        show();
    }

    public MessageDialog(String title)
    {
        //setHTML(title);
        super(new ButtonCaption(title));
        
        verticalPanel = new VerticalPanel();
        verticalPanel.setSpacing(4);
        setWidget(verticalPanel);
        
        scrollPanel = new ScrollPanel();
        verticalPanel.add(scrollPanel);
        
        htmlDialogBox = new HTML();
        scrollPanel.setWidget(htmlDialogBox);
        htmlDialogBox.setSize("100%", "100%");
        
        // handler for x icon in caption
        ButtonCaption ref = (ButtonCaption) this.getCaption();
        PushButton closeIcon = ref.getCloseButton();
        closeIcon.addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
                hide();
            }
        });
        
        btnClose = new Button("Close");
        btnClose.setStyleName(css.buttonsStyle());
        btnClose.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                hide();
            }
        });
        verticalPanel.add(btnClose);
    }
}
