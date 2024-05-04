package com.intuit.craft.urlshortner.models.bo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class ShortenUrlBO {
    String shortUrl;
    String longUrl;
    String userId;
    Integer baseValue;
    Integer expiry;
}
