package com.viasat.internalservice.querycollector.persistence;

import static org.junit.Assert.fail;

import java.sql.SQLException;

import javax.naming.NamingException;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
//import com.viasat.internalservice.querycollector.persistence.data.CollectorConfigType;

/**
 * Created by sbryan on 1/6/2017.
 */
public class QueryCollectorPersistenceTest
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

	 @Ignore @Test
	public void testMetricsCollectorPersistence()
	{
	    try {
            QueryCollectorPersistence metricsCollectorPersistence = new QueryCollectorPersistence();
//            CollectorConfigType response = metricsCollectorPersistence.getCollectionInfo();
//            assertNotNull(response);
        } catch (Exception e )//InternalServiceFault isf)
        {
            fail();
        }
	}
}