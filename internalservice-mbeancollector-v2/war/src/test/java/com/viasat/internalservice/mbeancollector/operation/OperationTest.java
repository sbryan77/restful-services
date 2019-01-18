package com.viasat.internalservice.mbeancollector.operation;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;

import javax.naming.NamingException;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.viasat.internalservice.mbeancollector.operations.Operation;

public class OperationTest
{
	private static final String VERSION_INFO = "v0.1";
	private static final String METRIC_NAME = "WebserviceAverageResponseTimeV2";
	private static final Double METRIC_VALUE = 24746.242038216562;

	@BeforeClass
	public static void setUpClass() throws ClassNotFoundException, NamingException, SQLException
	{

	}

	@Before
	public void setUp()
	{

	}

	/**
	 * Tear down the test set up.
	 */
	@After
	public void tearDown()
	{

	}

	@Test
    public void testRuntimeException() {

        Operation operation = new Operation("Operation String",VERSION_INFO);
        String versionInfo = operation.getVersionInfo();
        assertEquals(versionInfo,VERSION_INFO);
        operation.addMetric(METRIC_NAME,METRIC_VALUE);
        Double fetchedMetricValue = operation.getMetric(METRIC_NAME);
        assertEquals(fetchedMetricValue,METRIC_VALUE);
    }

}
