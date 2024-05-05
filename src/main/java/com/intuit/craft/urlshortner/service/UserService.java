package com.intuit.craft.urlshortner.service;

import com.intuit.craft.urlshortner.models.dto.request.CreateUserRequest;

public interface UserService {

    String createUser(CreateUserRequest createUserRequest);
}
