package com.viasat.internalservice.querycollector.rest;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

import java.sql.SQLException;

import javax.naming.NamingException;
import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.viasat.internalservice.querycollector.persistence.QueryCollectorPersistence;
import com.viasat.internalservice.querycollector.processor.QueryProcessor;
import com.viasat.internalservice.querycollector.props.LoadCollectorProps;

//@RunWith(PowerMockRunner.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:applicationContext.xml"})
public class QueryCollectorImplTest
{
    static {
        System.setProperty("COLLECTOR_IDS", "query001");
        System.setProperty("REGION","us-west-2");
        System.setProperty("TABLE_NAME","CollectionQueryMetrics");
    }
    private QueryCollectorImpl qci;
    private QueryCollectorPersistence queryCollectorPersistence;
	private QueryProcessor queryProcessor;

	private static final Logger LOGGER = LoggerFactory.getLogger(QueryCollectorImplTest.class);

	@BeforeClass
	public static void setUpClass() throws ClassNotFoundException, NamingException, SQLException
	{

	}

	@Before
	public void setUp()
	{
		queryCollectorPersistence = mock(QueryCollectorPersistence.class);
		queryProcessor = mock(QueryProcessor.class);


	}

	/**
	 * Tear down the test set up.
	 */
	@After
	public void tearDown()
	{

	}


	@Test
	public void testOnActionNotShutdown()
	{
		try
		{

            QueryCollectorImpl qci = new QueryCollectorImpl();
            qci.setQueryProcessor(queryProcessor);

            ReflectionTestUtils.setField(qci, "collectorIds", "query001");
			ReflectionTestUtils.setField(qci, "region", "us-west-2");
			ReflectionTestUtils.setField(qci, "tableName", "CollectionQueryMetrics");
			Response response = qci.onAction("collect");
			assertNotNull(response);
			assertEquals(response.getStatus(), 200);
		}
		catch (Exception ex)
		{
			LOGGER.error("Exception when attempting to collect ", ex);
		}
	}

	@Test
	public void testOnActionShutdown()
	{
		try
		{

			QueryCollectorImpl qci = new QueryCollectorImpl();
			qci.setQueryProcessor(queryProcessor);

			ReflectionTestUtils.setField(qci, "collectorIds", "query001");
			ReflectionTestUtils.setField(qci, "region", "us-west-2");
			ReflectionTestUtils.setField(qci, "tableName", "CollectionQueryMetrics");
			qci.onShutdown();
			Response response = qci.onAction("collect");
			assertNotNull(response);
			assertEquals(response.getStatus(), 200);
		}
		catch (Exception ex)
		{
			LOGGER.error("Exception when attempting to collect ", ex);
		}
	}

	@Test
	public void testShutdown()
	{
		try
		{
			QueryCollectorImpl qci = new QueryCollectorImpl();
            qci.onShutdown();
			Response response = qci.onTest();
			assertNotNull(response);
			assertEquals(response.getStatus(), 200);
		}
		catch (Exception ex)
		{
			fail();
			LOGGER.error("Exception when attempting configure ", ex);
		}
	}
	@Test
	public void testSetLoadProperties() {
		LoadCollectorProps loadCollectorProps = new LoadCollectorProps();
	    QueryCollectorImpl qci = new QueryCollectorImpl();
		qci.setLoadCollectorProps(loadCollectorProps);
		LoadCollectorProps lqpResp = qci.getLoadCollectorProps();
		assertEquals(loadCollectorProps,lqpResp);
	}
	@Test
	public void testSetQueryProcessor() {
		QueryProcessor qp = new QueryProcessor();
		QueryCollectorImpl qci = new QueryCollectorImpl();
		qci.setQueryProcessor(qp);
		QueryProcessor qpResp = qci.getQueryProcessor();
		assertEquals(qp,qpResp);
	}
	@Test
	public void testConfigure()
	{
		try
		{
			QueryCollectorImpl qci = new QueryCollectorImpl();
			qci.onConfigure();
		}
		catch (Exception ex)
		{
			fail();
			LOGGER.error("Exception when attempting configure ", ex);
		}
	}
//	@Test
//	public void testCollectShutdown()
//	{
//		try
//		{
//			QueryCollectorImpl mbeanCollector = new QueryCollectorImpl();
//			mbeanCollector.onShutdown();
//			mbeanCollector.onCollect();
//			Response response = mbeanCollector.onTest();
//			assertNotNull(response);
//			assertEquals(response.getStatus(), 200);
//		}
//		catch (Exception ex)
//		{
//			fail();
//			LOGGER.error("Exception when attempting configure ", ex);
//		}
//	}

}
