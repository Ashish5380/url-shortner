package com.intuit.craft.urlshortner.repository.mongo;

import com.intuit.craft.urlshortner.models.entity.UrlAuditEntity;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface UrlAuditMongoRepository extends MongoRepository<UrlAuditEntity, String> {
}
