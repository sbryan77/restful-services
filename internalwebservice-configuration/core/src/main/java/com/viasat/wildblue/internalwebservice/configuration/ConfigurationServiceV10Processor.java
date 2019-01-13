/* ****************************************************************************
 * File:             $Source: /SoftwareDev/WildBlue/InternalWebService/Configuration/core/src/main/java/com/viasat/wildblue/internalwebservice/configuration/ConfigurationServiceV10Processor.java,v $
 * Revision:         $Revision: 1.14 $
 * Last Modified By: $Author: scox $
 * Last Modified On: $Date: 2015/04/14 17:19:11 $
 *
 * ------------------------------------------------------------------
 *  Copyright (c) 2004-2010 WildBlue Communications. All rights reserved.
 * ------------------------------------------------------------------
 *
 * **************************************************************************** */

package com.viasat.wildblue.internalwebservice.configuration;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.viasat.wildblue.internalwebservice.configuration.data.ConfigurationItem;

/**
 * Class containing the business logic for the Configuration service.
 */
public class ConfigurationServiceV10Processor
{
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ConfigurationServiceV10Processor.class);

	public void deleteConfigurationDocument(String documentName) throws UpdateFailedException,
			DocumentNotFoundException
	{
		ConfigurationProxy proxy = ConfigManager.getConfigurationProxy(documentName, null);
		proxy.delete();
		proxy.reset();
	}

	/**
	 * Gets a list of the name-value pairs representing all the properties for
	 * an application. Non-unique names are numbered sequentially.
	 * 
	 * @param documentName
	 *            Name of the document, in uppercase. Typically the application
	 *            name.
	 * 
	 * @return List of properties.
	 * 
	 * @throws DocumentNotFoundException
	 *             if document cannot be found in persistent store.
	 */
	public List<ConfigurationItem> getCompleteConfiguration(String documentName)
			throws DocumentNotFoundException
	{
		LOGGER.trace("getCompleteConfiguration(" + documentName + ")");

		List<ConfigurationItem> itemList;

		try
		{
			ConfigurationProxy proxy = ConfigManager.getConfigurationProxy(documentName, null);
			itemList = proxy.getCompleteConfiguration();
			proxy.reset();
		}
		catch (RuntimeException ex)
		{
			throw new DocumentNotFoundException(ex.getMessage(), ex);
		}

		return itemList;
	}

	public String getConfigurationDocument(String documentName) throws DocumentNotFoundException
	{
		ConfigurationProxy proxy = ConfigManager.getConfigurationProxy(documentName, null);
		String doc = proxy.getConfigurationDocument();
		proxy.reset();
		return doc;
	}

	/**
	 * Gets a single property value.
	 * 
	 * @param documentName
	 *            Name of the document, in uppercase. Typically the application
	 *            name.
	 * @param key
	 *            Property name.
	 * 
	 * @return A single property value.
	 * 
	 * @throws DocumentNotFoundException
	 *             if document cannot be found in persistent storage.
	 * @throws PropertyNotFoundException
	 *             if the property cannot be found in persistent storage.
	 */
	public String getConfigurationItem(final String documentName, final String key)
			throws DocumentNotFoundException, PropertyNotFoundException
	{
		LOGGER.trace("getConfigurationItem(" + documentName + ", " + key + ")");

		String value = null;

		ConfigurationProxy proxy = ConfigManager.getConfigurationProxy(documentName, null);

		value = proxy.getConfigurationItem(key);
		// rest proxy, we really dont want to cache items in server
		proxy.reset();
		return value;
	}

	/**
	 * Fetches a configuration item as a Double.
	 * 
	 * @param documentName
	 *            Document name to process.
	 * @param key
	 *            Property name to fetch.
	 * 
	 * @return Double value for the property.
	 * 
	 * @throws DocumentNotFoundException
	 *             if the document cannot be found in persistent storage.
	 * @throws PropertyNotFoundException
	 *             if the property cannot be found in persistent storage.
	 */
	public Double getConfigurationItemDouble(final String documentName, final String key)
			throws DocumentNotFoundException, PropertyNotFoundException
	{
		LOGGER.trace("getConfigurationItemDouble(" + documentName + ", " + key + ")");

		Double doubleValue;
		String value;

		value = getConfigurationItem(documentName, key);
		doubleValue = new Double(value);

		return doubleValue;
	}

	/**
	 * Fetches a configuration item as an Integer.
	 * 
	 * @param documentName
	 *            Document name to process.
	 * @param key
	 *            Property name to fetch.
	 * 
	 * @return Integer value for the property.
	 * 
	 * @throws DocumentNotFoundException
	 *             if document cannot be found in persistent storage.
	 * @throws PropertyNotFoundException
	 *             if property cannot be found in persistent storage.
	 */
	public Integer getConfigurationItemInt(final String documentName, final String key)
			throws DocumentNotFoundException, PropertyNotFoundException
	{
		LOGGER.trace("getConfigurationItemInt(" + documentName + ", " + key + ")");

		Integer intValue;

		String value = getConfigurationItem(documentName, key);
		intValue = new Integer(value);

		return intValue;
	}

	/**
	 * Fetches a list of configuration items for a single property name.
	 * 
	 * @param documentName
	 *            Document name to process.
	 * @param key
	 *            Property name to fetch.
	 * 
	 * @return List of configuration items to match property name provided.
	 * 
	 * @throws DocumentNotFoundException
	 *             if document cannot be found in persistent storage.
	 */
	public List<ConfigurationItem> getConfigurationItemList(final String documentName,
			final String key) throws DocumentNotFoundException
	{
		LOGGER.trace("getConfigurationItemList(" + documentName + ")");

		List<ConfigurationItem> items;

		ConfigurationProxy proxy = ConfigManager.getConfigurationProxy(documentName, null);

		List<ConfigurationItem> itemList = proxy.getCompleteConfiguration();
		proxy.reset();
		if (itemList == null)
		{
			throw new DocumentNotFoundException("Unable to load document:" + documentName);
		}
		else
		{
			items = new ArrayList<ConfigurationItem>();

			for (ConfigurationItem item : itemList)
			{
				if (item.getKey().startsWith(key))
				{
					items.add(item);
					continue;
				}
			}
		}

		return items;
	}

	/**
	 * Fetches a list of configuration items for a list of property names.
	 * 
	 * @param documentName
	 *            Document name to process.
	 * @param keys
	 *            Property names to fetch.
	 * 
	 * @return List of configuration items to match property names provided.
	 * 
	 * @throws DocumentNotFoundException
	 *             if document cannot be found in persistent storage.
	 */
	public List<ConfigurationItem> getConfigurationItems(final String documentName,
			final List<String> keys) throws DocumentNotFoundException
	{
		LOGGER.trace("getConfigurationItems(" + documentName + ")");

		List<ConfigurationItem> items;

		ConfigurationProxy proxy = ConfigManager.getConfigurationProxy(documentName, null);

		items = new ArrayList<ConfigurationItem>();

		for (String key : keys)
		{
			try
			{
				ConfigurationItem item = proxy.getConfigurationItemHolder(key);

				items.add(item);
			}
			catch (PropertyNotFoundException ex)
			{
				if (LOGGER.isDebugEnabled())
				{
					LOGGER.debug("Property:" + key + " was not found");
				}
			}
		}
		proxy.reset();
		return items;
	}

	public List<String> getDocumentNames() throws QueryFailedException
	{
		ConfigurationProxy proxy = ConfigManager.getConfigurationProxy(null, null);
		List<String> names = proxy.getDocumentNames();
		proxy.reset();
		return names;
	}

	public void replaceConfigurationDocument(String documentName, String xml)
			throws UpdateFailedException, DocumentNotFoundException
	{
		ConfigurationProxy proxy = ConfigManager.getConfigurationProxy(documentName, null);
		proxy.update(xml);
		proxy.reset();
	}

	/**
	 * Resets the underlying cache.
	 */
	public void reset()
	{
		LOGGER.trace("reset()");

		ConfigManager.resetAll();
	}

	public void setConfigDaoAdapter(ConfigDaoAdapter configDaoAdapter)
	{
		ConfigManager.setConfigDaoAdapter(configDaoAdapter);
	}
}
