package com.intuit.craft.urlshortner.models.bo;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResolveUrlBo {

    private String longUrl;

    private long tps;

    private LocalDateTime expiry;
}
