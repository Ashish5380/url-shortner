package com.intuit.craft.urlshortner.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UrlServiceImpl {

    private final UrlDAO urlDao;
    private final BaseConversion BaseConversion;

    public String convertToShorrtUrl(LongUrlRequest request){
        
    }
}
