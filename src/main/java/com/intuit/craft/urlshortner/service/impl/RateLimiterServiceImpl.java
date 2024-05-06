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
        RedisAtomicInteger bucket = cache.atomicInteger(urlCode);
        int current = bucket.incrementAndGet();
        if(current <= 1) {
            bucket.expire(1L, TimeUnit.SECONDS);
        }

        return current > tps;
    }
}
