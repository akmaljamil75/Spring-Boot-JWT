package com.spring_jwt.entity;

import org.springframework.data.mongodb.core.mapping.Document;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "manajement-akses")
public class ManajementAksesEntity extends BaseEntity {
    
    private String role;

    private String uri;

}
