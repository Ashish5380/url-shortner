package com.intuit.craft.urlshortner.service.impl;

import com.intuit.craft.urlshortner.cache.Cache;
import com.intuit.craft.urlshortner.cache.DistributedCache;
import com.intuit.craft.urlshortner.exceptions.service.UrlCreationException;
import com.intuit.craft.urlshortner.models.bo.ShortenUrlBO;
import com.intuit.craft.urlshortner.models.dto.request.ShortenUrlRequest;
import com.intuit.craft.urlshortner.models.entity.UrlEntity;
import com.intuit.craft.urlshortner.repository.UrlDataAccess;
import com.intuit.craft.urlshortner.service.Conversion;
import com.intuit.craft.urlshortner.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

import static com.intuit.craft.urlshortner.constants.ServiceConstants.COUNTER_KEY;
import static com.intuit.craft.urlshortner.constants.ServiceConstants.URL_PREFIX;

@Service
@RequiredArgsConstructor
public class UrlServiceImpl implements UrlService {

    private final UrlDataAccess urlDao;
    private final Conversion conversion;
    private final DistributedCache distributedCache;
    private final Cache cache;

    @Override
    public String convertToShortUrl(ShortenUrlRequest request){
        Integer counter = distributedCache.atomicIntegerWithValue(COUNTER_KEY,1000000000).incrementAndGet();
        String suffix = conversion.encode(request.getUrl(), counter);
        String custom = String.valueOf(cache.get(request.getUserId(),String.class));
        StringBuilder shortUrlBuilder = new StringBuilder();
        if(Objects.nonNull(custom)){
            shortUrlBuilder.append(URL_PREFIX).append(custom).append(suffix);
        }else{
            shortUrlBuilder.append(URL_PREFIX).append(suffix);
        }
        Optional<UrlEntity> entity = urlDao.saveToDB(ShortenUrlBO.builder()
                .longUrl(request.getUrl())
                .shortUrl(shortUrlBuilder.toString())
                .baseValue(counter)
                .expiry(request.getExpiry())
                .userId(request.getUserId())
                .build());
        if(entity.isPresent()){
            cache.put(shortUrlBuilder.toString(),request.getUrl());
            return shortUrlBuilder.toString();
        }else{
            throw new UrlCreationException("Error while creating short url", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public String convertToLongUrl(String url) {

    }

}
