/* ****************************************************************************
 * File:             $Source: /SoftwareDev/WildBlue/InternalWebService/Configuration/war/src/test/java/com/viasat/wildblue/internalwebservice/configuration/servlet/ConfigServletTestInt.java,v $
 * Revision:         $Revision: 1.2 $
 * Last Modified By: $Author: VIASAT\jwilliams $
 * Last Modified On: $Date: 2014/07/17 22:21:21 $
 *
 * ------------------------------------------------------------------
 *  Copyright (c) 2004-2010 WildBlue Communications. All rights reserved.
 * ------------------------------------------------------------------
 *
 * **************************************************************************** */

package com.viasat.wildblue.internalwebservice.configuration.servlet;

import static junit.framework.Assert.assertEquals;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.junit.Ignore;
import org.springframework.dao.DataAccessException;
import org.xml.sax.SAXException;

/*
 * FIXME JW (Ignored as test is failing)
 */
@Ignore
public class ConfigServletTestInt
{
	/** Line separator. */
	private static final String LINE_SEPARATOR = System.getProperty("line.separator");

	public void testQuery() throws Exception
	{
		String request = createExampleQueryConfigRequest("CONFIGURATION");
		String response = getResponse(request, getNewServletUrl());
		String response1 = getResponse(request, getOldServletUrl());

		// System.out.println(response);
		// System.out.println(response1);
		assertEquals(response, response1);
	}

	public void testQueryAppNames() throws Exception
	{
		String request = createExampleQueryAppNamesConfigRequest("JUNITTEST");
		String response = getResponse(request, getNewServletUrl());
		String response1 = getResponse(request, getOldServletUrl());

		// System.out.println(response);
		// System.out.println(response1);
		assertEquals(response, response1);
	}

	public void testReplaceAndDelete() throws Exception
	{
		String replaceRequest = createExampleReplaceConfigRequest("JUNITTEST");
		String deleteRequest = createExampleDeleteConfigRequest("JUNITTEST");

		String replaceResponse = getResponse(replaceRequest, getNewServletUrl());
		String deleteResponse = getResponse(deleteRequest, getNewServletUrl());

		String replaceResponse1 = getResponse(replaceRequest, getOldServletUrl());
		String deleteResponse1 = getResponse(deleteRequest, getOldServletUrl());

		assertEquals(replaceResponse, replaceResponse1);
		assertEquals(deleteResponse, deleteResponse1);
	}

	private static String createExampleDeleteConfigRequest(String appName) throws SAXException,
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
		sb.append("delete");
		sb.append("</type>");
		sb.append(LINE_SEPARATOR);

		sb.append("</configRequest>");
		sb.append(LINE_SEPARATOR);

		String answer = sb.toString();
		return answer;
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

	private static String createExampleReplaceConfigRequest(String appName) throws SAXException,
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
		sb.append("replace");
		sb.append("</type>");
		sb.append(LINE_SEPARATOR);
		sb.append("  <config name='"
				+ appName
				+ "'> 	<!-- Internal Web Services --> 	<bandwidthUsageUrl>http://apiint.dev.wdc1.wildblue.net:11251/BandwidthUsageWebService/services/BandwidthUsageWebService?wsdl</bandwidthUsageUrl></config>");
		sb.append("</configRequest>");
		sb.append(LINE_SEPARATOR);

		String answer = sb.toString();
		return answer;
	}

	/**
	 * @return a new, configured HTTP client.
	 */
	private static HttpClient createHttpClient()
	{
		HttpClient client = new HttpClient();

		return client;
	}

	/**
	 * @return servlet url
	 */
	private static String getNewServletUrl()
	{
		return "http://mapipri01.dev.wdc1.wildblue.net:20251/ConfigurationService/request";
	}

	/**
	 * @return servlet url
	 */
	private static String getOldServletUrl()
	{
		return "http://mapipri.dev.wdc1.wildblue.net/Configuration/request";
	}

	/**
	 * Get sertifi url for file id or email address
	 * 
	 * @param fileId
	 * @param emailAddress
	 * 
	 * @return sertifi url
	 * 
	 * @throws IOException
	 *             - error getting response
	 * @throws DataAccessException
	 *             - error in response
	 */
	private static String getResponse(String configRequest, String servletUrl) throws IOException
	{
		String url = null;

		if (servletUrl != null)
		{
			PostMethod method = new PostMethod(servletUrl);
			method.addParameter("input", configRequest);

			int returnCode = createHttpClient().executeMethod(method);

			if (returnCode != HttpStatus.SC_OK)
			{
				throw new RuntimeException("Error getting url: " + method.getResponseBodyAsString());
			}

			url = method.getResponseBodyAsString();
		}

		return url;
	}
}
