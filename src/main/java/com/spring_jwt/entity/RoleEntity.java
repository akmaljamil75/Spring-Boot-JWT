package com.spring_jwt.entity;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "roles")
public class RoleEntity extends BaseEntity {
    
    @Indexed(unique = true)
    private String role;

}
