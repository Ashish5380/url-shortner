package com.intuit.craft.urlshortner.util;

import java.net.URI;

public class UrlUtil {
    public static boolean isUrlValid(String url){
        try{
            new URI(url);
            return true;
        }catch (Exception ex){
            return false;

        }
    }

    public static boolean isValidCustomSuffix(String suffix){
        return suffix.matches("^[a-zA-Z0-9$\\-_.+!*'(),]*$");
    }
}
