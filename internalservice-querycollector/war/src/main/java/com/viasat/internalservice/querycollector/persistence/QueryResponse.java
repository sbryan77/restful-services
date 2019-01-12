package com.viasat.internalservice.querycollector.persistence;


import java.util.HashMap;
import java.util.Map;

public class QueryResponse {

    private String metricName;
    private double field;
    private Map<String, String> tags = new HashMap<>();
    public String getMetricName()
    {
        return metricName;
    }

    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }

    public double getField() {
        return field;
    }

    public void setField(double field) {
        this.field = field;
    }

    public Map<String, String> getTags() {
        return tags;
    }

    public void setTags(Map<String, String> tags) {
        this.tags = tags;
    }



}

