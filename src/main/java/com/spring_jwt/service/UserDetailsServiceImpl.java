package com.spring_jwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spring_jwt.entity.UserEntity;
import com.spring_jwt.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity entity = repository.findByUsername(username).orElseThrow(() ->
            new UsernameNotFoundException("Username Tidak Ditemukan")
        );
        return org.springframework.security.core.userdetails.User.builder()
            .username(entity.getUsername())
            .password(entity.getPassword())
            .authorities(String.valueOf(entity.getRole()))
            .build();
    }
    
}
