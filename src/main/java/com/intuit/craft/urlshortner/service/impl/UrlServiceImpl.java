package com.intuit.craft.urlshortner.service.impl;

import com.intuit.craft.urlshortner.cache.Cache;
import com.intuit.craft.urlshortner.cache.DistributedCache;
import com.intuit.craft.urlshortner.constants.StringConstants;
import com.intuit.craft.urlshortner.exceptions.service.TooManyRequestException;
import com.intuit.craft.urlshortner.exceptions.service.UrlCreationException;
import com.intuit.craft.urlshortner.exceptions.service.UrlNotFoundException;
import com.intuit.craft.urlshortner.models.bo.ResolveUrlBo;
import com.intuit.craft.urlshortner.models.bo.ShortenUrlBO;
import com.intuit.craft.urlshortner.models.bo.UserCacheBO;
import com.intuit.craft.urlshortner.models.dto.request.CreateUserRequest;
import com.intuit.craft.urlshortner.models.dto.request.LongUrlUpdateRequest;
import com.intuit.craft.urlshortner.models.dto.request.ShortenUrlRequest;
import com.intuit.craft.urlshortner.models.entity.CustomUrlEntity;
import com.intuit.craft.urlshortner.models.entity.UrlEntity;
import com.intuit.craft.urlshortner.repository.CustomUrlDataAccess;
import com.intuit.craft.urlshortner.repository.UrlDataAccess;
import com.intuit.craft.urlshortner.service.Conversion;
import com.intuit.craft.urlshortner.service.RateLimiter;
import com.intuit.craft.urlshortner.service.UrlService;
import com.intuit.craft.urlshortner.service.UserService;
import com.intuit.craft.urlshortner.util.UrlUtil;
import com.intuit.craft.urlshortner.validation.UrlValidator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.intuit.craft.urlshortner.constants.ServiceConstants.*;

