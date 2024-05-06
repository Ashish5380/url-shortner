package com.intuit.craft.urlshortner.repository.mongo;

import com.intuit.craft.urlshortner.models.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserMongoRepository extends MongoRepository<UserEntity, String> {
    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findUserEntityById(String id);

}
