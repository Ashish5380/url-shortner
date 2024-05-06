package com.intuit.craft.urlshortner.repository.mongo;

import com.intuit.craft.urlshortner.models.entity.CustomUrlEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface CustomUrlMongoRepository extends MongoRepository<CustomUrlEntity, String> {

    Optional<CustomUrlEntity> findDistinctByShortUrl(String shortUrl);
}
