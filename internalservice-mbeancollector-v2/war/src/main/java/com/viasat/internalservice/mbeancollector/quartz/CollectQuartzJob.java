package com.viasat.internalservice.mbeancollector.quartz;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.viasat.internalservice.fault.InternalServiceFault;
import com.viasat.internalservice.mbeancollector.rest.MbeanCollectorImpl;


public class CollectQuartzJob extends QuartzJobBean {
    Logger LOGGER = Logger.getLogger(CollectQuartzJob.class);
    MbeanCollectorImpl mbeanCollector;

    public void executeInternal(JobExecutionContext context) throws JobExecutionException
    {
        if (LOGGER.isInfoEnabled())
        {
            LOGGER.info("In TransactionRecordsQuartzJob - executing its JOB on " + new Date()
                    + " by " + context.getTrigger().getDescription());
        }

        try {
            mbeanCollector.onCollect();
        }catch (InternalServiceFault isf){
            LOGGER.error("exception during collection. ");
            throw new JobExecutionException(isf);
        }
    }

    public MbeanCollectorImpl getMbeanCollectorImpl()
    {
        return mbeanCollector;
    }

    public void setMbeanCollectorImpl(
            MbeanCollectorImpl mbeanCollector)
    {
        this.mbeanCollector = mbeanCollector;
    }

}
