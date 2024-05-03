package com.intuit.craft.urlshortner.cache;

import java.util.concurrent.TimeUnit;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;

public interface DistributedCache extends Cache {

    RedisAtomicInteger atomicInteger(String key);

    RedisAtomicInteger atomicInteger(String key, long ttl, TimeUnit timeUnit);

}
