package com.example.me.exceptions;

import com.example.me.utils.enums.ErrorCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ApiException extends RuntimeException {

    private HttpStatus httpStatus;

    private ErrorCode errorCode;

    public ApiException(String message, ErrorCode errorCode) {
        super(message);
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        this.errorCode = errorCode;
    }
}
