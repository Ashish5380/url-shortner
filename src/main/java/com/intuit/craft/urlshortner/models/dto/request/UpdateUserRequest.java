package com.intuit.craft.urlshortner.models.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateUserRequest {
    String id;
    String name;
    String email;
    Integer tps;
    String customPrefix;
}
