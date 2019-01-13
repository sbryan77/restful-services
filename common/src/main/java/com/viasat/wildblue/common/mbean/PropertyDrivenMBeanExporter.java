package com.viasat.wildblue.common.mbean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.jmx.export.MBeanExporter;

import org.springframework.jndi.JndiObjectFactoryBean;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.management.MBeanServer;


/**
 * The Class PropertyDrivenMBeanExporter. Allows for version specific names of
 * MBeans driven by properties generated from the maven build (or user
 * specified). Where multiple versions of services may exist and be deployed and
 * the same WebLogic instances, mbeans must be exported/published using
 * different names. This class provides a utility where this name is read from
 * the maven project version, and requires no code modifications as source is
 * enhanced to create new versions. See example usage of this class in services:
 * PublicWebService/Finance and Facade/Billing. (do class usage search, and look
 * at the spring configuration XML files).
 */
public class PropertyDrivenMBeanExporter extends MBeanExporter
{
    private static final Logger LOGGER = LoggerFactory.getLogger(
            PropertyDrivenMBeanExporter.class);

    private static final String DEFAULT_RESOURCE_BUNDLE_NAME = "mbeanResources";
    private static final String DEFAULT_DOMAIN_RUNTIME_MBEAN_SERVER =
        "jndi/weblogic.management.mbeanservers.domainruntime";

    private String defaultMonitorName;
    private String domainRuntimeMbeanServer;
    private String monitorName;
    private String resourceBundleName;

    /**
     * Instantiates a new property driven m bean exporter.
     *
     * @param  mbean  the mbean
     */
    public PropertyDrivenMBeanExporter(Object mbean)
    {
        init(mbean, DEFAULT_RESOURCE_BUNDLE_NAME);
    }

    /**
     * Instantiates a new property driven m bean exporter.
     *
     * @param  mbean               the mbean
     * @param  resourceBundleName  the resource bundle name
     */
    public PropertyDrivenMBeanExporter(Object mbean, String resourceBundleName)
    {
        init(mbean, resourceBundleName);
    }

    /**
     * Inits the MBeanExporter.
     *
     * @param  mbean               the mbean
     * @param  resourceBundleName  the resource bundle name
     */
    public void init(Object mbean, String resourceBundleName)
    {
        this.resourceBundleName = resourceBundleName;
        this.defaultMonitorName = mbean.getClass().getSimpleName();
        readProperties();

        setAllowEagerInit(true);

        Map<String, Object> beans = new HashMap<String, Object>();
        beans.put(monitorName, mbean);
        setBeans(beans);

        JndiObjectFactoryBean mbeanServerFactory = new JndiObjectFactoryBean();
        mbeanServerFactory.setJndiName(this.domainRuntimeMbeanServer);
        setServer((MBeanServer)mbeanServerFactory.getObject());
    }

    /**
     * Read properties to configure the MBeanExporter.
     */
    private void readProperties()
    {
        try
        {
            String propertyValue = ResourceBundle.getBundle(
                    this.resourceBundleName).getString(
                    "mbean.MBeanExporter.MONITOR_NAME");
            this.monitorName = "com.viasat.wildblue:name=" + propertyValue;
        }
        catch (Throwable t)
        {
            this.monitorName = this.defaultMonitorName;
            LOGGER.error(
                "Cannot read property: 'mbean.MBeanExporter.MONITOR_NAME'. Default value '"
                + this.monitorName + "' will be used.", t);
        }

        try
        {
            String propertyValue = ResourceBundle.getBundle(
                    this.resourceBundleName).getString(
                    "mbean.MBeanServer.DOMAIN_RUNTIME_MBEAN_SERVER");
            this.domainRuntimeMbeanServer = propertyValue;
        }
        catch (Throwable t)
        {
            this.domainRuntimeMbeanServer = DEFAULT_DOMAIN_RUNTIME_MBEAN_SERVER;
            LOGGER.error(
                "Cannot read property: 'mbean.MBeanServer.DOMAIN_RUNTIME_MBEAN_SERVER'. Default value '"
                + this.domainRuntimeMbeanServer + "' will be used.", t);
        }

        LOGGER.info("MBean register:" + monitorName);
    }
}
