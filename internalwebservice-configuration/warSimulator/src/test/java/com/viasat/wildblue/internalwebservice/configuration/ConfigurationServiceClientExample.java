/* ****************************************************************************
 * File:             $Source: /SoftwareDev/WildBlue/InternalWebService/Configuration/warSimulator/src/test/java/com/viasat/wildblue/internalwebservice/configuration/ConfigurationServiceClientExample.java,v $
 * Revision:         $Revision: 1.5 $
 * Last Modified By: $Author: VIASAT\jkent $
 * Last Modified On: $Date: 2013/01/30 22:31:13 $
 *
 * ------------------------------------------------------------------
 *  Copyright (c) 2004-2010 WildBlue Communications. All rights reserved.
 * ------------------------------------------------------------------
 *
 * **************************************************************************** */


package com.viasat.wildblue.internalwebservice.configuration;

import com.viasat.wildblue.common.exception.ExceptionUtilities;
import com.viasat.wildblue.common.exception.WebServiceException;
import com.viasat.wildblue.internalwebservice.configuration.data.ConfigurationItem;

import java.net.URL;

import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.ws.WebServiceRef;


/**
 * Created by: gfidler Date: Oct 1, 2010
 */

public class ConfigurationServiceClientExample
{
    @WebServiceRef private ConfigurationService m_configService;

    private String m_wsdlLocation;
    private URL m_wsdlLocationURL;

    public static void main(String[] args)
    {
        ConfigurationServiceClientExample client =
            new ConfigurationServiceClientExample();
        client.setWsdlLocation(
            "http://ubuntu:9001/ConfigurationServiceSimulator/services/ConfigurationServiceSimulator?wsdl");

        @SuppressWarnings("unused")
        List<ConfigurationItem> configItems = client.getConfiguration();
    }

    public List<ConfigurationItem> getConfiguration()
    {
        List<ConfigurationItem> configItems = null;

        try
        {
            try
            {
                m_wsdlLocationURL = new URL(m_wsdlLocation);

                m_configService = new ConfigurationService(m_wsdlLocationURL,
                        new QName(
                            "http://www.wildblue.viasat.com/WSDL/v1.0/ConfigurationWebService",
                            "ConfigurationService"));

                ConfigurationServiceV10 configSvc =
                    m_configService.getConfigurationServiceEndpointV10();

                configItems = configSvc.getCompleteConfiguration(
                        "CONFIGURATION", null);

                if (configItems != null)
                {
                    System.out.println("configItems: " + configItems.size());

                    for (ConfigurationItem item : configItems)
                    {
                        System.out.println("key:" + item.getKey() + ", value:"
                            + item.getValue());
                    }
                }
                else
                {
                    System.out.println("configItems is null");
                }
            }
            catch (WebServiceException ex)
            {
                System.out.println("Caught WebServiceException");

                Exception exception = ExceptionUtilities.getException(ex);
                throw exception;
            }
        }
        catch (DocumentNotFoundException ex)
        {
            ex.printStackTrace();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return configItems;
    }

    public void setWsdlLocation(String m_wsdlLocation)
    {
        this.m_wsdlLocation = m_wsdlLocation;
    }
}
