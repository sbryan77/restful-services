package com.viasat.wildblue.common.exception;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Locale;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.viasat.wildblue.common.exception.contingency.WildBlueContingencyException;

/**
 * This is a utilities class that provides methods that merge exception,
 * exceptiondetail, and servletcontext into a single ExceptionDetail object
 */
public class ExceptionDetailUtilities
{
	/** The LOGGER. */
	private static Logger LOGGER = LoggerFactory.getLogger(ExceptionDetailUtilities.class);

	/** Singleton instance. */
	private static ExceptionDetailUtilities INSTANCE = null;

	/**
	 * System property populated by WebLogic containing the managed server name.
	 */
	private static final String WEBLOGIC_NAME = "weblogic.Name";

	/**
	 * Private constructor for static instance
	 */
	private ExceptionDetailUtilities()
	{
		// Do nothing
	}

	/**
	 * Get static instance
	 *
	 * @return instance
	 */
	public static ExceptionDetailUtilities getInstance()
	{
		if (INSTANCE == null)
		{
			INSTANCE = new ExceptionDetailUtilities();
		}

		return INSTANCE;
	}

	/**
	 * Get an exceptionDetail that has timestamp, tracking key, and node
	 * populated (if system properties are retrievable).Never returns null.
	 *
	 * @return exceptionDetail
	 */
	public ExceptionDetail createExceptionDetail()
	{
		return createExceptionDetail(null, null, null);
	}

	/**
	 * Translate a throwable into an ExceptionDetail object, Never returns null.
	 *
	 * @param cause
	 *            Cause.
	 *
	 * @return exceptionDetail
	 */
	public ExceptionDetail createExceptionDetail(Throwable cause)
	{
		if (cause instanceof WildBlueContingencyException)
		{
			return createExceptionDetail(null, cause, null);
		}
		else
		{
			return createFaultExceptionDetail(null, cause, null);
		}
	}

	/**
	 * Translate a servlet context into an ExceptionDetail object. Never returns
	 * null.
	 *
	 * @param context
	 *            Servlet context.
	 *
	 * @return exceptionDetail
	 */
	public ExceptionDetail createExceptionDetail(ServletContext context)
	{
		return createExceptionDetail(null, null, context);
	}

	/**
	 * Creates an exceptionDetail object with node, role, timestamp, and
	 * tracking key, in addition to exceptionDetail's values. Never returns
	 * null.
	 *
	 * @param exceptionDetail
	 *            Exception detail.
	 *
	 * @return exceptionDetail
	 */
	public ExceptionDetail createExceptionDetail(ExceptionDetail exceptionDetail)
	{
		return createExceptionDetail(exceptionDetail, null, null);
	}

	/**
	 * Translate a throwable and exceptionDetail object into one exceptionDetail
	 * object. Values in the exceptionDetail take precedence over values in the
	 * throwable. Never returns null.
	 *
	 * @param cause
	 *            Cause.
	 * @param exceptionDetail
	 *            Exception detail.
	 *
	 * @return exceptionDetail
	 */
	public ExceptionDetail createExceptionDetail(ExceptionDetail exceptionDetail, Throwable cause)
	{
		return createExceptionDetail(exceptionDetail, cause, null);
	}

	/**
	 * Translate a throwable and a servlet context into an ExceptionDetail
	 * object. Values in the throwable will take precedence over those in
	 * servletContext. Never returns null.
	 *
	 * @param cause
	 *            Cause.
	 * @param context
	 *            Servlet context.
	 *
	 * @return exceptionDetail
	 */
	public ExceptionDetail createExceptionDetail(Throwable cause, ServletContext context)
	{
		return createExceptionDetail(null, cause, context);
	}

	/**
	 * Translates an exceptionDetail object and a servletContext into one
	 * ExceptionDetail object. Values in the exceptionDetail take precedence
	 * over those in servletContext. Never returns null.
	 *
	 * @param exceptionDetail
	 *            Exception detail.
	 * @param context
	 *            Servlet context.
	 *
	 * @return exceptionDetail
	 */
	public ExceptionDetail createExceptionDetail(ExceptionDetail exceptionDetail,
			ServletContext context)
	{
		return createExceptionDetail(exceptionDetail, null, context);
	}

