package com.intuit.craft.urlshortner.validation;

import com.intuit.craft.urlshortner.exceptions.service.UrlExpiredException;
import com.intuit.craft.urlshortner.models.bo.ResolveUrlBo;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@UtilityClass
public class UrlValidator {

    public void validateUrl(ResolveUrlBo urlBo) {
        if (urlBo.getExpiry().isBefore(LocalDateTime.now())) {
            throw new UrlExpiredException("Url has expired", HttpStatus.BAD_REQUEST);
        }
    }
}
