package com.sbryan.internalservice.fault;

import com.sbryan.internalservice.fault.data.Input;
import com.sbryan.internalservice.fault.data.ValidationError;
import java.util.Arrays;
import java.util.List;

public class DeletedItemValidationFault extends ValidationFault {
    public String getMessage() {
        return "Bad Request".equals(super.getMessage()) ? "Target Previously Deleted" : super.getMessage();
    }

    public DeletedItemValidationFault() {
        super(ValidationCode.INVALID_STATE, (String)null, (String)null);
    }

    public DeletedItemValidationFault(String field, String value) {
        super(ValidationCode.INVALID_STATE, field, value);
    }

    public DeletedItemValidationFault(List<Input> inputs) {
        ValidationError validationError = new ValidationError();
        validationError.setValidationCode(ValidationCode.INVALID_STATE.getValue());
        validationError.getInput().addAll(inputs);
        this.setValidationErrorList(Arrays.asList(validationError));
    }

    public DeletedItemValidationFault(String message, String field, String value) {
        super(message, ValidationCode.INVALID_STATE, field, value);
    }

    public DeletedItemValidationFault(String message, List<Input> inputs) {
        this.setMessage(message);
        ValidationError validationError = new ValidationError();
        validationError.setValidationCode(ValidationCode.INVALID_STATE.getValue());
        validationError.getInput().addAll(inputs);
        this.setValidationErrorList(Arrays.asList(validationError));
    }
}
