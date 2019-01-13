package com.viasat.common.applicationstatus;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.util.Calendar;
import java.util.Enumeration;

import javax.management.ObjectName;

import org.apache.log4j.Appender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.viasat.common.applicationstatus.data.ApplicationStatus;
import com.viasat.common.applicationstatus.data.EnvironmentDetail;
import com.viasat.common.applicationstatus.utility.AppStatusConstants;
import com.viasat.wildblue.common.adapter.XmlDateTimeToCalendarAdapter;

public class DefaultEnvironmentDetailProcessor implements EnvironmentDetailProcessor
{
	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultEnvironmentDetailProcessor.class);

	@Override
	public ApplicationStatus execute(ApplicationStatus appStatus)
	{
		if (appStatus == null)
		{
			appStatus = new ApplicationStatus();
		}

		// get the log file location
		EnvironmentDetail logFileDetail = getLogFileLocation();

		if (logFileDetail != null)
		{
			appStatus.getEnvironmentDetail().add(logFileDetail);
		}

		// get host name
		EnvironmentDetail hostDetail = getHost();

		if (hostDetail != null)
		{
			appStatus.getEnvironmentDetail().add(hostDetail);
		}

		// get the current time
		EnvironmentDetail timeDetail = getTimeDetail();

		if (timeDetail != null)
		{
			appStatus.getEnvironmentDetail().add(timeDetail);
		}

		// get the server info
		EnvironmentDetail serverDetail = getServerDetail();

		if (serverDetail != null)
		{
			appStatus.getEnvironmentDetail().add(serverDetail);
		}

		// get the JVM info
		EnvironmentDetail javaRuntimeDetail = getJavaRuntimeDetail();

		if (javaRuntimeDetail != null)
		{
			appStatus.getEnvironmentDetail().add(javaRuntimeDetail);
		}

		return appStatus;
	}

	private EnvironmentDetail getLogFileLocation()
	{
		try
		{
			@SuppressWarnings("unchecked")
			Enumeration<org.apache.log4j.Logger> loggerEnum = LogManager.getCurrentLoggers();

			while (loggerEnum.hasMoreElements())
			{
				org.apache.log4j.Logger logger = loggerEnum.nextElement();

				@SuppressWarnings("unchecked")
				Enumeration<Appender> appenderEnum = logger.getAllAppenders();

				while (appenderEnum.hasMoreElements())
				{
					Appender appender = appenderEnum.nextElement();

					if (appender instanceof FileAppender)
					{
						String logFile = ((FileAppender) appender).getFile();

						EnvironmentDetail envDetail = new EnvironmentDetail();

						envDetail.setName(AppStatusConstants.ED_LOG_FILE);
						envDetail.setValue(logFile);

						return envDetail;
					}
				}
			}
		}
		catch (Exception e)
		{
			// This is a best guess approach, no big deal if it fails
			LOGGER.info("Failed to find log file detail:", e);
		}

		return null;
	}

	private EnvironmentDetail getHost()
	{

		try
		{
			// check the for the weblogic.Name system property
			String host = System.getProperty(AppStatusConstants.WEBLOGIC_NAME_PROP);

			// check for a host name
			if (host == null)
			{
				host = InetAddress.getLocalHost().getHostName();
			}

			// check for an IP address
			if (host == null)
			{
				host = InetAddress.getLocalHost().getHostAddress();
			}

			if (host != null)
			{
				EnvironmentDetail envDetail = new EnvironmentDetail();

				envDetail.setName(AppStatusConstants.ED_HOST_NAME);
				envDetail.setValue(host);

				return envDetail;
			}
		}
		catch (Exception e)
		{
			LOGGER.info("Failed to get host name detail:", e);
		}

		return null;
	}

	private EnvironmentDetail getTimeDetail()
	{
		try
		{
			EnvironmentDetail envDetail = new EnvironmentDetail();

			// use the common date/time adaptor to get an ISO 8601 date/time
			XmlDateTimeToCalendarAdapter adapter = new XmlDateTimeToCalendarAdapter();

			envDetail.setName(AppStatusConstants.ED_HOST_DATE);
			envDetail.setValue(adapter.marshal(Calendar.getInstance()));

			return envDetail;
		}
		catch (Exception e)
		{
			LOGGER.info("Failed to get time detail:", e);
		}

		return null;
	}

	private EnvironmentDetail getServerDetail()
	{
		try
		{
			String serverInfo = (String) ManagementFactory.getPlatformMBeanServer().getAttribute(
					new ObjectName(AppStatusConstants.TC_MBEAN_NAME), AppStatusConstants.TC_MBEAN_ATTRIBUTE);

			if (serverInfo != null)
			{
				EnvironmentDetail envDetail = new EnvironmentDetail();

				envDetail.setName(AppStatusConstants.ED_SERVER);
				envDetail.setValue(serverInfo);

				return envDetail;
			}
		}
		catch (Exception e)
		{
			LOGGER.trace("Failed to get server detail:", e);
		}

		return null;
	}

	private EnvironmentDetail getJavaRuntimeDetail()
	{
		try
		{
			String javaVersion = System.getProperty(AppStatusConstants.JAVA_RT_VER);
			String javaVendor = System.getProperty(AppStatusConstants.JAVA_VENDOR);

			if (!"".equals(javaVersion))
			{
				EnvironmentDetail envDetail = new EnvironmentDetail();

				envDetail.setName(AppStatusConstants.ED_JRE_VERSION);
				envDetail.setValue(javaVersion + " " + javaVendor);

				return envDetail;
			}
		}
		catch (Exception e)
		{
			LOGGER.info("Failed to get java detail:", e);
		}

		return null;
	}
}
