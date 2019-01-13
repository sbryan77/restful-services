/* ****************************************************************************
 * File:             $Source: /SoftwareDev/WildBlue/InternalWebService/Configuration/war/src/main/java/com/viasat/wildblue/internalwebservice/configuration/AppConfig.java,v $
 * Revision:         $Revision: 1.10 $
 * Last Modified By: $Author: VIASAT\jkent $
 * Last Modified On: $Date: 2013/05/24 21:42:31 $
 *
 * ------------------------------------------------------------------
 *  Copyright (c) 2004-2010 WildBlue Communications. All rights reserved.
 * ------------------------------------------------------------------
 *
 * **************************************************************************** */


package com.viasat.wildblue.internalwebservice.configuration;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

import org.springframework.jmx.export.MBeanExporter;

import org.springframework.jndi.JndiObjectFactoryBean;

import java.util.HashMap;
import java.util.Map;

import javax.management.MBeanServer;

import javax.sql.DataSource;


/**
 * This class configures the Spring application context for the application.  It
 * takes the place of the applicationContext.xml file.  This class is
 * referenced, by the Spring AnnotationConfigWebApplicationContext, when the web
 * application starts.  It imports the CxfConfig class to configure the CXF
 * server.
 *
 * @author  Gary Fidler
 * @see     org.springframework.web.context.support.AnnotationConfigWebApplicationContext
 */
@Configuration
@Import(CxfConfig.class)
public class AppConfig
{
    //    private static final Logger LOGGER = LoggerFactory.getLogger(
    //            AppConfig.class);

    private static final String JNDI_DATA_SOURCE = "jdbc/ConfigDataSource";
    private static final String MONITOR_NAME =
        "com.viasat.wildblue:name=ConfigurationMonitor";

    @SuppressWarnings("unused")
    private static final String RUNTIME_MBEAN_SERVER =
        "java:comp/env/jmx/runtime";

    private static final String DOMAIN_RUNTIME_MBEAN_SERVER =
        "jndi/weblogic.management.mbeanservers.domainruntime";

    //    @Bean public ConfigCache getConfigCache()
    //    {
    //        ConfigCache configCache = new ConfigCache();
    //        configCache.setConfigDao(getConfigurationDAO());
    //        configCache.setConfigDocReader(getConfigurationDocumentReader());
    //
    //        return configCache;
    //    }

    @Bean public ConfigDaoAdapter getConfigurationDAO()
    {
        ConfigurationDAOImpl configDao = new ConfigurationDAOImpl();
        configDao.setDataSource(getDataSource());
        configDao.setDocumentReader(getConfigurationDocumentReader());

        return configDao;
    }

    @Bean public ConfigurationDocumentReader getConfigurationDocumentReader()
    {
        return new ConfigurationDocumentReaderImpl();
    }

    @Bean public ConfigurationMonitorImpl getConfigurationMonitor()
    {
        return new ConfigurationMonitorImpl(getConfigurationServiceProcessor());
    }

    @Bean(name = "ConfigurationService")
    public ConfigurationServiceV10 getConfigurationService()
    {
        ConfigurationServiceV10Impl configService =
            new ConfigurationServiceV10Impl();

        configService.setConfigServiceProcessor(
            getConfigurationServiceProcessor());

        return configService;
    }

    @Bean public ConfigurationServiceV10Processor getConfigurationServiceProcessor()
    {
        ConfigurationServiceV10Processor configServiceProcessor =
            new ConfigurationServiceV10Processor();

        configServiceProcessor.setConfigDaoAdapter(getConfigurationDAO());

        return configServiceProcessor;
    }

    @Bean public DataSource getDataSource()
    {
        JndiDataSourceLookup jndiLookup = new JndiDataSourceLookup();

        return jndiLookup.getDataSource(JNDI_DATA_SOURCE);
    }

    @Bean public MBeanExporter getMBeanExporter()
    {
        MBeanExporter exporter = new MBeanExporter();

        exporter.setAllowEagerInit(true);

        Map<String, Object> beans = new HashMap<String, Object>();
        beans.put(MONITOR_NAME, getConfigurationMonitor());
        exporter.setBeans(beans);

        exporter.setServer(getWebLogicMBeanServer());

        return exporter;
    }

    @Bean public MBeanServer getWebLogicMBeanServer()
    {
        JndiObjectFactoryBean factory = new JndiObjectFactoryBean();
        factory.setJndiName(DOMAIN_RUNTIME_MBEAN_SERVER);

        return (MBeanServer)factory.getObject();
    }
}
