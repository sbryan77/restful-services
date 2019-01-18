package com.viasat.internalservice.mbeancollector.processor.thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.management.UnixOperatingSystemMXBean;
import com.viasat.internalservice.fault.InternalServiceFault;
import com.viasat.internalservice.mbeancollector.operations.Operation;
import com.viasat.internalservice.mbeancollector.persistence.data.CollectionInfoType;
import com.viasat.internalservice.mbeancollector.persistence.data.CollectorConfigType;
import com.viasat.internalservice.mbeancollector.persistence.data.DataBusConfigType;
import com.viasat.internalservice.mbeancollector.persistence.data.MetricDefinitionType;
import com.viasat.internalservice.mbeancollector.persistence.data.MetricDefinitionsType;
import com.viasat.internalservice.mbeancollector.persistence.data.TagType;
import com.viasat.internalservice.mbeancollector.persistence.data.TagsType;
import com.viasat.internalservice.mbeancollector.processor.exception.CollectionInterruptionException;

import io.viasat.databus.api.Databus;

public class MbeanCollect extends Thread
{
	private static final Logger LOGGER = LoggerFactory.getLogger(MbeanCollect.class);
	private GenericRecord metricData;
	private Databus bus;
	private static final int TIME_OUT = 5000;
	private Map tags;
	private Map<String, Double> metricsCache;
	private static final String FILTER_TAG = "Name: org.apache.cxf:bus.id=";
	private static final String OPERATION_EQUAL = "operation=";
	private static final String COLON = ":";
	private static final String WEBSERVICE_INVOCATION_COUNT = "WebserviceInvocationCountV2";
	private static final String WEBSERVICE_TOTAL_HANDLING_TIME = "WebserviceTotalHandlingTimeV2";
	private static final String WEBSERVICE_UNCHECKED_FAULTS = "WebserviceNumUnCheckedApplicationFaultsV2";
	private static final String WEBSERVICE_CHECKED_FAULTS = "WebserviceNumCheckedApplicationFaultsV2";
	private static final String WEBSERVICE_LOGICAL_FAULTS = "WebserviceClientNumLogicalRuntimeFaultsv2";
	private static final String WEBSERVICE_CLIENT_INVOCATION_COUNT = "WebserviceClientInvocationCount";
	private static final String WEBSERVICE_CLIENT_TOTAL_HANDLING_TIME = "WebserviceClientTotalHandlingTime";
	private static final String WEBSERVICE_CLIENT_UNCHECKED_FAULTS = "WebserviceClientNumUnCheckedApplicationFaults";
	private static final String WEBSERVICE_CLIENT_CHECKED_FAULTS = "WebserviceClientNumCheckedApplicationFaults";
	private static final String WEBSERVICE_CLIENT_LOGICAL_FAULTS = "WebserviceClientNumLogicalRuntimeFaults";
	private static final String TOTAL_HANDLING_TIME = "TotalHandlingTime";
	private static final String NUM_UNCHECKED_FAULTS = "NumUnCheckedApplicationFaults";
	private static final String NUM_CHECKED_FAULTS = "NumCheckedApplicationFaults";
	private static final String NUM_LOGICAL_FAULTS = "NumLogicalRuntimeFaults";
	private static final String MONITOR_METRIC = "MonitorElapsedTime";
	private static final String CLIENT = "Client";
	private static final String PERFORMANCE_COUNTER_CLIENT = "Performance.Counter.Client";
	private static final long COLLECTION_ERROR_TIME = 9999l;
	private static String metricSuffix = "";
	private CollectorConfigType collectorConfigType;
	private String collectorId;
	private DataBusConfigType dataBusConfigType;
	private Long unixTime;
	private CollectionInfoType collectionInfoType;

