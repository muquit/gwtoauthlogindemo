package com.example.GWTOAuthLoginDemo.client.ui;

import com.example.GWTOAuthLoginDemo.client.resources.MyResources;
import com.example.GWTOAuthLoginDemo.client.resources.css.MyStylesCss;
import com.example.GWTOAuthLoginDemo.client.resources.images.MyImages;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.DialogBox.Caption;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.PushButton;
/*
 * Add a Close icon (x) at the right side of a DialogBox.
 * Adapted from:
 * http://stackoverflow.com/questions/926539/gwt-close-button-in-title-bar-of-dialogbox
 */
public class ButtonCaption extends HorizontalPanel implements Caption
{
    protected InlineLabel text;
    protected PushButton closeDialog;
    protected Image closeImage;
    
    public static MyStylesCss css = MyResources.INSTANCE.css();
    public static MyImages images = MyResources.INSTANCE.images();
    
    public PushButton getCloseButton()
    {
        return closeDialog;
    }
    
    public Image getCloseImage()
    {
        return closeImage;
    }
    
    
    public ButtonCaption(String label)
    {
        super();
        setWidth("100%");
        setStyleName("Caption");
        closeImage = new Image(images.closeIconImageData().getSafeUri());
        closeImage.setSize("16px","16px");
        closeDialog = new PushButton(closeImage);
        
        closeDialog.setStyleName(css.closeIconStyle());
        
        add(text = new InlineLabel(label));
        add(closeDialog);
        setCellHorizontalAlignment(getElement(),ALIGN_RIGHT);
        setCellWidth(closeDialog,"1px");
        setCellHeight(closeDialog,"1px");
    }
    
    @Override
    public HandlerRegistration addMouseDownHandler(MouseDownHandler handler)
    {
        return addMouseDownHandler(handler);
    }

    @Override
    public HandlerRegistration addMouseUpHandler(MouseUpHandler handler)
    {
        return addMouseUpHandler(handler);
    }

    @Override
    public HandlerRegistration addMouseOutHandler(MouseOutHandler handler)
    {
        return addMouseOutHandler(handler);
    }

    @Override
    public HandlerRegistration addMouseOverHandler(MouseOverHandler handler)
    {
        return addMouseOverHandler(handler);
    }

    @Override
    public HandlerRegistration addMouseMoveHandler(MouseMoveHandler handler)
    {
        return addMouseMoveHandler(handler);
    }

    @Override
    public HandlerRegistration addMouseWheelHandler(MouseWheelHandler handler)
    {
        return addMouseWheelHandler(handler);
    }

    @Override
    public String getHTML()
    {
        return getElement().getInnerHTML();
    }

    @Override
    public void setHTML(String html)
    {
        remove(text);
        insert(text,1);
    }

    @Override
    public String getText()
    {
        return text.getText();
    }

    @Override
    public void setText(String text)
    {
        this.text.setText(text);
    }

    @Override
    public void setHTML(SafeHtml html)
    {
        setHTML(html.asString());
    }
}
