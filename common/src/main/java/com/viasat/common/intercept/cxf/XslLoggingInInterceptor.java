package com.viasat.common.intercept.cxf;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingMessage;

/**
 * Extension of CXF's LoggingInInterceptor which has logic to allow masking of
 * sensitive data in the in-bound XML stream when it is logged.
 */
public class XslLoggingInInterceptor extends LoggingInInterceptor
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

	public XslLoggingInInterceptor()
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