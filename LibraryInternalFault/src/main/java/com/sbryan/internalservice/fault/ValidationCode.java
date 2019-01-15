package com.sbryan.internalservice.fault;

public enum ValidationCode {
    DUPLICATE("DUPLICATE"),
    FORBIDDEN("FORBIDDEN"),
    TRANSACTION_ERROR("TRANSACTION_ERROR"),
    INVALID_COMBINATION("INVALID_COMBINATION"),
    INVALID_FORMAT("INVALID_FORMAT"),
    INVALID_LIST_SIZE("INVALID_LIST_SIZE"),
    INVALID_LENGTH("INVALID_LENGTH"),
    INVALID_VALUE("INVALID_VALUE"),
    INVALID_STATE("INVALID_STATE"),
    OUT_OF_RANGE("OUT_OF_RANGE"),
    REQUIRED("REQUIRED"),
    REQUIRED_ONE_OF_MANY("INVALID_COMBINATION"),
    MIN_LENGTH_INSUFFICIENT("INVALID_LENGTH"),
    AUTHENTICATION_FAILED("AUTHENTICATION_FAILED"),
    BEAM_CONFLICT("BEAM_CONFLICT"),
    NOT_FOUND("NOT_FOUND");

    private String value;

    public String getValue() {
        return this.value;
    }

    private ValidationCode(String value) {
        this.value = value;
    }
}

