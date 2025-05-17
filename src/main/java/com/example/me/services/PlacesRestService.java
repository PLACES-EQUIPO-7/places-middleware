package com.example.me.services;

import com.example.me.DTOs.LinkUserPLaceDto;
import com.example.me.DTOs.PlaceDTO;
import com.example.me.DTOs.UserDTO;
import com.example.me.DTOs.UserPLaceDTO;
import com.example.me.DTOs.place.BillingDTO;
import com.example.me.DTOs.place.PlaceUsersDTO;
import com.example.me.DTOs.place.ShipmentDTO;
import com.example.me.exceptions.ApiException;
import com.example.me.exceptions.DataNotFoundException;
import com.example.me.utils.enums.ErrorCode;
import com.example.me.utils.enums.ShipmentStatus;
import com.example.me.utils.enums.ShipmentType;
import jakarta.validation.Valid;
import org.springframework.core.ParameterizedTypeReference;
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

    private static final String GET_PLACE_BILLING_URL = "/api/places/billing?place_id=%s&month=%s&year=%s";

    private static final String GET_PLACE_SHIPMENTS_URL = "/api/places/shipments?place_id=%s";

    private static final String GET_USERS_BY_PLACE_URL = "/api/places/place/users?place_id=%s";

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

    public BillingDTO billing(Long placeId, Integer month, Integer year) {

        BillingDTO billingDTO = null;

        try {
            billingDTO = placesRestClient.get()
                    .uri(String.format(GET_PLACE_BILLING_URL, placeId, month, year))
                    .retrieve()
                    .body(BillingDTO.class);
        } catch (HttpClientErrorException e) {

            if (e.getStatusCode().value() == 404) {
                throw new DataNotFoundException("Billing not found:", ErrorCode.BAD_REQUEST);
            }

        } catch (Exception e) {
            throw new ApiException("Unexpected error occurred while getting billing", ErrorCode.INTERNAL_ERROR);
        }

        return billingDTO;
    }

    public List<ShipmentDTO> getShipments(Long placeId, ShipmentType type, ShipmentStatus status) {

        List<ShipmentDTO> shipments = null;
        String url = String.format(GET_PLACE_SHIPMENTS_URL, placeId);
        if (type != null) {
            url = url + "&type=" + type.getValue();
        }
        if (status != null) {
            url = url + "&status=" + status.getValue();
        }

        try {
            shipments = placesRestClient.get()
                    .uri(url)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});
        } catch (HttpClientErrorException e) {

            if (e.getStatusCode().value() == 404) {
                throw new DataNotFoundException("shipments not found:", ErrorCode.BAD_REQUEST);
            }

        } catch (Exception e) {
            throw new ApiException("Unexpected error occurred while getting shipments", ErrorCode.INTERNAL_ERROR);
        }

        return shipments;
    }

    public PlaceUsersDTO getUsersByPlace(Long placeId) {

        PlaceUsersDTO placeUsersDTO = null;
        try {
            placeUsersDTO = placesRestClient.get()
                    .uri(String.format(GET_USERS_BY_PLACE_URL, placeId))
                    .retrieve()
                    .body(PlaceUsersDTO.class);
        } catch (HttpClientErrorException e) {

            if (e.getStatusCode().value() == 404) {
                throw new DataNotFoundException("shipments not found:", ErrorCode.BAD_REQUEST);
            }

        } catch (Exception e) {
            throw new ApiException("Unexpected error occurred while getting shipments", ErrorCode.INTERNAL_ERROR);
        }

        return placeUsersDTO;

    }
}
