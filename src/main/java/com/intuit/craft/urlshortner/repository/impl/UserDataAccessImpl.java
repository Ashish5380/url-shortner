package com.intuit.craft.urlshortner.repository.impl;

import com.intuit.craft.urlshortner.constants.StringConstants;
import com.intuit.craft.urlshortner.exceptions.service.DatabaseException;
import com.intuit.craft.urlshortner.models.bo.UserBO;
import com.intuit.craft.urlshortner.models.entity.UserEntity;
import com.intuit.craft.urlshortner.repository.UserDataAccess;
import com.intuit.craft.urlshortner.repository.mongo.UserMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserDataAccessImpl implements UserDataAccess {
    private final UserMongoRepository userMongoRepository;
    private final MongoTemplate template;

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

    @Override
    public Optional<UserEntity> findUserById(String userId){
        return userMongoRepository.findUserEntityById(userId);
    }

    @Override
    public void upsertUser(UserEntity entity){
        try {
            Query query = new Query(Criteria.where("_id").is(entity.getId()));

            Update update = new Update()
                    .set("name", entity.getName())
                    .set("tps", entity.getTps())
                    .set("email", entity.getEmail());

            template.upsert(query, update, UserEntity.class);
        } catch (Exception e) {
            throw new DatabaseException(StringConstants.Error.DATABASE_FAILURE_MESSAGE,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
