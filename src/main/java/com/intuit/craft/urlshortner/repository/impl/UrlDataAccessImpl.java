package com.intuit.craft.urlshortner.repository.impl;

import com.intuit.craft.urlshortner.models.bo.ShortenUrlBO;
import com.intuit.craft.urlshortner.models.entity.UrlEntity;
import com.intuit.craft.urlshortner.repository.UrlDataAccess;
import com.intuit.craft.urlshortner.repository.mongo.UrlMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UrlDataAccessImpl implements UrlDataAccess {
    private final UrlMongoRepository urlMongoRepository;

    public Optional<UrlEntity> saveToDB(ShortenUrlBO request) {
        Optional<UrlEntity> existingEntity = urlMongoRepository.findDistinctByBaseValue(request.getBaseValue());
        if (existingEntity.isPresent()) {
            return existingEntity;
        }
        LocalDateTime time = LocalDateTime.now();
        UrlEntity newEntity = UrlEntity.builder()
                .actualUrl(request.getLongUrl())
                .shortUrl(request.getShortUrl())
                .expiry(time.plusDays((long)request.getExpiry()))
                .baseValue(request.getBaseValue())
                .createdAt(time)
                .updatedAt(time)
                .build();

        return Optional.of(urlMongoRepository.save(newEntity));
    }

    @Override
    public Optional<UrlEntity> findByBasePath(Integer base){
        return urlMongoRepository.findDistinctByBaseValue(base);
    }

    @Override
    public void upsertUrl(UrlEntity urlEntity){
        urlMongoRepository.save(urlEntity);
    }
}
