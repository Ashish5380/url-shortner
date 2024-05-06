package com.intuit.craft.urlshortner.repository.mongo;

import com.intuit.craft.urlshortner.models.entity.UrlEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface UrlMongoRepository extends MongoRepository<UrlEntity, String> {
    Optional<UrlEntity> findDistinctByBaseValue(Integer baseValue);
    Optional<UrlEntity> findByShortUrl(String shortUrl);
}
