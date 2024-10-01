package com.spring_jwt.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.spring_jwt.entity.ManajementAksesEntity;

import java.util.Optional;

public interface ManajamentAksesRepository extends MongoRepository<ManajementAksesEntity, String> {
    
    Optional<ManajementAksesEntity> findByRoleAndUri(String role, String uri);

}
