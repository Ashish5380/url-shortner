package com.intuit.craft.urlshortner.api.controller;

import com.intuit.craft.urlshortner.api.HealthApi;
import com.intuit.craft.urlshortner.models.dto.GenericResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController implements HealthApi {
    @Override
    public ResponseEntity<?> ping() {
        return GenericResponse.ok("pong");
    }
}
