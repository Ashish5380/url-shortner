package com.intuit.craft.urlshortner.api;

import com.intuit.craft.urlshortner.models.dto.request.CreateUserRequest;
import com.intuit.craft.urlshortner.models.dto.request.UpdateUserRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user")
public interface UserApi {

    @PostMapping
    ResponseEntity<?> createUser(@RequestBody CreateUserRequest request);

    @PutMapping
    ResponseEntity<?> updateUser(@RequestBody UpdateUserRequest request);
}
