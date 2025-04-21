package com.example.me.DTOs;

import com.example.me.utils.enums.ErrorCode;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseDTO {
    private ErrorCode code;
    private String message;
}
