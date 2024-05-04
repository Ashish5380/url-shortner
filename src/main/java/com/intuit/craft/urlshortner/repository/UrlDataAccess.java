package com.intuit.craft.urlshortner.repository;

import com.intuit.craft.urlshortner.models.bo.ShortenUrlBO;
import com.intuit.craft.urlshortner.models.entity.UrlEntity;

import java.util.Optional;

public interface UrlDataAccess {
    Optional<UrlEntity> saveToDB(ShortenUrlBO request);
}
