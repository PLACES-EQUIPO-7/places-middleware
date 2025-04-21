package com.example.me.DTOs.place;

import com.example.me.DTOs.CreateUserDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePLaceDTO extends PLaceDTO {

    private String UserId;

}
