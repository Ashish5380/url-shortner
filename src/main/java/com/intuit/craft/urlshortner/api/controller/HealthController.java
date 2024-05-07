package com.intuit.craft.urlshortner.api.controller;

import com.intuit.craft.urlshortner.api.HealthApi;
import com.intuit.craft.urlshortner.models.dto.response.GenericResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for health checking operations within the application.
 * This controller is primarily used to verify the operational status of the service.
 *
 * Methods:
 * - {@code ping()}: A simple method that returns a "pong" response. This is often used
 *   to check if the service is responsive and operational. The method returns a
 *   standard 200 OK response with the message "pong", indicating that the server is
 *   up and running.
 *
 * This health check endpoint is useful for monitoring and service discovery purposes,
 * where automated systems or administrators may periodically check the health of the
 * application to ensure it is operational.
 */
@RestController
public class HealthController implements HealthApi {
    @Override
    public ResponseEntity<?> ping() {
        return GenericResponse.ok("pong");
    }
}
