package com.example.GWTOAuthLoginDemo.client.resources;

import com.google.gwt.core.client.GWT;

//import com.google.gwt.core.shared.GWT;

public class MyResources
{
    public static MyBundle INSTANCE = GWT.create(MyBundle.class);
    static
    {
        INSTANCE.css().ensureInjected();
    }
}
