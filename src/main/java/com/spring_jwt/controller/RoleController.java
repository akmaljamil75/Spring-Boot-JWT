package com.spring_jwt.controller;

import org.springframework.web.bind.annotation.RestController;

import com.spring_jwt.dto.BaseSearchDTO;
import com.spring_jwt.dto.role.CreateRoleDTO;
import com.spring_jwt.service.RoleService;
import com.spring_jwt.utility.ResponseUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController()
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;
    
    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> create(@RequestBody @Validated CreateRoleDTO dto) throws Exception {
        return ResponseUtils.success("Berhasil Membuat Data User", roleService.save(dto), HttpStatus.CREATED);
    }

    @PostMapping(path = "/search", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> search(@RequestBody BaseSearchDTO dto) throws Exception {
        return ResponseUtils.success("Berhasil Mencari Data Role", roleService.search(dto, "roles"), HttpStatus.OK);
    }

}
