package com.sbryan.internalservice.fault.data;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "FaultDetail",
        propOrder = {"node", "timestamp", "trackingKey", "method", "contextPath", "message", "validationError", "faultDetail"}
)
@XmlRootElement(
        name = "FaultDetail"
)
public class FaultDetail {
    @XmlElement(
            name = "node"
    )
    private String node = null;
    @XmlElement(
            name = "timestamp"
    )
    private OffsetDateTime timestamp = null;
    @XmlElement(
            name = "trackingKey"
    )
    private String trackingKey = null;
    @XmlElement(
            name = "method"
    )
    private String method = null;
    @XmlElement(
            name = "contextPath"
    )
    private String contextPath = null;
    @XmlElement(
            name = "message"
    )
    private String message = null;
    @XmlElement(
            name = "validationError"
    )
    private List<ValidationError> validationError = new ArrayList();
    @XmlElement(
            name = "FaultDetail"
    )
    private FaultDetail faultDetail = null;

    public FaultDetail() {
    }

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

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ValidationError> getValidationError() {
        return this.validationError;
    }

    public void setValidationError(List<ValidationError> validationError) {
        this.validationError = validationError;
    }

    public FaultDetail getFaultDetail() {
        return this.faultDetail;
    }

    public void setFaultDetail(FaultDetail faultDetail) {
        this.faultDetail = faultDetail;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class FaultDetail {\n");
        sb.append("    node: ").append(toIndentedString(this.node)).append("\n");
        sb.append("    timestamp: ").append(toIndentedString(this.timestamp)).append("\n");
        sb.append("    trackingKey: ").append(toIndentedString(this.trackingKey)).append("\n");
        sb.append("    method: ").append(toIndentedString(this.method)).append("\n");
        sb.append("    contextPath: ").append(toIndentedString(this.contextPath)).append("\n");
        sb.append("    message: ").append(toIndentedString(this.message)).append("\n");
        sb.append("    validationError: ").append(toIndentedString(this.validationError)).append("\n");
        sb.append("    faultDetail: ").append(toIndentedString(this.faultDetail)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    private static String toIndentedString(Object o) {
        return o == null ? "null" : o.toString().replace("\n", "\n    ");
    }
}

