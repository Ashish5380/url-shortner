package com.intuit.craft.urlshortner.service.impl;

import com.intuit.craft.urlshortner.cache.Cache;
import com.intuit.craft.urlshortner.cache.DistributedCache;
import com.intuit.craft.urlshortner.service.RateLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.intuit.craft.urlshortner.constants.ServiceConstants.DEFAULT_TPS;

@Service
@RequiredArgsConstructor
public class RateLimiterServiceImpl implements RateLimiter {

    // token bucket rate limiting
    private final DistributedCache cache;

    @Override
    public boolean isTooManyRequests(final String urlCode, final long tps) {
        // Get or create an atomic integer in Redis for the given URL code.
        RedisAtomicInteger bucket = cache.atomicInteger(urlCode);

        // Increment the request count atomically.
        int current = bucket.incrementAndGet();

        // If this is the first request in the current second, set the Redis key to expire after 1 second.
        if(current <= 1) {
            bucket.expire(1L, TimeUnit.SECONDS);
        }
        // Check if the current number of requests has exceeded the tps limit.
        return current > tps;
    }
}
