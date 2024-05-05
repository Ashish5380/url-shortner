package com.intuit.craft.urlshortner.repository.impl;

import com.intuit.craft.urlshortner.models.bo.UserBO;
import com.intuit.craft.urlshortner.models.entity.UserEntity;
import com.intuit.craft.urlshortner.repository.UserDataAccess;
import com.intuit.craft.urlshortner.repository.mongo.UserMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserDataAccessImpl implements UserDataAccess {
    private final UserMongoRepository userMongoRepository;

    @Override
    public Optional<UserEntity> addToDB(UserBO userBO){
        Optional<UserEntity> existingUser = userMongoRepository.findByEmail(userBO.getEmail());
        if(existingUser.isPresent()){
            return existingUser;
        }else{
            UserEntity newEntity = UserEntity
                    .builder()
                    .name(userBO.getName())
                    .email(userBO.getEmail())
                    .tps(userBO.getTps())
                    .updatedAt(LocalDateTime.now())
                    .createdAt(LocalDateTime.now())
                    .build();
            return Optional.of(userMongoRepository.save(newEntity));
        }

    }
}
