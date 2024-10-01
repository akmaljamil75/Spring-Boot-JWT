package com.spring_jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring_jwt.dto.BaseSearchDTO;
import com.spring_jwt.dto.manajement_akses.CreateManajementAksesDTO;
import com.spring_jwt.service.ManajementAksesService;
import com.spring_jwt.utility.ResponseUtils;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/manajement-akses")
public class ManajementAksesController {
    
    @Autowired
    private ManajementAksesService manajementAksesService;

    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> save(@RequestBody @Valid CreateManajementAksesDTO dto) throws Exception {
        return ResponseUtils.success("Berhasil Membuat Data Manajament Akses", manajementAksesService.save(dto), HttpStatus.CREATED);
    }
    
    @PostMapping(path = "/search", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> search(@RequestBody BaseSearchDTO dto) throws Exception {
        return ResponseUtils.success("Berhasil Mencari Data Manajament Akses", manajementAksesService.search(dto,"manajement-akses"), HttpStatus.OK);
    }

}
