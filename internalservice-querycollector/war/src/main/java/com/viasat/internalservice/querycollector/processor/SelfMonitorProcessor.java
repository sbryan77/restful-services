package com.viasat.internalservice.querycollector.processor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.common.errors.TimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.viasat.internalservice.fault.InternalServiceFault;
import com.viasat.internalservice.querycollector.persistence.data.DynamoResult;

import io.viasat.databus.api.Databus;

public class SelfMonitorProcessor
{
	private static final Logger LOGGER = LoggerFactory.getLogger(SelfMonitorProcessor.class);
	private GenericRecord metricData;
	private Databus bus;
	private Map tags;
	private static final String MONITOR_METRIC = "MonitorElapsedTime";
	private static final String COLLECTOR_TYPE = "queryCollector";

	public void selfMonitor(List<DynamoResult> dymanoResults, Long startUnixTime, Long endUnixTime,
			String collectorIds) throws InternalServiceFault
	{
		try
		{
			if (dymanoResults != null && !dymanoResults.isEmpty())
			{
				idbSetup(dymanoResults.get(0));
				sendMetric(endUnixTime - startUnixTime, dymanoResults.get(0), endUnixTime,
						collectorIds);
				closeBus();
			}
			else
			{
				LOGGER.warn("dynamoResults was empty.");
			}
		}
		catch (Exception e)
		{
			LOGGER.error("Exception processing self monitoring metrics: ", e);
		}
	}

	private void idbSetup(DynamoResult dynamoResult) throws InternalServiceFault
	{
		try
		{
			LOGGER.debug("Databus endpoint[" + dynamoResult.getDatabusEndPoint() + "] Stream Name["
					+ dynamoResult.getDatabusStreamName() + "]");
			if (bus != null)
			{
				closeBus();
			}
			bus = new Databus.Builder().endpoint(dynamoResult.getDatabusEndPoint())
					.stream(dynamoResult.getDatabusStreamName()).build();
			List<Schema> schemas = bus.getSchemas();
			Schema schema = schemas.get(0);
			metricData = new GenericData.Record(schema);
			bus.initProducer();
			tags = new HashMap();
		}
		catch (Exception ex)
		{
			LOGGER.error("Exception during idbsetup", ex);
			throw new InternalServiceFault("Databus setup exception");

		}
	}

	private void sendMetric(Long metricValue, DynamoResult dynamoResult, long unixTime,
			String collectorIds) throws InternalServiceFault
	{
		try
		{
			LOGGER.debug("sending metric for self monitoring");
			tags.clear();
			tags.put("collectorType", COLLECTOR_TYPE);
			tags.put("collectionQuery", dynamoResult.getCollectionQuery());
			tags.put("collectorIds", collectorIds);
			metricData.put("timestamp", unixTime);
			metricData.put("metric", MONITOR_METRIC);
			metricData.put("value", metricValue);
			metricData.put("tags", tags);
			LOGGER.debug("Sending metric [" + MONITOR_METRIC + "] value [" + metricValue
					+ "] unixtime [" + unixTime + "] tags [" + tags.toString() + "]");
			bus.produce("1", metricData);
		}
		catch (TimeoutException te)
		{
			LOGGER.warn("Timeout error attempting to send to IDB. Processing will continue.");
		}
		catch (Exception ex)
		{
			LOGGER.error("Exception thrown attempting to send metric", ex);
			throw new InternalServiceFault("Databus send metric exception");
		}
	}

	private void closeBus()
	{
		try
		{
			if (bus != null)
			{
				bus.close();
				bus.closeProducers();
				bus = null;
			}
		}
		catch (Exception e)
		{
			LOGGER.warn("Exception trying to clean up open databus connections." + e.getMessage());
		}
	}
}
