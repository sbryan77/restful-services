package com.viasat.common.applicationstatus.healthcheck;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.viasat.common.applicationstatus.DefaultApplicationHealthCheckProcessor;
import com.viasat.common.applicationstatus.HealthCheck;
import com.viasat.common.applicationstatus.HealthCheckStatus;
import com.viasat.common.applicationstatus.HealthCheckType;
import com.viasat.common.applicationstatus.utility.AppStatusConstants;
import com.viasat.common.applicationstatus.utility.ServiceTestingUtility;

/**
 * This check is currently added by default in the
 * {@link DefaultApplicationHealthCheckProcessor}
 */
public class GenericConfigServiceCheck implements HealthCheck
{
	private static final Logger LOGGER = LoggerFactory.getLogger(GenericConfigServiceCheck.class);

	@Override
	public HealthCheckStatus execute()
	{
		try
		{
			String urlString = System.getProperty(AppStatusConstants.CONFIG_SVC_PROP);

			if (urlString == null)
			{
				throw new Exception(AppStatusConstants.CONFIG_SVC_PROP + " is missing from the JVM.");
			}

			// add WSDL suffix since end point doesn't return 200 on non-soap
			return ServiceTestingUtility.testHTTPConnection(urlString + AppStatusConstants.WSDL_SFX);
		}
		catch (Exception e)
		{
			LOGGER.error("Failed to get service health status for" + this.getName(), e);
		}

		return HealthCheckStatus.ERROR;
	}

	@Override
	public String getName()
	{
		return AppStatusConstants.DEF_CONF_SVC_CHECK;
	}

	@Override
	public HealthCheckType getType()
	{
		return HealthCheckType.SERVICE;
	}

}
