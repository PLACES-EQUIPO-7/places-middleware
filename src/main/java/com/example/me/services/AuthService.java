package com.example.me.services;

import com.example.me.DTOs.users.LoginDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsersRestService usersRestService;

    public String login(LoginDTO login) {

        return usersRestService.login(login);

    }


}
