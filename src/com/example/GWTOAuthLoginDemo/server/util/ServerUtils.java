package com.example.GWTOAuthLoginDemo.server.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import argo.format.JsonFormatter;
import argo.format.PrettyJsonFormatter;
import argo.jdom.JdomParser;
import argo.jdom.JsonRootNode;

public class ServerUtils
{
    private static final Logger logger=Logger.getLogger(ServerUtils.class.getName());
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
        logger.info("params size: " + ps.length);
        Map<String,String> map = new HashMap<String,String>();
        logger.info("map: " + map);
        
        for (String p: ps )
        {
            String k = p.split("=")[0];
            String v = p.split("=")[1];
            logger.info("k= " + k + " v=" + v);
            map.put(k,v);
        }
        logger.info("returing map");
        return map;
    }

    public static String getQueryStringValue(String qs,String name)
    {
        logger.info("Parse query string: " + qs + " for " + name);
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
        logger.info("URL: " + urlString);
        URL url = new URL(urlString);
        String qs = url.getQuery();
        return getQueryStringValue(qs,qsName);
    }
    
}
