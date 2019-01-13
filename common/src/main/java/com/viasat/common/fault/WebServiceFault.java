package com.viasat.common.fault;

import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.WebFault;
import javax.xml.ws.WebServiceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.viasat.common.util.EnvironmentContextUtility;

/**
 * Common WebServiceException
 */
@WebFault(name = "faultDetail", targetNamespace = "http://www.viasat.com/XMLSchema/v1/Fault")
public class WebServiceFault extends Exception
{
	public static final String CLIENT_FAULT = "REJECTED";

	public static final String SYSTEM_FAULT = "SYSTEM_FAULT";

	/** Serial version UID */
	public static final long serialVersionUID = 20101025090759L;

	@SuppressWarnings("unused")
	/** The LOGGER. */
	private static Logger LOGGER = LoggerFactory.getLogger(WebServiceFault.class);

	private String faultString = null;

	/** Fault info */
	private FaultDetail m_faultDetail;

	/**
     */
	public WebServiceFault()
	{
		super();
		initExceptionDetail();
	}

	/**
	 * Cxf requires this constructor but the context path won't be set
	 * 
	 * @param message
	 */
	public WebServiceFault(String message)
	{
		faultString = SYSTEM_FAULT;
		initExceptionDetail();
		getFaultDetail().setMessage(message);
	}

	/**
	 * This constructor is used by CXF. Do not remove.
	 * 
	 * @param message
	 *            the detail message. The detail message is saved for later
	 *            retrieval by the Throwable.getMessage() method.
	 * @param faultDetail
	 *            fault detail.
	 */
	public WebServiceFault(String message, FaultDetail faultDetail)
	{
		faultString = message;
		m_faultDetail = faultDetail;
	}

	public WebServiceFault(ApplicationContext applicationContext, List<ValidationError> errors)
	{
		faultString = CLIENT_FAULT;
		initExceptionDetail();
		getFaultDetail().getValidationError().addAll(errors);

		String contextPath = EnvironmentContextUtility.getContextPath(applicationContext);
		getFaultDetail().setContextPath(contextPath);
		getFaultDetail().setMessage(CLIENT_FAULT);
	}

	public WebServiceFault(ApplicationContext applicationContext, String errorKey,
			String fieldName, String fieldValue)
	{
		faultString = CLIENT_FAULT;
		initExceptionDetail();

		List<ValidationError> validationErrors = new ArrayList<ValidationError>();
		ValidationError valError = new ValidationError();
		String errorCode = FaultKeyEnum.findCodeByKey(errorKey);

		valError.setValidationCode(errorCode);

		com.viasat.common.fault.Input input = new Input();
		input.setField(fieldName);
		input.setValue(fieldValue);
		valError.getInput().add(input);
		validationErrors.add(valError);

		getFaultDetail().getValidationError().addAll(validationErrors);

		String contextPath = EnvironmentContextUtility.getContextPath(applicationContext);
		getFaultDetail().setContextPath(contextPath);
		getFaultDetail().setMessage(CLIENT_FAULT);
	}

	public WebServiceFault(WebServiceContext context, String message)
	{
		faultString = SYSTEM_FAULT;
		initExceptionDetail();
		getFaultDetail().setMessage(message);
		getFaultDetail().setContextPath(EnvironmentContextUtility.getContextPath(context));
	}

	/**
	 * Internal/Facade Webservice Checked Exception Example
	 */
	public WebServiceFault(WebServiceContext context, List<ValidationError> errors)
	{
		faultString = CLIENT_FAULT;
		initExceptionDetail();
		getFaultDetail().getValidationError().addAll(errors);
		getFaultDetail().setContextPath(EnvironmentContextUtility.getContextPath(context));
		getFaultDetail().setMessage(CLIENT_FAULT);
	}

	public WebServiceFault(WebServiceContext context, String errorKey, String fieldName,
			String fieldValue)
	{
		faultString = CLIENT_FAULT;
		initExceptionDetail();

		List<ValidationError> validationErrors = new ArrayList<ValidationError>();
		ValidationError valError = new ValidationError();
		String errorCode = FaultKeyEnum.findCodeByKey(errorKey);

		valError.setValidationCode(errorCode);

		com.viasat.common.fault.Input input = new Input();
		input.setField(fieldName);
		input.setValue(fieldValue);
		valError.getInput().add(input);
		validationErrors.add(valError);

		getFaultDetail().getValidationError().addAll(validationErrors);

		getFaultDetail().setContextPath(EnvironmentContextUtility.getContextPath(context));
		getFaultDetail().setMessage(CLIENT_FAULT);
	}

	/**
	 * Internal/Facade Webservice System Exception Example<BR/>
	 * Internal/Facade Webservice System Exception caused by an unhandled Client
	 * Exception Example
	 * 
	 * @param context
	 * @param cause
	 */
	public WebServiceFault(WebServiceContext context, Throwable cause)
	{
		this(context, cause.getMessage());
	}

	public WebServiceFault(ApplicationContext applicationContext, String message)
	{
		faultString = SYSTEM_FAULT;
		initExceptionDetail();
		getFaultDetail().setMessage(message);

		String contextPath = EnvironmentContextUtility.getContextPath(applicationContext);
		getFaultDetail().setContextPath(contextPath);
	}

	/**
	 * Internal/Facade Webservice System Exception Example #2
	 * 
	 * @param context
	 * @param cause
	 */
	public WebServiceFault(WebServiceContext context, String message, WebServiceFault cause)
	{
		this(context, message);
		getFaultDetail().setFaultDetail(cause.getFaultDetail());
	}

	/**
	 * Internal/Facade Webservice Checked Exception Example 2
	 * 
	 * @param context
	 * @param errors
	 * @param cause
	 */
	public WebServiceFault(WebServiceContext context, List<ValidationError> errors,
			WebServiceFault cause)
	{
		this(context, errors);
		getFaultDetail().setFaultDetail(cause.getFaultDetail());
	}

	/**
	 * @see com.viasat.wildblue.common.exception.HasExceptionDetail#getFaultDetail()
	 */
	public FaultDetail getFaultDetail()
	{
		return getFaultInfo();
	}

	/**
	 * @return ExceptionDetail object
	 */
	public FaultDetail getFaultInfo()
	{
		return m_faultDetail;
	}

	public String getMessage()
	{
		return faultString;
	}

	/**
	 * @see java.lang.Throwable#toString()
	 */
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder("Message = ");
		sb.append(getMessage());
		sb.append("\n");

		if (getFaultInfo() != null)
		{
			sb.append(getFaultInfo().toString());
		}

		return sb.toString();
	}

	/**
	 * Setter for subclasses
	 * 
	 * @param exceptionDetail
	 *            Exception detail.
	 */
	protected void setFaultInfo(FaultDetail exceptionDetail)
	{
		m_faultDetail = exceptionDetail;
	}

	private void initExceptionDetail()
	{
		m_faultDetail = new FaultDetail();
		m_faultDetail.setMethod(EnvironmentContextUtility.getMethod(getStackTrace()[0]));
		m_faultDetail.setNode(EnvironmentContextUtility.getNode());
		m_faultDetail.setTimestamp(EnvironmentContextUtility.getTimestamp());
		m_faultDetail.setTrackingKey(EnvironmentContextUtility.getTrackingKey());
	}
}
