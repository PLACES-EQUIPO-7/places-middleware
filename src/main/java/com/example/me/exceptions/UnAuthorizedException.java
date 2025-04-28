package com.example.me.exceptions;

import com.example.me.utils.enums.ErrorCode;
import org.springframework.http.HttpStatus;

public class UnAuthorizedException extends ApiException {

    public UnAuthorizedException(String message, ErrorCode errorCode) {
        super(message, errorCode);
        this.setHttpStatus(HttpStatus.UNAUTHORIZED);
    }
}