	public MbeanCollect(CollectionInfoType collectionInfoType,
			CollectorConfigType collectorConfigType, String collectorId,
			DataBusConfigType dataBusConfigType, Long unixTime, Map<String, Double> metricsCache)
			throws InternalServiceFault
	{
		this.collectorConfigType = collectorConfigType;
		this.collectorId = collectorId;
		this.dataBusConfigType = dataBusConfigType;
		this.unixTime = unixTime;
		this.collectionInfoType = collectionInfoType;
		this.metricsCache = metricsCache;
		if (dataBusConfigType.getStreamName().contains("test"))
		{
			metricSuffix = "test";
		} else if (dataBusConfigType.getStreamName().contains("dev"))
		{
			metricSuffix = "dev";
		}
		LOGGER.info("Metric suffix is ["+metricSuffix+"]");
	}

	@Override
	public void run()
	{
		StringBuffer collectedData = null;
		String previousCollectionQuery = null;
		List<String> queryResponseLines = new ArrayList<>();
		String user = collectorConfigType.getCredentials().getUser();
		String password = collectorConfigType.getCredentials().getPassword();
		String userpass = user + ":" + password;
		String basicAuth = "Basic "
				+ javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());
		LOGGER.debug("Opening connection to: "
				+ collectionInfoType.getCollectionSource().getCollectionQuery());

