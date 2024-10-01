package com.spring_jwt.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring_jwt.dto.role.CreateRoleDTO;
import com.spring_jwt.entity.RoleEntity;
import com.spring_jwt.exception.impl.BadRequestException;
import com.spring_jwt.repository.RoleRepository;
import com.spring_jwt.utility.DateUtils;
import com.spring_jwt.utility.GetSecurityPrincipal;

@Service
public class RoleService extends BaseSearchService {
    
    @Autowired
    private RoleRepository repo;

    public Object save(CreateRoleDTO dto) throws Exception { 

        Optional<RoleEntity> findDup = repo.findByRole(dto.getRole());
        if(findDup.isPresent()) {
            throw new BadRequestException("Role : " + dto.getRole() + " Already exist");
        }

        RoleEntity entity = new RoleEntity(); 
        entity.setRole(dto.getRole());
        entity.setCreated_at(DateUtils.getDate());
        entity.setUpdated_at(DateUtils.getDate());
        entity.set__version(System.currentTimeMillis());
        entity.setCreated_by((String) GetSecurityPrincipal.getObjectPrincipal("username", String.class));
        entity.setUpdated_by((String) GetSecurityPrincipal.getObjectPrincipal("username", String.class));
        return repo.save(entity);
    }

    public Optional<RoleEntity> findByRole(String role) throws Exception { 
        return repo.findByRole(role);
    }

}
