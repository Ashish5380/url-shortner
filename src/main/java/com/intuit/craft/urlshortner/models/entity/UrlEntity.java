package com.intuit.craft.urlshortner.models.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "url")
public class UrlEntity extends BaseEntity{
    String actualUrl;

    String shortUrl;

    String userId;

    Integer baseValue;

    LocalDateTime expiry;
}
