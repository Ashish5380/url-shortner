package com.intuit.craft.urlshortner.api.controller;

import com.intuit.craft.urlshortner.api.UserApi;
import com.intuit.craft.urlshortner.exceptions.service.UserUpdationException;
import com.intuit.craft.urlshortner.models.dto.request.CreateUserRequest;
import com.intuit.craft.urlshortner.models.dto.request.UpdateUserRequest;
import com.intuit.craft.urlshortner.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {
    private final UserService userService;
    @Override
    public ResponseEntity<?> createUser(CreateUserRequest request){
        return new ResponseEntity<>(userService.createUser(request), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> updateUser(UpdateUserRequest request){
        return new ResponseEntity<>(userService.updateUser(request), HttpStatus.OK);
    }
}
