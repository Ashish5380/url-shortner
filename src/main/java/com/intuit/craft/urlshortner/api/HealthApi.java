package com.intuit.craft.urlshortner.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/healthz")
public interface HealthApi {

    @GetMapping("/ping")
    ResponseEntity<?> ping();
}
