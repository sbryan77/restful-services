package com.viasat.internalservice.mbeancollector.processor;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import org.apache.avro.Schema;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.viasat.internalservice.mbeancollector.persistence.MbeanCollectorPersistence;
import com.viasat.internalservice.mbeancollector.persistence.data.CollectionInfoType;
import com.viasat.internalservice.mbeancollector.persistence.data.CollectionInfosType;
import com.viasat.internalservice.mbeancollector.persistence.data.CollectionSourceType;
import com.viasat.internalservice.mbeancollector.persistence.data.CollectorConfigType;
import com.viasat.internalservice.mbeancollector.persistence.data.ConfigType;
import com.viasat.internalservice.mbeancollector.persistence.data.CredentialsType;
import com.viasat.internalservice.mbeancollector.persistence.data.DataBusConfigType;
import com.viasat.internalservice.mbeancollector.processor.thread.MbeanCollect;

import io.viasat.databus.util.AvroUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest(
{ MbeanProcessor.class, Thread.class })
public class MbeanProcessorTest
{
	private MbeanCollectorPersistence mbeanCollectorPersistence;
	private MbeanProcessor mbeanProcessor;
	private CollectorConfigType collectorConfigType;
	private static final Logger LOGGER = LoggerFactory
			.getLogger(com.viasat.internalservice.mbeancollector.rest.MbeanCollectorImplTest.class);
	private static final String collectionQuery = "https://fcd-provisioningvolubill01.dev.sandbox.wdc1.wildblue.net:8443/manager/jmxproxy/?qry=*:type=Performance.Counter.Server,* ";
	private String versionFilter = "ProvisioningVolubill-v4";
	private static final String COLLECTOR_ID = "collector1";

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
	public void testRuntimeException()
	{
		// The expectation is when collectorConfigType.getCredentials().getUser(); is called in the thread
		// it will throw a NPE since collectorConfigType is a mock.
		try
		{
            List<CollectionInfoType> citList = new ArrayList<>();
			ConfigType cct = loadCollectorConfigType();
			CollectorConfigType collectorConfig = mock(CollectorConfigType.class);
			String mbeanData = loadMbeanData();
			MbeanProcessor mbp = new MbeanProcessor();
			CollectionInfoType cit = mock(CollectionInfoType.class);
			citList.add(cit);
            CollectionInfosType cist = mock(CollectionInfosType.class);
			CredentialsType ct = mock(CredentialsType.class);
			CollectionSourceType cst = mock(CollectionSourceType.class);
			DataBusConfigType dbct = mock(DataBusConfigType.class);
			MbeanCollect mbeanCollect = mock(MbeanCollect.class);
            when(collectorConfigType.getCollectionInfos()).thenReturn(cist);
            when(cist.getCollectionInfo()).thenReturn(citList);
			when(cit.getCollectionSource()).thenReturn(cst);
			when(cst.getCollectionQuery()).thenReturn(collectionQuery);
			when(collectorConfig.getCollectionInfos()).thenReturn(cist);
			when(cist.getCollectionInfo()).thenReturn(citList);
			when(cit.getAssignedCollector()).thenReturn("collector1");

			long unixTime = System.currentTimeMillis() / 1000L;
			mbp.collect(collectorConfigType, COLLECTOR_ID, dbct,unixTime);

		}
		catch (NullPointerException npe)
		{
			LOGGER.info("NPE expected test will pass ");
		}
		catch (Exception ex)
		{
			LOGGER.error("Exception when attempting configure ", ex);
			fail();
		}
	}
	@Test
	public void testCollect()
	{
		try
		{
			List<CollectionInfoType> citList = new ArrayList<>();
			ConfigType cct = loadCollectorConfigType();
			CollectorConfigType collectorConfig = mock(CollectorConfigType.class);
			String mbeanData = loadMbeanData();
			MbeanProcessor mbp = new MbeanProcessor();
			CollectionInfoType cit = mock(CollectionInfoType.class);
			citList.add(cit);
			CollectionInfosType cist = mock(CollectionInfosType.class);
			CredentialsType ct = mock(CredentialsType.class);
			CollectionSourceType cst = mock(CollectionSourceType.class);
			DataBusConfigType dbct = mock(DataBusConfigType.class);
			MbeanCollect mbeanCollect = mock(MbeanCollect.class);
			whenNew(MbeanCollect.class).withAnyArguments().thenReturn(mbeanCollect);
			Thread mockThread = mock(Thread.class);
			whenNew(Thread.class).withAnyArguments().thenReturn(mockThread);

			when(collectorConfigType.getCollectionInfos()).thenReturn(cist);
			when(cist.getCollectionInfo()).thenReturn(citList);
			when(cit.getCollectionSource()).thenReturn(cst);
			when(cst.getCollectionQuery()).thenReturn(collectionQuery);
			when(collectorConfig.getCollectionInfos()).thenReturn(cist);
			when(cist.getCollectionInfo()).thenReturn(citList);
			when(cit.getAssignedCollector()).thenReturn("collector1");

			long unixTime = System.currentTimeMillis() / 1000L;
			mbp.collect(collectorConfigType, COLLECTOR_ID, dbct,unixTime);

		}
		catch (Exception ex)
		{
			LOGGER.error("Exception when attempting configure ", ex);
			ex.printStackTrace();
			fail();
		}
	}
	private ConfigType loadCollectorConfigType()
	{
		ConfigType ct = null;
		try
		{
			InputStreamReader reader = new InputStreamReader(
					this.getClass().getResourceAsStream("/data/collectorConfig.xml"), "UTF-8");
			XMLStreamReader xsr = XMLInputFactory.newFactory().createXMLStreamReader(reader);
			xsr.nextTag(); // Advance to the first tag within "Body"
			Unmarshaller unmarshaller = JAXBContext.newInstance(ConfigType.class)
					.createUnmarshaller();
			JAXBElement<ConfigType> je = unmarshaller.unmarshal(xsr,
					ConfigType.class);
			ct = je.getValue();
		}
		catch (Exception ex)
		{
			LOGGER.error("Exception loading collector config. ", ex);
		}
		return ct;
	}
	private String loadMbeanData() {
		String mbeanData = new String();
		try {
			BufferedReader inReader = new BufferedReader(new InputStreamReader(
					this.getClass().getResourceAsStream("/data/mbeanData.txt")));
			String inputLine;
			mbeanData = inReader.readLine();
			}
		catch (Exception e){
			LOGGER.error("Error getting unit test mbean data",e);
		}
		return mbeanData;
	}

	private Schema loadSchema(){
		try {
			System.out.println("path: "+this.getClass().getResource("/data/denveritmbean.json").toString());
			File schemaFile = new File(this.getClass().getResource("/data/denveritmbean.json").toURI());
			Schema schema = AvroUtils.getSchemaFromFile(schemaFile);
			//Schema schema = AvroUtils.getSchemaFromFile(this.getClass().getResource("./data/schema.json").toString());
			return  schema;
		}catch (Exception e){
			LOGGER.error("error loading schema file ",e);
			e.printStackTrace();
		}
		return null;
}

}
