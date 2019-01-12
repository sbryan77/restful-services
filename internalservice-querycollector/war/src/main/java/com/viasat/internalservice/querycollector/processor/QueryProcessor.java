package com.viasat.internalservice.querycollector.processor;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.viasat.internalservice.fault.InternalServiceFault;
import com.viasat.internalservice.querycollector.persistence.QueryDynamo;
import com.viasat.internalservice.querycollector.persistence.QueryResponse;
import com.viasat.internalservice.querycollector.persistence.QuerySql;
import com.viasat.internalservice.querycollector.persistence.data.DynamoResult;
import com.viasat.internalservice.querycollector.props.LoadCollectorProps;

import io.viasat.databus.api.Databus;

/**
 * Created by sbryan on 12/5/2016.
 */
public class QueryProcessor
{
	private static final Logger LOGGER = LoggerFactory.getLogger(QueryProcessor.class);

	private SelfMonitorProcessor selfMonitorProcessor;
	private Map tags;
	private Map<String, Double> metricsCache = new HashMap<>();
	private QueryDynamo queryDynamo;
	private QuerySql querySql;
	private GenericRecord metricData;
	private Databus bus;
	private static LoadCollectorProps loadCollectorProps;

	public void collect(String collectorIds, Long unixTime) throws InternalServiceFault
	{
		loadCollectorProps.readProperties();
		long startUnixTime = System.currentTimeMillis() / 1000L;
		LOGGER.debug("Start collection time: " + startUnixTime);
		Connection connection = null;
		String tableName = loadCollectorProps.getAwsCollectorTableName();
		String region = loadCollectorProps.getRegion();
		try
		{
			List<DynamoResult> dynamoResults = queryDynamo.getCollectorInfo(collectorIds, region,
					tableName);

			// DataSource dataSource = querySql.getDataSource();
			connection = querySql.getConnection();

			if (dynamoResults != null)
			{
				for (DynamoResult dynamoResult : dynamoResults)
				{
					if (dynamoResult.getCollectionQuery() != null)
					{
						List<QueryResponse> queryResponses = querySql.getMetrics(dynamoResult);
						if (queryResponses != null && queryResponses.size() > 0)
						{
							sendMetrics(dynamoResult, queryResponses, startUnixTime);
						}
					}
				}
			}

			long endUnixTime = System.currentTimeMillis() / 1000L;
			LOGGER.info("Total collection time in seconds [" + (endUnixTime - startUnixTime) + "]");
			LOGGER.debug("End collection time: " + endUnixTime);
			selfMonitorProcessor.selfMonitor(dynamoResults, startUnixTime, endUnixTime,
					collectorIds);
		}
		catch (Exception e)
		{
			LOGGER.error("Exception collecting ", e);
		}
		finally
		{
			try
			{
				if (connection != null)
				{
					connection.close();
				}
			}
			catch (Exception closeEx)
			{
				LOGGER.error("Exception closing SQL Connection: ", closeEx);
			}
		}
	}

	private void sendMetrics(DynamoResult dynamoResult, List<QueryResponse> queryResponses,
			long startUnixTime)
	{
		int metricSentCount = 0;
		try
		{
			idbSetup(dynamoResult);
			for (QueryResponse queryResponse : queryResponses)
			{
				metricSentCount++;
				LOGGER.info("Sending metric for [" + queryResponse.getMetricName() + "]  Tags "
						+ queryResponse.getTags().toString() + "Field [" + queryResponse.getField()
						+ "]");
				metricData.put("timestamp", startUnixTime);
				metricData.put("metric", dynamoResult.getMetricDefinition());
				metricData.put("value", queryResponse.getField());
				metricData.put("tags", queryResponse.getTags());
				bus.produce("1", metricData);
			}
		}
		catch (Exception e)
		{
			LOGGER.warn("Exception attempting to send to IDB: ", e);
		}
		finally
		{
			LOGGER.info("Metric sent count [" + metricSentCount + "]");
			closeBus();
		}
	}

	private void idbSetup(DynamoResult dynamoResult)
	{
		try
		{
			if (bus != null)
			{
				closeBus();
			}
			LOGGER.debug("Creating new bus connection");
			bus = new Databus.Builder().endpoint(dynamoResult.getDatabusEndPoint())
					.stream(dynamoResult.getDatabusStreamName()).build();
			List<Schema> schemas = bus.getSchemas();
			Schema schema = schemas.get(0);
			LOGGER.debug("Databus endpoint[" + dynamoResult.getDatabusEndPoint() + "] Stream Name["
					+ dynamoResult.getDatabusStreamName() + "]");

			metricData = new GenericData.Record(schema);
			bus.initProducer();
			tags = new HashMap();
		}
		catch (Exception ex)
		{
			LOGGER.error("Exception during idbsetup", ex);
			closeBus();
		}
	}

	private void closeBus()
	{
		try
		{
			LOGGER.debug("Closing ");
			if (bus != null)
			{
				LOGGER.debug("Setting bus to null");
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

	public void setQueryDynamo(QueryDynamo queryDynamo)
	{
		this.queryDynamo = queryDynamo;
	}

	public QueryDynamo getQueryDynamo()
	{
		return this.queryDynamo;
	}

	public void setQuerySql(QuerySql querySql)
	{
		this.querySql = querySql;
	}

	public QuerySql getQuerySql()
	{
		return this.querySql;
	}

	public void setSelfMonitorProcessor(SelfMonitorProcessor selfMonitorProcessor)
	{
		LOGGER.debug("setting self monitor processor");
		this.selfMonitorProcessor = selfMonitorProcessor;
	}

	public SelfMonitorProcessor getSelfMonitorProcessor()
	{
		return this.selfMonitorProcessor;
	}

	public LoadCollectorProps getLoadCollectorProps()
	{
		return loadCollectorProps;
	}

	public void setLoadCollectorProps(LoadCollectorProps loadCollectorProps)
	{
		this.loadCollectorProps = loadCollectorProps;

	}
}
