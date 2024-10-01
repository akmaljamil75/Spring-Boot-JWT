package com.spring_jwt.dto.role;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateRoleDTO {
    
    @NotBlank(message = "Role is Required")
    private String role;

}
