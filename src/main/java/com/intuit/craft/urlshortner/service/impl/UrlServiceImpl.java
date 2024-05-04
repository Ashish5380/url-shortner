package com.intuit.craft.urlshortner.service.impl;

import com.intuit.craft.urlshortner.models.dto.request.ShortenUrlRequest;
import com.intuit.craft.urlshortner.repository.UrlDataAccess;
import com.intuit.craft.urlshortner.service.Conversion;
import com.intuit.craft.urlshortner.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class UrlServiceImpl implements UrlService {

    private final UrlDataAccess urlDao;
    private final Conversion conversion;

    HashMap<String, Integer> ltos;
    HashMap<Integer, String> stol;
    static Integer COUNTER=1000000000;
    String elements;

    public String convertToShortUrl(ShortenUrlRequest request){
        String suffix = conversion.encode(request.getUrl());
        return request.getCustomPrefix() + suffix;
    }

    public String convertToLongUrl(String url) {

    }

}
