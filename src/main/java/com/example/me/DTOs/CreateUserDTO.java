package com.example.me.DTOs;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserDTO extends UserDTO {

    @NotBlank(message = "password is required")
    private String password;

}
