package com.viasat.internalservice.mbeancollector.rest;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.viasat.internalservice.fault.InternalServiceFault;
import com.viasat.internalservice.mbeancollector.api.MbeanCollectorApi;
import com.viasat.internalservice.mbeancollector.api.model.Test;
import com.viasat.internalservice.mbeancollector.persistence.MbeanCollectorPersistence;
import com.viasat.internalservice.mbeancollector.persistence.data.CollectorConfigType;
import com.viasat.internalservice.mbeancollector.processor.MbeanProcessor;
import com.viasat.internalservice.metricscollector.rest.IMetricCollection;
import com.yammer.metrics.Metrics;

public class MbeanCollectorImpl implements MbeanCollectorApi, IMetricCollection
{
	private static final Logger LOGGER = LoggerFactory.getLogger(MbeanCollectorImpl.class);
	private MbeanProcessor mbeanProcessor;
	private boolean m_ConfigLoaded = false;
	private MbeanCollectorPersistence mbeanCollectorPersistence;
	private CollectorConfigType collectorConfigType;
	private boolean shutdown = false;
	@Value("#{environment['COLLECTOR_ID']}")
	private String collectorId;

	public enum CollectorStatus
	{
		CONFIGURING, CONFIGURED, COLLECTING, SHUTTINGDOWN, ERRORSTATE;
	}

	private OffsetDateTime lastCollection;
	private CollectorStatus lastStatus;

	@Override
	public Response onAction(String action) throws InternalServiceFault
	{
		Response response;
		switch (action.toLowerCase())
		{
		case "config":
			lastStatus = CollectorStatus.CONFIGURING;
			onConfigure();
			lastStatus = CollectorStatus.CONFIGURED;
			response = Response.status(Response.Status.OK).build();
			break;
		case "collect":
			lastStatus = CollectorStatus.COLLECTING;
			onCollect();
			response = Response.status(Response.Status.OK).build();
			break;
		case "shutdown":
			onShutdown();
			lastStatus = CollectorStatus.SHUTTINGDOWN;
			response = Response.status(Response.Status.OK).build();
			break;
		default:
			response = Response.status(Response.Status.BAD_REQUEST).build();
		}

		return response;
	}

	public void setMbeanCollectorPersistence(MbeanCollectorPersistence mbeanCollectorPersistence)
	{
		LOGGER.debug("setting persistence thing");
		this.mbeanCollectorPersistence = mbeanCollectorPersistence;
	}

	public MbeanCollectorPersistence getMbeanCollectorPersistence()
	{
		return this.mbeanCollectorPersistence;
	}

	public void setMbeanProcessor(MbeanProcessor mbeanProcessor)
	{
		LOGGER.debug("setting mbean processor");
		this.mbeanProcessor = mbeanProcessor;
	}

	public MbeanProcessor getMbeanProcessor()
	{
		return mbeanProcessor;
	}

	@Override
	public void onCollect() throws InternalServiceFault
	{
		// create an executable pool for the number of items in the config
		// for each item in the config start the thread
		LOGGER.debug("Entering onCollect");
		LOGGER.debug("Current CollectorId: " + collectorId);
		long unixTime = System.currentTimeMillis() / 1000L;
		LOGGER.info("Current Unix Time:[" + unixTime + "]");
		if (!shutdown)
		{
			if (!m_ConfigLoaded)
			{
				onConfigure();
			}
			else
			{
				LOGGER.debug("Config is already loaded, no need to reload.");
			}

			if (collectorConfigType != null)
			{
				LOGGER.debug("Calling MBean Collector Processor");
				long startUnixTime = System.currentTimeMillis() / 1000L;
				mbeanProcessor.collect(collectorConfigType, collectorId,
						collectorConfigType.getDataBusConfig(), unixTime);

				lastCollection = OffsetDateTime.now(ZoneOffset.UTC);
			}
			else
			{
				LOGGER.error("No configuration data to process.  No collection will be attempted.");
				throw new InternalServiceFault(
						"No configuration data to process.  No collection will be attempted.");
			}
		}
		else
		{
			LOGGER.info(
					"Shut Down has been requested.  No more collection will occur until the application is restarted.");
		}
	}

	@Override
	public void onConfigure() throws InternalServiceFault
	{
		LOGGER.debug("onConfigure executing");
		if (mbeanCollectorPersistence != null)
		{
			collectorConfigType = mbeanCollectorPersistence.getCollectionInfo();
			m_ConfigLoaded = true;
			shutdown = false;
		}
	}

	@Override
	public void onShutdown()
	{
		shutdown = true;
		lastStatus = CollectorStatus.SHUTTINGDOWN;
		Metrics.shutdown();
	}

	@Override
	public Response onTest()
	{
		Response response;
		Test test = new Test();
		test.setStatus(lastStatus.toString());
		if (lastCollection != null)
		{
			test.setLastCollection(lastCollection);
		}
		else
		{
			test.setLastCollection(null);
		}
		response = Response.ok(test).build();
		return response;
	}
}
