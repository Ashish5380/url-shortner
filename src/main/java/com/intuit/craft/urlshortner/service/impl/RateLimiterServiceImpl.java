package com.intuit.craft.urlshortner.service.impl;

import com.intuit.craft.urlshortner.cache.Cache;
import com.intuit.craft.urlshortner.cache.DistributedCache;
import com.intuit.craft.urlshortner.service.RateLimiter;
import lombok.RequiredArgsConstructor;
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
    public boolean isTooManyRequests(final String urlCode) {
        RedisAtomicLong bucket = getOrInitializeBucket(urlCode, DEFAULT_TPS);

        long newCount = bucket.decrementAndGet();
        if (newCount < 0) {
            bucket.incrementAndGet();
            return true;
        }
        return false;
    }

    @Override
    public void updateLimit(final String urlCode, final long limit) {
        getOrInitializeBucket(urlCode, limit);
    }

    private RedisAtomicLong getOrInitializeBucket(final String code, final long tps) {
        RedisAtomicLong currentTps = cache.atomicLong(code, 1L, TimeUnit.SECONDS);
        if (currentTps.get() == 0) {
            // Initialize bucket with tokens equal to the rate limit
            currentTps.set(tps);
        }
        return currentTps;
    }
}
