package com.viasat.internalservice.mbeancollector.persistence;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.viasat.internalservice.fault.InternalServiceFault;
import com.viasat.internalservice.mbeancollector.persistence.data.CollectorConfigType;
import com.viasat.internalservice.mbeancollector.persistence.data.ConfigType;
import com.viasat.wildblue.common.exception.fault.WildBlueFaultException;
import com.viasat.wildblue.common.header.InvokedBy;
import com.viasat.wildblue.common.header.WildBlueHeader;
import com.viasat.wildblue.internalwebservice.configuration.ConfigManager;
import com.viasat.wildblue.internalwebservice.configuration.ConfigurationProxy;

/**
 * Created by sbryan on 12/1/2016.
 */

public class MbeanCollectorPersistence
{
	private static final Logger LOGGER = LoggerFactory.getLogger(MbeanCollectorPersistence.class);
	private String APPLICATION = "InternalService-MbeanCollector";
	private String USER = "monitoring";
	private static String CONFIG_DOC = "MBEAN_COLLECTOR_v2";
	private String CONFIG_KEY = "collectorConfig";
	private static final String CONFIGURATION_SERVICE_ENDPOINT = "configurationServiceEndpoint";
	@Value("#{environment['LOAD_LOCATION']}")
	private String loadLocation;

	public CollectorConfigType getCollectionInfo() throws InternalServiceFault {

        try {
            if (loadLocation == null) {
                WildBlueHeader wbHeader = new WildBlueHeader();
                InvokedBy invokedBy = new InvokedBy();
                invokedBy.setApplication(APPLICATION);
                invokedBy.setUsername(USER);
                wbHeader.setInvokedBy(invokedBy);
                String configDoc = getConfigDocument(CONFIG_DOC, wbHeader);
                LOGGER.debug("Config document retrieved: " + configDoc);
                InputStreamReader reader = new InputStreamReader(
                        new ByteArrayInputStream(configDoc.getBytes()), "UTF-8");
                XMLStreamReader xsr = XMLInputFactory.newFactory().createXMLStreamReader(reader);
                xsr.nextTag(); // Advance to the first tag within "Body"
                Unmarshaller unmarshaller = JAXBContext.newInstance(ConfigType.class)
                        .createUnmarshaller();
                JAXBElement<ConfigType> je = unmarshaller.unmarshal(xsr, ConfigType.class);

                ConfigType response = je.getValue();
                if (response == null) {
                    LOGGER.error(
                            "No data un-marshaled from the configuration file.  Collections will not occur.");
                    throw new InternalServiceFault("No config data found.");
                }
                reader.close();
                return response.getCollectorConfig();
            } else {
            	LOGGER.debug("Load Location: ]"+loadLocation+"]");

                InputStreamReader reader = new InputStreamReader(this.getClass().getResourceAsStream(loadLocation), "UTF-8");
                XMLStreamReader xsr = XMLInputFactory.newFactory().createXMLStreamReader(reader);
                xsr.nextTag(); // Advance to the first tag within "Body"
                Unmarshaller unmarshaller = JAXBContext.newInstance(ConfigType.class).createUnmarshaller();
                JAXBElement<ConfigType> je = unmarshaller.unmarshal(xsr, ConfigType.class);
                ConfigType response = je.getValue();
                return response.getCollectorConfig();
            }
        } catch (Exception e) {
            LOGGER.error("Exception occurred in persistence: ", e);
            throw new InternalServiceFault("Exception occurred in persistence");
        }
    }



	private static String getConfigDocument(String key, WildBlueHeader header)
	{
		ConfigurationProxy config = ConfigManager.getConfigurationProxy(CONFIG_DOC, header);
		String doc;

		try
		{
			doc = config.getConfigurationDocument();
		}
		catch (Exception e)
		{
			LOGGER.error("caught getting Configuration document", e);
			throw new WildBlueFaultException(e.getMessage(), e);
		}

		return doc;
	}

	public void setUp()
	{
		// setup configuration service
		if (System.getenv(CONFIGURATION_SERVICE_ENDPOINT) == null)
		{
			System.setProperty(CONFIGURATION_SERVICE_ENDPOINT,
					"http://iws-configuration.sandbox.dev.wdc1.wildblue.net/ConfigurationService/services/ConfigurationService");
		}
		else
		{
			System.setProperty(CONFIGURATION_SERVICE_ENDPOINT,
					System.getenv(CONFIGURATION_SERVICE_ENDPOINT));
		}
		LOGGER.debug("Using -D" + CONFIGURATION_SERVICE_ENDPOINT + "="
				+ System.getProperty(CONFIGURATION_SERVICE_ENDPOINT));
	}
}
