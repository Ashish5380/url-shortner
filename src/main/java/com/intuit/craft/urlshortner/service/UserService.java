package com.intuit.craft.urlshortner.service;

import com.intuit.craft.urlshortner.models.bo.UserCacheBO;
import com.intuit.craft.urlshortner.models.dto.request.CreateUserRequest;
import com.intuit.craft.urlshortner.models.dto.request.UpdateUserRequest;

public interface UserService {

    /**
     * Creates a new user in the system using the provided CreateUserRequest data.
     * The user data is built into a UserBO and added to the database.
     * On successful creation, the user ID is logged and returned.
     * If user creation fails, logs the error and throws a UserCreationException.
     *
     * @param createUserRequest the data transfer object containing user creation details.
     * @return the unique identifier (userId) of the newly created user as a hexadecimal string.
     */
    String createUser(CreateUserRequest createUserRequest);

    /**
     * Retrieves a UserCacheBO from cache. If not present, fetches from the database and caches it.
     * This method ensures that user data is efficiently retrieved with caching mechanisms to improve performance.
     * Throws UserNotFoundException if the user cannot be found in the database.
     *
     * @param userId the user's unique identifier.
     * @return UserCacheBO containing user data from the cache or database.
     */
    UserCacheBO getUserCachedObject(String userId);

    /**
     * Updates existing user data based on the provided UpdateUserRequest.
     * The method first updates the user data in the database, then updates the cache.
     * Returns the userId of the updated user.
     *
     * @param updateUserRequest the data transfer object containing the updated user details.
     * @return the userId of the updated user.
     */
    String updateUser(UpdateUserRequest updateUserRequest);
}