	/**
	 * Translates an exceptionDetail, throwable, and servletContext into one
	 * exceptionDetail object. First, use cause simpleName and message, then if
	 * cause has an ExceptionDetail, override those values. Then override with
	 * exceptionDetail values. Top it off with populating node, role, timestamp,
	 * and tracking key if they are still not yet populated. Never returns null.
	 *
	 * @param exceptionDetail
	 *            Exception detail.
	 * @param cause
	 *            Cause.
	 * @param context
	 *            Servlet context.
	 *
	 * @return exceptionDetail
	 */
	public ExceptionDetail createExceptionDetail(ExceptionDetail exceptionDetail, Throwable cause,
			ServletContext context)
	{
		ExceptionDetail mergedDetail = new ExceptionDetail();

		if (cause != null)
		{
			ExceptionDetail causeDetail = new ExceptionDetail();
			ExceptionInfo exceptionInfo = getExceptionInfo(cause);

			causeDetail.setCode(determineCode(cause, exceptionInfo));
			causeDetail.setReason(determineReason(exceptionInfo));
			causeDetail.setDetail(cause.getMessage());

			populateExceptionDetail(mergedDetail, causeDetail);

			if (cause instanceof HasExceptionDetail)
			{
				populateExceptionDetail(mergedDetail,
						((HasExceptionDetail) cause).getExceptionDetail());
			}
		}

		if (exceptionDetail != null)
		{
			populateExceptionDetail(mergedDetail, exceptionDetail);
		}

		if (mergedDetail.getRole() == null)
		{
			mergedDetail.setRole(getRoleFromServletContext(context));
		}

		if (mergedDetail.getNode() == null)
		{
			String node = getNodeValue(context);
			mergedDetail.setNode(node);
		}

		if (mergedDetail.getTimestamp() == null)
		{
			mergedDetail.setTimestamp(getTimestamp());
		}

		if (mergedDetail.getTrackingKey() == null)
		{
			mergedDetail.setTrackingKey(getTrackingKey());
		}

		return mergedDetail;
	}

