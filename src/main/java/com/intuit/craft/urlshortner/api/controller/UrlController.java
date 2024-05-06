package com.intuit.craft.urlshortner.api.controller;

import com.intuit.craft.urlshortner.api.UrlApi;
import com.intuit.craft.urlshortner.models.dto.request.LongUrlUpdateRequest;
import com.intuit.craft.urlshortner.models.dto.request.ShortenUrlRequest;
import com.intuit.craft.urlshortner.service.UrlService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.net.URI;
import java.net.URISyntaxException;

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
        return new ResponseEntity<>(urlService.updateLongUrl(request),HttpStatus.OK);

    }
}
