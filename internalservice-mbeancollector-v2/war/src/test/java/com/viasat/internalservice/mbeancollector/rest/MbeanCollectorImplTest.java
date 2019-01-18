package com.viasat.internalservice.mbeancollector.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.viasat.internalservice.mbeancollector.persistence.MbeanCollectorPersistence;
import com.viasat.internalservice.mbeancollector.persistence.data.CollectionInfoType;
import com.viasat.internalservice.mbeancollector.persistence.data.CollectionInfosType;
import com.viasat.internalservice.mbeancollector.persistence.data.CollectorConfigType;
import com.viasat.internalservice.mbeancollector.persistence.data.DataBusConfigType;
import com.viasat.internalservice.mbeancollector.processor.MbeanProcessor;

/**
 * Created by sbryan on 1/9/2017.
 */
public class MbeanCollectorImplTest
{
	private MbeanCollectorPersistence mbeanCollectorPersistence;
	private MbeanProcessor mbeanProcessor;
	private CollectorConfigType collectorConfigType;
	private static final Logger LOGGER = LoggerFactory.getLogger(MbeanCollectorImplTest.class);

	@BeforeClass
	public static void setUpClass() throws ClassNotFoundException, NamingException, SQLException
	{

	}

	@Before
	public void setUp()
	{
		mbeanCollectorPersistence = mock(MbeanCollectorPersistence.class);
		mbeanProcessor = mock(MbeanProcessor.class);
		collectorConfigType = mock(CollectorConfigType.class);

	}

	/**
	 * Tear down the test set up.
	 */
	@After
	public void tearDown()
	{

	}

	@Test
	public void testConfig()
	{
		try
		{
			CollectorConfigType cct = loadCollectorConfigType();
			MbeanCollectorImpl mbeanCollector = new MbeanCollectorImpl();
			mbeanCollector.setMbeanCollectorPersistence(mbeanCollectorPersistence);
			when(mbeanCollectorPersistence.getCollectionInfo()).thenReturn(cct);

			mbeanCollector.setMbeanProcessor(mbeanProcessor);
			when(mbeanCollectorPersistence.getCollectionInfo()).thenReturn(collectorConfigType);
			CollectionInfosType collectionInfosType = mock(CollectionInfosType.class);
			when(collectorConfigType.getCollectionInfos()).thenReturn(collectionInfosType);
			List<CollectionInfoType> cit = new ArrayList<>();
			CollectionInfoType collectionInfoType = new CollectionInfoType();
			when(collectionInfosType.getCollectionInfo()).thenReturn(cit);

			Response response = mbeanCollector.onAction("config");
			assertNotNull(response);
			assertEquals(response.getStatus(), 200);

		}
		catch (Exception ex)
		{
			LOGGER.error("Exception when attempting configure ", ex);
		}
	}
	@Test
	public void testOnActionNotShutdown()
	{
		try
		{
			CollectorConfigType cct = loadCollectorConfigType();
			CollectionInfoType cit = cct.getCollectionInfos().getCollectionInfo().get(0);
			DataBusConfigType dbct = cct.getDataBusConfig();;
			MbeanCollectorImpl mbeanCollector = new MbeanCollectorImpl();
			mbeanCollector.setMbeanCollectorPersistence(mbeanCollectorPersistence);
			mbeanCollector.setMbeanProcessor(mbeanProcessor);
			when(mbeanCollectorPersistence.getCollectionInfo()).thenReturn(collectorConfigType);
			when(collectorConfigType.getCollectionInfos()).thenReturn(cct.getCollectionInfos());
			Response response = mbeanCollector.onAction("collect");
			assertNotNull(response);
			assertEquals(response.getStatus(), 200);
		}
		catch (Exception ex)
		{
			LOGGER.error("Exception when attempting configure ", ex);
		}
	}

	@Test
	public void testShutdown()
	{
		try
		{
			MbeanCollectorImpl mbeanCollector = new MbeanCollectorImpl();
			mbeanCollector.onShutdown();
			Response response = mbeanCollector.onTest();
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
	public void testCollectShutdown()
	{
		try
		{
			MbeanCollectorImpl mbeanCollector = new MbeanCollectorImpl();
			mbeanCollector.onShutdown();
			mbeanCollector.onCollect();
			Response response = mbeanCollector.onTest();
			assertNotNull(response);
			assertEquals(response.getStatus(), 200);
		}
		catch (Exception ex)
		{
			fail();
			LOGGER.error("Exception when attempting configure ", ex);
		}
	}

	private CollectorConfigType loadCollectorConfigType()
	{
		CollectorConfigType cct = null;
		try
		{
			InputStreamReader reader = new InputStreamReader(
					this.getClass().getResourceAsStream("/data/collectorConfig.xml"), "UTF-8");
			XMLStreamReader xsr = XMLInputFactory.newFactory().createXMLStreamReader(reader);
			xsr.nextTag(); // Advance to the first tag within "Body"
			Unmarshaller unmarshaller = JAXBContext.newInstance(CollectorConfigType.class)
					.createUnmarshaller();
			JAXBElement<CollectorConfigType> je = unmarshaller.unmarshal(xsr,
					CollectorConfigType.class);
			cct = je.getValue();
		}
		catch (Exception ex)
		{
			LOGGER.error("Exception loading collector config. ", ex);
		}
		return cct;
	}
}
