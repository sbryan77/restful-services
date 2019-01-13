/* ****************************************************************************
 * File:             $Source: /SoftwareDev/WildBlue/InternalWebService/Configuration/api/src/main/java/com/viasat/wildblue/internalwebservice/configuration/ConfigurationProxyMBean.java,v $
 * Revision:         $Revision: 1.1 $
 * Last Modified By: $Author: dxu $
 * Last Modified On: $Date: 2010/11/15 18:37:59 $
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
 * Configuration proxy MBean for reset configuration proxy cache
 */
public class ConfigurationProxyMBean
{
    /** The LOGGER */
    private static final Logger LOGGER = LoggerFactory.getLogger(
            ConfigurationProxyMBean.class);

    /**
     * Default constructor
     */
    public ConfigurationProxyMBean()
    {
    }

    /**
     * MBean operation. Reset configuration cache.
     *
     * @param  name  document name
     */
    public void reset(String documentName)
    {
        if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug("Reset configuration: " + documentName);
        }

        try
        {
            ConfigurationProxy configProxy = ConfigManager
                .getConfigurationProxy(documentName, null);
            configProxy.reset();
        }
        catch (Exception e)
        {
            LOGGER.error("Error reset " + documentName, e);
        }
    }

    /**
     * MBean operation. Reset all configuration caches.
     */
    public void resetAll()
    {
        if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug("ConfigurationProxyMBean resetAll(). ");
        }

        ConfigManager.resetAll();
    }
}
