package com.spring_jwt.entity;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
abstract class BaseEntity {
    
    @Id
    private String id;

    private String created_at;

    private String created_by;

    private String updated_at;

    private String updated_by;

    private Long __version; 

}