		BufferedReader inReader = null;
		URLConnection myURLConnection = null;
		try
		{
			String currentService = getTagValue(collectionInfoType.getCollectionSource().getTags(),
					"service");
			LOGGER.debug("Collecting data for service: [" + currentService + "]");
			if (previousCollectionQuery != null || !collectionInfoType.getCollectionSource()
					.getCollectionQuery().equals(previousCollectionQuery))
			{
				URL myURL = new URL(collectionInfoType.getCollectionSource().getCollectionQuery());
				myURLConnection = myURL.openConnection();
				myURLConnection.setConnectTimeout(TIME_OUT);
				myURLConnection.setReadTimeout(TIME_OUT);
				myURLConnection.setRequestProperty("Authorization", basicAuth);
				myURLConnection.connect();
				inReader = new BufferedReader(
						new InputStreamReader(myURLConnection.getInputStream()));
				String inputLine;
				queryResponseLines.clear();
				while ((inputLine = inReader.readLine()) != null)
				{
					if (this.isInterrupted())
					{
						throw new InterruptedException("Interruped during Read loop");
					}
					queryResponseLines.add(inputLine);
				}
			}
			Map<String, Operation> operationMap = removeUnneededLines(queryResponseLines);
			boolean isSOAP = checkIsSOAP(currentService);
			String versionFilter = getTagValue(collectionInfoType.getCollectionSource().getTags(),
					"version");
			collectedData = new StringBuffer();
			Map<String, Operation> activeMetrics = new HashMap<>();
			LOGGER.debug("calling idbSetup");
			idbSetup(dataBusConfigType);
			if (bus != null)
			{
				for (Map.Entry<String, Operation> entry : operationMap.entrySet())
				{
					if (this.isInterrupted())
					{
						throw new InterruptedException("Interruped while processing metrics map");
					}
					String operationName = entry.getKey();
					Operation operationValues = entry.getValue();
					// ...

					if (versionFilter == null)
					{
						activeMetrics.put(operationName, operationValues);
					}
					else
					{
						if (operationValues.getVersionInfo().startsWith(FILTER_TAG))
						{
							if ((isSOAP
									&& operationValues.getVersionInfo().toLowerCase()
											.contains("wsdl")
									&& operationValues.getVersionInfo().toLowerCase()
											.contains(versionFilter))
									|| (!isSOAP && !operationValues.getVersionInfo().toLowerCase()
											.contains("wsdl"))
									|| operationValues.getVersionInfo().contains(PERFORMANCE_COUNTER_CLIENT))
							{
								activeMetrics.put(operationName, operationValues);
							}
						}
					}
				}
			}
			else
			{
				LOGGER.error("Unable to get a databus connection during this collection cycle.");
			}

			LOGGER.debug(collectedData.toString());
			processMetrics(collectorConfigType.getMetricDefinitions(), activeMetrics,
					collectionInfoType, dataBusConfigType, unixTime);
			previousCollectionQuery = collectionInfoType.getCollectionSource().getCollectionQuery();
			long endUnixTime = System.currentTimeMillis() / 1000L;
			sendSelfMonitorMetric(endUnixTime - unixTime, collectionInfoType, unixTime);
			closeBus();
		}
		catch (MalformedURLException e)
		{
			// new URL() failed
			// ...
			LOGGER.error("MalformedURLException attempting to collect data ", e);
			sendCollectionErrorTime();

		}
		catch (SocketTimeoutException ste)
		{
			LOGGER.warn("Socket time out atempting to connect. This thread will terminate. ",ste);
			LOGGER.warn("URL causing timeout: "+ collectionInfoType.getCollectionSource().getCollectionQuery());
			throw new RuntimeException(ste);
		}
		catch (IOException e)
		{
			LOGGER.error("IOexception attempting to collect data ", e);
			OperatingSystemMXBean os = ManagementFactory.getOperatingSystemMXBean();
			if (os.getName().equals("Linux"))
			{
				LOGGER.debug("Number of open fd: "
						+ ((UnixOperatingSystemMXBean) os).getOpenFileDescriptorCount());
			}
			sendCollectionErrorTime();
			throw new RuntimeException(e);
		}
		catch (InterruptedException ie)
		{
			throw new CollectionInterruptionException(ie);
		}
		catch (Exception exc)
		{
			LOGGER.error("exception attempting to collect data ", exc);
			sendCollectionErrorTime();
			throw new RuntimeException(exc);
		}
		finally
		{
			try
			{
				LOGGER.debug("Inside finally to close inReader");
				if (inReader != null)
				{
					inReader.close();
					LOGGER.debug("inReader should be closed");
				}
				if (myURLConnection != null)
				{
					LOGGER.debug("Disconnecting URL Connection");
					((HttpURLConnection) myURLConnection).disconnect();
				}
				if (bus != null)
				{
					bus.close();
				}
				OperatingSystemMXBean os = ManagementFactory.getOperatingSystemMXBean();
				if (os.getName().equals("Linux"))
				{
					LOGGER.info("Number of open fd: "
							+ ((UnixOperatingSystemMXBean) os).getOpenFileDescriptorCount());
				}
			}
			catch (Exception e)
			{
				LOGGER.error("Error closing input file from socket: ", e);
			}
		}
	}

	private void sendCollectionErrorTime()
	{
		LOGGER.info("Sending collection error time for["
				+ collectionInfoType.getCollectionSource().getCollectionQuery() + "]");
		try
		{
			//idbSetup(dataBusConfigType);
			sendSelfMonitorMetric(COLLECTION_ERROR_TIME, collectionInfoType, unixTime);
		}
		catch (Exception e)
		{
			LOGGER.error("Error attempting to send collection error time: ", e);
		}
	}

	private String getTagValue(TagsType tags, String searchTag)
	{
		String tagValue = null;
		for (TagType tag : tags.getTag())
		{
			if (tag.getId().equals(searchTag))
			{
				tagValue = tag.getValue();
				break;
			}
		}
		return tagValue;
	}

	private Map<String, Operation> removeUnneededLines(List<String> queryResponseLines)
	{
		Map<String, Operation> operations = new HashMap<>();
		List<String> cleanedQueryResponseLines = new ArrayList<>();
		// Start by removing unneeded lines from the beginning of the list
		int i = 0;
		Operation operation = null;
		String operationName = null;
		while (i < queryResponseLines.size())
		{
			while (!queryResponseLines.get(i).contains("operation"))
			{
				i++;
				if (i >= queryResponseLines.size())
				{
					break;
				}
			}
			if (i < queryResponseLines.size())
			{
				if (queryResponseLines.get(i).contains("operation"))
				{
					String revisedLine = queryResponseLines.get(i).replace("\"", "");
					int operationIndex = revisedLine.indexOf(OPERATION_EQUAL)
							+ OPERATION_EQUAL.length();
					String nameSpace = "";
					String nameSpaceSuffix = "";
					if (revisedLine.contains("{") && revisedLine.contains("}"))
					{
						int startPos = revisedLine.indexOf("{") +1;
						int endPos = revisedLine.indexOf("}");
						nameSpace = revisedLine.substring(startPos,endPos);
						startPos = revisedLine.indexOf("}")+1;
						endPos = revisedLine.indexOf(",",startPos);
						nameSpaceSuffix = revisedLine.substring(startPos,endPos);
					}
					operationName = revisedLine.substring(operationIndex);
					LOGGER.debug("operation ["+operationName+"] Name space ["+nameSpace+"]");
					LOGGER.debug("Namespace Suffix ["+nameSpaceSuffix+"]");
					String versionInfo = revisedLine.substring(0,
							revisedLine.indexOf(OPERATION_EQUAL) - 1);
					operation = new Operation(operationName, versionInfo);
					operation.setNameSpace(nameSpace);
					operation.setNameSpaceSuffix(nameSpaceSuffix);
					i++;
				}
			}
			if (i < queryResponseLines.size())
			{
				while (queryResponseLines.get(i).length() > 0 && i < queryResponseLines.size())
				{
					cleanedQueryResponseLines.add(queryResponseLines.get(i));
					String revisedMetricLine = queryResponseLines.get(i).replace("\"", "");
					int colonIndex = revisedMetricLine.indexOf(COLON);
					String beforeColon = revisedMetricLine.substring(0, colonIndex);
					String afterColon = revisedMetricLine.substring(colonIndex + 1).trim();
					boolean isNumeric = afterColon.chars().allMatch(Character::isDigit);
					if (isNumeric)
					{
						operation.addMetric(beforeColon, Double.valueOf(afterColon));
					}
					i++;
				}
			}
			if (operation != null)
			{
				operations.put(operationName, operation);
			}
		}
		return operations;
	}

	private boolean checkIsSOAP(String service)
	{
		boolean isSOAP = false;

		if (service.toLowerCase().contains("wsdl"))
		{
			isSOAP = true;
		}
		return isSOAP;
	}

	private void processMetrics(MetricDefinitionsType metricDefinitions,
			Map<String, Operation> operations, CollectionInfoType collectionInfoType,
			DataBusConfigType dataBusConfig, Long unixTime) throws InternalServiceFault

	{
		try
		{
			StringBuffer totalHandlingTimeCacheKey = new StringBuffer();
			for (MetricDefinitionType metricDefinitionType : metricDefinitions
					.getMetricDefinition())
			{
				LOGGER.debug("Collecting " + metricDefinitionType.getField());
				if (!metricDefinitionType.getSearch().startsWith("@calc"))
				{
					if (this.isInterrupted())
					{
						throw new InterruptedException("Interruped while processing metrics");
					}
					for (Map.Entry<String, Operation> entry : operations.entrySet())
					{
						String operationName = entry.getKey();
						Operation operationValues = entry.getValue();
						LOGGER.debug("Collecting metrics for operation: [" + operationName + "]");
						Double metricValue = getMetricValue(operationValues,
								metricDefinitionType.getSearch());
						LOGGER.debug("metric value: " + metricValue);
						sendMetric(metricValue, metricDefinitionType.getName(),
								collectionInfoType.getCollectionSource().getTags(), operationName,
								unixTime, dataBusConfig, operationValues);
					}
				}
				else
				{
					StringBuffer numInvocationsCacheKey;
					StringBuffer tagValues = new StringBuffer();
					for (TagType tagType : collectionInfoType.getCollectionSource().getTags()
							.getTag())
					{
						tagValues.append(tagType.getValue() + "~");
					}

					for (Map.Entry<String, Operation> entry : operations.entrySet())
					{
						String operationName = entry.getKey();
						Operation operationValues = entry.getValue();
						String metricCacheKey = null;
						Double webServiceInvocationCountValue = null;
						Double numInvocations = null;
						Double metricValue = null;
						LOGGER.debug("Collecting metrics for operation: [" + operationName + "]");
						switch (metricDefinitionType.getField())
						{
							case "averageResponseTime":
								LOGGER.debug("Collecting averageResponseTime");
								if (metricDefinitionType.getName().contains(CLIENT)) {
									totalHandlingTimeCacheKey = new StringBuffer(WEBSERVICE_CLIENT_TOTAL_HANDLING_TIME + metricSuffix);
									numInvocationsCacheKey = new StringBuffer(WEBSERVICE_CLIENT_INVOCATION_COUNT + metricSuffix);
								} else {
									totalHandlingTimeCacheKey = new StringBuffer(WEBSERVICE_TOTAL_HANDLING_TIME + metricSuffix);
									numInvocationsCacheKey = new StringBuffer(WEBSERVICE_INVOCATION_COUNT + metricSuffix);
								}
								LOGGER.info("Getting total handling time cache value key ["+totalHandlingTimeCacheKey.toString() + tagValues
										+ operationName+"]");
								Double totalHandlingTimeCacheValue = metricsCache
										.get(totalHandlingTimeCacheKey.toString() + tagValues
												+ operationName);
								metricCacheKey = numInvocationsCacheKey.toString() + tagValues
										+ operationName;
								webServiceInvocationCountValue = metricsCache.get(metricCacheKey);

								Double totalHandlingTime = getMetricValue(operationValues,
										"TotalHandlingTime");
								numInvocations = getMetricValue(operationValues, "NumInvocations");
								metricValue = new Double(0);
								LOGGER.debug("i2 = [" + numInvocations + "] i1 = ["
										+ webServiceInvocationCountValue + "] t2 = ["
										+ totalHandlingTime + "] t1 = [" + totalHandlingTimeCacheValue
										+ "]");

								if (webServiceInvocationCountValue == null
										|| numInvocations - webServiceInvocationCountValue < 0
										|| totalHandlingTimeCacheValue == null
										|| totalHandlingTime - totalHandlingTimeCacheValue < 0) {
									webServiceInvocationCountValue = Double.valueOf(0);
									totalHandlingTimeCacheValue = Double.valueOf(0);
								}
								if (numInvocations - webServiceInvocationCountValue == 0) {
									metricValue = Double.valueOf(0);
								} else {
									metricValue = (totalHandlingTime - totalHandlingTimeCacheValue)
											/ (numInvocations - webServiceInvocationCountValue);
								}

								break;
							case "invocationCountDelta":
								LOGGER.debug("Collecting invocationCountDelta");
								if (metricDefinitionType.getName().contains(CLIENT)) {
									numInvocationsCacheKey= new StringBuffer(WEBSERVICE_CLIENT_INVOCATION_COUNT + metricSuffix);
								} else {
									numInvocationsCacheKey= new StringBuffer(WEBSERVICE_INVOCATION_COUNT + metricSuffix);
								}
								metricCacheKey = numInvocationsCacheKey.toString() + tagValues
										+ operationName;
								webServiceInvocationCountValue = metricsCache.get(metricCacheKey);

								numInvocations = getMetricValue(operationValues, "NumInvocations");
								metricValue = new Double(0);
								if (numInvocations != null && webServiceInvocationCountValue != null) {

									if (numInvocations - webServiceInvocationCountValue > 0) {
										metricValue = numInvocations - webServiceInvocationCountValue;
									} else if (numInvocations != null
											&& numInvocations - webServiceInvocationCountValue < 0) {
										metricValue = numInvocations;
									} else {
										metricValue = new Double(0);
									}
								} else if (webServiceInvocationCountValue == null) {
									metricValue = new Double(0);
								}
								break;
							case "totalHandlingTimeDelta":
								LOGGER.debug("Collecting totalHandlingTimeDelta");
								if (metricDefinitionType.getName().contains(CLIENT)) {
									totalHandlingTimeCacheKey= new StringBuffer(WEBSERVICE_CLIENT_TOTAL_HANDLING_TIME + metricSuffix);
								} else {
									totalHandlingTimeCacheKey= new StringBuffer(WEBSERVICE_TOTAL_HANDLING_TIME + metricSuffix);
								}
								metricCacheKey = totalHandlingTimeCacheKey.toString() + tagValues
										+ operationName;
								Double totalHandlingtimeCacheValue = metricsCache.get(metricCacheKey);

								totalHandlingTime = getMetricValue(operationValues,
										TOTAL_HANDLING_TIME);
								metricValue = new Double(0);
								if (totalHandlingTime != null && totalHandlingtimeCacheValue != null) {

									if (totalHandlingTime - totalHandlingtimeCacheValue > 0) {
										metricValue = totalHandlingTime - totalHandlingtimeCacheValue;
									} else {
										metricValue = totalHandlingTime;
									}
								} else if (totalHandlingtimeCacheValue == null) {
									metricValue = new Double(0);
								}
								break;
							case "numUncheckedApplicationFaultsDelta":
								LOGGER.debug("Collecting numUncheckedApplicationFaultsDelta");
								StringBuffer uncheckedFaultsCacheKey = new StringBuffer();
								if (metricDefinitionType.getName().contains(CLIENT)) {
									uncheckedFaultsCacheKey.append(WEBSERVICE_CLIENT_UNCHECKED_FAULTS + metricSuffix);
								} else {
									uncheckedFaultsCacheKey.append(WEBSERVICE_UNCHECKED_FAULTS + metricSuffix);
								}
								metricCacheKey = uncheckedFaultsCacheKey.toString() + tagValues
										+ operationName;
								Double uncheckedFaultsCacheValue = metricsCache.get(metricCacheKey);
								LOGGER.info("Cache value for unchecked faults delta with key ["+metricCacheKey+"] value ["+uncheckedFaultsCacheValue+"]");
								Double numUncheckedFaults = getMetricValue(operationValues,
										NUM_UNCHECKED_FAULTS);
								LOGGER.info("Current number of unchecked faults [" + numUncheckedFaults + "] for metric [" + metricDefinitionType.getField() + "] for operation [" + operationName + "]");
								metricValue = new Double(0);
								if (numUncheckedFaults != null && uncheckedFaultsCacheValue != null) {

									if (numUncheckedFaults - uncheckedFaultsCacheValue >= 0) {
										metricValue = numUncheckedFaults - uncheckedFaultsCacheValue;
									} else {
										metricValue = numUncheckedFaults;
									}
								} else if (uncheckedFaultsCacheValue == null) {
									metricValue = new Double(0);
								}
								break;
							case "numCheckedApplicationFaultsDelta":
								LOGGER.debug("Collecting numUncheckedApplicationFaultsDelta");
								StringBuffer checkedFaultsCacheKey = new StringBuffer();
								if (metricDefinitionType.getName().contains(CLIENT)) {
									checkedFaultsCacheKey.append(WEBSERVICE_CLIENT_CHECKED_FAULTS + metricSuffix);
								} else {
									checkedFaultsCacheKey.append(WEBSERVICE_CHECKED_FAULTS + metricSuffix);
								}
								metricCacheKey = checkedFaultsCacheKey.toString() + tagValues
										+ operationName;
								Double checkedFaultsCacheValue = metricsCache.get(metricCacheKey);
								LOGGER.info("Cache value for checked faults delta with key ["+metricCacheKey+"] value ["+checkedFaultsCacheValue+"]");
								Double numCheckedFaults = getMetricValue(operationValues,
										NUM_CHECKED_FAULTS);
								LOGGER.info("Current number of checked faults [" + numCheckedFaults + "] for metric [" + metricDefinitionType.getField() + "] for operation [" + operationName + "]");
								metricValue = new Double(0);
								if (numCheckedFaults != null && checkedFaultsCacheValue != null)
								{
									if (numCheckedFaults - checkedFaultsCacheValue >= 0)
									{
										metricValue = numCheckedFaults - checkedFaultsCacheValue;
									}
									else
									{
										metricValue = numCheckedFaults;
									}
								}
								else if (checkedFaultsCacheValue == null)
								{
									metricValue = new Double(0);
								}
								break;
							case "numLogicalRuntimeFaultsDelta":
								LOGGER.debug("Collecting numLogicalRuntimeFaultsDelta");
								StringBuffer logicalRuntimeFaultsCacheKey = new StringBuffer();
								if (metricDefinitionType.getName().contains(CLIENT)) {
									logicalRuntimeFaultsCacheKey.append(WEBSERVICE_CLIENT_LOGICAL_FAULTS + metricSuffix);
								} else {
									logicalRuntimeFaultsCacheKey.append(WEBSERVICE_LOGICAL_FAULTS + metricSuffix);
								}
								metricCacheKey = logicalRuntimeFaultsCacheKey.toString() + tagValues
										+ operationName;
								Double logicalRuntimeFaultsCacheValue = metricsCache.get(metricCacheKey);
								LOGGER.info("Cache value for logical runtime faults delta with key ["+metricCacheKey+"] value ["+logicalRuntimeFaultsCacheValue+"]");
								Double numLogicalFaults = getMetricValue(operationValues,
										NUM_LOGICAL_FAULTS);
								LOGGER.info("Current number of logical faults [" + numLogicalFaults + "] for metric [" + metricDefinitionType.getField() + "] for operation [" + operationName + "]");
								metricValue = new Double(0);
								if (numLogicalFaults != null && logicalRuntimeFaultsCacheValue != null)
								{
									if (numLogicalFaults - logicalRuntimeFaultsCacheValue >= 0)
									{
										metricValue = numLogicalFaults - logicalRuntimeFaultsCacheValue;
									}
									else
									{
										metricValue = numLogicalFaults;
									}
								}
								else if (logicalRuntimeFaultsCacheValue == null)
								{
									metricValue = new Double(0);
								}
								break;
						default:
							LOGGER.error("Unknown calculation requested. "
									+ metricDefinitionType.getSearch());
						}
						LOGGER.debug("Metric name [" + metricDefinitionType.getName()
								+ "] Metric value [" + metricValue + "]");
						sendMetric(metricValue, metricDefinitionType.getName(),
								collectionInfoType.getCollectionSource().getTags(), operationName,
								unixTime, dataBusConfig,operationValues);
					}
				}
			}
		}
		catch (InterruptedException ie)
		{
			throw new CollectionInterruptionException(ie);
		}
	}

	private Double getMetricValue(Operation operationValues, String searchValue)
	{
		return operationValues.getMetric(searchValue);
	}

	private void idbSetup(DataBusConfigType dataBusConfig) throws InternalServiceFault
	{
		try
		{
			if (bus != null)
			{
				LOGGER.debug("calling closeBus");
				closeBus();
				LOGGER.debug("Returned from closeBus");
			}
			LOGGER.debug("Creating a new bus endpoint");
			bus = new Databus.Builder().endpoint(dataBusConfig.getEndpoint())
					.stream(dataBusConfig.getStreamName()).build();
			List<Schema> schemas = bus.getSchemas();
			Schema schema = schemas.get(0);
			LOGGER.debug("Databus endpoint[" + dataBusConfig.getEndpoint() + "] Stream Name["
					+ dataBusConfig.getStreamName() + "]");

			metricData = new GenericData.Record(schema);
			bus.initProducer();
			tags = new HashMap();
		}
		catch (Exception ex)
		{
			LOGGER.error("Exception during idbsetup", ex);
			closeBus();
			throw new InternalServiceFault(ex);
		}
		catch (Throwable t)
		{
			LOGGER.error("Throwable caught ", t);
			closeBus();
			throw new InternalServiceFault(t);
		}
		finally
		{
			LOGGER.debug("Exiting idbSetup");
		}
	}

	private void closeBus()
	{
		try
		{
			if (bus != null)
			{
				bus.close();
			}
		}
		catch (Exception e)
		{
			LOGGER.warn("Exception trying to clean up open databus connections." + e.getMessage());
		}
		catch (Throwable t)
		{
			LOGGER.error("Throwable caught  in closeBus", t);
			throw new RuntimeException(t);
		}
		finally
		{
			LOGGER.debug("Exiting closeBus");
		}
	}

	private void sendMetric(Double metricValue, String metricName, TagsType tagTypes,
			String operation, long unixTime, DataBusConfigType dataBusConfigType, Operation operationValues)
			throws InternalServiceFault
	{
		try
		{
			LOGGER.debug("sending metric for operation: " + operation);
			tags.clear();
			StringBuffer cacheKey = new StringBuffer(metricName);
			cacheKey.append(metricSuffix);
			for (TagType tagType : tagTypes.getTag())
			{
				tags.put(tagType.getId(), tagType.getValue());
				cacheKey.append(tagType.getValue() + "~");
			}
			cacheKey.append(operation);

			Double previousValue = null;
			previousValue = metricsCache.get(cacheKey.toString());
			metricData.put("timestamp", unixTime);
			tags.put("operation", operation);
			tags.put("namespace",operationValues.getNameSpace());
			tags.put("namespacesuffix",operationValues.getNameSpaceSuffix());
			metricData.put("metric", metricName);
			metricData.put("value", metricValue);
			metricData.put("tags", tags);
			LOGGER.info("Updating cache, key ["+cacheKey+"]");
			if (previousValue != null)
			{
				metricsCache.replace(cacheKey.toString(), metricValue);
			}
			else
			{
				metricsCache.put(cacheKey.toString(), metricValue);
			}
			LOGGER.info("Sending metric [" + metricName + "] value [" + metricValue + "] unixtime ["
					+ unixTime + "] tags [" + tags.toString() + "]");
			bus.produce("1", metricData);
		}
		catch (TimeoutException te)
		{
			LOGGER.warn("Timeout occured attempting to send metric[" + metricName
					+ "] to databus.  Processing will continue");
			closeBus();
			idbSetup(dataBusConfigType);
		}
		catch (org.apache.kafka.common.errors.TimeoutException ke)
		{
			LOGGER.error(
					"Kafka timeout exception, no data stored for [" + metricName + ":" + metricValue
							+ "] self monitoring this collection cycle. Processing will continue");
			closeBus();
			idbSetup(dataBusConfigType);
		}
		catch (Exception ex)
		{
			LOGGER.error("Exception thrown attempting to send metric, resetting databus connection",
					ex);
			closeBus();
			idbSetup(dataBusConfigType);
		}
		catch (Throwable t)
		{
			LOGGER.error("Throwable caught in sendMetric ", t);
			throw new RuntimeException(t);
		}
	}

	private void sendSelfMonitorMetric(Long metricValue, CollectionInfoType collectionInfoType,
			long unixTime) throws InternalServiceFault
	{
		try
		{
			LOGGER.debug("sending metric for self monitoring");
			tags.clear();
			tags.put("collectorType", collectionInfoType.getCollectorType());
			tags.put("cluster",
					getTagValue(collectionInfoType.getCollectionSource().getTags(), "cluster"));
			tags.put("instance",
					getTagValue(collectionInfoType.getCollectionSource().getTags(), "instance"));

			tags.put("collectorId", collectionInfoType.getAssignedCollector());
			metricData.put("timestamp", unixTime);
			if (dataBusConfigType.getStreamName().endsWith("dev")
					|| dataBusConfigType.getStreamName().endsWith("test"))
			{
				metricData.put("metric", MONITOR_METRIC + dataBusConfigType.getStreamName());
			}
			else
			{
				metricData.put("metric", MONITOR_METRIC);
			}
			metricData.put("value", metricValue);
			metricData.put("tags", tags);
			LOGGER.info("Sending metric [" + MONITOR_METRIC + "] Stream ["
					+ dataBusConfigType.getStreamName() + "] value [" + metricValue + "] unixtime ["
					+ unixTime + "] tags [" + tags.toString() + "]");
			bus.produce("1", metricData);
		}
		catch (TimeoutException te)
		{
			LOGGER.warn("Timeout error attempting to send to IDB. Processing will continue.");
		}
		catch (org.apache.kafka.common.errors.TimeoutException ke)
		{
			LOGGER.error(
					"Kafka timeout exception, no data store for self monitoring this collection cycle. Processing will continue");
		}
		catch (Exception ex)
		{
			LOGGER.error("Exception thrown attempting to send metric, thread will terminate.", ex);
			closeBus();
		}
		catch (Throwable t)
		{
			LOGGER.error("Throwable caught in sendSelfmonitoringMetric ", t);
			throw new RuntimeException(t);
		}
	}

}