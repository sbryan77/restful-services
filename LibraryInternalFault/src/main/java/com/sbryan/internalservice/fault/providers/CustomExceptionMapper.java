package com.sbryan.internalservice.fault.providers;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.sbryan.internalservice.fault.InternalServiceFault;
import com.sbryan.internalservice.fault.NotFoundFault;
import com.sbryan.internalservice.fault.ValidationCode;
import com.sbryan.internalservice.fault.ValidationFault;
import com.sbryan.internalservice.fault.data.FaultDetail;
import com.sbryan.internalservice.fault.data.Input;
import com.sbryan.internalservice.fault.data.ValidationError;
import com.sbryan.internalservice.fault.utils.FaultUtil;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import org.apache.cxf.jaxrs.ext.MessageContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomExceptionMapper implements ExceptionMapper<Throwable> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomExceptionMapper.class);
    @Context
    MessageContext messageContext;
    private boolean logStackTraces = true;

    public CustomExceptionMapper() {
    }

    public void setMessageContext(MessageContext messageContext) {
        this.messageContext = messageContext;
    }

    public boolean isLogStackTraces() {
        return this.logStackTraces;
    }

    public void setLogStackTraces(boolean logStackTraces) {
        this.logStackTraces = logStackTraces;
    }

    public Response toResponse(Throwable t) {
        LOGGER.debug("Converting Throwable to Response");
        if (this.isLogStackTraces()) {
            LOGGER.warn("CustomExceptionMapper intercepted the following:", t);
        }

        FaultDetail resp;
        if (t instanceof InternalServiceFault) {
            InternalServiceFault f = (InternalServiceFault)t;
            if (f.getContextPath() == null) {
                f.setContextPath(FaultUtil.messageContextToContextPath(this.messageContext));
            }

            resp = FaultUtil.internalServiceFaultToFaultDetail(f);
            if (t instanceof ValidationFault) {
                return t instanceof NotFoundFault ? this.buildResponse(404, resp) : this.buildResponse(400, resp);
            } else {
                return this.buildResponse(500, resp);
            }
        } else {
            resp = new FaultDetail();
            resp.setNode(FaultUtil.getNodeFromInetAddress());
            resp.setTimestamp(OffsetDateTime.now());
            resp.setTrackingKey(FaultUtil.getTrackingKey());
            resp.setMethod(FaultUtil.getMethod(t));
            resp.setContextPath(FaultUtil.messageContextToContextPath(this.messageContext));
            resp.setMessage(t.getMessage());
            if (t instanceof JsonProcessingException) {
                resp.setMessage("Bad Request");
                List<ValidationError> validationErrorList = new ArrayList(1);
                resp.setValidationError(validationErrorList);
                ValidationError validationError = new ValidationError();
                validationErrorList.add(validationError);
                validationError.setValidationCode(ValidationCode.INVALID_FORMAT.getValue());
                List<Input> inputList = new ArrayList(1);
                validationError.setInput(inputList);
                Input input = new Input();
                inputList.add(input);
                if (t instanceof InvalidFormatException) {
                    InvalidFormatException e = (InvalidFormatException)t;
                    String fieldName = ((Reference)e.getPath().stream().findFirst().get()).getFieldName();
                    input.setField(fieldName);
                    input.setValue(e.getValue().toString());
                } else if (t instanceof JsonParseException) {
                    JsonParseException e = (JsonParseException)t;
                    JsonParser parser = e.getProcessor();
                    input.setField(parser.getParsingContext().getCurrentName());
                    String value = e.getMessage().replaceFirst("(?s)([^']*')([^']*)(.*)", "$2");
                    input.setValue(value);
                }

                return this.buildResponse(400, resp);
            } else {
                return this.buildResponse(500, resp);
            }
        }
    }

    private Response buildResponse(int status, Object entity) {
        return Response.status(status).type(MediaType.APPLICATION_JSON_TYPE).entity(entity).build();
    }
}
