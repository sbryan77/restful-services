/* ****************************************************************************
 * File:             $Source: /SoftwareDev/WildBlue/InternalWebService/Configuration/core/src/main/java/com/viasat/wildblue/internalwebservice/configuration/ConfigurationMonitor.java,v $
 * Revision:         $Revision: 1.4 $
 * Last Modified By: $Author: gfidler $
 * Last Modified On: $Date: 2010/11/02 14:52:55 $
 *
 * ------------------------------------------------------------------
 *  Copyright (c) 2004-2010 WildBlue Communications. All rights reserved.
 * ------------------------------------------------------------------
 *
 * **************************************************************************** */


package com.viasat.wildblue.internalwebservice.configuration;

/**
 * Interface for Monitor MBean.
 */
public interface ConfigurationMonitor
{
    /**
     * Instructs the Configuration Service to reset its cache.
     */
    void reset();
}
