package com.intuit.craft.urlshortner.repository.mongo;

import com.intuit.craft.urlshortner.models.entity.UrlEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UrlMongoRepository extends MongoRepository<UrlEntity, String> {
}
