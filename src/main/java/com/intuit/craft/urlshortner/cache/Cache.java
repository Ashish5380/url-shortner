package com.intuit.craft.urlshortner.cache;

import com.fasterxml.jackson.core.type.TypeReference;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import com.intuit.craft.urlshortner.exceptions.fatal.CacheOperationException;
import lombok.NonNull;

public interface Cache {

    /**
     * Stores a value in Redis under the specified key.
     * The value is serialized to JSON before being stored.
     *
     * @param key   the key under which the value is stored.
     * @param value the value to store.
     * @throws CacheOperationException if there is an error during the serialization or the Redis operation.
     */
    <TValue> void put(@NonNull String key, TValue value);

    /**
     * Stores a value in Redis under the specified key with a TTL (time to live).
     * The value is serialized to JSON before being stored. The key will expire after the specified TTL.
     *
     * @param key      the key under which the value is stored.
     * @param value    the value to store.
     * @param ttl      the time-to-live duration.
     * @param timeUnit the unit of time for the TTL.
     * @throws CacheOperationException if there is an error during the serialization, the Redis operation, or setting the TTL.
     */
    <TValue> void put(@NonNull String key, TValue value, long ttl, @NonNull TimeUnit timeUnit);

    /**
     * Retrieves a value from Redis by its key and deserializes it into the specified type.
     *
     * @param key  the key whose associated value is to be returned.
     * @param type the class type of the value.
     * @return an Optional containing the value if it is found, or an empty Optional if the key does not exist.
     * @throws CacheOperationException if there is an error during the deserialization or the Redis operation.
     */
    <TValue> Optional<TValue> get(@NonNull String key, @NonNull TypeReference<TValue> type);

    /**
     * Retrieves a value from Redis by its key and deserializes it into the specified complex type (e.g., generic types).
     *
     * @param key  the key whose associated value is to be returned.
     * @param type a TypeReference describing the type of the value.
     * @return an Optional containing the value if it is found, or an empty Optional if the key does not exist.
     * @throws CacheOperationException if there is an error during the deserialization or the Redis operation.
     */
    <TValue> Optional<TValue> get(@NonNull String key, @NonNull Class<TValue> type);

    /**
     * Deletes a key from Redis. This is achieved by setting its expiration to zero.
     *
     * @param key the key to delete.
     * @throws CacheOperationException if there is an error during the deletion operation.
     */
    void delete(String key);
}
