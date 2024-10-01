package com.spring_jwt.dto.user;


import com.spring_jwt.utility.ERole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserDTO {
    
    @NotBlank(message = "username is required")
    private String username;

    @NotBlank(message = "email is required")
    @Email(message = "Format email tidak valid")
    private String email;
    
    @NotBlank(message = "role is required")
    private String role = ERole.USER.getValue(); 

    @NotBlank(message = "password is required")
    private String password;

}
