package com.viasat.internalservice.querycollector.quartz;

import org.apache.log4j.Logger;

import com.viasat.internalservice.fault.InternalServiceFault;
import com.viasat.internalservice.querycollector.rest.QueryCollectorImpl;

public class CollectProcessTask
{
	private QueryCollectorImpl queryCollectorImpl;
	Logger LOGGER = Logger.getLogger(CollectProcessTask.class);

	public void collect()
	{

		LOGGER.info("In CollectProcessTask");
		try
		{
			queryCollectorImpl.onCollect();
		}
		catch (InternalServiceFault isf)
		{
			LOGGER.error("Internal service fault thrown ", isf);
		}
	}

	public void setQueryCollectorImpl(QueryCollectorImpl queryCollectorImpl)
	{
		LOGGER.debug("setting query processor");
		this.queryCollectorImpl = queryCollectorImpl;
	}

	public QueryCollectorImpl getQueryCollectorImpl()
	{
		LOGGER.debug("getting query processor");
		return this.queryCollectorImpl;
	}

}
