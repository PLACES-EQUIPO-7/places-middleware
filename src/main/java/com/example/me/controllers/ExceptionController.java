package com.example.me.controllers;

import com.example.me.DTOs.ErrorResponseDTO;
import com.example.me.exceptions.ApiException;
import com.example.me.exceptions.DataNotFoundException;
import com.example.me.exceptions.UnAuthorizedException;
import com.example.me.utils.enums.ErrorCode;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<ErrorResponseDTO> handleApiExceptionException(ApiException e) {

        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
        errorResponseDTO.setMessage(e.getMessage());
        errorResponseDTO.setCode(e.getErrorCode());

        return ResponseEntity.status(e.getHttpStatus()).body(errorResponseDTO);
    }

    @ExceptionHandler(value = DataNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleDataNotFoundException(DataNotFoundException e) {

        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
        errorResponseDTO.setMessage(e.getMessage());
        errorResponseDTO.setCode(e.getErrorCode());

        return ResponseEntity.status(e.getHttpStatus()).body(errorResponseDTO);
    }

    @ExceptionHandler(value = UnAuthorizedException.class)
    public ResponseEntity<ErrorResponseDTO> handleUnAuthorizedException(DataNotFoundException e) {

        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
        errorResponseDTO.setMessage(e.getMessage());
        errorResponseDTO.setCode(e.getErrorCode());

        return ResponseEntity.status(e.getHttpStatus()).body(errorResponseDTO);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationException(ConstraintViolationException e) {

        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
        errorResponseDTO.setMessage(e.getMessage());
        errorResponseDTO.setCode(ErrorCode.BAD_REQUEST);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDTO);
    }
}
