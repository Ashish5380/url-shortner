package com.intuit.craft.urlshortner.service;

import com.intuit.craft.urlshortner.models.dto.request.LongUrlUpdateRequest;
import com.intuit.craft.urlshortner.models.dto.request.ShortenUrlRequest;

public interface UrlService {

    String convertToShortUrl(ShortenUrlRequest request);

    String convertToLongUrl(String path);

    String updateLongUrl(LongUrlUpdateRequest urlRequest);
}
