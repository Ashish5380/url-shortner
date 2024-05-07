package com.intuit.craft.urlshortner.service;

public interface RateLimiter {

    /**
     * Determines if the number of requests associated with a specific URL code has exceeded a specified threshold within a one-second period.
     * This function utilizes a RedisAtomicInteger to track the count of requests per URL code, incrementing this count on each function call.
     * If the count exceeds the specified transactions per second (tps) threshold, it indicates a rate limit breach.
     *
     * The Redis counter is automatically reset every second, ensuring that only requests within the last second are considered.
     *
     * @param urlCode The unique identifier for the URL or request type, used as the key in Redis to store and retrieve the request count.
     * @param limit     The maximum allowed transactions per second (tps) for the given URL code. If the request count exceeds this number,
     *                the function returns true, indicating too many requests.
     * @return boolean True if the current number of requests exceeds the tps threshold, false otherwise.
     */
    boolean isTooManyRequests(final String urlCode, final long limit);
}
