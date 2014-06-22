package com.example.GWTOAuthLoginDemo.server.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.UUID;

import com.google.gwt.dev.util.collect.HashMap;

import argo.format.JsonFormatter;
import argo.format.PrettyJsonFormatter;
import argo.jdom.JdomParser;
import argo.jdom.JsonRootNode;

public class ServerUtils
{
    /* all OAuth parameters are moved to OAuthParams.java */
    public static String makeRandomString()
    {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
    
    /**
     * Pretty print json string.
     * @param uglyJson
     * @return prettyfied json if possible, ugly one otherwise
     * I found Argo is the only one which can format a json string the way I want.
     * Argo: http://argo.sourceforge.net/documentation.html
     */
    public static String prettyPrintJsonString(String uglyJson)
    {
        try
        {
            JsonRootNode jsonRootNode = new JdomParser().parse(uglyJson);
            JsonFormatter jsonFormatter = new PrettyJsonFormatter();
            String prettyJson = jsonFormatter.format(jsonRootNode);
            return prettyJson;
        }
        catch(Exception e)
        {
            return uglyJson;
        }
    }

    private static Map<String,String> parseQueryString(String qs)
    {
        String[] ps = qs.split("&");
        Map<String,String> map = new HashMap<String,String>();
        
        for (String p: ps )
        {
            String k = p.split("=")[0];
            String v = p.split("=")[1];
            map.put(k,v);
        }
        return map;
    }

    public static String getQueryStringValue(String qs,String name)
    {
        Map<String,String> map = parseQueryString(qs);
        return map.get(name);
        
    }
    /**
     * return the value of the query string from a URL
     * @param url
     * @param qsName
     * @return value of query string if it exists, null otherwise
     * @throws MalformedURLException 
     */
    public static String getQueryStringValueFromUrl(String urlString, String qsName) throws MalformedURLException
    {
        URL url = new URL(urlString);
        String qs = url.getQuery();
        return getQueryStringValue(qs,qsName);
    }
    
}
