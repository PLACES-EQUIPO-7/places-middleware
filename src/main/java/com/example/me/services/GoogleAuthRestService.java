package com.example.me.services;

import com.example.me.DTOs.google.auth.AuthFormDTO;
import com.example.me.DTOs.google.auth.AuthResponseDTO;
import com.example.me.exceptions.ApiException;
import com.example.me.utils.enums.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestClient;

import java.awt.*;

@Service
@Validated
@RequiredArgsConstructor
public class GoogleAuthRestService {

    private final RestClient googleRestClient;

    private static final String TOKEN_URL = "/token";


    public AuthResponseDTO getAuthCode(MultiValueMap<String, String> formData) {

        AuthResponseDTO authResponseDTO;

        try {

            authResponseDTO = googleRestClient.post()
                    .uri(TOKEN_URL)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(formData)
                    .retrieve()
                    .body(AuthResponseDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException("Unexpected error occurred while creating user", ErrorCode.INTERNAL_ERROR);
        }


        return authResponseDTO;
    }

}
