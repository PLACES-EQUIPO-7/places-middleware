package com.example.me.DTOs.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestBody;

@Getter
@Setter
public class LoginDTO {

    @NotBlank
    private String userName;

    @NotBlank
    private String password;
}
