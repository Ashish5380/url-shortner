package com.intuit.craft.urlshortner.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public interface UrlApi {

    @GetMapping("/resolve/{path}")
    RedirectView resolveUrl(@PathVariable("path") String path);
}
