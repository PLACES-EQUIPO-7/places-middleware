package com.example.me.utils.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ReceiverType {

    BUYER("BUYER"),
    COLLECT("COLLECT");

    private final String value;

    ReceiverType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
