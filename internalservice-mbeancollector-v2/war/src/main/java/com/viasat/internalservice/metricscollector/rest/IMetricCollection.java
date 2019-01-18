package com.viasat.internalservice.metricscollector.rest;

import javax.ws.rs.core.Response;

import com.viasat.internalservice.fault.InternalServiceFault;

/**
 * Created by sbryan on 12/2/2016.
 */
public interface IMetricCollection {
    public Response onAction(String action) throws InternalServiceFault;
    public void onCollect() throws InternalServiceFault;
    public void onConfigure() throws InternalServiceFault;
    public void onShutdown();
    public Response onTest();
}
