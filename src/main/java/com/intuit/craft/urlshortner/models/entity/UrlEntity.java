package com.intuit.craft.urlshortner.models.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@SuperBuilder
@Document(collection = "url")
public class UrlEntity extends BaseEntity{
    String actualUrl;

    String shortUrl;

    String customPrefix;

    Integer tps;

    String userId;

    Long baseValue;
}
