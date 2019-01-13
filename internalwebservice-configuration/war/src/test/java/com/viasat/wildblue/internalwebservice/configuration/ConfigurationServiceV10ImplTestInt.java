/* ****************************************************************************
 * File:             $Source: /SoftwareDev/WildBlue/InternalWebService/Configuration/war/src/test/java/com/viasat/wildblue/internalwebservice/configuration/ConfigurationServiceV10ImplTestInt.java,v $
 * Revision:         $Revision: 1.3 $
 * Last Modified By: $Author: jwilliams $
 * Last Modified On: $Date: 2016/04/26 20:38:08 $
 *
 * ------------------------------------------------------------------
 *  Copyright (c) 2004-2010 WildBlue Communications. All rights reserved.
 * ------------------------------------------------------------------
 *
 * **************************************************************************** */

package com.viasat.wildblue.internalwebservice.configuration;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by: gfidler Date: Oct 4, 2010
 */

public class ConfigurationServiceV10ImplTestInt
{
	private static final String CONFIG = "CONFIGURATION";
	private static final String DEFAULT_STRING = "DEFAULT_STRING";
	private static final String JUNK = "JUNK";
	// private static final String PORTAL = "portal";
	// private static final String APPLICATION = "application";
	// private static final String APPLICATION_25_CONFIG_NAME = APPLICATION
	// + ".25.configName";
	// private static final String TEST_DOUBLE = "testDouble";
	// private static final String TEST_INT = "testInt";
	// private static final String APPSERVER = "appserver";
	// private static final String APPLICATION_3 = "application.3";

	ConfigurationServiceV10Impl impl;

	@Before
	public void setUp() throws Exception
	{
		impl = new ConfigurationServiceV10Impl();

		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"spring/dataAccessContext.xml");
		ConfigurationDAOImpl dao = (ConfigurationDAOImpl) applicationContext
				.getBean("configurationDao");
		dao.setDocumentReader(new ConfigurationDocumentReaderImpl());

		ConfigurationServiceV10Processor processor = new ConfigurationServiceV10Processor();
		processor.setConfigDaoAdapter(dao);

