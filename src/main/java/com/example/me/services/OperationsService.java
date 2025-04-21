package com.example.me.services;

import com.example.me.DTOs.RegisterUserAndPLaceDTO;
import com.example.me.DTOs.UserDTO;
import com.example.me.DTOs.UserPLaceDTO;
import com.example.me.DTOs.place.CreatePLaceDTO;
import com.example.me.DTOs.place.PLaceDTO;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperationsService {

    private final UsersRestService usersRestService;

    private final PlacesRestService placesRestService;

    public OperationsService(UsersRestService usersRestService, PlacesRestService placesRestService) {
        this.usersRestService = usersRestService;
        this.placesRestService = placesRestService;
    }


    public void registerUserAndPLace(RegisterUserAndPLaceDTO register) {

        UserDTO userDTO = usersRestService.createUser(register.getUser());

        PLaceDTO pLaceDTO = placesRestService.createPlace(buildCreatePLaceDTO(register.getPlace(), userDTO));

    }

    private CreatePLaceDTO buildCreatePLaceDTO(@NotNull PLaceDTO place, UserDTO userDTO) {
        return CreatePLaceDTO.builder()
                .UserId(userDTO.getId())
                .name(place.getName())
                .address(place.getAddress())
                .latitude(place.getLatitude())
                .longitude(place.getLongitude())
                .zipCode(place.getZipCode())
                .build();
    }

    public List<UserPLaceDTO> getPlacesByUser(String userId) {
        return placesRestService.getPlacesByUser(userId);
    }
}
