package com.intuit.craft.urlshortner.api.controller;

import com.intuit.craft.urlshortner.api.UrlApi;
import com.intuit.craft.urlshortner.models.dto.request.LongUrlUpdateRequest;
import com.intuit.craft.urlshortner.models.dto.request.ShortenUrlRequest;
import com.intuit.craft.urlshortner.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.net.URISyntaxException;

/**
 * REST controller for managing URL redirection and shortening services.
 * This controller handles HTTP requests related to:
 * - Redirecting from a shortened URL to its original URL.
 * - Creating shortened URLs from original URLs.
 * - Updating the destination of existing shortened URLs.
 *
 * Methods:
 * - {@code resolveUrl(String)}: Redirects a request from a shortened URL to the corresponding original URL.
 * - {@code shortenUrl(ShortenUrlRequest)}: Creates a shortened URL for a given original URL and returns the shortened version.
 * - {@code updateShortenedUrl(LongUrlUpdateRequest)}: Updates an existing shortened URL to redirect to a new original URL.
 *
 * Uses {@link UrlService} to process URL conversion and updates, encapsulating the business logic associated with URL management.
 */
@RestController
@RequiredArgsConstructor
public class UrlController implements UrlApi {

    private final UrlService urlService;

    @Override
    public RedirectView resolveUrl(String path) throws URISyntaxException {
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(urlService.convertToLongUrl(path));
        return redirectView;
    }

    @Override
    public ResponseEntity<?> shortenUrl(ShortenUrlRequest request) {
        return new ResponseEntity<>(urlService.convertToShortUrl(request), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> updateShortenedUrl(LongUrlUpdateRequest request) {
        return new ResponseEntity<>(urlService.updateShortUrl(request),HttpStatus.OK);

    }
}
