package com.example.me.DTOs;

import com.example.me.DTOs.place.CreatePLaceDTO;
import com.example.me.DTOs.place.PLaceDTO;
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

    @NotNull
    private CreateUserDTO user;

    @NotNull
    private PLaceDTO place;

}
