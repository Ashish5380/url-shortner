package com.intuit.craft.urlshortner.service;

public interface RateLimiter {

    boolean isTooManyRequests(final String urlCode, final long limit);
}