	/**
	 * Translates an exceptionDetail, throwable, and servletContext into one
	 * exceptionDetail object. First, use cause simpleName and message, then if
	 * cause has an ExceptionDetail, override those values. Then override with
	 * exceptionDetail values. Top it off with populating node, role, timestamp,
	 * and tracking key if they are still not yet populated. Never returns null.
	 *
	 * @param exceptionDetail
	 *            Exception detail.
	 * @param cause
	 *            Cause.
	 * @param context
	 *            Servlet context.
	 *
	 * @return exceptionDetail
	 */
	public ExceptionDetail createFaultExceptionDetail(ExceptionDetail exceptionDetail,
			Throwable cause, ServletContext context)
	{
		ExceptionDetail mergedDetail = new ExceptionDetail();

		if (cause != null)
		{
			ExceptionDetail causeDetail = new ExceptionDetail();
			ExceptionInfo exceptionInfo = getExceptionInfo(cause);

			causeDetail.setCode(determineCode(cause, exceptionInfo));
			causeDetail.setReason(determineReason(exceptionInfo));

			causeDetail.setDetail("Unexpected exception");

			populateExceptionDetail(mergedDetail, causeDetail);

			if (cause instanceof HasExceptionDetail)
			{
				populateExceptionDetail(mergedDetail,
						((HasExceptionDetail) cause).getExceptionDetail());
			}
		}

		if (exceptionDetail != null)
		{
			populateExceptionDetail(mergedDetail, exceptionDetail);
		}

		if (mergedDetail.getRole() == null)
		{
			mergedDetail.setRole(getRoleFromServletContext(context));
		}

		if (mergedDetail.getNode() == null)
		{
			String node = getNodeValue(context);
			mergedDetail.setNode(node);
		}

		if (mergedDetail.getTimestamp() == null)
		{
			mergedDetail.setTimestamp(getTimestamp());
		}

		if (mergedDetail.getTrackingKey() == null)
		{
			mergedDetail.setTrackingKey(getTrackingKey());
		}

		return mergedDetail;
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
	public String getNodeValue(ServletContext context)
	{
		String node = getNodeFromSystem();

		if (node == null)
		{
			node = getNodeFromInetAddress();

			if ((node == null) && (context != null))
			{
				node = getNodeFromServletContext(context);
			}
		}

		return node;
	}

	/**
	 * @return a new exceptionInfoProcessor
	 */
	private ExceptionInfoProcessor createExceptionInfoProcessor()
	{
		ExceptionInfoProvider exceptionInfoProvider = new FileBasedExceptionInfoProvider();

		return new ExceptionInfoProcessor(exceptionInfoProvider);
	}

	/**
	 * @param cause
	 *            Cause.
	 * @param exceptionInfo
	 *            Exception info.
	 *
	 * @return the code for the given parameter.
	 */
	private String determineCode(Throwable cause, ExceptionInfo exceptionInfo)
	{
		String answer = null;

		if (cause != null)
		{
			if (exceptionInfo != null)
			{
				answer = exceptionInfo.getCode();
			}

			if (answer == null)
			{
				answer = cause.getClass().getName();
			}
		}

		return answer;
	}

	/**
	 * @param exceptionInfo
	 *            Exception info.
	 *
	 * @return the code for the given parameter.
	 */
	private String determineReason(ExceptionInfo exceptionInfo)
	{
		String answer = null;

		if (exceptionInfo != null)
		{
			answer = exceptionInfo.getReason();
		}

		return answer;
	}

	/**
	 * @param cause
	 *            Cause.
	 *
	 * @return the code for the given parameter.
	 */
	private ExceptionInfo getExceptionInfo(Throwable cause)
	{
		ExceptionInfo answer = null;

		if (cause != null)
		{
			String exceptionClassname = cause.getClass().getName();
			Locale locale = Locale.getDefault();
			ExceptionInfoProcessor processor = createExceptionInfoProcessor();

			answer = processor.getExceptionInfo(exceptionClassname, locale);
		}

		return answer;
	}

	/**
	 * @return the node value.
	 */
	private String getNodeFromInetAddress()
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
	 * @param context
	 *            Servlet context.
	 *
	 * @return the node value.
	 */
	private String getNodeFromServletContext(ServletContext context)
	{
		String node = null;

		if (context != null)
		{
			String realPath = context.getRealPath("/index.jsp");

			if (realPath != null)
			{
				String x = new String(realPath);

				if (x.indexOf("servers") > -1)
				{
					x = x.substring(x.indexOf("servers") + "servers".length() + 1);

					if (x.indexOf("/") > -1)
					{
						node = x.substring(0, x.indexOf("/"));
					}
				}
			}
		}

		return node;
	}

	/**
	 * @return the node value.
	 */
	private String getNodeFromSystem()
	{
		String node = System.getProperty(WEBLOGIC_NAME);
		return node;
	}

	/**
	 * @param context
	 *            Servlet context.
	 *
	 * @return the role value.
	 */
	private String getRoleFromServletContext(ServletContext context)
	{
		return (context == null) ? null : context.getContextPath();
	}

	/**
	 * @return the timestamp value.
	 */
	private Date getTimestamp()
	{
		return new Date();
	}

	/**
	 * @return the tracking key.
	 */
	private String getTrackingKey()
	{
		long timeInMillis = System.currentTimeMillis();
		String trackingKey = Long.toString(timeInMillis);
		return trackingKey;
	}

	/**
	 * Override values in toExceptionDetail with those in fromExceptionDetail,
	 * if those values are not null
	 *
	 * @param toExceptionDetail
	 *            To exception detail.
	 * @param fromExceptionDetail
	 *            To exception detail.
	 */
	private void populateExceptionDetail(ExceptionDetail toExceptionDetail,
			ExceptionDetail fromExceptionDetail)
	{
		if (fromExceptionDetail.getCode() != null)
		{
			toExceptionDetail.setCode(fromExceptionDetail.getCode());
		}

		if (fromExceptionDetail.getDetail() != null)
		{
			toExceptionDetail.setDetail(fromExceptionDetail.getDetail());
		}

		if (fromExceptionDetail.getReason() != null)
		{
			toExceptionDetail.setReason(fromExceptionDetail.getReason());
		}

		if (fromExceptionDetail.getTimestamp() != null)
		{
			toExceptionDetail.setTimestamp(fromExceptionDetail.getTimestamp());
		}

		if (fromExceptionDetail.getRole() != null)
		{
			toExceptionDetail.setRole(fromExceptionDetail.getRole());
		}

		if (fromExceptionDetail.getNode() != null)
		{
			toExceptionDetail.setNode(fromExceptionDetail.getNode());
		}

		if (fromExceptionDetail.getTrackingKey() != null)
		{
			toExceptionDetail.setTrackingKey(fromExceptionDetail.getTrackingKey());
		}
	}
}
