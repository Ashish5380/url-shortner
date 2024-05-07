package com.intuit.craft.urlshortner.service;

import com.intuit.craft.urlshortner.models.dto.request.LongUrlUpdateRequest;
import com.intuit.craft.urlshortner.models.dto.request.ShortenUrlRequest;

public interface UrlService {

    /**
     * Converts a long URL to a short URL, storing it in the database.
     * If a custom short URL is provided, it saves it directly.
     * Otherwise, it generates a unique short URL using a counter from a distributed cache.
     *
     * @param request contains the long URL and optionally a custom short URL.
     * @return the generated or custom short URL.
     */
    String convertToShortUrl(ShortenUrlRequest request);

    /**
     * Resolves a short URL to its corresponding long URL, handling rate limiting and validation.
     * If the request rate exceeds the configured limit, a TooManyRequestException is thrown.
     *
     * @param path the short URL path to resolve.
     * @return the long URL if resolution is successful.
     */
    String convertToLongUrl(String path);

    /**
     * Updates the long URL corresponding to a given short URL suffix.
     *
     * @param urlRequest contains the short URL and the new long URL to update.
     * @return the short URL suffix if the update is successful.
     */
    String updateLongUrl(LongUrlUpdateRequest urlRequest);
}