		impl.setConfigServiceProcessor(processor);
	}

	@Test
	public void testGetCompleteConfiguration() throws Exception
	{
		boolean threw = false;

		try
		{
			impl.getCompleteConfiguration(CONFIG, null);
		}
		catch (Exception ex)
		{
			threw = true;
			ex.printStackTrace();
		}

		assertFalse("Exception should not have been thrown", threw);
	}

	@Test
	public void testGetCompleteConfigurationBad() throws Exception
	{
		boolean threw = false;

		try
		{
			impl.getCompleteConfiguration(JUNK, null);
		}
		catch (Exception ex)
		{
			threw = true;
		}

		assertTrue("Exception should have been thrown", threw);
	}

	@Test
	public void testGetCompleteConfigurationNull() throws Exception
	{
		boolean threw = false;

		try
		{
			impl.getCompleteConfiguration(null, null);
		}
		catch (Exception ex)
		{
			threw = true;
		}

		assertTrue("Exception should have been thrown", threw);
	}

	// @Test public void testGetConfigurationItem() throws Exception
	// {
	// boolean threw = false;
	// String value = null;
	//
	// try
	// {
	// value = impl.getConfigurationItem(CONFIG, PORTAL, null);
	// }
	// catch (Exception ex)
	// {
	// threw = true;
	// }
	//
	// assertFalse("Exception should not have been thrown", threw);
	// assertNotNull("Value should not be null", value);
	// }

	@Test
	public void testGetConfigurationItemBadDocument() throws Exception
	{
		boolean threw = false;

		try
		{
			impl.getConfigurationItem(JUNK, "wildBlueSchemaFileName", null);
		}
		catch (Exception ex)
		{
			threw = true;
		}

		assertTrue("Exception should have been thrown", threw);
	}

	@Test
	public void testGetConfigurationItemBadDocumentDouble() throws Exception
	{
		boolean threw = false;

		try
		{
			impl.getConfigurationItem(JUNK, JUNK, null);
		}
		catch (Exception ex)
		{
			threw = true;
		}

		assertTrue("Exception should have been thrown", threw);
	}

	@Test
	public void testGetConfigurationItemBadProperty() throws Exception
	{
		boolean threw = false;

		try
		{
			impl.getConfigurationItem(CONFIG, JUNK, null);
		}
		catch (Exception ex)
		{
			threw = true;
		}

		assertTrue("Exception should have been thrown", threw);
	}

	@Test
	public void testGetConfigurationItemBadPropertyDouble() throws Exception
	{
		boolean threw = false;

		try
		{
			impl.getConfigurationItem(CONFIG, JUNK, null);
		}
		catch (Exception ex)
		{
			threw = true;
		}

		assertTrue("Exception should have been thrown", threw);
	}

	@Test
	public void testGetConfigurationItemDefault() throws Exception
	{
		boolean threw = false;
		String value = null;

		try
		{
			value = impl.getConfigurationItemDefault(CONFIG, JUNK, DEFAULT_STRING, null);
		}
		catch (Exception ex)
		{
			threw = true;
		}

		assertFalse("Exception should not have been thrown", threw);
		assertTrue("Value should be DEFAULT_STRING", DEFAULT_STRING.equals(value));
	}

	@Test
	public void testGetConfigurationItemDefaultBadDocument() throws Exception
	{
		boolean threw = false;
		String value = null;

		try
		{
			value = impl.getConfigurationItemDefault(JUNK, JUNK, DEFAULT_STRING, null);
		}
		catch (Exception ex)
		{
			threw = true;
		}

		assertFalse("Exception should not have been thrown", threw);
		assertTrue("Value should be DEFAULT_STRING", DEFAULT_STRING.equals(value));
	}

	// @Test public void testGetConfigurationItemDouble() throws Exception
	// {
	// boolean threw = false;
	// Double doubleValue = null;
	//
	// try
	// {
	// doubleValue = impl.getConfigurationItemDouble(CONFIG, TEST_DOUBLE,
	// null);
	// }
	// catch (Exception ex)
	// {
	// threw = true;
	// }
	//
	// assertFalse("Exception should not have been thrown", threw);
	// assertNotNull("Value should not be null", doubleValue);
	// assertTrue("Value should be 1.0", 1.0 == doubleValue);
	// }

	@Test
	public void testGetConfigurationItemDoubleDefault() throws Exception
	{
		boolean threw = false;
		Double doubleValue = null;
		Double defaultDouble = new Double(369);

		try
		{
			doubleValue = impl.getConfigurationItemDoubleDefault(CONFIG, JUNK, defaultDouble, null);
		}
		catch (Exception ex)
		{
			threw = true;
		}

		assertFalse("Exception should not have been thrown", threw);
		assertTrue("Value should be 369", defaultDouble.equals(doubleValue));
	}

	@Test
	public void testGetConfigurationItemDoubleDefaultConv() throws Exception
	{
		boolean threw = false;
		Double doubleValue = null;
		Double defaultDouble = new Double(369);

		try
		{
			// Non-numeric property
			doubleValue = impl.getConfigurationItemDoubleDefault(CONFIG, "wildBlueSchemaFileName",
					defaultDouble, null);
		}
		catch (Exception ex)
		{
			threw = true;
		}

		assertFalse("Exception should not have been thrown", threw);
		assertTrue("Value should be 369", defaultDouble.equals(doubleValue));
	}

	// @Test public void testGetConfigurationItemInt() throws Exception
	// {
	// boolean threw = false;
	// Integer intValue = null;
	//
	// try
	// {
	// intValue = impl.getConfigurationItemInt(CONFIG, TEST_INT, null);
	// }
	// catch (Exception ex)
	// {
	// threw = true;
	// }
	//
	// assertFalse("Exception should not have been thrown", threw);
	// assertNotNull("Value should not be null", intValue);
	// }

	@Test
	public void testGetConfigurationItemIntDefault() throws Exception
	{
		boolean threw = false;
		Integer intValue = null;
		Integer defaultInt = new Integer(369);

		try
		{
			intValue = impl.getConfigurationItemIntDefault(CONFIG, JUNK, defaultInt, null);
		}
		catch (Exception ex)
		{
			threw = true;
		}

		assertFalse("Exception should not have been thrown", threw);
		assertTrue("Value should be 369", defaultInt.equals(intValue));
	}

	// @Test public void testGetConfigurationItemList() throws Exception
	// {
	// boolean threw = false;
	// List<ConfigurationItem> items = null;
	//
	// try
	// {
	// items = impl.getConfigurationItemList(CONFIG, APPSERVER, null);
	// }
	// catch (Exception ex)
	// {
	// threw = true;
	// }
	//
	// assertFalse("Exception should not have been thrown", threw);
	// assertNotNull("Items should not be null", items);
	// assertTrue("Items should contain 2 items", items.size() == 2);
	//
	// try
	// {
	// items = impl.getConfigurationItemList(CONFIG, APPLICATION, null);
	// }
	// catch (Exception ex)
	// {
	// threw = true;
	// }
	//
	// assertFalse("Exception should not have been thrown", threw);
	// assertNotNull("Items should not be null", items);
	// assertTrue("Items should contain 75 items", items.size() == 75);
	//
	// try
	// {
	// items = impl.getConfigurationItemList(CONFIG, APPLICATION_3, null);
	// }
	// catch (Exception ex)
	// {
	// threw = true;
	// }
	//
	// assertFalse("Exception should not have been thrown", threw);
	// assertNotNull("Items should not be null", items);
	// assertTrue("Items should contain 3 items", items.size() == 3);
	// }

	// @Test public void testGetConfigurationItems() throws Exception
	// {
	// boolean threw = false;
	// List<ConfigurationItem> items = null;
	// List<String> keys = new ArrayList<String>();
	//
	// keys.add(TEST_INT);
	// keys.add(PORTAL);
	// keys.add(JUNK);
	// keys.add(APPLICATION_25_CONFIG_NAME);
	//
	// try
	// {
	// items = impl.getConfigurationItems(CONFIG, keys, null);
	// }
	// catch (Exception ex)
	// {
	// threw = true;
	// }
	//
	// assertFalse("Exception should not have been thrown", threw);
	// assertNotNull("Items should not be null", items);
	// assertTrue("Items should contain 3 items", items.size() == 3);
	//
	// ConfigurationItem item0 = items.get(0);
	// assertTrue("Items(0).key should be " + TEST_INT,
	// TEST_INT.equals(item0.getKey()));
	// assertTrue("Items(0).value should be 42",
	// "42".equals(item0.getValue()));
	//
	// ConfigurationItem item1 = items.get(1);
	// assertTrue("Items(1).key should be " + PORTAL,
	// PORTAL.equals(item1.getKey()));
	// assertTrue("Items(1).value should be http://portal1.perf.wildblue.net",
	// "http://portal1.perf.wildblue.net".equals(item1.getValue()));
	//
	// ConfigurationItem item2 = items.get(2);
	// assertTrue("Items(2).key should be " + APPLICATION_25_CONFIG_NAME,
	// APPLICATION_25_CONFIG_NAME.equals(item2.getKey()));
	// assertTrue("Items(2).value should be BATCH_ORDER",
	// "BATCH_ORDER".equals(item2.getValue()));
	// }
}
