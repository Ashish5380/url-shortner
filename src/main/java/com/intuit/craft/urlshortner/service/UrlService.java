package com.intuit.craft.urlshortner.service;

import com.intuit.craft.urlshortner.models.dto.request.ShortenUrlRequest;

public interface UrlService {

    String convertToShortUrl(ShortenUrlRequest request);
}
