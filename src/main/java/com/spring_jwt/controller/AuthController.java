package com.spring_jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring_jwt.dto.auth.ForgetPasswordDTO;
import com.spring_jwt.dto.auth.LoginDTO;
import com.spring_jwt.dto.user.CreateUserDTO;
import com.spring_jwt.service.UserService;
import com.spring_jwt.utility.ResponseUtils;

import jakarta.validation.Valid;

@RequestMapping("/auth")
@RestController
public class AuthController {
    
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> create(@RequestBody @Valid CreateUserDTO dto) throws Exception {
        return ResponseUtils.success("Berhasil Membuat Data User", userService.save(dto), HttpStatus.CREATED);
    }

    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> login(@RequestBody @Valid LoginDTO dto) throws Exception {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
        return ResponseUtils.success("Berhasil Login", userService.generatedToken(dto), HttpStatus.OK);
    }

    @PostMapping(path = "/reset-password", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> resetPassowrd(@RequestBody @Valid ForgetPasswordDTO dto) throws Exception {
        userService.sendMailVertificationResetPassword(dto);
        return ResponseUtils.success("Berhasil Mengirim Reset Passowrd", HttpStatus.OK);
    }

    @GetMapping("/reset-password/{token}/{username}")
    public Object forgetPassword(
            @PathVariable String token, 
            @PathVariable String username,
            @RequestParam String password) throws Exception {
        userService.resetPassword(token, username, password);
        return ResponseUtils.success("Berhasil Reset Passowrd", HttpStatus.OK);
    }

}
