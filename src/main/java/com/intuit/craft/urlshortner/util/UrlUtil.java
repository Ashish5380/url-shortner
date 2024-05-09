package com.intuit.craft.urlshortner.util;

import java.net.URI;

import static com.intuit.craft.urlshortner.constants.ServiceConstants.URL_PREFIX;

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

    /**
     * Helper method to form the full short URL string from a suffix.
     *
     * @param suffix the short URL suffix.
     * @return the full short URL.
     */
    public static String shortUrlString(String suffix){
        return URL_PREFIX + suffix;
    }
}
