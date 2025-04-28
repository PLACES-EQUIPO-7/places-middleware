package com.example.me.DTOs;

import com.example.me.utils.enums.UserPlaceRole;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LinkUserPLaceDto {


    @NotBlank(message = "user_id is required")
    private String userId;

    @NotBlank(message = "place_nit is required")
    @JsonProperty("place_nit")
    private String placeNIT;

    @NotNull(message = "role is required")
    private UserPlaceRole role;
}


