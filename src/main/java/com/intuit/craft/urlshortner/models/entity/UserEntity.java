package com.intuit.craft.urlshortner.models.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@SuperBuilder
@Document(collection = "user")
public class UserEntity extends BaseEntity{
    String name;
    String email;

}
