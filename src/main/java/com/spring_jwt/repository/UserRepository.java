package com.spring_jwt.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

import com.spring_jwt.entity.UserEntity;

public interface UserRepository extends MongoRepository<UserEntity, String> {

    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmail(String email);
    
}
