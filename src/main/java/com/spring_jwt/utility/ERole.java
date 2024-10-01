package com.spring_jwt.utility;


public enum ERole {
    
    ADMINISTRATOR("administrator"),
    USER("user");

    private final String value;

    ERole(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
