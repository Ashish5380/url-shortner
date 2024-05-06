package com.intuit.craft.urlshortner.service.impl;

import com.intuit.craft.urlshortner.cache.Cache;
import com.intuit.craft.urlshortner.constants.StringConstants;
import com.intuit.craft.urlshortner.exceptions.service.UserCreationException;
import com.intuit.craft.urlshortner.exceptions.service.UserNotFoundException;
import com.intuit.craft.urlshortner.exceptions.service.UserUpdationException;
import com.intuit.craft.urlshortner.models.bo.UserBO;
import com.intuit.craft.urlshortner.models.bo.UserCacheBO;
import com.intuit.craft.urlshortner.models.dto.request.CreateUserRequest;
import com.intuit.craft.urlshortner.models.dto.request.UpdateUserRequest;
import com.intuit.craft.urlshortner.models.entity.UserEntity;
import com.intuit.craft.urlshortner.repository.UserDataAccess;
import com.intuit.craft.urlshortner.service.UserService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private final Cache cache;

    private final UserDataAccess userDataAccess;

    @Override
    public String createUser(CreateUserRequest createUserRequest){
        Optional<UserEntity> user = userDataAccess.addToDB(UserBO
                        .builder()
                        .name(createUserRequest.getName())
                        .tps(createUserRequest.getTps())
                        .email(createUserRequest.getEmail())
                        .customPrefix(createUserRequest.getCustomPrefix())
                        .build());
        if(user.isPresent()){
            String userId = user.get().getId().toHexString();
            LOGGER.info("[createUser] : created user with email {} with user-id : {}",createUserRequest.getEmail(),
                    userId);
            cache.put(userId, UserCacheBO.builder()
                            .customPrefix(user.get().getCustomPrefix())
                            .tps(user.get().getTps())
                            .build());
            return userId;
        }else{
            LOGGER.info("[createUser]: error : {} for user with email : {}",StringConstants.Error.USER_CREATION_ERROR,
                    createUserRequest.getEmail());
            throw new UserCreationException(StringConstants.Error.USER_CREATION_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public UserCacheBO getUserCachedObject(String userId){
        Optional<UserCacheBO> userCacheBO = cache.get(userId, UserCacheBO.class);
        if(!userCacheBO.isPresent()) {
            Optional<UserEntity> user = userDataAccess.findUserById(userId);
            if (user.isPresent()) {
                userCacheBO = Optional.ofNullable(UserCacheBO.builder()
                                .tps(user.get().getTps())
                                .customPrefix(user.get().getCustomPrefix())
                        .build());
            } else {
                LOGGER.error("[getUserCachedObject]: error getting user cache object : {} for user : {}",
                        StringConstants.Error.USER_ID_NOT_EXIST, userId);
                throw new UserNotFoundException(StringConstants.Error.USER_ID_NOT_EXIST, HttpStatus.BAD_REQUEST);
            }
        }
        UserCacheBO cacheObject = userCacheBO.get();
        cache.put(userId,cacheObject);
        return cacheObject;
    }

    @Override
    public String updateUser(UpdateUserRequest updateUserRequest){
        userDataAccess.upsertUser(UserEntity.builder()
                .id(new ObjectId(updateUserRequest.getId()))
                .name(updateUserRequest.getName())
                .tps(updateUserRequest.getTps())
                .email(updateUserRequest.getEmail())
                .customPrefix(updateUserRequest.getCustomPrefix())
                .build());
        UserCacheBO userCacheBO = getUserCachedObject(updateUserRequest.getId());
        userCacheBO.setTps(updateUserRequest.getTps());
        userCacheBO.setCustomPrefix(updateUserRequest.getCustomPrefix());
        cache.put(updateUserRequest.getId(),userCacheBO);
        return updateUserRequest.getId();
    }


}
