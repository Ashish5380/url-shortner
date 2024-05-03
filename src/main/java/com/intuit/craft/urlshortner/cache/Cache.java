package com.intuit.craft.urlshortner.cache;

import com.fasterxml.jackson.core.type.TypeReference;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import lombok.NonNull;

public interface Cache {

    <TValue> void put(@NonNull String key, TValue value);

    <TValue> void put(@NonNull String key, TValue value, long ttl, @NonNull TimeUnit timeUnit);

    <TValue> Optional<TValue> get(@NonNull String key, @NonNull TypeReference<TValue> type);

    <TValue> Optional<TValue> get(@NonNull String key, @NonNull Class<TValue> type);

    void delete(String key);
}
