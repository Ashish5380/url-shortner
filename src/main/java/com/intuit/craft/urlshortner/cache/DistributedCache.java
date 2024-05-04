package com.intuit.craft.urlshortner.cache;

import java.util.concurrent.TimeUnit;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;

public interface DistributedCache extends Cache {

    RedisAtomicInteger atomicInteger(String key);

    RedisAtomicInteger atomicInteger(String key, long ttl, TimeUnit timeUnit);

    RedisAtomicLong atomicLong(String key);

    RedisAtomicLong atomicLong(String key, long ttl, TimeUnit timeUnit);

}
