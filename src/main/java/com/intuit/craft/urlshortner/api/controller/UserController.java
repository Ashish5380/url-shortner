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

/**
 * REST controller for managing user operations within the application.
 * This controller handles HTTP requests related to:
 * - Creating new users.
 * - Updating existing user information.
 *
 * Methods:
 * - {@code createUser(CreateUserRequest)}: Receives a request to create a new user,
 *   processes it through the {@link UserService}, and returns a response indicating
 *   successful creation with a CREATED status.
 * - {@code updateUser(UpdateUserRequest)}: Receives a request to update an existing user,
 *   processes it through the {@link UserService}, and returns a response indicating
 *   successful update with an OK status.
 *
 * This controller utilizes {@link UserService} to perform the necessary business logic
 * for user creation and update operations, thereby abstracting direct data management
 * away from the controller.
 */
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
