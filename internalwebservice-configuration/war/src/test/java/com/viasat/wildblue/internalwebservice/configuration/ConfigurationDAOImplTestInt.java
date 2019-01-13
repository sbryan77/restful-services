/* ****************************************************************************
 * File:             $Source: /SoftwareDev/WildBlue/InternalWebService/Configuration/war/src/test/java/com/viasat/wildblue/internalwebservice/configuration/ConfigurationDAOImplTestInt.java,v $
 * Revision:         $Revision: 1.4 $
 * Last Modified By: $Author: jwilliams $
 * Last Modified On: $Date: 2016/04/26 19:58:25 $
 *
 * ------------------------------------------------------------------
 *  Copyright (c) 2004-2011 WildBlue Communications. All rights reserved.
 * ------------------------------------------------------------------
 *
 * **************************************************************************** */

package com.viasat.wildblue.internalwebservice.configuration;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.w3c.dom.Document;

/**
 * Created by: gfidler Date: Sep 28, 2010
 */

public class ConfigurationDAOImplTestInt
{
	private static final String CONFIG = "CONFIGURATION";
	ConfigurationDAOImpl dao;

	public void getAllClobs() throws Exception
	{
		List<String> appNames = dao.getAllApps();

		for (String appName : appNames)
		{
			String xml = dao.getConfigurationDocument(appName);
			System.out.println('\n' + appName + '\n' + xml);
		}
	}

	@Before
	public void setUp() throws Exception
	{
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"spring/dataAccessContext.xml");
		dao = (ConfigurationDAOImpl) applicationContext.getBean("configurationDao");
	}

	@After
	public void tearDown() throws Exception
	{
	}

	@Test
	public void testDelete() throws Exception
	{
	}

	@Test
	public void testQuery() throws Exception
	{
		Document doc = dao.query(CONFIG);

		assertNotNull("query should not return null", doc);
		System.out.println("config Document: " + doc.getTextContent());
	}

	@Test
	public void testQueryEmpty() throws Exception
	{
		boolean threw = false;
		Document doc = null;

		try
		{
			doc = dao.query("");
		}
		catch (DocumentNotFoundException ex)
		{
			threw = true;
		}

		assertTrue("query should have thrown", threw);
		assertNull("doc should be null", doc);
	}

	@Test
	public void testQueryNull() throws Exception
	{
		boolean threw = false;
		Document doc = null;

		try
		{
			doc = dao.query(null);
		}
		catch (IllegalArgumentException ex)
		{
			threw = true;
		}

		assertTrue("query should have thrown", threw);
		assertNull("doc should be null", doc);
	}

	@Test
	public void testQueryString() throws Exception
	{
		String orderSvc = dao.getConfigurationDocument(CONFIG);
		System.out.println("config: " + orderSvc);
	}

	@Test
	public void testUpdate() throws Exception
	{
	}
}
