package com.spring_jwt.entity;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "users")
public class UserEntity extends BaseEntity {
    
    @Indexed(unique = true)
    private String username;

    @JsonIgnore
    private String password;

    @Indexed(unique = true)
    private String email;

    private String role;

}
