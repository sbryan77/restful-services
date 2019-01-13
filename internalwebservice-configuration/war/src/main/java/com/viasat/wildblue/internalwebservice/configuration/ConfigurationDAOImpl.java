/* ****************************************************************************
 * File:             $Source: /SoftwareDev/WildBlue/InternalWebService/Configuration/war/src/main/java/com/viasat/wildblue/internalwebservice/configuration/ConfigurationDAOImpl.java,v $
 * Revision:         $Revision: 1.17 $
 * Last Modified By: $Author: jwilliams $
 * Last Modified On: $Date: 2016/04/27 13:28:58 $
 *
 * ------------------------------------------------------------------
 *  Copyright (c) 2004-2010 WildBlue Communications. All rights reserved.
 * ------------------------------------------------------------------
 *
 * **************************************************************************** */

package com.viasat.wildblue.internalwebservice.configuration;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.w3c.dom.Document;

import com.viasat.wildblue.common.header.WildBlueHeader;
import com.viasat.wildblue.internalwebservice.configuration.data.ConfigurationItem;

/**
 * Class responsible for database interaction logic.
 */
public class ConfigurationDAOImpl extends SimpleJdbcDaoSupport implements ConfigurationDAO,
		ConfigDaoAdapter
{
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationDAOImpl.class);

	/** Query to fetch configuration CLOB * */
	// private static final String QUERY_SQL =
	// "Select APP_CONFIG From CONFIG Where APP_NAME = :appName";

	/** Query to fetch configuration CLOB as string * */
	private static final String QUERY_STRING_SQL = "select c.APP_CONFIG.getClobVal() from CONFIG c where APP_NAME = ?";

	/** Query to delete configuration CLOB * */
	private static final String DELETE_SQL = "call config_app.delete_config(?)";

	/** Query to update configuration CLOB * */
	private static final String UPDATE_SQL = "call config_app.set_config(?, ?)";

	/** Query to return the names of all configurations * */
	private static final String QUERY_ALL_APPS_SQL = "select APP_NAME from CONFIG";

	private ConfigurationDocumentReader m_documentReader;

	@Override
	public void delete(String appName)
	{
		if (appName == null)
		{
			throw new IllegalArgumentException("appName cannot be null");
		}

		getJdbcTemplate().update(DELETE_SQL, appName);
	}

	/**
	 * Returns a list of all configuration names. This method is only used for
	 * test.
	 * 
	 * @return List of configuration names.
	 */
	public List<String> getAllApps()
	{
		List<String> appNames = getJdbcTemplate().query(QUERY_ALL_APPS_SQL, new RowMapper<String>()
		{
			public String mapRow(ResultSet rs, int rowNum) throws SQLException
			{
				String appName = rs.getString("APP_NAME");
				return appName;
			}
		});
		return appNames;
	}

	@Override
	public String getConfigurationDocument(String appName) throws DocumentNotFoundException
	{
		LOGGER.trace("queryString(" + appName + ")");

		String xml;

		xml = fetchStringType(appName);

		return xml;
	}

	@Override
	public List<ConfigurationItem> getConfigurationItems(String documentName,
			WildBlueHeader wildBlueHeader) throws DocumentNotFoundException
	{
		Document doc = null;

		if ("true".equals(System.getProperty("allowConfigOverride")))
		{
			doc = checkForConfigOverrideFile(documentName + ".xml");
		}

		if (doc == null)
		{
			doc = query(documentName);
		}

		return m_documentReader.getConfigItemList(doc);
	}

	@Override
	public List<String> getDocumentNames() throws QueryFailedException
	{
		try
		{
			return getAllApps();
		}
		catch (Exception e)
		{
			throw new QueryFailedException(e);
		}
	}

	@Override
	public Document query(String appName) throws DocumentNotFoundException
	{
		LOGGER.trace("query(" + appName + ")");

		if (appName == null)
		{
			throw new IllegalArgumentException("appName cannot be null");
		}

		String xml = fetchStringType(appName);
		return convertStringToDoc(xml);
	}

	public void setDocumentReader(ConfigurationDocumentReader documentReader)
	{
		m_documentReader = documentReader;
	}

	@Override
	public void update(String appName, String xml)
	{
		if (appName == null)
		{
			throw new IllegalArgumentException("appName cannot be null");
		}

		getJdbcTemplate().update(UPDATE_SQL, appName, xml);
	}

	/**
	 * Check for config override file, a file with the same name as the
	 * configuration being looked up (eg: FINANCE = FINANCE.xml) anywhere on the
	 * class path, if found the file will be used instead of the DB
	 * configuration.
	 * 
	 * @param resourceName
	 *            the resource name
	 * 
	 * @return the document
	 */
	private Document checkForConfigOverrideFile(String resourceName)
	{
		Document doc = null;
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(resourceName);

		if (is != null)
		{
			try
			{
				doc = DocumentUtils.loadXMLFrom(is);
			}
			catch (Exception e)
			{
				LOGGER.warn("Override config file found [" + resourceName
						+ "], but unable to parse as XML:" + e.getMessage(), e);
			}
		}

		return doc;
	}

	private Document convertStringToDoc(String xml)
	{
		Document doc;

		if (LOGGER.isDebugEnabled())
		{
			LOGGER.debug("xml:" + xml);
		}

		InputStream istrm = new ByteArrayInputStream(xml.getBytes());

		try
		{
			doc = DocumentUtils.loadXMLFrom(istrm);
		}
		catch (Exception e)
		{
			throw new ConfigurationFaultException(e.getMessage(), e);
		}

		return doc;
	}

	/**
	 * Fetches a configuration CLOB as a string.
	 * 
	 * @param appName
	 *            Application name to process.
	 * 
	 * @return XML string for application name.
	 * 
	 * @throws DocumentNotFoundException
	 *             if document cannot be found on database.
	 */
	private String fetchStringType(String appName) throws DocumentNotFoundException
	{
		String xml;

		// CFGWS-63 : Convert the appName to uppercase so it matches on the data stored in the db, which should be stored in uppercase.
		appName = appName.toUpperCase();
				
		Object[] args =
		{ appName };

		int[] argTypes = new int[]
		{ Types.VARCHAR };

		try
		{
			xml = getJdbcTemplate().queryForObject(QUERY_STRING_SQL, args, argTypes, String.class);
		}
		catch (DataAccessException ex)
		{
			throw new DocumentNotFoundException("Failed to retrieve configuration information", ex);
		}

		return xml;
	}

	// private Document fetchXmlType(String appName)
	// {
	// Document doc;
	//
	// Object[] args = { appName };
	// doc = (Document)getJdbcTemplate().queryForObject(QUERY_SQL, args,
	// new RowMapper()
	// {
	// public Object mapRow(ResultSet rs, int rowNum)
	// throws SQLException
	// {
	// XMLType xmlType = XMLType.createXML(
	// (OPAQUE)rs.getObject(1));
	// // String xml = xmlType.getStringVal();
	//
	// Document doc = xmlType.getDocument();
	//
	// return doc;
	// }
	// });
	//
	// return doc;
	// }

	// private InputStream getResStream(String appName)
	// {
	// ClassLoader classLoader = this.getClass().getClassLoader();
	// InputStream istrm = classLoader.getResourceAsStream(appName);
	//
	// return istrm;
	// }
}
