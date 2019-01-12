package com.viasat.internalservice.querycollector.quartz;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.viasat.internalservice.fault.InternalServiceFault;
import com.viasat.internalservice.querycollector.rest.QueryCollectorImpl;


public class CollectQuartzJob extends QuartzJobBean {
    Logger LOGGER = Logger.getLogger(CollectQuartzJob.class);
    QueryCollectorImpl queryCollector;

    public void executeInternal(JobExecutionContext context) throws JobExecutionException
    {
        if (LOGGER.isInfoEnabled())
        {
            LOGGER.info("In TransactionRecordsQuartzJob - executing its JOB on " + new Date()
                    + " by " + context.getTrigger().getDescription());
        }

        try {
            queryCollector.onCollect();
        }catch (InternalServiceFault isf){
            LOGGER.error("exception during collection. ");
            throw new JobExecutionException(isf);
        }
    }

    public QueryCollectorImpl getQueryCollectorImpl()
    {
        return queryCollector;
    }

    public void setQueryCollectorImpl(
            QueryCollectorImpl queryCollector)
    {
        this.queryCollector = queryCollector;
    }

}
