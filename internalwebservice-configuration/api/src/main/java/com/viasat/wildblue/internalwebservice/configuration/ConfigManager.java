/* ****************************************************************************
 * File:             $Source: /SoftwareDev/WildBlue/InternalWebService/Configuration/api/src/main/java/com/viasat/wildblue/internalwebservice/configuration/ConfigManager.java,v $
 * Revision:         $Revision: 1.3 $
 * Last Modified By: $Author: wbaddc1\dxu $
 * Last Modified On: $Date: 2011/02/11 18:52:03 $
 *
 * ------------------------------------------------------------------
 *  Copyright (c) 2004-2011 WildBlue Communications. All rights reserved.
 * ------------------------------------------------------------------
 *
 * **************************************************************************** */


package com.viasat.wildblue.internalwebservice.configuration;

import com.viasat.wildblue.common.header.WildBlueHeader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


/**
 * This class manages all configuration service proxies. Use this to get a
 * configuration proxy
 */
public class ConfigManager
{
    /** The LOGGER */
    private static final Logger LOGGER = LoggerFactory.getLogger(
            ConfigManager.class);

    /** Registry to keep track of all configuration proxies. */
    private static Map<String, ConfigurationProxy> config_registry =
        new HashMap<String, ConfigurationProxy>();

    private static ConfigDaoAdapter m_configDaoAdapter;

    /**
     * Do not create instance object.
     */
    private ConfigManager()
    {
    }

    /**
     * Get a configuration proxy
     *
     * @param   documentName    document name
     * @param   wildBlueHeader  WildBlueHeader
     *
     * @return  A configuration proxy
     */
    public static synchronized ConfigurationProxy getConfigurationProxy(
        String documentName, WildBlueHeader wildBlueHeader)
    {
        if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug("Getting Configuration Proxy: " + documentName);
        }

        if (config_registry.get(documentName) == null)
        {
            if (m_configDaoAdapter == null)
            {
                m_configDaoAdapter = new ConfigProxyDaoAdapter(wildBlueHeader);
            }

            ConfigurationProxy instance = new ConfigurationProxy(documentName,
                    wildBlueHeader, m_configDaoAdapter);
            config_registry.put(documentName, instance);
        }

        ConfigurationProxy proxy = config_registry.get(documentName);

        if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug("Configuration Proxy instance is: " + proxy);
        }

        return proxy;
    }

    /**
     * Reset all the configuration cache.
     */
    public static synchronized void resetAll()
    {
        if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug("ConfigManager resetAll(). ");
        }

        Collection<ConfigurationProxy> proxies = config_registry.values();

        for (ConfigurationProxy proxy : proxies)
        {
            proxy.reset();
        }
    }

    public static void setConfigDaoAdapter(ConfigDaoAdapter configDaoAdapter)
    {
        ConfigManager.m_configDaoAdapter = configDaoAdapter;
    }
}
