package com.example.me.DTOs;

import com.example.me.utils.enums.UserPlaceRole;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserAndPLaceDTO  {

    @NotNull(message = "user is required")
    private CreateUserDTO user;

    @NotNull(message = "place is required")
    private PlaceDTO place;

    @NotNull(message = "role is required")
    private UserPlaceRole role;

}
