package com.viasat.internalservice.mbeancollector.persistence;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.sql.SQLException;

import javax.naming.NamingException;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.viasat.internalservice.fault.InternalServiceFault;
import com.viasat.internalservice.mbeancollector.persistence.data.CollectorConfigType;

/**
 * Created by sbryan on 1/6/2017.
 */
public class MbeanCollectorPersistenceTest
{
    private static final String CONFIGURATION_SERVICE_ENDPOINT = "configurationServiceEndpoint";
	@BeforeClass
    public static void setUpClass() throws ClassNotFoundException, NamingException, SQLException
    {

    }

	@Before
    public void setUp()
    {
        // setup configuration service
        if (System.getenv(CONFIGURATION_SERVICE_ENDPOINT) == null)
        {
            System.setProperty(
                    CONFIGURATION_SERVICE_ENDPOINT,
                    "http://iws-configuration.sandbox.dev.wdc1.wildblue.net/ConfigurationService/services/ConfigurationService");
        }
        else
        {
            System.setProperty(CONFIGURATION_SERVICE_ENDPOINT,
                    System.getenv(CONFIGURATION_SERVICE_ENDPOINT));
        }


    }

	/**
     * Tear down the test set up.
     */
    @After
    public void tearDown()
    {

    }

    @Test
	public void testMetricsCollectorPersistence()
	{
	    try {
            MbeanCollectorPersistence metricsCollectorPersistence = new MbeanCollectorPersistence();
            CollectorConfigType response = metricsCollectorPersistence.getCollectionInfo();
            assertNotNull(response);
        } catch (InternalServiceFault isf)
        {
            fail();
        }
	}
}