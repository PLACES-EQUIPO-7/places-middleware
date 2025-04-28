package com.example.me.services;

import com.example.me.DTOs.LinkUserPLaceDto;
import com.example.me.DTOs.PlaceDTO;
import com.example.me.DTOs.UserDTO;
import com.example.me.DTOs.UserPLaceDTO;
import com.example.me.exceptions.ApiException;
import com.example.me.exceptions.DataNotFoundException;
import com.example.me.utils.enums.ErrorCode;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@Validated
public class PlacesRestService {

    private final RestClient placesRestClient;

    private static final String CREATE_PLACE_URL = "/api/places/create";

    private static final String GET_PLACES_BY_USER_URL = "/api/places/by/user/%s";

    private static final String GET_PLACE_BY_DNI_URL = "/api/places/by/dni/%s";

    private static final String POST_REGISTER_USER = "/api/places/register/user";

    private static final String POST_LINK_USER_PLACE_URL = "/api/places/link/user_place";

    public PlacesRestService(RestClient placesRestClient) {
        this.placesRestClient = placesRestClient;
    }

    public PlaceDTO createPlace(@Valid PlaceDTO placeDTO) {

        PlaceDTO userCreated;

        try {
            userCreated = placesRestClient.post()
                    .uri(CREATE_PLACE_URL)
                    .body(placeDTO)
                    .retrieve()
                    .body(PlaceDTO.class);
        } catch (Exception e) {
            throw new ApiException("Unexpected error occurred while creating PLACE", ErrorCode.INTERNAL_ERROR);
        }


        return userCreated;
    }

    public void registerUser(@Valid UserDTO userDTO) {

        try {
            placesRestClient.post()
                    .uri(POST_REGISTER_USER)
                    .body(userDTO)
                    .retrieve()
                    .body(Void.class);

        } catch (Exception e) {
            throw new ApiException("Unexpected error occurred while creating user en palces", ErrorCode.INTERNAL_ERROR);
        }

    }

    public void linkPlace(@Valid LinkUserPLaceDto linkUserPLaceDto) {

        try {
            placesRestClient.post()
                    .uri(POST_LINK_USER_PLACE_URL)
                    .body(linkUserPLaceDto)
                    .retrieve()
                    .body(Void.class);

        } catch (Exception e) {
            throw new ApiException("Unexpected error occurred while creating PLACE", ErrorCode.INTERNAL_ERROR);
        }

    }

    public List<UserPLaceDTO> getPlacesByUser(String userId) {

        List<UserPLaceDTO> userPlaces;

        try {
            userPlaces = placesRestClient.get()
                    .uri(String.format(GET_PLACES_BY_USER_URL, userId))
                    .retrieve()
                    .body(List.class);
        } catch (Exception e) {
            throw new ApiException("Unexpected error occurred while getting PLACES", ErrorCode.INTERNAL_ERROR);
        }

        return userPlaces;
    }

    public PlaceDTO getPlaceByDNI(String dni) {

        PlaceDTO place = null;

        try {
            place = placesRestClient.get()
                    .uri(String.format(GET_PLACE_BY_DNI_URL, dni))
                    .retrieve()
                    .body(PlaceDTO.class);
        } catch (HttpClientErrorException e) {

            if (e.getStatusCode().value() == 404) {
                throw new DataNotFoundException("Place not found dni:" + dni, ErrorCode.USER_NOT_FOUND);
            }

        } catch (Exception e) {
            throw new ApiException("Unexpected error occurred while getting PLACES", ErrorCode.INTERNAL_ERROR);
        }

        return place;
    }

}
