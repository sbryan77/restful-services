package com.viasat.common.intercept.cxf;

import org.apache.cxf.interceptor.LoggingMessage;
import org.apache.cxf.interceptor.LoggingOutInterceptor;

/**
 * Extension of CXF's LoggingOutInterceptor which has logic to allow masking of
 * sensitive data in the in-bound XML stream when it is logged.
 */
public class XslLoggingOutInterceptor extends LoggingOutInterceptor
{
	private LoggingProcessor processor = new LoggingProcessor();

	public void setElementMaskFileName(String elementMaskFileName)
	{
		processor.setElementMaskFileName(elementMaskFileName);
	}

	public void setXslStylesheetFileName(String xslStylesheetFileName)
	{
		processor.setXslStylesheetFileName(xslStylesheetFileName);
	}

	public XslLoggingOutInterceptor()
	{
		// Increase the limit on payload length to the maximum
		super(Integer.MAX_VALUE);
	}

	@Override
	protected String formatLoggingMessage(LoggingMessage loggingMessage)
	{
		return processor.formatLoggingMessage(loggingMessage);
	}
}