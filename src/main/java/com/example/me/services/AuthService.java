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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final GoogleAuthRestService googleAuthRestService;

    private final UsersRestService usersRestService;

    @Value( "${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;

    @Value( "${spring.security.oauth2.client.registration.google.client-secret}")
    private String googleClientSecret;

    @Value("${spring.application.host}")
    private String host;



    private final ObjectMapper objectMapper;

    public String login(LoginDTO login) {

        return usersRestService.login(login);

    }

    public String loginWIthGoogle(String code) {

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("code", code);
        formData.add("client_id", googleClientId);
        formData.add("client_secret", googleClientSecret);
        formData.add("grant_type", "authorization_code");
        formData.add("redirect_uri", String.format("http://%s/api/places/login/google/callback", host));


        AuthResponseDTO authResponseDTO = googleAuthRestService.getAuthCode(formData);

        GoogleTokenValidator.validToken(authResponseDTO.getIdToken());

        return usersRestService.loginWithGmail(authResponseDTO.getIdToken());

    }



}
