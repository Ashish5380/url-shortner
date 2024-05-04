package com.intuit.craft.urlshortner.repository.impl;

import com.intuit.craft.urlshortner.repository.UrlAuditDataAccess;
import com.intuit.craft.urlshortner.repository.mongo.UrlMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UrlAuditDataAccessImpl implements UrlAuditDataAccess {
    private final UrlMongoRepository urlMongoRepository;
}
