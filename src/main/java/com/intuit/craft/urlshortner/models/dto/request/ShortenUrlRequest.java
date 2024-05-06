package com.intuit.craft.urlshortner.models.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ShortenUrlRequest {

    private String url;
    private String userId;
    Integer expiry;
}
