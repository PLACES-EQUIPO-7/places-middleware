package com.example.me.DTOs;

import com.example.me.utils.enums.UserPlaceRole;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPLaceDTO {


    @NotNull
    @Valid
    private UserDTO user;

    @NotNull
    @Valid
    private PlaceDTO place;

    @NotNull
    private UserPlaceRole role;

    @JsonProperty("is_enabled")
    private boolean isEnabled;
}
