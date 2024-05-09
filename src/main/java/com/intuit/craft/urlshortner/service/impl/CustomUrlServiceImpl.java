package com.intuit.craft.urlshortner.service.impl;

import com.intuit.craft.urlshortner.constants.StringConstants;
import com.intuit.craft.urlshortner.exceptions.service.UrlCreationException;
import com.intuit.craft.urlshortner.models.bo.ShortenUrlBO;
import com.intuit.craft.urlshortner.models.dto.request.ShortenUrlRequest;
import com.intuit.craft.urlshortner.models.entity.CustomUrlEntity;
import com.intuit.craft.urlshortner.repository.CustomUrlDataAccess;
import com.intuit.craft.urlshortner.service.CustomUrlService;
import com.intuit.craft.urlshortner.util.UrlUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUrlServiceImpl implements CustomUrlService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomUrlServiceImpl.class);
    private final CustomUrlDataAccess customUrlDataAccess;


    @Override
    public String convertToShortUrl(ShortenUrlRequest request){
        return saveCustomUrl(request);
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
                return UrlUtil.shortUrlString(request.getShortUrl());
            }else{
                LOGGER.error("[saveCustomUrl]: {} for long url {}", StringConstants.Error.URL_CREATION_ERROR, request.getUrl());
                throw new UrlCreationException(StringConstants.Error.URL_CREATION_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }else{
            LOGGER.info("[saveCustomUrl]: Error which creating custom mapping : {} is : {}" ,
                    StringConstants.Error.INVALID_LONG_URL, request.getUrl());
            throw new UrlCreationException(StringConstants.Error.INVALID_LONG_URL, HttpStatus.BAD_REQUEST);
        }
    }

    public
}
