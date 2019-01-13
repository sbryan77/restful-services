/* ****************************************************************************
 * File:             $Source: /SoftwareDev/WildBlue/InternalWebService/Configuration/api/src/main/java/com/viasat/wildblue/internalwebservice/configuration/ConfigProxyDaoAdapter.java,v $
 * Revision:         $Revision: 1.11 $
 * Last Modified By: $Author: ybhyri $
 * Last Modified On: $Date: 2016/03/18 18:31:11 $
 *
 * ------------------------------------------------------------------
 *  Copyright (c) 2004-2010 WildBlue Communications. All rights reserved.
 * ------------------------------------------------------------------
 *
 * **************************************************************************** */

package com.viasat.wildblue.internalwebservice.configuration;

import java.net.URL;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.viasat.wildblue.common.exception.WebServiceException;
import com.viasat.wildblue.common.header.WildBlueHeader;
import com.viasat.wildblue.internalwebservice.configuration.data.ConfigurationItem;

public class ConfigProxyDaoAdapter implements ConfigDaoAdapter
{
	/** The LOGGER */
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigProxyDaoAdapter.class);

	/** Configuration web service */
	private static ConfigurationServiceV10 m_configSvc;

	/**
	 * Property name to set configuration service endpoint. This system property
	 * is set when weblogic server starts.
	 */
	private static final String PROPERTY_CONFIG_ENDPOINT = "configurationServiceEndpoint";

	/** WildBlueHeader */
	private WildBlueHeader m_wildBlueHeader = null;

	/**
	 * Constructor
	 * 
	 * @param wildBlueHeader
	 *            WildBlue Header
	 */
	public ConfigProxyDaoAdapter(WildBlueHeader wildBlueHeader)
	{
		super();
		this.m_wildBlueHeader = wildBlueHeader;
	}

	@Override
	public void delete(String documentName) throws UpdateFailedException
	{
		try
		{
			initConfigService();
			m_configSvc.deleteConfigurationDocument(documentName, m_wildBlueHeader);
		}
		catch (WebServiceException e)
		{
			LOGGER.error("Failed to delete document ", e);
			throw new UpdateFailedException(e);
		}
	}

	@Override
	public String getConfigurationDocument(String documentName) throws DocumentNotFoundException
	{
		try
		{
			initConfigService();
			return m_configSvc.getConfigurationDocument(documentName, m_wildBlueHeader);
		}
		catch (WebServiceException e)
		{
			LOGGER.error("Failed to getCompleteConfiguration from configuration service.", e);
			throw new DocumentNotFoundException(e);
		}
	}

	@Override
	public List<ConfigurationItem> getConfigurationItems(String document,
			WildBlueHeader wildBlueHeader) throws DocumentNotFoundException
	{
		List<ConfigurationItem> configItems = null;

		try
		{
			initConfigService();
			configItems = m_configSvc.getCompleteConfiguration(document, m_wildBlueHeader);
		}
		catch (WebServiceException e)
		{
			LOGGER.error("Failed to getCompleteConfiguration from configuration service.", e);
			throw new DocumentNotFoundException(e);
		}

		return configItems;
	}

	@Override
	public List<String> getDocumentNames() throws QueryFailedException
	{
		try
		{
			initConfigService();
			return m_configSvc.getDocumentNames(m_wildBlueHeader);
		}
		catch (WebServiceException e)
		{
			LOGGER.error("Failed to getDocumentNames from configuration service.", e);
			throw new QueryFailedException(e);
		}
	}

	@Override
	public void update(String documentName, String xml) throws UpdateFailedException
	{
		try
		{
			initConfigService();
			m_configSvc.replaceConfigurationDocument(documentName, xml, m_wildBlueHeader);
		}
		catch (WebServiceException e)
		{
			LOGGER.error("Failed to update document ", e);
			throw new UpdateFailedException(e);
		}
	}

	/**
	 * Initialize the configuration web service
	 * 
	 * @throws WebServiceException
	 */
	private void initConfigService() throws WebServiceException
	{
		if (LOGGER.isDebugEnabled())
		{
			LOGGER.debug("initConfigService()");
		}

		if (m_configSvc == null)
		{
			if (LOGGER.isDebugEnabled())
			{
				LOGGER.debug("Config service is null)");
			}

			try
			{
				String endpoint = System.getProperty(PROPERTY_CONFIG_ENDPOINT);

				LOGGER.info("Configuration service endpoint:" + endpoint);

				if (endpoint == null)
				{
					throw new WebServiceException("Can't find configuration service endpoint.");
				}

				// JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
				//
				// factory.setServiceClass(ConfigurationServiceV10.class);
				// factory.setAddress(endpoint);
				//
				// m_configSvc = (ConfigurationServiceV10)factory.create();

				// ClientProxyFactoryBean factory = new
				// ClientProxyFactoryBean();
				// factory.setServiceClass(ConfigurationServiceV10.class);
				// factory.setAddress(endpoint);
				// m_configSvc = (ConfigurationServiceV10)factory.create();

				ConfigurationService service = new ConfigurationService(new URL(endpoint + "?wsdl"));

				// LOGGER.info("service=" + service);
				// System.out.println("service=" + service);

				m_configSvc = service.getConfigurationServiceEndpointV10();
			}
			catch (Exception e)
			{
				LOGGER.error("initConfigService failed.", e);
				throw new WebServiceException("initConfigService failed", e);
			}
		}
	}
}
