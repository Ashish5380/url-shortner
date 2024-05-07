package com.intuit.craft.urlshortner.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.intuit.craft.urlshortner.models.bo.ResolveUrlBo;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class UrlValidatorTest {
    @Test
    void testValidateUrl() {
        ResolveUrlBo.ResolveUrlBoBuilder builderResult = ResolveUrlBo.builder();
        ResolveUrlBo urlBo = builderResult.expiry(LocalDate.of(1970, 1, 1).atStartOfDay())
                .longUrl("https://ashish.com/test")
                .tps(1L)
                .build();

        UrlValidator.validateUrl(urlBo);
        assertEquals("1997-03-14", urlBo.getExpiry().toLocalDate().toString());
        assertEquals("https://ashish.com/test", urlBo.getLongUrl());
        assertEquals(1L, urlBo.getTps());
    }
}
