package com.example.me.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.me.DTOs.google.auth.AuthFormDTO;
import com.example.me.DTOs.google.auth.AuthResponseDTO;
import com.example.me.DTOs.users.LoginDTO;
import com.example.me.config.GoogleTokenValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final GoogleAuthRestService googleAuthRestService;

    private final UsersRestService usersRestService;

    private final ObjectMapper objectMapper;

    public String login(LoginDTO login) {

        return usersRestService.login(login);

    }

    public String loginWIthGoogle(String code) {

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("code", code);
        formData.add("client_id", "----");
        formData.add("client_secret", "----");
        formData.add("grant_type", "authorization_code");
        formData.add("redirect_uri", "http://localhost:8080/api/places/login/google/callback");


        AuthResponseDTO authResponseDTO = googleAuthRestService.getAuthCode(formData);

        GoogleTokenValidator.validToken(authResponseDTO.getIdToken());

        return usersRestService.loginWithGmail(authResponseDTO.getIdToken());

    }



}
