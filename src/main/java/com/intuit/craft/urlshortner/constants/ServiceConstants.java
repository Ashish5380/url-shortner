package com.intuit.craft.urlshortner.constants;

public class ServiceConstants {

    public static final String ALLOWED_CHARACTERS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String COUNTER_KEY = "url-shortener-counter";

    public static final String URL_PREFIX = "http://localhost:9015/url/r/";

    public static final Integer DEFAULT_TPS = 1000;

    public static final String RATE_LIMIT_REDIS_KEY_PREFIX = "TPS-";
}
