package com.intuit.craft.urlshortner.service;

import com.intuit.craft.urlshortner.models.bo.UserCacheBO;
import com.intuit.craft.urlshortner.models.dto.request.CreateUserRequest;
import com.intuit.craft.urlshortner.models.dto.request.UpdateUserRequest;

public interface UserService {

    String createUser(CreateUserRequest createUserRequest);

    UserCacheBO getUserCachedObject(String userId);

    String updateUser(UpdateUserRequest updateUserRequest);
}
