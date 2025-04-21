package com.example.me.services;

import com.example.me.DTOs.CreateUserDTO;
import com.example.me.DTOs.UserDTO;
import com.example.me.DTOs.users.LoginDTO;
import com.example.me.exceptions.ApiException;
import com.example.me.utils.enums.ErrorCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class UsersRestService {

    private final ObjectMapper objectMapper;

    private final RestClient usersRestClient;

    private static final String USERS_CREATE_URL = "/api/users/create";

    private static final String USERS_AUTH_URL = "/api/users/login";

    public UserDTO createUser(CreateUserDTO createUserDTO) {

        UserDTO userCreated;

        try {
            userCreated = usersRestClient.post()
                    .uri(USERS_CREATE_URL)
                    .body(createUserDTO)
                    .retrieve()
                    .body(UserDTO.class);
        } catch (Exception e) {
            throw new ApiException("Unexpected error occurred while creating user", ErrorCode.INTERNAL_ERROR);
        }


        return userCreated;
    }

    public String login(LoginDTO loginDTO) {

        ResponseEntity<Void> response;


        try {
             response = usersRestClient.post()
                    .uri(USERS_AUTH_URL)
                    .body(loginDTO)
                    .retrieve()
                     .toEntity(Void.class);

        } catch (Exception e) {
            throw new ApiException("Unexpected error occurred while login user", ErrorCode.INTERNAL_ERROR);
        }

        return response.getHeaders().getFirst("Authorization");

    }
}
