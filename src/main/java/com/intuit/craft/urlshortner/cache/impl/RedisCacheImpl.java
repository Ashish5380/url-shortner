package com.intuit.craft.urlshortner.cache.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import com.intuit.craft.urlshortner.cache.DistributedCache;
import com.intuit.craft.urlshortner.cache.Locker;
import com.intuit.craft.urlshortner.exceptions.fatal.CacheOperationException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisCacheImpl implements DistributedCache, Locker {

    private final RedisTemplate<String, String> redisTemplate;

    private final ObjectMapper objectMapper;

    @Override
    public <TValue> void put(@NonNull String key, TValue value) {
        try {
            redisTemplate.opsForValue().set(key, objectMapper.writeValueAsString(value));
        } catch (Exception e) {
            throw new CacheOperationException("Unable to put value in cache", e, key);
        }
    }

    @Override
    public <TValue> void put(@NonNull String key, TValue value, long ttl, @NonNull TimeUnit timeUnit) {
        try {
            redisTemplate.opsForValue().set(key, objectMapper.writeValueAsString(value));
            redisTemplate.expire(key, ttl, timeUnit);
        } catch (Exception e) {
            throw new CacheOperationException("Unable to put value in cache", e, key);
        }
    }

    @Override
    public <TValue> Optional<TValue> get(@NonNull String key, @NonNull Class<TValue> type) {
        try {
            return Optional.ofNullable(redisTemplate.opsForValue().get(key))
              .map(valueAsJson -> {
                  try {
                      return objectMapper.readValue(valueAsJson, type);
                  } catch (JsonProcessingException e) {
                      throw new CacheOperationException("Unable to parse " + valueAsJson + " to " + type.getName(), e, key);
                  }
              });
        } catch (Exception e) {
            throw new CacheOperationException("Unable to get value", e, key);
        }
    }

    @Override
    public <TValue> Optional<TValue> get(@NonNull String key, @NonNull TypeReference<TValue> type) {
        try {
            return Optional.ofNullable(redisTemplate.opsForValue().get(key))
              .map(valueAsJson -> {
                  try {
                      return objectMapper.readValue(valueAsJson, type);
                  } catch (JsonProcessingException e) {
                      throw new CacheOperationException("Unable to parse " + valueAsJson + " to " + type.getType().getTypeName(), e, key);
                  }
              });
        } catch (Exception e) {
            throw new CacheOperationException("Unable to get value", e, key);
        }
    }

    @Override
    public void delete(String key) {
        try {
            redisTemplate.expire(key, 0, TimeUnit.MICROSECONDS);
        } catch (Exception e) {
            throw new CacheOperationException("Unable to delete value", e, key);
        }
    }

    @Override
    public RedisAtomicInteger atomicInteger(String key) {
        return new RedisAtomicInteger(key, Objects.requireNonNull(redisTemplate.getConnectionFactory()));
    }

    @Override
    public RedisAtomicInteger atomicInteger(String key, long ttl, TimeUnit timeUnit) {
        RedisAtomicInteger atomicInteger = new RedisAtomicInteger(key, Objects.requireNonNull(redisTemplate.getConnectionFactory()));
        atomicInteger.expire(ttl, timeUnit);
        return atomicInteger;
    }

    @Override
    public RedisAtomicLong atomicLong(String key) {
        return new RedisAtomicLong(key, Objects.requireNonNull(redisTemplate.getConnectionFactory()));
    }

    @Override
    public RedisAtomicLong atomicLong(String key, long ttl, TimeUnit timeUnit) {
        RedisAtomicLong atomicInteger = new RedisAtomicLong(key, Objects.requireNonNull(redisTemplate.getConnectionFactory()));
        atomicInteger.expire(ttl, timeUnit);
        return atomicInteger;
    }

    @Override
    public boolean hasLock(String key) {
        RedisAtomicInteger lockInt = atomicInteger(key);
        return lockInt.incrementAndGet() == 1;
    }

    @Override
    public boolean hasLock(String key, long ttl, TimeUnit timeUnit) {
        RedisAtomicInteger lockInt = atomicInteger(key, ttl, timeUnit);
        return lockInt.incrementAndGet() == 1;
    }

    @Override
    public void releaseLock(String key) {
        delete(key);
    }

    @Override
    public RedisAtomicInteger atomicIntegerWithValue(String key, Integer initialValue) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.setIfAbsent(key, String.valueOf(initialValue));
        return new RedisAtomicInteger(key, Objects.requireNonNull(redisTemplate.getConnectionFactory()));
    }
}
