package com.example.me.DTOs.google.auth;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthFormDTO {
    private String code;
    private String clientId;
    private String clientSecret;
    private String grantType;
    private String redirectUri;
}

