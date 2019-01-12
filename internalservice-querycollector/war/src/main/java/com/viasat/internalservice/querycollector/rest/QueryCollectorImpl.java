package com.viasat.internalservice.querycollector.rest;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.viasat.internalservice.fault.InternalServiceFault;
import com.viasat.internalservice.metricscollector.rest.IMetricCollection;
import com.viasat.internalservice.querycollector.api.QueryCollectorApi;
import com.viasat.internalservice.querycollector.api.model.Test;
import com.viasat.internalservice.querycollector.processor.QueryProcessor;
import com.viasat.internalservice.querycollector.processor.SelfMonitorProcessor;
import com.viasat.internalservice.querycollector.props.LoadCollectorProps;
import com.yammer.metrics.Metrics;

public class QueryCollectorImpl implements QueryCollectorApi, IMetricCollection
{
	private static final Logger LOGGER = LoggerFactory.getLogger(QueryCollectorImpl.class);
	private QueryProcessor queryProcessor;
	private SelfMonitorProcessor selfMonitorProcessor;
	private boolean m_ConfigLoaded = false;
	private LoadCollectorProps loadCollectorProps;

	private boolean shutdown = false;
	@Value("#{environment['COLLECTOR_IDS']}")
	private String collectorIds;
	@Value("#{environment['REGION']}")
	private String region;

	public enum CollectorStatus
	{
		COLLECTING, SHUTTINGDOWN;
	}

	private OffsetDateTime lastCollection;
	private CollectorStatus lastStatus;

	@Override
	public Response onAction(String action) throws InternalServiceFault
	{
		Response response;
		switch (action.toLowerCase())
		{
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

	public void setQueryProcessor(QueryProcessor queryProcessor)
	{
		LOGGER.debug("setting query processor");
		this.queryProcessor = queryProcessor;
	}

	public QueryProcessor getQueryProcessor()
	{
		return queryProcessor;
	}

	public void setLoadCollectorProps(LoadCollectorProps loadCollectorProps)
	{
		LOGGER.debug("setting  LoadCollectorProps");
		this.loadCollectorProps = loadCollectorProps;
	}

	public LoadCollectorProps getLoadCollectorProps()
	{
		return loadCollectorProps;
	}

	public void setSelfMonitorProcessor(SelfMonitorProcessor selfMonitorProcessor)
	{
		LOGGER.debug("setting self monitor processor");
		this.selfMonitorProcessor = selfMonitorProcessor;
	}

	@Override
	public void onCollect() throws InternalServiceFault
	{
		// create an executable pool for the number of items in the config
		// for each item in the config start the thread
		LOGGER.debug("Entering onCollect");
		LOGGER.debug("Current CollectorId: " + collectorIds);
		LOGGER.debug("Region: " + region);
		LOGGER.debug("Table: " + loadCollectorProps.getAwsCollectorTableName());
		long unixTime = System.currentTimeMillis() / 1000L;
		LOGGER.info("Current Unix Time:[" + unixTime + "]");
		if (!shutdown)
		{
			if (collectorIds != null)
			{
				LOGGER.debug("Calling Query Collector Processor");
				long startUnixTime = System.currentTimeMillis() / 1000L;
				queryProcessor.collect(collectorIds, unixTime);

				lastCollection = OffsetDateTime.now(ZoneOffset.UTC);
			}
			else
			{
				LOGGER.error("No collector ids identified.  No collection will be attempted.");
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
