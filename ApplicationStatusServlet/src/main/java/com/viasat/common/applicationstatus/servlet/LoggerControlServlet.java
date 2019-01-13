package com.viasat.common.applicationstatus.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;

import org.apache.log4j.Appender;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpRequestHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viasat.common.applicationstatus.data.AppenderDetail;
import com.viasat.common.applicationstatus.data.LoggerDetail;
import com.viasat.common.applicationstatus.data.LoggerInfo;
import com.viasat.common.applicationstatus.utility.AppStatusConstants;

public class LoggerControlServlet implements HttpRequestHandler
{
	private static final Logger LOGGER = LoggerFactory.getLogger(LoggerControlServlet.class);
	private static final String APPENDER_NAME_KEY = "appender";
	private static final String APPENDER_THRESHOLD_KEY = "threshold";
	private static final String LOGGER_NAME_KEY = "logger";
	private static final String LOGGER_LEVEL_KEY = "level";

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{

		HashMap<String, String> queryParams = getQueryParams(request.getQueryString());
		String appenderName = queryParams.get(APPENDER_NAME_KEY);
		String appenderThreshold = queryParams.get(APPENDER_THRESHOLD_KEY);
		String loggerName = queryParams.get(LOGGER_NAME_KEY);
		String loggerLevel = queryParams.get(LOGGER_LEVEL_KEY);
		Appender theAppender = null;
		if (appenderName != null)
		{
			theAppender = setAppenderThreshold(appenderName, appenderThreshold);
		}

		if (loggerName != null && loggerLevel != null)
		{
			org.apache.log4j.Logger logger = LogManager.getLogger(loggerName);
			if (!logger.getAllAppenders().hasMoreElements() && theAppender != null)
			{
				logger.addAppender(theAppender);
			}
			logger.setLevel(Level.toLevel(loggerLevel));
		}

		PrintWriter out = response.getWriter();

		String acceptHeader = request.getHeader(AppStatusConstants.HTTP_HDR_ACCEPT);

		try
		{
			if (acceptHeader != null
					&& acceptHeader.contains(AppStatusConstants.CONTENT_TYPE_APP_JSON))
			{
				// set the response content type
				response.setContentType(AppStatusConstants.CONTENT_TYPE_APP_JSON);

				ObjectMapper mapper = new ObjectMapper();
				mapper.writeValue(out, getLoggerInfo());
			}
			else
			{
				// set response content type
				response.setContentType(AppStatusConstants.CONTENT_TYPE_TXT_XML);

				JAXBContext context = JAXBContext.newInstance(LoggerInfo.class);
				Marshaller m = context.createMarshaller();
				m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
				m.marshal(getLoggerInfo(), out);
			}
		}
		catch (PropertyException e)
		{
			LOGGER.debug("Caught Property Exception", e);
		}
		catch (JAXBException e)
		{
			LOGGER.debug("Error Marshalling to XML", e);
		}

	}

	private Appender setAppenderThreshold(String appenderName, String appenderThreshold)
	{
		Appender theAppender = null;
		for (@SuppressWarnings("rawtypes")
		Enumeration loggerEnum = LogManager.getCurrentLoggers(); loggerEnum.hasMoreElements();)
		{
			org.apache.log4j.Logger logger = (org.apache.log4j.Logger) loggerEnum.nextElement();
			for (@SuppressWarnings("rawtypes")
			Enumeration appenders = logger.getAllAppenders(); appenders.hasMoreElements();)
			{
				Appender appender = (Appender) appenders.nextElement();
				if (appenderName.equals(appender.getName()))
				{
					if (appender instanceof org.apache.log4j.AppenderSkeleton)
					{
						AppenderSkeleton aSkeleton = (AppenderSkeleton) appender;
						aSkeleton.setThreshold(Level.toLevel(appenderThreshold));
					}
					theAppender = appender;
					break;
				}
			}
		}
		return theAppender;
	}

	private LoggerInfo getLoggerInfo()
	{
		LoggerInfo info = new LoggerInfo();
		HashMap<String, AppenderDetail> appenderSet = new HashMap<String, AppenderDetail>();
		for (@SuppressWarnings("rawtypes")
		Enumeration loggerEnum = LogManager.getCurrentLoggers(); loggerEnum.hasMoreElements();)
		{
			org.apache.log4j.Logger logger = (org.apache.log4j.Logger) loggerEnum.nextElement();

			LoggerDetail lDetail = new LoggerDetail();

			@SuppressWarnings("rawtypes")
			Enumeration appenders = logger.getAllAppenders();
			if (appenders.hasMoreElements())
			{
				lDetail.setLevel(logger.getLevel().toString());
				lDetail.setName(logger.getName());
			}
			while (appenders.hasMoreElements())
			{
				Appender appender = (Appender) appenders.nextElement();
				if (appenderSet.get(appender.getName()) == null)
				{
					AppenderDetail aDetail = new AppenderDetail();
					if (appender instanceof AppenderSkeleton)
					{
						AppenderSkeleton aSkeleton = (AppenderSkeleton) appender;
						aDetail.setThreshold(aSkeleton.getThreshold().toString());
					}
					aDetail.setName(appender.getName());
					appenderSet.put(appender.getName(), aDetail);
				}

				lDetail.getAppenderName().add(appender.getName());
				info.getLogger().add(lDetail);
			}

		}
		for (AppenderDetail aDetail : appenderSet.values())
		{
			info.getAppender().add(aDetail);
		}
		return info;
	}

	private HashMap<String, String> getQueryParams(String queryString)
	{
		HashMap<String, String> queryParams = new HashMap<String, String>();
		if (queryString != null)
		{
			String[] queryParamsArray = queryString.split("&");
			for (String kvPair : queryParamsArray)
			{
				String[] keyValue = kvPair.split("=");
				queryParams.put(keyValue[0], keyValue[1]);
			}
		}
		return queryParams;
	}
}
