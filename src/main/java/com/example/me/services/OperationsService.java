package com.example.me.services;

import com.example.me.DTOs.*;
import com.example.me.exceptions.DataNotFoundException;
import com.example.me.utils.enums.UserPlaceRole;
import com.example.me.utils.enums.UserRole;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OperationsService {

    private final UsersRestService usersRestService;

    private final PlacesRestService placesRestService;

    public OperationsService(UsersRestService usersRestService, PlacesRestService placesRestService) {
        this.usersRestService = usersRestService;
        this.placesRestService = placesRestService;
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
}
