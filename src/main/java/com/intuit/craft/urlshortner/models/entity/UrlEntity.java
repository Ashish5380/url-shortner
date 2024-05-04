package com.intuit.craft.urlshortner.models.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@Document(collection = "url")
public class UrlEntity extends BaseEntity{
    String actualUrl;

    String shortUrl;

    String userId;

    Integer baseValue;

    LocalDateTime expiry;
}
