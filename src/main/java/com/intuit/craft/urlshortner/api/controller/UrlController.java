package com.intuit.craft.urlshortner.api.controller;

import com.intuit.craft.urlshortner.api.UrlApi;
import com.intuit.craft.urlshortner.models.dto.GenericResponse;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class UrlController implements UrlApi {

    @Override
    public RedirectView resolveUrl(String path) {
        return new RedirectView();
    }
}
