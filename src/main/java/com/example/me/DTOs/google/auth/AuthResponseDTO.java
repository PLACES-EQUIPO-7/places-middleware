package com.example.me.DTOs.google.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDTO {

    private String accessToken;
    private Integer expiresIn;
    private String scope;
    private String tokenType;
    private String idToken;
}


