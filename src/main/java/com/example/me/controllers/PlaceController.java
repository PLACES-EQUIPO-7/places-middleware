package com.example.me.controllers;

import com.example.me.DTOs.RegisterUserAndPLaceDTO;
import com.example.me.DTOs.UserDTO;
import com.example.me.DTOs.UserPLaceDTO;
import com.example.me.DTOs.place.BillingDTO;
import com.example.me.DTOs.place.PlaceRelationshipUsersDTO;
import com.example.me.DTOs.place.PlaceUsersDTO;
import com.example.me.DTOs.place.ShipmentDTO;
import com.example.me.DTOs.users.ChangePasswordDTO;
import com.example.me.config.JWTUtil;
import com.example.me.services.OperationsService;
import com.example.me.utils.enums.ShipmentStatus;
import com.example.me.utils.enums.ShipmentType;
import com.example.me.utils.enums.UserRole;
import jakarta.validation.Valid;
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

    @GetMapping("/shipments")
    public ResponseEntity<List<ShipmentDTO>> getShipments (
            @RequestParam(value = "place_id") Long placeId,
            @RequestParam(value = "type", required = false) ShipmentType type,
            @RequestParam(value = "status", required = false) ShipmentStatus status
    )
    {
        List<ShipmentDTO> shipments = operationsService.getShipments(placeId, type, status);

        return ResponseEntity.status(HttpStatus.OK).body(shipments);
    }

    @GetMapping("/billing")
    public ResponseEntity<BillingDTO> billing (
            @RequestParam(value = "place_id") Long placeId,
            @RequestParam(value = "month") Integer month,
            @RequestParam(value = "year") Integer year
    )
    {
        BillingDTO billing = operationsService.billing(placeId, month, year);

        return ResponseEntity.status(HttpStatus.OK).body(billing);
    }

    @PostMapping("/change/password")
    public ResponseEntity<Void> post(@Valid @RequestBody ChangePasswordDTO changePasswordDTO,
                                     @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {

        String userId = jwtUtil.getUserFromToken(authorization);
        changePasswordDTO.setUserId(userId);
        operationsService.changePassword(changePasswordDTO);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @GetMapping(value = "/get/users/by/place")
    public ResponseEntity<PlaceRelationshipUsersDTO> getUsersByPlace(
            @RequestParam(value = "place_id") Long placeId,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {

        String userId = jwtUtil.getUserFromToken(authorization);
        PlaceRelationshipUsersDTO usersRelations = operationsService.getUsersByPlace(placeId, userId);

        return ResponseEntity.status(HttpStatus.OK).body(usersRelations);
    }

    @GetMapping("/user/details")
    public ResponseEntity<UserDTO> getUserById(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        String userId = jwtUtil.getUserFromToken(authorization);
        UserDTO user = operationsService.getUserById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(user);

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

    @GetMapping("/start")
    public ResponseEntity<ShipmentDTO> start(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
            @RequestParam(value = "target_id") String targetId) {

        String userId = jwtUtil.getUserFromToken(authorization);

        ShipmentDTO shipmentDTO = operationsService.start(targetId, userId);

        return ResponseEntity.ok(shipmentDTO);
    }

    @PostMapping("/process")
    public ResponseEntity<Void> process(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
            @Valid @RequestBody ShipmentDTO shipmentDTO) {

        String userId = jwtUtil.getUserFromToken(authorization);

        operationsService.process(shipmentDTO, userId);

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
