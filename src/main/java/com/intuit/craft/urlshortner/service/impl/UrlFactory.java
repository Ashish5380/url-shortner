package com.intuit.craft.urlshortner.service.impl;

import com.intuit.craft.urlshortner.models.dto.request.ShortenUrlRequest;
import com.intuit.craft.urlshortner.service.CustomUrlService;
import com.intuit.craft.urlshortner.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UrlFactory {

    private final UrlService urlService;
    private final CustomUrlService customUrlService;

    public String generateShortUrl(ShortenUrlRequest shortenUrlRequest){
        if(Objects.isNull(shortenUrlRequest.getShortUrl())){
            return urlService.convertToShortUrl(shortenUrlRequest);
        }else{
            return customUrlService.convertToShortUrl(shortenUrlRequest);
        }
    }

    public String redirectToActualUrl(String shortUrl){

    }




}
