/* ****************************************************************************
 * File:             $Source: /SoftwareDev/WildBlue/InternalWebService/Configuration/warSimulator/src/main/java/com/viasat/wildblue/internalwebservice/configuration/ConfigurationDAOSimulatorImpl.java,v $
 * Revision:         $Revision: 1.12 $
 * Last Modified By: $Author: VIASAT\lchapman $
 * Last Modified On: $Date: 2014/08/20 23:06:38 $
 *
 * ------------------------------------------------------------------
 *  Copyright (c) 2004-2010 WildBlue Communications. All rights reserved.
 * ------------------------------------------------------------------
 *
 * **************************************************************************** */

package com.viasat.wildblue.internalwebservice.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.viasat.wildblue.common.header.WildBlueHeader;
import com.viasat.wildblue.internalwebservice.configuration.data.ConfigurationItem;

/**
 * Created by: gfidler Date: Oct 11, 2010
 */

public class ConfigurationDAOSimulatorImpl implements ConfigurationDAO, ConfigDaoAdapter
{
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ConfigurationDAOSimulatorImpl.class);

	ConfigurationDocumentReader m_documentReader;

	@Override
	public void delete(String appName)
	{
	}

	@Override
	public List<ConfigurationItem> getConfigurationItems(String document,
			WildBlueHeader wildBlueHeader) throws DocumentNotFoundException
	{
		Document doc = query(document);

		return m_documentReader.getConfigItemList(doc);
	}

	@Override
	public List<String> getDocumentNames() throws QueryFailedException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Document query(String appName) throws DocumentNotFoundException
	{
		Document doc;

		InputStream resStream = getFileStream(appName);

		try
		{
			doc = DocumentUtils.loadXMLFrom(resStream);
		}
		catch (SAXException e)
		{
			throw new DocumentNotFoundException("Exception fetching canned data", e);
		}
		catch (IOException e)
		{
			throw new DocumentNotFoundException("Exception fetching canned data", e);
		}
		catch (ParserConfigurationException e)
		{
			throw new DocumentNotFoundException("Exception fetching canned data", e);
		}
		catch (RuntimeException e)
		{
			throw new DocumentNotFoundException("Exception fetching canned data", e);
		}

		return doc;
	}

	@Override
	public String getConfigurationDocument(String appName)
	{
		return null;
	}

	public void setDocumentReader(ConfigurationDocumentReader documentReader)
	{
		m_documentReader = documentReader;
	}

	@Override
	public void update(String appName, String xml)
	{
		// TODO Auto-generated method stub
	}

	private InputStream getFileStream(String appName)
	{
		InputStream istrm = null;

		String userDir = System.getProperty("user.dir");
		String sep = System.getProperty("file.separator");
		String localConfig = System.getProperty("configDir", "localConfig");
		String filePath = userDir + sep + localConfig + sep + appName + ".xml";
		System.out.println("filePath: " + filePath);

		File file = new File(filePath);

		try
		{
			istrm = new FileInputStream(file);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}

		return istrm;
	}

	/**
	 * Fetches an input stream for an application name. Constructs a file name
	 * by appending ".xml" to appName argument.
	 * 
	 * @param appName
	 *            Application name to process.
	 * 
	 * @return Input stream to the file containing the canned xml response.
	 */
	// private InputStream getResStream(String appName)
	// {
	// ClassLoader classLoader = this.getClass().getClassLoader();
	// InputStream istrm = classLoader.getResourceAsStream(appName + ".xml");
	//
	// return istrm;
	// }
}
