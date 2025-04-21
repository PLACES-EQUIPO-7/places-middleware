package com.example.me.DTOs.place;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PLaceDTO {

    @NotNull
    private String name;
    @NotNull
    private String address;
    @NotNull
    private String zipCode;
    @NotNull
    private Double latitude;
    @NotNull
    private Double longitude;

}