@Service
@RequiredArgsConstructor
public class UrlServiceImpl implements UrlService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UrlServiceImpl.class);
    private final Cache cache;

    private final UrlDataAccess urlDao;

    private final Conversion conversion;

    private final UserService userService;

    private final RateLimiter rateLimiter;

    private final DistributedCache distributedCache;

    private final CustomUrlDataAccess customUrlDataAccess;

    @Override
    public String convertToShortUrl(ShortenUrlRequest request){
        if(Objects.isNull(request.getShortUrl())){
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
                LOGGER.info("[convertToShortUrl]: created short url suffix is: {}" , suffix);
                return shortUrlString(suffix);
            }else{
                LOGGER.error("[convertToShortUrl]: {} for long url {}",StringConstants.Error.URL_CREATION_ERROR, request.getUrl());
                throw new UrlCreationException(StringConstants.Error.URL_CREATION_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }else{
            return saveCustomUrl(request);
        }
    }

    @Override
    public String convertToLongUrl(String path) {
        // Get path
        ResolveUrlBo longUrlBo = getUrlFromPath(path);

        // Validate url
        UrlValidator.validateUrl(longUrlBo);

        // Check if TPS exceeds
        if (rateLimiter.isTooManyRequests(RATE_LIMIT_REDIS_KEY_PREFIX + path, longUrlBo.getTps())) {
            throw new TooManyRequestException("Throttling request", HttpStatus.TOO_MANY_REQUESTS);
        }

        // Return resolved URL
        return longUrlBo.getLongUrl();
    }

    @Override
    public String updateShortUrl(LongUrlUpdateRequest urlRequest){
        String suffix = findUrlSuffix(urlRequest.getShortUrl());
        Integer baseValue = conversion.decode(suffix);
        Optional<UrlEntity> urlEntity = urlDao.findByBasePath(baseValue);
        if(urlEntity.isPresent()){
            UrlEntity existingObj = urlEntity.get();
            Optional.ofNullable(urlRequest.getUrl()).ifPresent(existingObj::setActualUrl);
            Optional.ofNullable(urlRequest.getExpiry())
                    .ifPresent(expiry -> existingObj.setExpiry(LocalDateTime.now().plusDays((long)urlRequest.getExpiry())));
            urlDao.upsertUrl(existingObj);
            cache.delete(suffix);
            LOGGER.info("[updateShortUrl]: updated short url for suffix : {} is : {}" , suffix, urlRequest.getUrl());
            return shortUrlString(suffix);
        }else{
            return updateCustomShortUrl(urlRequest);
        }
    }

    /**
     * Updates the long URL corresponding to a given custom short URL suffix.
     * @param urlRequest contains the short URL and the new long URL to update.
     * @return the short URL suffix if the update is successful
     */
    private String updateCustomShortUrl(LongUrlUpdateRequest urlRequest) {
        String suffix = findUrlSuffix(urlRequest.getShortUrl());
        Optional<CustomUrlEntity> urlEntity = customUrlDataAccess.findByShortSuffix(suffix);
        if(urlEntity.isPresent()) {
            CustomUrlEntity existingObj = urlEntity.get();
            Optional.ofNullable(urlRequest.getUrl()).ifPresent(existingObj::setActualUrl);
            Optional.ofNullable(urlRequest.getExpiry())
                    .ifPresent(expiry -> existingObj.setExpiry(LocalDateTime.now().plusDays((long) urlRequest.getExpiry())));
            customUrlDataAccess.upsertUrl(existingObj);
            cache.delete(suffix);
            LOGGER.info("[updateCustomShortUrl]: updated long url for suffix : {} is : {}", suffix, urlRequest.getUrl());
            return shortUrlString(suffix);
        } else{
            LOGGER.info("[updateCustomShortUrl]: updated long url for suffix : {} is : {}" , suffix, urlRequest.getUrl());
            throw new UrlNotFoundException(StringConstants.Error.URL_NOT_FOUND, HttpStatus.BAD_REQUEST);
            }
    }

    /**
     * Helper method to form the full short URL string from a suffix.
     *
     * @param suffix the short URL suffix.
     * @return the full short URL.
     */
    private String shortUrlString(String suffix){
        return URL_PREFIX + suffix;
    }

    /**
     * Retrieves a resolved URL object from cache or database based on the short URL path provided.
     *
     * @param path the short URL path.
     * @return ResolveUrlBo containing URL data.
     * @throws UrlNotFoundException if the URL cannot be found.
     */
    private ResolveUrlBo getUrlFromPath(final String path) {
        Optional<ResolveUrlBo> cachedUrlBo = cache.get(path, ResolveUrlBo.class);
        if(cachedUrlBo.isPresent()){
            return cachedUrlBo.get();
        }
        Optional<ResolveUrlBo> urlBoFromCustom = checkCustomUrlDataAccess(path);
        if (urlBoFromCustom.isPresent()) {
            return urlBoFromCustom.get();
        }

        Integer basePath = conversion.decode(path);
        return checkGeneralUrlDao(basePath, path);
    }

    /**
     * Attempts to fetch URL data from a custom URL data source.
     *
     * @param path the short URL path.
     * @return an optional ResolveUrlBo if found.
     */
    private Optional<ResolveUrlBo> checkCustomUrlDataAccess(String path) {
        return customUrlDataAccess.findByShortSuffix(path).map(entity -> {
            UserCacheBO user = userService.getUserCachedObject(entity.getUserId());
            ResolveUrlBo urlBo = ResolveUrlBo.builder()
                    .tps(user.getTps())
                    .longUrl(entity.getActualUrl())
                    .expiry(entity.getExpiry())
                    .build();
            cache.put(path, urlBo, 10L, TimeUnit.SECONDS);
            return urlBo;
        });
    }

    /**
     * Fetches URL data from the general URL data source if not available in custom data access.
     *
     * @param basePath the decoded base path.
     * @param path the short URL path.
     * @return ResolveUrlBo containing URL data.
     * @throws UrlNotFoundException if the URL data cannot be retrieved.
     */
    private ResolveUrlBo checkGeneralUrlDao(Integer basePath, String path) throws UrlNotFoundException {
        return urlDao.findByBasePath(basePath)
                .map(entity -> {
                    UserCacheBO user = userService.getUserCachedObject(entity.getUserId());
                    ResolveUrlBo urlBo = ResolveUrlBo.builder()
                            .tps(user.getTps())
                            .longUrl(entity.getActualUrl())
                            .expiry(entity.getExpiry())
                            .build();
                    cache.put(path, urlBo, 10L, TimeUnit.SECONDS);
                    return urlBo;
                })
                .orElseThrow(() -> new UrlNotFoundException(StringConstants.Error.URL_NOT_FOUND, HttpStatus.BAD_REQUEST));
    }


    /**
     * Saves a custom short URL to the database and returns the full short URL if successful.
     *
     * @param request the request containing the long URL and custom short URL.
     * @return the full short URL.
     * @throws UrlCreationException if there is an issue saving the URL.
     */
    private String saveCustomUrl(ShortenUrlRequest request){
        if(UrlUtil.isUrlValid(request.getUrl()) && UrlUtil.isValidCustomSuffix(request.getShortUrl())){
            Optional<CustomUrlEntity> entity = customUrlDataAccess.saveToDB(ShortenUrlBO.builder()
                    .longUrl(request.getUrl())
                    .shortUrl(request.getShortUrl())
                    .expiry(request.getExpiry())
                    .userId(request.getUserId())
                    .build());
            if(entity.isPresent()) {
                LOGGER.info("[saveCustomUrl]: created custom short url suffix is: {}", request.getShortUrl());
                return shortUrlString(request.getShortUrl());
            }else{
                LOGGER.error("[saveCustomUrl]: {} for long url {}",StringConstants.Error.URL_CREATION_ERROR, request.getUrl());
                throw new UrlCreationException(StringConstants.Error.URL_CREATION_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }else{
            LOGGER.info("[saveCustomUrl]: Error which creating custom mapping : {} is : {}" ,
                    StringConstants.Error.INVALID_LONG_URL, request.getUrl());
            throw new UrlCreationException(StringConstants.Error.INVALID_LONG_URL, HttpStatus.BAD_REQUEST);
        }
    }

    private String findUrlSuffix(String shortUrl){
        String[] parts = shortUrl.split("/");
        return parts[parts.length - 1];
    }

}
