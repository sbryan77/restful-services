package com.sbryan.internalservice.fault;

public class NotFoundFault extends ValidationFault {
    public String getMessage() {
        return "Bad Request".equals(super.getMessage()) ? "Not Found" : super.getMessage();
    }

    public NotFoundFault() {
        super(ValidationCode.NOT_FOUND, (String)null, (String)null);
    }

    public NotFoundFault(String message, String field, String value) {
        super(message, ValidationCode.NOT_FOUND, field, value);
    }

    public NotFoundFault(String field, String value) {
        super(ValidationCode.NOT_FOUND, field, value);
    }
}
