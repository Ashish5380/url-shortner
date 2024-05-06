package com.intuit.craft.urlshortner.service.impl;

import com.intuit.craft.urlshortner.cache.Cache;
import com.intuit.craft.urlshortner.cache.DistributedCache;
import com.intuit.craft.urlshortner.exceptions.service.UrlCreationException;
import com.intuit.craft.urlshortner.exceptions.service.UrlNotFoundException;
import com.intuit.craft.urlshortner.models.bo.ShortenUrlBO;
import com.intuit.craft.urlshortner.models.bo.UserCacheBO;
import com.intuit.craft.urlshortner.models.dto.request.LongUrlUpdateRequest;
import com.intuit.craft.urlshortner.models.dto.request.ShortenUrlRequest;
import com.intuit.craft.urlshortner.models.entity.UrlEntity;
import com.intuit.craft.urlshortner.repository.UrlDataAccess;
import com.intuit.craft.urlshortner.service.Conversion;
import com.intuit.craft.urlshortner.service.UrlService;
import com.intuit.craft.urlshortner.service.UserService;
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
    private final UserService userService;

    @Override
    public String convertToShortUrl(ShortenUrlRequest request){
        Integer counter = distributedCache.atomicIntegerWithValue(COUNTER_KEY,1000000000).incrementAndGet();
        String suffix = conversion.encode(request.getUrl(), counter);
        Optional<UrlEntity> entity = urlDao.saveToDB(ShortenUrlBO.builder()
                .longUrl(request.getUrl())
                .shortUrl(suffix)
                .baseValue(counter)
                .expiry(request.getExpiry())
                .userId(request.getUserId())
                .build());
        if(entity.isPresent()){
            cache.put(suffix,request.getUrl());
            return shortUrlString(suffix, request.getUserId());
        }else{
            throw new UrlCreationException("Error while creating short url", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public String convertToLongUrl(String path) {
        Optional<String> longUrl = cache.get(path,String.class);
        if(longUrl.isPresent()){
            return longUrl.get();
        }else{
            Integer basePath = conversion.decode(path);
            Optional<UrlEntity> urlEntity= urlDao.findByBasePath(basePath);
            if(urlEntity.isPresent()){
                cache.put(urlEntity.get().getShortUrl(), urlEntity.get().getActualUrl());
                return urlEntity.get().getActualUrl();
            }else{
                throw new UrlNotFoundException("Request url not found", HttpStatus.BAD_REQUEST);
            }
        }
    }

    @Override
    public String updateLongUrl(LongUrlUpdateRequest urlRequest, String userId){
        String[] parts = urlRequest.getShortUrl().split("/");
        String suffix = parts[parts.length - 1];
        Integer baseValue = conversion.decode(suffix);
        Optional<UrlEntity> urlEntity = urlDao.findByBasePath(baseValue);
        if(urlEntity.isPresent()){
            UrlEntity existingObj = urlEntity.get();
            existingObj.setActualUrl(urlRequest.getUrl());
            urlDao.upsertUrl(existingObj);
            cache.put(suffix,urlRequest.getUrl());
            return shortUrlString(suffix,userId);
        }else{
            throw new UrlNotFoundException("Provided url does not exist in the system", HttpStatus.BAD_REQUEST);
        }
    }

    private String shortUrlString(String suffix, String userId){
        UserCacheBO cacheBO = userService.getUserCachedObject(userId);
        StringBuilder shortUrlBuilder = new StringBuilder();
        if(Objects.nonNull(cacheBO.getCustomPrefix())){
            shortUrlBuilder.append(URL_PREFIX).append(cacheBO.getCustomPrefix()).append("/").append(suffix);
        }else{
            shortUrlBuilder.append(URL_PREFIX).append(suffix);
        }
        return shortUrlBuilder.toString();
    }

}
