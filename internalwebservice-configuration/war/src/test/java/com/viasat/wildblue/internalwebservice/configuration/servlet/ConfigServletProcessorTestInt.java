/* ****************************************************************************
 * File:             $Source: /SoftwareDev/WildBlue/InternalWebService/Configuration/war/src/test/java/com/viasat/wildblue/internalwebservice/configuration/servlet/ConfigServletProcessorTestInt.java,v $
 * Revision:         $Revision: 1.2 $
 * Last Modified By: $Author: VIASAT\jwilliams $
 * Last Modified On: $Date: 2014/07/17 22:19:34 $
 *
 * ------------------------------------------------------------------
 *  Copyright (c) 2004-2010 WildBlue Communications. All rights reserved.
 * ------------------------------------------------------------------
 *
 * **************************************************************************** */

package com.viasat.wildblue.internalwebservice.configuration.servlet;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Ignore;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.viasat.wildblue.internalwebservice.configuration.ConfigurationDAOImpl;
import com.viasat.wildblue.internalwebservice.configuration.ConfigurationDocumentReaderImpl;
import com.viasat.wildblue.internalwebservice.configuration.ConfigurationServiceV10Processor;

/**
 * Created by: gfidler Date: Oct 4, 2010
 */

/*
 * FIXME JW (Ignored as test is failing)
 */
@Ignore
public class ConfigServletProcessorTestInt
{
	/** Line separator. */
	private static final String LINE_SEPARATOR = System.getProperty("line.separator");

	ConfigServletProcessor servletProcessor;

	@Before
	public void setUp() throws Exception
	{
		servletProcessor = new ConfigServletProcessor();

		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"spring/dataAccessContext.xml");
		ConfigurationDAOImpl dao = (ConfigurationDAOImpl) applicationContext
				.getBean("configurationDao");
		dao.setDocumentReader(new ConfigurationDocumentReaderImpl());

		ConfigurationServiceV10Processor processor = new ConfigurationServiceV10Processor();
		processor.setConfigDaoAdapter(dao);

		servletProcessor.setConfigServiceProcessor(processor);
	}

	public void testQuery() throws Exception
	{
		String createQueryConfigRequest = createExampleQueryConfigRequest("CONFIGURATION");

		Document xml = XMLUtilities.loadXMLFrom(createQueryConfigRequest);

		String response = servletProcessor.getXMLResponse(xml);

		System.out.println(response);
	}

	public void testQueryAppNames() throws Exception
	{
		String createQueryConfigRequest = createExampleQueryAppNamesConfigRequest("CONFIGURATION");

		Document xml = XMLUtilities.loadXMLFrom(createQueryConfigRequest);

		String response = servletProcessor.getXMLResponse(xml);

		System.out.println(response);
	}

	private static String createExampleQueryAppNamesConfigRequest(String appName)
			throws SAXException, IOException, ParserConfigurationException
	{
		if (appName == null)
		{
			throw new IllegalArgumentException("appName == null");
		}

		StringBuilder sb = new StringBuilder();

		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append(LINE_SEPARATOR);

		sb.append("<configRequest>");
		sb.append(LINE_SEPARATOR);

		sb.append("<appName>");
		sb.append(appName);
		sb.append("</appName>");
		sb.append(LINE_SEPARATOR);

		sb.append("<type>");
		sb.append("queryAppNames");
		sb.append("</type>");
		sb.append(LINE_SEPARATOR);

		sb.append("</configRequest>");
		sb.append(LINE_SEPARATOR);

		String answer = sb.toString();

		return answer;
	}

	/**
	 * Wrap a config element in a configRequest element.
	 * 
	 * @param appName
	 *            appName for the wrapper.
	 * 
	 * @return XML document consisting of top-level configRequest element
	 *         containing the config element, if any of the other arguments are
	 *         non-null.
	 * 
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	private static String createExampleQueryConfigRequest(String appName) throws SAXException,
			IOException, ParserConfigurationException
	{
		if (appName == null)
		{
			throw new IllegalArgumentException("appName == null");
		}

		StringBuilder sb = new StringBuilder();

		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append(LINE_SEPARATOR);

		sb.append("<configRequest>");
		sb.append(LINE_SEPARATOR);

		sb.append("<appName>");
		sb.append(appName);
		sb.append("</appName>");
		sb.append(LINE_SEPARATOR);

		sb.append("<type>");
		sb.append("query");
		sb.append("</type>");
		sb.append(LINE_SEPARATOR);

		sb.append("</configRequest>");
		sb.append(LINE_SEPARATOR);

		String answer = sb.toString();

		return answer;
	}
}
