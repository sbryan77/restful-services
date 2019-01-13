/* ****************************************************************************
 * File:             $Source: /SoftwareDev/WildBlue/InternalWebService/Configuration/api/src/main/java/com/viasat/wildblue/internalwebservice/configuration/ConfigurationProxy.java,v $
 * Revision:         $Revision: 1.8 $
 * Last Modified By: $Author: VIASAT\lchapman $
 * Last Modified On: $Date: 2014/08/20 23:06:35 $
 *
 * ------------------------------------------------------------------
 *  Copyright (c) 2004-2010 WildBlue Communications. All rights reserved.
 * ------------------------------------------------------------------
 *
 * **************************************************************************** */

/**
 * A configuration service proxy. It has a build-in cache and cache will be reset in a certain interval
 * (reset interval can be set in the configuration also. key is cache_reset_interval. Unit of the value is minutes).
 */
package com.viasat.wildblue.internalwebservice.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.viasat.wildblue.common.exception.WebServiceException;
import com.viasat.wildblue.common.header.WildBlueHeader;
import com.viasat.wildblue.internalwebservice.configuration.data.ConfigurationItem;

public class ConfigurationProxy
{
	/** The LOGGER */
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationProxy.class);

	/**
	 * Default cache reset interval (5 minutes). If interval is not set in the
	 * config, this value is used.
	 */
	private static final int DEFAULT_RESET_INTERVAL = 5 * 60 * 1000;

	/** Cache interval config key */
	private static final String PROPERTY_CACHE_RESET_INTERVAL = "cache_reset_interval";

	/** Configuration cache. */
	private Map<String, ConfigurationItem> m_cacheMap = null;

	private ConfigDaoAdapter m_configDaoAdapter = null;

	/** Document name */
	private String m_documentName = null;

	/** Last time when cache was reset. */
	private AtomicLong m_lastResetTime = new AtomicLong(0);

	/** Cache reset interval. Unit is milliseconds */
	private AtomicInteger m_resetInterval = new AtomicInteger(DEFAULT_RESET_INTERVAL);

	/** WildBlueHeader */
	private WildBlueHeader m_wildBlueHeader = null;

	/**
	 * Constructor. Only ConfigurationManage instantiate it.
	 * 
	 * @param documentName
	 *            document name
	 * @param wildblueHeader
	 *            WildBlue Header
	 * @param configDaoAdapter
	 *            Configuration DAO Adapter
	 */
	protected ConfigurationProxy(String documentName, WildBlueHeader wildblueHeader,
			ConfigDaoAdapter configDaoAdapter)
	{
		m_documentName = documentName;
		m_wildBlueHeader = wildblueHeader;
		m_configDaoAdapter = configDaoAdapter;
	}

	public void delete() throws UpdateFailedException, DocumentNotFoundException
	{
		m_configDaoAdapter.delete(m_documentName);
		buildCache();
	}

	public String getConfigurationDocument() throws DocumentNotFoundException
	{
		return m_configDaoAdapter.getConfigurationDocument(m_documentName);
	}

	/**
	 * Get a configuration
	 * 
	 * @param key
	 *            Config key
	 * 
	 * @return Config value
	 * 
	 * @throws DocumentNotFoundException
	 * @throws PropertyNotFoundException
	 */
	public String getConfigurationItem(String key) throws DocumentNotFoundException,
			PropertyNotFoundException
	{
		String value = getValue(key);
		return value;
	}

	/**
	 * Get a configuration.
	 * 
	 * @param key
	 *            Config key
	 * @param defaultString
	 *            the default value if config value doesn't exist
	 * 
	 * @return Config value
	 */
	public String getConfigurationItemDefault(String key, String defaultString)
	{
		String value = defaultString;

		try
		{
			value = getConfigurationItem(key);
		}
		catch (Exception e)
		{
			LOGGER.error("GetConfig Error. Document " + m_documentName + ", Key " + key, e);
		}

		return value;
	}

	/**
	 * Get a configuration.
	 * 
	 * @param key
	 *            Config key
	 * 
	 * @return Config value
	 * 
	 * @throws DocumentNotFoundException
	 * @throws PropertyNotFoundException
	 */
	public Double getConfigurationItemDouble(String key) throws DocumentNotFoundException,
			PropertyNotFoundException
	{
		String value = getConfigurationItem(key);
		Double valueDouble = null;

		try
		{
			valueDouble = new Double(value);
		}
		catch (NumberFormatException ne)
		{
			throw new PropertyNotFoundException("GetConfig Error:" + "Document " + m_documentName
					+ ", Key " + key + "is not a number");
		}

		return valueDouble;
	}

	/**
	 * Get a configuration.
	 * 
	 * @param key
	 *            Config key
	 * @param defaultDouble
	 *            the default value if config value doesn't exist
	 * 
	 * @return Config value
	 */
	public Double getConfigurationItemDoubleDefault(String key, Double defaultDouble)
	{
		Double valueDouble = defaultDouble;

		try
		{
			valueDouble = getConfigurationItemDouble(key);
		}
		catch (Exception e)
		{
			LOGGER.error("GetConfig Error. Document " + m_documentName + ", Key " + key, e);
		}

		return valueDouble;
	}

	/**
	 * Get a configuration.
	 * 
	 * @param key
	 *            Config key
	 * 
	 * @return Config value
	 * 
	 * @throws DocumentNotFoundException
	 * @throws PropertyNotFoundException
	 */
	public Integer getConfigurationItemInt(String key) throws DocumentNotFoundException,
			PropertyNotFoundException
	{
		String value = getConfigurationItem(key);
		Integer valueInt = null;

		try
		{
			valueInt = new Integer(value);
		}
		catch (NumberFormatException ne)
		{
			throw new PropertyNotFoundException("GetConfig Error: Document " + m_documentName
					+ ", Key " + key + "is not a number");
		}

		return valueInt;
	}

	/**
	 * Get a configuration.
	 * 
	 * @param key
	 *            Config key
	 * @param defaultInt
	 *            the default value if config value doesn't exist
	 * 
	 * @return Config value
	 */
	public Integer getConfigurationItemIntDefault(String key, Integer defaultInt)
	{
		Integer valueInt = defaultInt;

		try
		{
			valueInt = getConfigurationItemInt(key);
		}
		catch (Exception e)
		{
			LOGGER.error("GetConfig Error. Document " + m_documentName + ", Key " + key, e);
		}

		return valueInt;
	}

	public List<String> getDocumentNames() throws QueryFailedException
	{
		return m_configDaoAdapter.getDocumentNames();
	}

	/**
	 * Reset cache. This does not rebuild the cache. Still keep the old cache.
	 * Set cache last reset time to 0. Cache will be rebuilt when next time
	 * proxy request config value.
	 */
	public void reset()
	{
		LOGGER.trace("Reset config " + m_documentName);

		synchronized (m_lastResetTime)
		{
			m_lastResetTime.set(0);
		}
	}

	public void update(String xml) throws UpdateFailedException, DocumentNotFoundException
	{
		m_configDaoAdapter.update(m_documentName, xml);
		buildCache();
	}

	/**
	 * Get all configuration
	 * 
	 * @return All configurations
	 * 
	 * @throws DocumentNotFoundException
	 */
	List<ConfigurationItem> getCompleteConfiguration() throws DocumentNotFoundException
	{
		return getValueList();
	}

	/**
	 * Get the configuration item by a key
	 * 
	 * @param key
	 *            config key
	 * 
	 * @return Configuration item
	 * 
	 * @throws DocumentNotFoundException
	 * @throws PropertyNotFoundException
	 */

	ConfigurationItem getConfigurationItemHolder(String key) throws DocumentNotFoundException,
			PropertyNotFoundException
	{
		return getValueHolder(key);
	}

	/**
	 * Get complete configuration from configuration web service and build the
	 * cache
	 * 
	 * @throws DocumentNotFoundException
	 */
	private void buildCache() throws DocumentNotFoundException
	{
		List<ConfigurationItem> configItems = null;

		// Get the complete config from config service
		try
		{
			configItems = m_configDaoAdapter
					.getConfigurationItems(m_documentName, m_wildBlueHeader);
		}
		catch (DocumentNotFoundException e)
		{
			synchronized (m_lastResetTime)
			{
				// if cache map is null, it means it has never been built
				if (m_cacheMap == null)
				{
					throw e;
				}
			}
		}

		if (configItems == null)
		{
			// This should not happen, Config Adapter should either throw
			// DocumentNotFoundException, or return a list of config items
			String msg = "Config Adapter returned NULL config item list";
			LOGGER.warn("buildCache(): " + msg);
			throw new DocumentNotFoundException(msg);
		}
		else
		{
			String resetIntervalStr = null;

			synchronized (m_lastResetTime)
			{
				if (LOGGER.isDebugEnabled())
				{
					LOGGER.debug("Start building cache. ");
				}

				if (m_cacheMap == null)
				{
					m_cacheMap = new HashMap<String, ConfigurationItem>();
				}
				else
				{
					m_cacheMap.clear();
				}

				// populate the cache
				for (ConfigurationItem configItem : configItems)
				{
					m_cacheMap.put(configItem.getKey(), configItem);
				}

				m_lastResetTime.set(System.currentTimeMillis());

				if (LOGGER.isDebugEnabled())
				{
					LOGGER.debug("Finish building cache. ");
				}

				ConfigurationItem timeoutConfig = m_cacheMap.get(PROPERTY_CACHE_RESET_INTERVAL);

				if (timeoutConfig != null)
				{
					resetIntervalStr = timeoutConfig.getValue();
				}

				if (resetIntervalStr != null)
				{
					try
					{
						// Reset interval unit is minutes
						m_resetInterval
								.set(Integer.valueOf(resetIntervalStr).intValue() * 60 * 1000);
						LOGGER.info("Cache reset interval time for config " + m_documentName
								+ " is " + resetIntervalStr);
					}
					catch (NumberFormatException ne)
					{
						LOGGER.error("Cache reset interval time " + resetIntervalStr
								+ "  for config " + m_documentName + "is not a valid number.");
					}
				}
				else
				{
					LOGGER.warn("Cache reset interval time for config " + m_documentName
							+ " is not set in config xml. Set to default value.");
				}
			}
		}
	}

	/**
	 * Get value from cache. If it doesn't find in the cache, request from
	 * config web services. If the value returned from web service is not null,
	 * cache it.
	 * 
	 * @param key
	 *            Config key
	 * 
	 * @return Config value
	 * 
	 * @throws DocumentNotFoundException
	 * @throws PropertyNotFoundException
	 */
	private String getValue(String key) throws DocumentNotFoundException, PropertyNotFoundException
	{
		ConfigurationItem configItem = getValueHolder(key);
		return configItem.getValue();
	}

	/**
	 * Get the configuration item by a key
	 * 
	 * @param key
	 *            config key
	 * 
	 * @return Configuration Item
	 * 
	 * @throws DocumentNotFoundException
	 * @throws PropertyNotFoundException
	 */
	private ConfigurationItem getValueHolder(String key) throws DocumentNotFoundException,
			PropertyNotFoundException
	{
		if (LOGGER.isDebugEnabled())
		{
			LOGGER.debug("Get config " + key);
		}

		ConfigurationItem configItem;

		// Check if cache has expired. Rebuild the cache if expired.
		validateCache();

		synchronized (m_lastResetTime)
		{
			if (m_cacheMap == null)
			{
				throw new DocumentNotFoundException("Cache is not populated");
			}

			configItem = m_cacheMap.get(key);

			if (configItem != null)
			{
				if (LOGGER.isDebugEnabled())
				{
					LOGGER.debug("Cache hit");
				}
			}
			else
			{
				if (LOGGER.isDebugEnabled())
				{
					LOGGER.debug("Cache miss");
				}

				throw new PropertyNotFoundException("Property:" + key + " was not found");
			}
		}

		return configItem;
	}

	/**
	 * Get all configurations
	 * 
	 * @return complete configuration list
	 * 
	 * @throws DocumentNotFoundException
	 */
	private List<ConfigurationItem> getValueList() throws DocumentNotFoundException
	{
		List<ConfigurationItem> itemList;

		validateCache();

		synchronized (m_lastResetTime)
		{
			if (m_cacheMap == null)
			{
				throw new DocumentNotFoundException("Cache is not populated");
			}

			itemList = new ArrayList<ConfigurationItem>(m_cacheMap.values());
		}

		return itemList;
	}

	/**
	 * Check if cache has expired. If expired, rebuild cache.
	 * 
	 * @throws WebServiceException
	 */
	private void validateCache() throws DocumentNotFoundException
	{
		// Check if cache has expired
		if ((System.currentTimeMillis() - m_lastResetTime.get()) > m_resetInterval.get())
		{
			synchronized (m_lastResetTime)
			{
				// Check expire again after get the lock, another thread may
				// already updated cache
				if ((System.currentTimeMillis() - m_lastResetTime.get()) > m_resetInterval.get())
				{
					if (LOGGER.isDebugEnabled())
					{
						LOGGER.debug("Cache expired. Rebuild cache. ");
					}

					buildCache();
				}
			}
		}
	}
}
