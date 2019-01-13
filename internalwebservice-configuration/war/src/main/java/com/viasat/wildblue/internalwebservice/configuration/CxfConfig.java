/* ****************************************************************************
 * File:             $Source: /SoftwareDev/WildBlue/InternalWebService/Configuration/war/src/main/java/com/viasat/wildblue/internalwebservice/configuration/CxfConfig.java,v $
 * Revision:         $Revision: 1.4 $
 * Last Modified By: $Author: gfidler $
 * Last Modified On: $Date: 2010/11/02 21:03:53 $
 *
 * ------------------------------------------------------------------
 *  Copyright (c) 2004-2010 WildBlue Communications. All rights reserved.
 * ------------------------------------------------------------------
 *
 * **************************************************************************** */


package com.viasat.wildblue.internalwebservice.configuration;

import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.EndpointImpl;


//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;


/**
 * This class participates in the configuration of the Spring application
 * context for the application.  It takes the place of the cxfContext.xml file.
 *
 * @author  Gary Fidler
 */
@Configuration
@ImportResource(
    {
        "classpath:META-INF/cxf/cxf.xml",
        "classpath:META-INF/cxf/cxf-extension-soap.xml",
        "classpath:META-INF/cxf/cxf-servlet.xml"
    }
)
public class CxfConfig
{
    //    private static final Logger LOGGER = LoggerFactory.getLogger(
    //            CxfConfig.class);

    private static final String ADDRESS = "/ConfigurationService";

    @Autowired(required = true)
    @Qualifier("ConfigurationService")
    ConfigurationServiceV10 configurationService;

    @Bean public EndpointImpl ConfigurationServiceEndpoint()
    {
        EndpointImpl ep;

        ep = (EndpointImpl)EndpointImpl.create(configurationService);

        ep.getInInterceptors().add(getLogInbound());
        ep.getOutInterceptors().add(getLogOutbound());
        ep.getOutFaultInterceptors().add(getLogOutbound());

        ep.publish(ADDRESS);

        return ep;
    }

    @Bean public Interceptor getLogInbound()
    {
        return new LoggingInInterceptor();
    }

    @Bean public Interceptor getLogOutbound()
    {
        return new LoggingOutInterceptor();
    }
}
