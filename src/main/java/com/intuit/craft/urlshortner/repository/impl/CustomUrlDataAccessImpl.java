package com.intuit.craft.urlshortner.repository.impl;

import com.intuit.craft.urlshortner.models.bo.ShortenUrlBO;
import com.intuit.craft.urlshortner.models.entity.CustomUrlEntity;
import com.intuit.craft.urlshortner.models.entity.UrlEntity;
import com.intuit.craft.urlshortner.repository.CustomUrlDataAccess;
import com.intuit.craft.urlshortner.repository.mongo.CustomUrlMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CustomUrlDataAccessImpl implements CustomUrlDataAccess {
    private final CustomUrlMongoRepository urlMongoRepository;

    @Override
    public Optional<CustomUrlEntity> saveToDB(ShortenUrlBO request) {
        Optional<CustomUrlEntity> existingEntity = urlMongoRepository.findDistinctByShortUrl(request.getShortUrl());
        if (existingEntity.isPresent()) {
            return existingEntity;
        }
        LocalDateTime time = LocalDateTime.now();
        CustomUrlEntity newEntity = CustomUrlEntity.builder()
                .actualUrl(request.getLongUrl())
                .shortUrl(request.getShortUrl())
                .userId(request.getUserId())
                .expiry(time.plusDays((long)request.getExpiry()))
                .createdAt(time)
                .updatedAt(time)
                .build();

        return Optional.of(urlMongoRepository.save(newEntity));
    }

    @Override
    public Optional<CustomUrlEntity> findByShortSuffix(String suffix){
        return urlMongoRepository.findDistinctByShortUrl(suffix);
    }
}
