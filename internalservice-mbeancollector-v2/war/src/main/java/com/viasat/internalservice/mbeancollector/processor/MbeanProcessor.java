package com.viasat.internalservice.mbeancollector.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.avro.generic.GenericRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.viasat.internalservice.fault.InternalServiceFault;
import com.viasat.internalservice.mbeancollector.persistence.data.CollectionInfoType;
import com.viasat.internalservice.mbeancollector.persistence.data.CollectorConfigType;
import com.viasat.internalservice.mbeancollector.persistence.data.DataBusConfigType;
import com.viasat.internalservice.mbeancollector.processor.thread.MbeanCollect;

import io.viasat.databus.api.Databus;


public class MbeanProcessor
{
	private static final long  COLLECTION_INTERVAL = 300;
	private static final Logger LOGGER = LoggerFactory.getLogger(MbeanProcessor.class);
	private GenericRecord metricData;
	private Databus bus;

	private Map tags;
	private Map<String, Double> metricsCache = new HashMap<>();

	public void collect(CollectorConfigType collectorConfigType, String collectorId,
			DataBusConfigType dataBusConfigType, Long unixTime) throws InternalServiceFault
	{

		Thread.UncaughtExceptionHandler handler = new Thread.UncaughtExceptionHandler() {

			public void uncaughtException(Thread th, Throwable ex) {
				LOGGER.warn("Uncaught exception for thread: " + th.getName());
				LOGGER.warn("Exception information: ",ex);
			}
		};

		List<CollectionInfoType> collectionInfos = collectorConfigType.getCollectionInfos()
				.getCollectionInfo();

		List<Thread> collectThreads = new ArrayList<>();

		for (CollectionInfoType collectionInfoType : collectionInfos)
		{
			CharSequence assignedCollector = collectionInfoType.getAssignedCollector().trim();
			if (collectorId.contains(assignedCollector))
			{
				MbeanCollect collectThread =
						new MbeanCollect(collectionInfoType, collectorConfigType, collectorId,
								dataBusConfigType, unixTime, metricsCache);
				collectThreads.add(collectThread);
				collectThread.setUncaughtExceptionHandler(handler);
				collectThread.start();
			}
		}
		// monitor threads that are still processing
		while (collectThreads.size() > 0)
		{
				long currentUnitTime = System.currentTimeMillis() / 1000L;

				List<Thread> removeItems = new ArrayList<>();
				boolean terminateThreads = false;
				if ((currentUnitTime - unixTime) > (COLLECTION_INTERVAL / 2.0)) {
					terminateThreads = true;
				}
				for (Thread mbeanCollect : collectThreads) {
					if (!mbeanCollect.isAlive()) {
						removeItems.add(mbeanCollect);
					} else if (terminateThreads) {
						LOGGER.info("Interrupting thread: " + mbeanCollect.getName());
						mbeanCollect.interrupt();
						removeItems.add(mbeanCollect);
					}
				}
				collectThreads.removeAll(removeItems);
				if (collectThreads.size() == 0) {
					LOGGER.info("All threads have terminated. Collection cycle will end.");
					break;
				}
				LOGGER.info("Number of collection threads still alive [" + collectThreads.size() + "]");

			try
			{
				Thread.sleep(5000);
			}
			catch (InterruptedException ie)
			{
				LOGGER.error("Exception sleeping wihile monitoring threads.", ie);
			}

		}
		long endUnixTime = System.currentTimeMillis() / 1000L;
		LOGGER.info("Total collection time in seconds ["+(endUnixTime - unixTime) + "]");
	}
}
