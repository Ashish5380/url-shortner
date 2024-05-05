package com.intuit.craft.urlshortner.service.impl;

import com.intuit.craft.urlshortner.cache.Cache;
import com.intuit.craft.urlshortner.exceptions.service.UserCreationException;
import com.intuit.craft.urlshortner.models.bo.UserBO;
import com.intuit.craft.urlshortner.models.bo.UserCacheBO;
import com.intuit.craft.urlshortner.models.dto.request.CreateUserRequest;
import com.intuit.craft.urlshortner.models.entity.UserEntity;
import com.intuit.craft.urlshortner.repository.UserDataAccess;
import com.intuit.craft.urlshortner.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDataAccess userDataAccess;
    private final Cache cache;

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
            cache.put(userId, UserCacheBO.builder()
                            .customPrefix(user.get().getCustomPrefix())
                            .tps(user.get().getTps())
                            .build());
            return userId;
        }else{
            throw new UserCreationException("Unable to create user in the system", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
