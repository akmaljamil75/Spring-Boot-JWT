package com.spring_jwt.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring_jwt.dto.manajement_akses.CreateManajementAksesDTO;
import com.spring_jwt.entity.ManajementAksesEntity;
import com.spring_jwt.entity.RoleEntity;
import com.spring_jwt.exception.impl.BadRequestException;
import com.spring_jwt.exception.impl.ConflictException;
import com.spring_jwt.repository.ManajamentAksesRepository;
import com.spring_jwt.repository.RoleRepository;
import com.spring_jwt.utility.DateUtils;

@Service
public class ManajementAksesService extends BaseSearchService {
    
    @Autowired
    private ManajamentAksesRepository ManajementAksesrepository;

    @Autowired
    private RoleRepository roleRepository;

    public ManajementAksesEntity save(CreateManajementAksesDTO dto) throws Exception {
        
        Optional<ManajementAksesEntity> findDuplicated = ManajementAksesrepository.findByRoleAndUri(dto.getRole(), dto.getUri());
        if(findDuplicated.isPresent()) {
            throw new ConflictException("Data Tersebtu Sudah Ada");
        }
        
        Optional<RoleEntity> findRole = roleRepository.findByRole(dto.getRole());
        if(!findRole.isPresent()) {
            throw new BadRequestException("Role tersebut tidak ada");
        }
        
        ManajementAksesEntity entity = new ManajementAksesEntity();
        entity.setRole(dto.getRole());
        entity.setUri(dto.getUri());
        entity.setCreated_at(DateUtils.getDate());
        entity.setUpdated_at(DateUtils.getDate());
        entity.set__version(System.currentTimeMillis());
        entity.setCreated_by("DMUSER");
        entity.setUpdated_by("DMUSER");
        return ManajementAksesrepository.insert(entity);
    }

    public Optional<ManajementAksesEntity> findAuthorization(String role, String uri) throws Exception {
       return ManajementAksesrepository.findByRoleAndUri(role, uri);
    }

}
