package com.example.me.services;

import com.example.me.DTOs.CreateUserDTO;
import com.example.me.DTOs.UserDTO;
import com.example.me.DTOs.users.LoginDTO;
import com.example.me.exceptions.ApiException;
import com.example.me.exceptions.DataNotFoundException;
import com.example.me.exceptions.UnAuthorizedException;
import com.example.me.utils.enums.ErrorCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

@Service
@Validated
@RequiredArgsConstructor
public class UsersRestService {

    private final ObjectMapper objectMapper;

    private final RestClient usersRestClient;

    private static final String USERS_CREATE_URL = "/api/users/create";

    private static final String USERS_AUTH_URL = "/api/users/login";

    private static final String USERS_GET_URL = "/api/users?user_id=%s";

    private static final String USERS_GET_BY_DNI_URL = "/api/users/by/dni/%s";

    public UserDTO createUser(@Valid CreateUserDTO createUserDTO) {

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

    public UserDTO getUser(String userId) {

        UserDTO userCreated = null;

        try {
            userCreated = usersRestClient.get()
                    .uri(String.format(USERS_GET_URL, userId))
                    .retrieve()
                    .body(UserDTO.class);

        } catch (HttpClientErrorException e) {

            if (e.getStatusCode().value() == 404) {
                throw new DataNotFoundException("User not found dni:" + userId, ErrorCode.USER_NOT_FOUND);
            }

        } catch (Exception e) {
            throw new ApiException("Unexpected error occurred while creating user", ErrorCode.INTERNAL_ERROR);
        }


        return userCreated;
    }

    public UserDTO getUserByDNI(String dni) {

        UserDTO userCreated = null;

        try {
            userCreated = usersRestClient.get()
                    .uri(String.format(USERS_GET_BY_DNI_URL, dni))
                    .retrieve()
                    .body(UserDTO.class);
        } catch (HttpClientErrorException e) {

            if (e.getStatusCode().value() == 404) {
                throw new DataNotFoundException("User not found dni:" + dni, ErrorCode.USER_NOT_FOUND);
            }

        } catch (Exception e) {
            throw new ApiException("Unexpected error occurred while creating user", ErrorCode.INTERNAL_ERROR);
        }


        return userCreated;
    }

    public String login(LoginDTO loginDTO) {

        ResponseEntity<Void> response = null;


        try {
             response = usersRestClient.post()
                    .uri(USERS_AUTH_URL)
                    .body(loginDTO)
                    .retrieve()
                     .toEntity(Void.class);

        } catch (HttpClientErrorException e) {

            if (e.getStatusCode().value() == 401) {
                throw new UnAuthorizedException("Unauthorized: ", ErrorCode.UNAUTHORIZED);
            }

        } catch (Exception e) {
            throw new ApiException("Unexpected error occurred while login user", ErrorCode.INTERNAL_ERROR);
        }

        return response.getHeaders().getFirst("Authorization");

    }
}
