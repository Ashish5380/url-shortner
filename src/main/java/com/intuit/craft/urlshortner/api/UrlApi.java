package com.intuit.craft.urlshortner.api;

import com.intuit.craft.urlshortner.models.dto.request.LongUrlUpdateRequest;
import com.intuit.craft.urlshortner.models.dto.request.ShortenUrlRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.net.URISyntaxException;

@RequestMapping("/url")
public interface UrlApi {

    @GetMapping("/r/{path}")
    RedirectView resolveUrl(@PathVariable("path") String path) throws URISyntaxException;

    @PostMapping
    ResponseEntity<?> shortenUrl(@RequestBody ShortenUrlRequest request);

    @PutMapping
    ResponseEntity<?> updateShortenedUrl(@RequestBody LongUrlUpdateRequest request);
}
