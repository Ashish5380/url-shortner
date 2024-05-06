package com.intuit.craft.urlshortner.models.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShortenUrlRequest {
    private String shortUrl;
    private String url;
    private String userId;
    Integer expiry;
}
