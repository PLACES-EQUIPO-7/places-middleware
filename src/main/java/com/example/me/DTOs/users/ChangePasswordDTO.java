package com.example.me.DTOs.users;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChangePasswordDTO {


    private String userId;

    @NotBlank
    private String oldPassword;

    @NotBlank
    private String newPassword;
}
