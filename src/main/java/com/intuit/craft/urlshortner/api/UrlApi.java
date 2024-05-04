package com.intuit.craft.urlshortner.api;

import com.intuit.craft.urlshortner.models.dto.request.ShortenUrlRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RequestMapping("/url")
public interface UrlApi {

    @GetMapping("/resolve/{path}")
    RedirectView resolveUrl(@PathVariable("path") String path);

    @PostMapping
    ResponseEntity<?> shortenUrl(@RequestBody ShortenUrlRequest request);

    @PutMapping("/{code}")
    ResponseEntity<?> updateshortenedUrl(@RequestBody ShortenUrlRequest request);
}
