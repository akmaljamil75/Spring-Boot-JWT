package com.spring_jwt.dto.manajement_akses;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateManajementAksesDTO {

    @NotBlank(message = "Uri is Required")
    private String uri;

    @NotBlank(message = "Role is Required")
    private String role;
    
}
