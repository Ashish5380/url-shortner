package com.intuit.craft.urlshortner.repository;

import com.intuit.craft.urlshortner.models.bo.UserBO;
import com.intuit.craft.urlshortner.models.entity.UserEntity;

import java.util.Optional;

public interface UserDataAccess {

    Optional<UserEntity> addToDB(UserBO userBO);
}
