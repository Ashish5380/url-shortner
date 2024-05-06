package com.intuit.craft.urlshortner.repository;

import com.intuit.craft.urlshortner.models.bo.ShortenUrlBO;
import com.intuit.craft.urlshortner.models.entity.CustomUrlEntity;

import java.util.Optional;

public interface CustomUrlDataAccess {

    Optional<CustomUrlEntity> saveToDB(ShortenUrlBO request);

    Optional<CustomUrlEntity> findByShortSuffix(String suffix);
}
