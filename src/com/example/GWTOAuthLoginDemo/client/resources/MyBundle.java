package com.example.GWTOAuthLoginDemo.client.resources;

import com.example.GWTOAuthLoginDemo.client.resources.css.MyStylesCss;
import com.example.GWTOAuthLoginDemo.client.resources.images.MyImages;
import com.google.gwt.resources.client.ClientBundle;

public interface MyBundle extends ClientBundle
{
    @Source("css/MyStylesCss.css")
    public MyStylesCss css();
    
    public MyImages images();
}
