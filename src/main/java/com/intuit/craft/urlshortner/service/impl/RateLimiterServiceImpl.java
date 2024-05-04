package com.intuit.craft.urlshortner.service.impl;

import com.intuit.craft.urlshortner.cache.Cache;
import com.intuit.craft.urlshortner.cache.DistributedCache;
import com.intuit.craft.urlshortner.service.RateLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RateLimiterServiceImpl implements RateLimiter {

    // Leaky bucket rate limiting / token bucket rate limiting / window based limiting
//    private final DistributedCache cache;
//
//    @Override
//    public boolean isTooManyRequests(final String urlCode) {
//        long currentTime = System.currentTimeMillis();
//        // Calculate the number of tokens to add based on elapsed time since the last refill.
//        long tokensToAdd = ((currentTime - lastRefillTimestamp) / windowUnit) * threshold;
//
//        // Refill the bucket with the calculated number of tokens.
//        if (tokensToAdd > 0) {
//            // Ensure the bucket does not exceed its capacity.
//            tokens.set(Math.min(threshold, tokens.addAndGet(tokensToAdd)));
//            // Update the refill timestamp.
//            lastRefillTimestamp = currentTime;
//        }
//
//        // Attempt to acquire a token.
//        if (tokens.get() > 0) {
//            // Decrement the token count and grant access.
//            tokens.decrementAndGet();
//            return true;
//        }
//
//        // Token bucket is empty; deny access.
//        return false;
//    }
//
//    @Override
//    public void updateLimit(final String urlCode, final long limit) {
//
//    }
//
//    private RedisAtomicLong getOrInitializeBucket(final String code, final long tps) {
//        RedisAtomicLong currentTps = cache.atomicLong(code);
//    }
}
