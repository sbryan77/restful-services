package com.viasat.common.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

public class EnvironmentContextUtility
{
	/** The LOGGER. */
	private static Logger LOGGER = LoggerFactory.getLogger(EnvironmentContextUtility.class);

	/**
	 * System property populated by WebLogic containing the managed server name.
	 */
	private static final String WEBLOGIC_NAME = "weblogic.Name";

	public static String getContextPath(ApplicationContext applicationContext)
	{
		if ((applicationContext != null) && (applicationContext instanceof WebApplicationContext))
		{
			WebApplicationContext webContext = (WebApplicationContext) applicationContext;
			return webContext.getServletContext().getContextPath();
		}

		return null;
	}

	public static String getContextPath(WebServiceContext context)
	{
		ServletContext sc = (context == null) ? null
				: ((context.getMessageContext() == null) ? null : (ServletContext) context
						.getMessageContext().get(MessageContext.SERVLET_CONTEXT));

		return (sc == null) ? null : sc.getContextPath();
	}

	public static String getEndpoint(HttpServletRequest request)
	{
		if (request != null)
		{
			String endPoint = request.getRequestURI();

			if ((request.getQueryString() != null) && (request.getRequestURI().length() != 0))
			{
				endPoint = endPoint + "?" + request.getQueryString();
			}

			return endPoint;
		}

		return null;
	}

	public static String getEndpoint(HttpServletRequest request,
			ApplicationContext applicationContext)
	{
		String endpoint = getEndpoint(request);

		if (endpoint == null)
		{
			endpoint = getContextPath(applicationContext);
		}

		return endpoint;
	}

	public static String getMethod(HttpServletRequest request)
	{
		return (request == null) ? null : request.getMethod();
	}

	public static String getMethod(StackTraceElement ste)
	{
		return ste.getClassName() + "." + ste.getMethodName() + "(" + ste.getLineNumber() + ")";
	}

	/**
	 * Find the value for an ExceptionDetail instance's Node property. Servlet
	 * context is optional, and only used if non-null.
	 * 
	 * @param context
	 * @param mergedDetail
	 * 
	 * @return
	 */
	public static String getNode()
	{
		String node = getNodeFromSystem();

		if (node == null)
		{
			node = getNodeFromInetAddress();
		}

		return node;
	}

	/**
	 * @return the node value.
	 */
	public static String getNodeFromInetAddress()
	{
		String node = null;

		try
		{
			node = InetAddress.getLocalHost().getHostName();
			if (node == null)
			{
				node = InetAddress.getLocalHost().getHostAddress();
			}
		}
		catch (UnknownHostException ignore)
		{
			LOGGER.error("Unable to get host name", ignore);
		}
		return node;
	}

	/**
	 * @return the node value.
	 */
	public static String getNodeFromSystem()
	{
		return System.getProperty(WEBLOGIC_NAME);
	}

	public static String getServletContextName(ApplicationContext applicationContext)
	{
		WebApplicationContext webContext = (WebApplicationContext) applicationContext;

		ServletContext servletContext = webContext.getServletContext();
		return servletContext.getServletContextName();
	}

	/**
	 * @return the timestamp value.
	 */
	public static Date getTimestamp()
	{
		return new Date();
	}

	/**
	 * @return the tracking key.
	 */
	public static String getTrackingKey()
	{
		long timeInMillis = System.currentTimeMillis();
		String trackingKey = Long.toString(timeInMillis);
		return trackingKey;
	}
}
