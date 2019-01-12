package com.viasat.internalservice.querycollector.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.viasat.internalservice.querycollector.persistence.data.DynamoResult;
import com.viasat.internalservice.querycollector.props.LoadCollectorProps;

public class QueryDynamo
{
	private static final Logger LOGGER = LoggerFactory.getLogger(QueryDynamo.class);
	private static LoadCollectorProps loadCollectorProps;

	public QueryDynamo()
	{

	}

	public List<DynamoResult> getCollectorInfo(String collectorIds, String region, String tableName)
	{
		String[] ids = collectorIds.split(",");
		List<DynamoResult> dynamoResults = new ArrayList<>();
		try
		{

			LOGGER.info("Ready to create db client");
			AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(region)
					.build();
			LOGGER.info("Client Created");
			DynamoDB dynamoDB = new DynamoDB(client);
			Table table = dynamoDB.getTable(tableName);
			HashMap<String, String> nameMap = new HashMap<String, String>();
			nameMap.put("#id", "collectorKey");
			HashMap<String, Object> valueMap = new HashMap<>();
			for (String id : ids)
			{
				valueMap.clear();
				valueMap.put(":collector", id);

				QuerySpec querySpec = new QuerySpec().withKeyConditionExpression("#id = :collector")
						.withNameMap(nameMap).withValueMap(valueMap);
				ItemCollection<QueryOutcome> items = null;
				Iterator<Item> iterator = null;
				Item item = null;

				items = table.query(querySpec);
				iterator = items.iterator();

				while (iterator.hasNext())
				{
					item = iterator.next();
					DynamoResult dynamoResult = new DynamoResult();
					dynamoResult.setCollectionQuery(item.getString("collectionQuery"));
					dynamoResult.setDatabusEndPoint(item.getString("databusEndpoint"));
					dynamoResult.setDatabusStreamName(item.getString("databusStreamName"));
					dynamoResult.setMetricDefinition(item.getString("metricDefinition"));
					dynamoResult.setTags(item.getList("tags"));
					dynamoResult.setCollectionInterval(item.getInt("collectionInterval"));
					dynamoResults.add(dynamoResult);
				}
			}
		}
		catch (Exception e)
		{
			LOGGER.error("exception performing dynamo query", e);
		}

		return dynamoResults;

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
