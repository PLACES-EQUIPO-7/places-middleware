package com.example.me.services;

import com.example.me.DTOs.*;
import com.example.me.DTOs.place.BillingDTO;
import com.example.me.DTOs.place.PlaceRelationshipUsersDTO;
import com.example.me.DTOs.place.PlaceUsersDTO;
import com.example.me.DTOs.place.ShipmentDTO;
import com.example.me.DTOs.users.ChangePasswordDTO;
import com.example.me.DTOs.users.UserIdsDTO;
import com.example.me.exceptions.DataNotFoundException;
import com.example.me.utils.enums.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class OperationsService {

    private final UsersRestService usersRestService;

    private final PlacesRestService placesRestService;

    public OperationsService(UsersRestService usersRestService, PlacesRestService placesRestService) {
        this.usersRestService = usersRestService;
        this.placesRestService = placesRestService;
    }

    public BillingDTO billing(Long placeId, Integer month, Integer year) {
        return placesRestService.billing(placeId, month, year);
    }

    public List<ShipmentDTO> getShipments(Long placeId, ShipmentType type, ShipmentStatus status) {
        return placesRestService.getShipments(placeId, type, status);
    }


    public void registerUserAndPLace(RegisterUserAndPLaceDTO register, String aggregatorUserId) {

        UserDTO user = getOrCreateUser(register.getUser());

        placesRestService.registerUser(user);


        PlaceDTO place = getOrCreatePlace(register.getPlace());


        //link user
        placesRestService.linkPlace(buildlinkUserPLaceDTO(user, register.getPlace(), register.getRole()));

        //link aggregator
        placesRestService.linkPlace(buildlinkUserPLaceDTO(
                UserDTO.builder().id(aggregatorUserId).build(),
                register.getPlace(),
                UserPlaceRole.PLACE_AGGREGATOR));


    }

    private PlaceDTO getOrCreatePlace(PlaceDTO placeDTO) {

        try {
           return placesRestService.getPlaceByDNI(placeDTO.getNit());
        } catch (DataNotFoundException e) {
            log.warn("Place not found " + placeDTO.getNit());
            return placesRestService.createPlace(placeDTO);
        }

    }

    private UserDTO getOrCreateUser(@NotNull(message = "user is required") CreateUserDTO userDTO) {

        userDTO.setRole(UserRole.STANDARD_USER);

        try {
            return usersRestService.getUserByDNI(userDTO.getDNI());
        } catch (DataNotFoundException e) {
            log.warn("User not found " + userDTO.getDNI());
            return usersRestService.createUser(userDTO);
        }

    }

    private LinkUserPLaceDto buildlinkUserPLaceDTO(UserDTO user, PlaceDTO place, UserPlaceRole role) {
        return LinkUserPLaceDto.builder()
                .placeNIT(place.getNit())
                .userId(user.getId())
                .role(role)
                .build();
    }


    public List<UserPLaceDTO> getPlacesByUser(String userId) {
        return placesRestService.getPlacesByUser(userId);
    }

    public void changePassword(@Valid ChangePasswordDTO changePasswordDTO) {

        usersRestService.changePassword(changePasswordDTO);
    }

    public PlaceRelationshipUsersDTO getUsersByPlace(Long placeId, String userId) {

        PlaceUsersDTO placeUsersDTO = placesRestService.getUsersByPlace(placeId);

        UserIdsDTO userIdsDTO = UserIdsDTO.builder()
                .userIds(placeUsersDTO.getUsers().stream().map(PlaceUsersDTO.UserRelationship::getUserId).toList())
                .build();

        List<UserDTO> users = usersRestService.getUsersByIds(userIdsDTO);

        return PlaceRelationshipUsersDTO.builder()
                .users(users.stream().map(e -> {
                    PlaceUsersDTO.UserRelationship urr = placeUsersDTO.getUsers().stream()
                            .filter(ur -> Objects.equals(e.getId(), ur.getUserId()))
                            .findFirst()
                            .orElseThrow(() -> new DataNotFoundException("User not found", ErrorCode.USER_NOT_FOUND));
                    e.setPlaceRole(urr.getRole());
                    e.setEnabled(urr.getIsEnabled());
                    return e;
                }).collect(java.util.stream.Collectors.toList()))
                .place(placeUsersDTO.getPlace())
                .build();
    }

    public UserDTO getUserById(String userId) {

        return usersRestService.getUser(userId);
    }
}
