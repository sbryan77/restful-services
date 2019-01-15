package com.sbryan.internalservice.fault;

import com.sbryan.internalservice.fault.data.FaultDetail;
import com.sbryan.internalservice.fault.utils.FaultUtil;
import java.time.OffsetDateTime;

public class InternalServiceFault extends Exception {
    String node;
    OffsetDateTime timestamp;
    String trackingKey;
    String method;
    String contextPath;
    String message;
    FaultDetail faultDetail;

    public String getNode() {
        return this.node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public OffsetDateTime getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(OffsetDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getTrackingKey() {
        return this.trackingKey;
    }

    public void setTrackingKey(String trackingKey) {
        this.trackingKey = trackingKey;
    }

    public String getMessage() {
        if (this.message != null) {
            return this.message;
        } else {
            return super.getMessage() != null ? super.getMessage() : "Internal Error";
        }
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMethod() {
        return this.method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getContextPath() {
        return this.contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public FaultDetail getFaultDetail() {
        return this.faultDetail;
    }

    public void setFaultDetail(FaultDetail faultDetail) {
        this.faultDetail = faultDetail;
    }

    public InternalServiceFault() {
        this.populateGenericFields();
    }

    public InternalServiceFault(String message) {
        super(message);
        this.populateGenericFields();
    }

    public InternalServiceFault(String message, Throwable cause) {
        super(message, cause);
        this.populateGenericFields();
        this.populateFaultDetail();
    }

    public InternalServiceFault(Throwable cause) {
        super("Internal Error", cause);
        this.populateGenericFields();
        this.populateFaultDetail();
    }

    private void populateGenericFields() {
        this.setNode(FaultUtil.getNodeFromInetAddress());
        this.setTimestamp(OffsetDateTime.now());
        this.setTrackingKey(FaultUtil.getTrackingKey());
        this.setMethod(FaultUtil.getMethod(this));
    }

    private void populateFaultDetail() {
        if (this.getCause() != null && this.getCause() instanceof InternalServiceFault) {
            InternalServiceFault subFault = (InternalServiceFault)this.getCause();
            FaultDetail fd = new FaultDetail();
            fd.setContextPath(subFault.getContextPath());
            fd.setMessage(subFault.getMessage());
            fd.setMethod(subFault.getMethod());
            fd.setNode(subFault.getNode());
            fd.setTimestamp(subFault.getTimestamp());
            fd.setTrackingKey(subFault.getTrackingKey());
            fd.setFaultDetail(subFault.getFaultDetail());
            if (subFault instanceof ValidationFault) {
                fd.setValidationError(((ValidationFault)subFault).getValidationErrorList());
            }

            this.setFaultDetail(fd);
        }

    }
}

