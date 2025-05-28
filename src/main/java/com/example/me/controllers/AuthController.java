package com.example.me.controllers;

import com.example.me.DTOs.users.LoginDTO;
import com.example.me.services.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/places")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginDTO loginDTO) {

        String token = authService.login(loginDTO);

        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION,token).build();
    }

    @GetMapping("/login/google/callback")
    public void googleAuthCallback(HttpServletResponse response, @RequestParam("code") String code) throws IOException {

        String token = authService.loginWIthGoogle(code);

        String redirect = String.format("http://ec2-3-128-221-93.us-east-2.compute.amazonaws.com/dashboard?token=%s", token);

        response.sendRedirect(redirect);
        
    }
}
