package com.example.me.DTOs.users;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginTokenDTO {

    @NotBlank(message = "token is required")
    private String token;

}
