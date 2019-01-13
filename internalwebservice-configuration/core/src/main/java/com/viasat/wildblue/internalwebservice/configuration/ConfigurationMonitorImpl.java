/* ****************************************************************************
 * File:             $Source: /SoftwareDev/WildBlue/InternalWebService/Configuration/core/src/main/java/com/viasat/wildblue/internalwebservice/configuration/ConfigurationMonitorImpl.java,v $
 * Revision:         $Revision: 1.6 $
 * Last Modified By: $Author: gfidler $
 * Last Modified On: $Date: 2010/11/02 14:52:55 $
 *
 * ------------------------------------------------------------------
 *  Copyright (c) 2004-2010 WildBlue Communications. All rights reserved.
 * ------------------------------------------------------------------
 *
 * **************************************************************************** */


package com.viasat.wildblue.internalwebservice.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * MBean used to monitor the Configuration service.
 */
public class ConfigurationMonitorImpl implements ConfigurationMonitor
{
    /** Logger * */
    private static final Logger LOGGER = LoggerFactory.getLogger(
            ConfigurationMonitorImpl.class);

    /** Injected Configuration service reference * */
    private ConfigurationServiceV10Processor m_configServiceProcessor;

    /**
     * Constructor
     *
     * @param  configServiceProcessor  Configuration service to monitor
     */
    public ConfigurationMonitorImpl(
        final ConfigurationServiceV10Processor configServiceProcessor)
    {
        m_configServiceProcessor = configServiceProcessor;
    }

    @Override public void reset()
    {
        LOGGER.trace("reset()");

        m_configServiceProcessor.reset();
    }
}
