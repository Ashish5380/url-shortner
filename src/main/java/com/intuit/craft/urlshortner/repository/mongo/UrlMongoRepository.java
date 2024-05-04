package com.intuit.craft.urlshortner.repository.mongo;

import com.intuit.craft.urlshortner.models.entity.UrlEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UrlMongoRepository extends MongoRepository<UrlEntity, String> {
    Optional<UrlEntity> findByBaseValue(Integer baseValue);
    Optional<UrlEntity> findByShortUrl(String shortUrl);
}
