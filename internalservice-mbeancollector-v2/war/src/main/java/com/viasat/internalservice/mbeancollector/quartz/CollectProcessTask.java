package com.viasat.internalservice.mbeancollector.quartz;

import org.apache.log4j.Logger;

import com.viasat.internalservice.fault.InternalServiceFault;
import com.viasat.internalservice.mbeancollector.rest.MbeanCollectorImpl;

public class CollectProcessTask
{
	private MbeanCollectorImpl mbeanCollectorImpl;
	Logger LOGGER = Logger.getLogger(CollectProcessTask.class);

	public void collect()
	{

		LOGGER.info("In CollectProcessTask");
		try
		{
			mbeanCollectorImpl.onCollect();
		}
		catch (InternalServiceFault isf)
		{
			LOGGER.error("Internal service fault thrown ", isf);
		}
	}

	public void setMbeanCollectorImpl(MbeanCollectorImpl mbeanCollectorImpl)
	{
		LOGGER.debug("setting mbean processor");
		this.mbeanCollectorImpl = mbeanCollectorImpl;
	}

	public MbeanCollectorImpl getMbeanCollectorImpl()
	{
		LOGGER.debug("getting mbean processor");
		return this.mbeanCollectorImpl;
	}

}
