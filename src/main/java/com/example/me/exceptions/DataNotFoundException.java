package com.example.me.exceptions;

import com.example.me.utils.enums.ErrorCode;
import org.springframework.http.HttpStatus;

public class DataNotFoundException extends ApiException {

    public DataNotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
        this.setHttpStatus(HttpStatus.NOT_FOUND);
    }
}
