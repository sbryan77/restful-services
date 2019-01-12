package com.viasat.internalservice.querycollector.persistence.data;

import java.util.List;


public class DynamoResult
{
	private String collectionQuery;
	private String databusEndPoint;
	private String databusStreamName;
	private String metricDefinition;
	private List<String> tags;
	private int collectionInterval;

	public DynamoResult()
	{

	}

	public String getCollectorKey()
	{
		return collectorKey;
	}

	public void setCollectorKey(String collectorKey)
	{
		this.collectorKey = collectorKey;
	}

	private String collectorKey;

	public String getCollectionQuery()
	{
		return collectionQuery;
	}

	public void setCollectionQuery(String collectionQuery)
	{
		this.collectionQuery = collectionQuery;
	}

	public String getDatabusEndPoint()
	{
		return databusEndPoint;
	}

	public void setDatabusEndPoint(String databusEndPoint)
	{
		this.databusEndPoint = databusEndPoint;
	}

	public String getDatabusStreamName()
	{
		return databusStreamName;
	}

	public void setDatabusStreamName(String databusStreamName)
	{
		this.databusStreamName = databusStreamName;
	}

	public String getMetricDefinition()
	{
		return metricDefinition;
	}

	public void setMetricDefinition(String metricDefinition)
	{
		this.metricDefinition = metricDefinition;
	}

	public List<String> getTags()
	{
		return tags;
	}

	public void setTags(List<String> tags)
	{
		this.tags = tags;
	}

	public void setCollectionInterval(int collectionInterval)
	{
		this.collectionInterval = collectionInterval;
	}

	public int getCollectionInterval()
	{
		return collectionInterval;
	}

}
