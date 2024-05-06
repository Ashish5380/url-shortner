package com.intuit.craft.urlshortner.models.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LongUrlUpdateRequest {
    private String url;
    Integer expiry;
    String shortUrl;
}
