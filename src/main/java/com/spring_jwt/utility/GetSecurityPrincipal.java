package com.spring_jwt.utility;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import io.jsonwebtoken.Claims;

public class GetSecurityPrincipal {
    
    public static <T> Object getObjectPrincipal(String field, Class<T> cls) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Claims claims = (Claims) authentication.getPrincipal();
        return claims.get(field, cls);
    }

}
