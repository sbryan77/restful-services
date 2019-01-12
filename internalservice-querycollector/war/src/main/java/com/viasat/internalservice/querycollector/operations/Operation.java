package com.viasat.internalservice.querycollector.operations;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sbryan on 2/22/2017.
 */
public class Operation {
    private String name;
    private String versionInfo;
    private Map<String,Double> metrics = new HashMap<>();

    public Operation(String name, String versionInfo){
        this.name = name;
        this.versionInfo = versionInfo;
    }
    public void addMetric(String metricName, Double metricValue)
    {
        metrics.put(metricName,metricValue);
    }
    public String getVersionInfo()
    {
        return versionInfo;
    }
    public Double getMetric(String metricName)
    {

        Double value = Double.valueOf(0);
        for (Map.Entry<String, Double> entry : metrics.entrySet()) {
            String mapMetricName = entry.getKey();
            if (mapMetricName.equals(metricName)) {
                value = entry.getValue();
                break;
            }
        }
        return value;
    }
}
