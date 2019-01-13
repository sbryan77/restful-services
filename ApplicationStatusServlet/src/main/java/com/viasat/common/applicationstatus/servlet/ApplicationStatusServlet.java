package com.viasat.common.applicationstatus.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpRequestHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.viasat.common.applicationstatus.ApplicationHealthCheckProcessor;
import com.viasat.common.applicationstatus.ApplicationInfoProcessor;
import com.viasat.common.applicationstatus.EnvironmentDetailProcessor;
import com.viasat.common.applicationstatus.HealthCheckStatus;
import com.viasat.common.applicationstatus.data.ApplicationStatus;
import com.viasat.common.applicationstatus.interceptor.LastResponseTime;
import com.viasat.common.applicationstatus.utility.AppStatusConstants;

public class ApplicationStatusServlet implements HttpRequestHandler
{
	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationStatusServlet.class);

	private ApplicationInfoProcessor appInfoProcessor;
	private ApplicationHealthCheckProcessor appHealthCheckProcessor;
	private EnvironmentDetailProcessor envDetailProcessor;

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		if (LOGGER.isDebugEnabled())
		{
			LOGGER.debug("Starting Application Status Servlet.");
		}

		ApplicationStatus appStatus = new ApplicationStatus();

		// get the version info
		appStatus = getAppInfoProcessor().execute(appStatus);

		// get the health check info
		appStatus = getAppHealthCheckProcessor().execute(appStatus);

		// get some environment details
		appStatus = getEnvDetailProcessor().execute(appStatus);

		// get last response time (this action should be the last event)
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(LastResponseTime.getLastResponseTime());
		appStatus.setLastRequestTimestamp(cal);

		// set the HTTP response code
		response.setStatus(HealthCheckStatus.valueOf(appStatus.getStatus()).getHttpCode());

		try
		{
			PrintWriter out = response.getWriter();
			
			String acceptHeader = request.getHeader(AppStatusConstants.HTTP_HDR_ACCEPT);

			if (acceptHeader != null
					&& acceptHeader.contains(AppStatusConstants.CONTENT_TYPE_APP_JSON))
			{
				// set the response content type
				response.setContentType(AppStatusConstants.CONTENT_TYPE_APP_JSON);

				ObjectMapper mapper = new ObjectMapper();
				mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
				mapper.setTimeZone(TimeZone.getDefault());
				mapper.setDateFormat(new SimpleDateFormat(AppStatusConstants.DF_ISO_8601));
				mapper.writeValue(out, appStatus);
			}
			else
			{
				// set response content type
				response.setContentType(AppStatusConstants.CONTENT_TYPE_TXT_XML);

				JAXBContext context = JAXBContext.newInstance(ApplicationStatus.class);
				Marshaller m = context.createMarshaller();
				m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
				m.marshal(appStatus, out);
			}

		}
		catch (Exception e)
		{
			if (LOGGER.isDebugEnabled())
			{
				LOGGER.debug("Exception caught marshalling response:", e);
			}

			throw new ServletException(e);
		}
		finally
		{
			if (LOGGER.isDebugEnabled())
			{
				LOGGER.debug("Finished Application Status Servlet.");
			}
		}
	}

	public ApplicationInfoProcessor getAppInfoProcessor()
	{
		return appInfoProcessor;
	}

	public void setAppInfoProcessor(ApplicationInfoProcessor appInfoProcessor)
	{
		this.appInfoProcessor = appInfoProcessor;
	}

	public ApplicationHealthCheckProcessor getAppHealthCheckProcessor()
	{
		return appHealthCheckProcessor;
	}

	public void setAppHealthCheckProcessor(ApplicationHealthCheckProcessor appHealthCheckProcessor)
	{
		this.appHealthCheckProcessor = appHealthCheckProcessor;
	}

	public EnvironmentDetailProcessor getEnvDetailProcessor()
	{
		return envDetailProcessor;
	}

	public void setEnvDetailProcessor(EnvironmentDetailProcessor envDetailProcessor)
	{
		this.envDetailProcessor = envDetailProcessor;
	}
}
