package com.intuit.craft.urlshortner.cache;

import java.util.concurrent.TimeUnit;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;

public interface DistributedCache extends Cache {

    /**
     * Creates a RedisAtomicInteger at the specified key.
     * If the key does not already exist, it is initialized to zero.
     *
     * @param key the key for the atomic integer.
     * @return a RedisAtomicInteger instance associated with the given key.
     */
    RedisAtomicInteger atomicInteger(String key);

    /**
     * Creates a RedisAtomicInteger at the specified key with a TTL (time to live).
     * If the key does not already exist, it is initialized to zero and set to expire after the specified TTL.
     *
     * @param key      the key for the atomic integer.
     * @param ttl      the time-to-live duration.
     * @param timeUnit the unit of time for the TTL.
     * @return a RedisAtomicInteger instance associated with the given key, with expiration set.
     */
    RedisAtomicInteger atomicInteger(String key, long ttl, TimeUnit timeUnit);

    /**
     * Creates a RedisAtomicLong at the specified key.
     * If the key does not already exist, it is initialized to zero.
     *
     * @param key      the key for the atomic long.
     * @return a RedisAtomicLong instance associated with the given key, with expiration set.
     */
    RedisAtomicLong atomicLong(String key);

    /**
     * Creates a RedisAtomicLong at the specified key with a TTL (time to live).
     * If the key does not already exist, it is initialized to zero and set to expire after the specified TTL.
     *
     * @param key      the key for the atomic long.
     * @param ttl      the time-to-live duration.
     * @param timeUnit the unit of time for the TTL.
     * @return a RedisAtomicLong instance associated with the given key, with expiration set.
     */
    RedisAtomicLong atomicLong(String key, long ttl, TimeUnit timeUnit);

}
