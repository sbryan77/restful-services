/* ****************************************************************************
 * File:             $Source: /SoftwareDev/WildBlue/InternalWebService/Configuration/sender/src/main/java/com/viasat/internalwebservice/configuration/ConfigurationClientImpl.java,v $
 * Revision:         $Revision: 1.3 $
 * Last Modified By: $Author: VIASAT\lchapman $
 * Last Modified On: $Date: 2014/08/20 23:06:37 $
 *
 * ------------------------------------------------------------------
 *  Copyright (c) 2004-2010 WildBlue Communications. All rights reserved.
 * ------------------------------------------------------------------
 *
 * **************************************************************************** */

package com.viasat.internalwebservice.configuration;

import java.net.MalformedURLException;
import java.net.URL;

import com.viasat.wildblue.wsdl.v1_0.configurationwebservice.ConfigurationService;
import com.viasat.wildblue.wsdl.v1_0.configurationwebservice.ConfigurationServiceV10;
import com.viasat.wildblue.wsdl.v1_0.configurationwebservice.WebServiceException;

public class ConfigurationClientImpl implements ConfigurationClient
{
	private ConfigurationServiceV10 m_adapter = null;
	private String m_endpoint = null;

	public ConfigurationClientImpl(String endpoint)
	{
		m_endpoint = endpoint;
	}

	@Override
	public void deleteConfigurationFile(String documentName, String user)
			throws WebServiceException
	{
		getConfigurationService().deleteConfigurationDocument(documentName);
	}

	@Override
	public String getConfigurationDocument(String documentName, String user)
			throws WebServiceException
	{
		return getConfigurationService().getConfigurationDocument(documentName);
	}

	@Override
	public void replaceConfigurationFile(String documentName, String xml, String user)
			throws WebServiceException
	{
		getConfigurationService().replaceConfigurationDocument(documentName, xml);
	}

	private ConfigurationServiceV10 getConfigurationService()
	{
		if (m_adapter == null)
		{
			if (m_endpoint == null)
			{
				throw new RuntimeException("Endpoint not set");
			}

			try
			{
				ConfigurationService service = new ConfigurationService(new URL(m_endpoint));

				m_adapter = service.getConfigurationServiceEndpointV10();
			}
			catch (MalformedURLException e)
			{
				throw new RuntimeException("Problem acquiring Configuration Service Endpoint", e);
			}
		}

		return m_adapter;
	}
}
