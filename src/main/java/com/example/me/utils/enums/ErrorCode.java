package com.example.me.utils.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.http.HttpStatus;

public enum ErrorCode {
    INTERNAL_ERROR("INTERNAL_ERROR"),
    UNAUTHORIZED("UNAUTHORIZED"),
    BAD_REQUEST("BAD_REQUEST"),
    USER_NOT_FOUND("USER_NOT_FOUND"),
    PLACE_NO_FOUND("PLACE_NOT_FOUND"),
    SHIPMENT_NOT_FOUND("SHIPMENT_NOT_FOUND");

     ErrorCode(String value) {
        this.value = value;
    }

    private final String value;

    @JsonValue
    public String getValue() {
        return value;
    }








}
