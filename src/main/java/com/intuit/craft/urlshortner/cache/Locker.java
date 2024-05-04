package com.intuit.craft.urlshortner.cache;

import java.util.concurrent.TimeUnit;

public interface Locker {

    /**
     * Attempts to acquire a lock for the specified key.
     * This method increments an atomic integer and checks if the result is 1 (indicating that the lock was successfully acquired).
     *
     * @param key the key for the lock.
     * @return true if the lock is successfully acquired, false otherwise.
     */
    boolean hasLock(String key);

    /**
     * Attempts to acquire a lock for the specified key with a TTL (time to live).
     * This method increments an atomic integer and checks if the result is 1 (indicating that the lock was successfully acquired),
     * and ensures that the key expires after the specified TTL to avoid indefinite locks.
     *
     * @param key      the key for the lock.
     * @param ttl      the time-to-live duration.
     * @param timeUnit the unit of time for the TTL.
     * @return true if the lock is successfully acquired, false otherwise.
     */
    boolean hasLock(String key, long ttl, TimeUnit timeUnit);

    /**
     * Releases a lock for the specified key by deleting it.
     * This method essentially deletes the key, thus releasing any lock associated with it.
     *
     * @param key the key for the lock to release.
     */
    void releaseLock(String key);
}
