package com.spring_jwt.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.spring_jwt.entity.RoleEntity;

public interface RoleRepository extends MongoRepository<RoleEntity, String> {
    
    Optional<RoleEntity> findByRole(String role);

}
