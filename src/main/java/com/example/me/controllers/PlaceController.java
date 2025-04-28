package com.example.me.controllers;

import com.example.me.DTOs.RegisterUserAndPLaceDTO;
import com.example.me.DTOs.UserPLaceDTO;
import com.example.me.config.JWTUtil;
import com.example.me.services.OperationsService;
import com.example.me.utils.enums.UserRole;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/places")
public class PlaceController {

    private final OperationsService operationsService;

    private final JWTUtil jwtUtil;

    public PlaceController(OperationsService operationsService, JWTUtil jwtUtil) {
        this.operationsService = operationsService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/my-places")
    public ResponseEntity<List<UserPLaceDTO>> getMyPlaces(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {

        String userId = jwtUtil.getUserFromToken(authorization);

        List<UserPLaceDTO> placeUsersDTOS = operationsService.getPlacesByUser(userId);

        return ResponseEntity.status(HttpStatus.OK).body(placeUsersDTOS);

    }

    @PostMapping("/register/user-place")
    public ResponseEntity<Void> createUser(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
            @RequestBody RegisterUserAndPLaceDTO register)
    {
        String userId = jwtUtil.getUserFromToken(authorization);

        String userRole = jwtUtil.getRoleFromToken(authorization);

        if (!UserRole.AGGREGATOR.getValue().equals(userRole)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        operationsService.registerUserAndPLace(register, userId);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
