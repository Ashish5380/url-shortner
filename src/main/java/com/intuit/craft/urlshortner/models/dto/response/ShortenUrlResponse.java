package com.intuit.craft.urlshortner.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ShortenUrlResponse {

    private String code;

    private String url;

    private LocalDateTime expiry;
}
