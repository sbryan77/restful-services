package com.sbryan.internalservice.fault;

import com.sbryan.internalservice.fault.data.Input;
import com.sbryan.internalservice.fault.data.ValidationError;
import java.util.ArrayList;
import java.util.List;

public class ValidationFault extends InternalServiceFault {
    private List<ValidationError> validationErrorList;

    public String getMessage() {
        return "Internal Error".equals(super.getMessage()) ? "Bad Request" : super.getMessage();
    }

    public List<ValidationError> getValidationErrorList() {
        if (this.validationErrorList == null) {
            this.validationErrorList = new ArrayList(1);
        }

        return this.validationErrorList;
    }

    public void setValidationErrorList(List<ValidationError> validationErrorList) {
        this.validationErrorList = validationErrorList;
    }

    public ValidationFault() {
    }

    public ValidationFault(ValidationCode validationCode, String field, String value) {
        this.addValidationError(validationCode, field, value);
    }

    public ValidationFault(ValidationError validationError) {
        this.getValidationErrorList().add(validationError);
    }

    public ValidationFault(List<ValidationError> validationErrorList) {
        this.validationErrorList = validationErrorList;
    }

    public ValidationFault(String message, ValidationCode validationCode, String field, String value) {
        super(message);
        this.addValidationError(validationCode, field, value);
    }

    public ValidationFault(String message, ValidationError validationError) {
        super(message);
        this.getValidationErrorList().add(validationError);
    }

    public ValidationFault(String message, List<ValidationError> validationErrorList) {
        super(message);
        this.validationErrorList = validationErrorList;
    }

    public ValidationFault(String message, Throwable cause, ValidationCode validationCode, String field, String value) {
        super(message, cause);
        this.addValidationError(validationCode, field, value);
    }

    public ValidationFault(String message, Throwable cause, ValidationError validationError) {
        super(message, cause);
        this.getValidationErrorList().add(validationError);
    }

    public ValidationFault(String message, Throwable cause, List<ValidationError> validationErrorList) {
        super(message, cause);
        this.validationErrorList = validationErrorList;
    }

    public ValidationFault(Throwable cause, ValidationCode validationCode, String field, String value) {
        super(cause);
        this.addValidationError(validationCode, field, value);
    }

    public ValidationFault(Throwable cause, ValidationError validationError) {
        super(cause);
        this.getValidationErrorList().add(validationError);
    }

    public ValidationFault(Throwable cause, List<ValidationError> validationErrorList) {
        super(cause);
        this.validationErrorList = validationErrorList;
    }

    private void addValidationError(ValidationCode validationCode, String field, String value) {
        ValidationError ve = new ValidationError();
        ve.setValidationCode(validationCode.getValue());
        if (field != null || value != null) {
            Input i = new Input();
            ve.getInput().add(i);
            i.setField(field);
            i.setValue(value);
        }

        this.getValidationErrorList().add(ve);
    }
}
