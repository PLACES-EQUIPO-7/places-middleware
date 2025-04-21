package com.example.me.services;

import com.example.me.DTOs.UserDTO;
import com.example.me.DTOs.UserPLaceDTO;
import com.example.me.DTOs.place.CreatePLaceDTO;
import com.example.me.DTOs.place.PLaceDTO;
import com.example.me.exceptions.ApiException;
import com.example.me.utils.enums.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class PlacesRestService {

    private final RestClient placesRestClient;

    private static final String CREATE_PLACE_URL = "/api/places/create";

    private static final String GET_PLACES_BY_USER_URL = "/api/places/by/user/%s";

    public PlacesRestService(RestClient placesRestClient) {
        this.placesRestClient = placesRestClient;
    }

    public PLaceDTO createPlace(CreatePLaceDTO createPlaceDTO) {

        PLaceDTO userCreated;

        try {
            userCreated = placesRestClient.post()
                    .uri(CREATE_PLACE_URL)
                    .body(createPlaceDTO)
                    .retrieve()
                    .body(PLaceDTO.class);
        } catch (Exception e) {
            throw new ApiException("Unexpected error occurred while creating PLACE", ErrorCode.INTERNAL_ERROR);
        }


        return userCreated;
    }

    public List<UserPLaceDTO> getPlacesByUser(String userId) {

        List<UserPLaceDTO> userPlaces;

        try {
            userPlaces = placesRestClient.get()
                    .uri(String.format(GET_PLACES_BY_USER_URL, userId))
                    .retrieve()
                    .body(List.class);
        } catch (Exception e) {
            throw new ApiException("Unexpected error occurred while creating PLACE", ErrorCode.INTERNAL_ERROR);
        }

        return userPlaces;
    }
}
