package com.intuit.craft.urlshortner.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "custom_url")
public class CustomUrlEntity extends BaseEntity{
    String userId;
    String actualUrl;
    String shortUrl;
    LocalDateTime expiry;
}
