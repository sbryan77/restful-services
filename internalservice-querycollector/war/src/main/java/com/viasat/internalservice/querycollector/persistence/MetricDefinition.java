package com.viasat.internalservice.querycollector.persistence;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by sbryan on 12/1/2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "metricDefinition", propOrder = {
        "operation",
        "metricName",
        "field",
        "matchValue",
        "metricType"
})
public class MetricDefinition {
    @XmlElement(required = true)
    protected String operation;
    @XmlElement(required = true)
    protected String metricName;
    @XmlElement(required = true)
    protected String field;
    @XmlElement(required = true)
    protected String matchValue;
    @XmlElement(required = true)
    protected String metricType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MetricDefinition that = (MetricDefinition) o;

        if (operation != null ? !operation.equals(that.operation) : that.operation != null) return false;
        if (metricName != null ? !metricName.equals(that.metricName) : that.metricName != null) return false;
        if (field != null ? !field.equals(that.field) : that.field != null) return false;
        if (matchValue != null ? !matchValue.equals(that.matchValue) : that.matchValue != null) return false;
        return metricType != null ? metricType.equals(that.metricType) : that.metricType == null;
    }

    @Override
    public int hashCode() {
        int result = operation != null ? operation.hashCode() : 0;
        result = 31 * result + (metricName != null ? metricName.hashCode() : 0);
        result = 31 * result + (field != null ? field.hashCode() : 0);
        result = 31 * result + (matchValue != null ? matchValue.hashCode() : 0);
        result = 31 * result + (metricType != null ? metricType.hashCode() : 0);
        return result;
    }

    public String getMetricName() {

        return metricName;
    }

    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getMatchValue() {
        return matchValue;
    }

    public void setMatchValue(String matchValue) {
        this.matchValue = matchValue;
    }

    public String getMetricType() {
        return metricType;
    }

    public void setMetricType(String metricType) {
        this.metricType = metricType;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}
