package com.viasat.common.intercept.cxf;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.apache.cxf.interceptor.LoggingMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Extension of CXF's LoggingInInterceptor which has logic to allow masking of
 * sensitive data in the in-bound XML stream when it is logged.
 */
public class LoggingProcessor
{
	private static final Logger LOGGER = LoggerFactory.getLogger(XslLoggingInInterceptor.class);

	public String elementMaskFileName = "elementMask.properties";
	public String xslStylesheetFileName = "logging.xsl";

	public Masker masker = null;
	public XmlMasker xmlMasker = null;

	public void setElementMaskFileName(String elementMaskFileName)
	{
		this.elementMaskFileName = elementMaskFileName;
	}

	public void setXslStylesheetFileName(String xslStylesheetFileName)
	{
		this.xslStylesheetFileName = xslStylesheetFileName;
	}

	public String formatLoggingMessage(LoggingMessage loggingMessage)
	{
		initMasker();
		try
		{
			String maskedHeader = maskHeader(loggingMessage);
			loggingMessage.getHeader().setLength(0);
			loggingMessage.getHeader().append(maskedHeader);

			if (masker == null || masker.isAddressMask())
			{
				String maskedAddress = maskAddress(loggingMessage);
				loggingMessage.getAddress().setLength(0);
				loggingMessage.getAddress().append(maskedAddress);
			}

			String maskedPayload = null;
			String contentType = loggingMessage.getContentType().toString();
			if (contentType.contains("xml"))
			{
				initXmlMasker();

				if (xmlMasker == null)
				{
					maskedPayload = "Payload could not be logged because there was a problem reading "
							+ xslStylesheetFileName;
				}
				else
				{
					maskedPayload = maskXml(loggingMessage.getPayload().toString());
				}

			}
			else if (contentType.contains("json"))
			{
				maskedPayload = maskJson(loggingMessage);
			}
			else if (contentType.contains("x-www-form-urlencoded"))
			{
				maskedPayload = maskForm(loggingMessage);
			}

			else if (contentType.contains("jwt"))
			{
				maskedPayload = Masker.REPLACEMENT_MASK;
			}

			else if (contentType.trim().length() == 0)
			{
				// If all else fails, try to do a best guess
				String payload = loggingMessage.getPayload().toString().trim();
				if (payload.startsWith("<"))
				{
					initXmlMasker();

					if (xmlMasker == null)
					{
						maskedPayload = "Payload could not be logged because there was a problem reading "
								+ xslStylesheetFileName;
					}
					else
					{
						maskedPayload = maskXml(payload);
					}
				}
				else if (payload.startsWith("[") || payload.startsWith("{"))
				{
					maskedPayload = maskJson(loggingMessage);
				}
				else
				{
					maskedPayload = maskForm(loggingMessage);
				}
			}
			else
			{
				maskedPayload = "Unhandled content type " + contentType + ". Payload not logged.";
			}

			loggingMessage.getPayload().setLength(0);
			loggingMessage.getPayload().append(maskedPayload);

			return loggingMessage.toString();
		}
		catch (RuntimeException e)
		{
			LOGGER.error("Runtime exception in formatLoggingMessage", e);
			return "Runtime exception. See previous stack trace";
		}
	}

	private String maskXml(String payload)
	{
		String maskedXml = null;
		try
		{
			maskedXml = xmlMasker.maskXml(payload);
		}
		catch (TransformerException e)
		{
			LOGGER.error("Error transforming xml", e);
			maskedXml = "Error in " + getClass().getSimpleName()
					+ ". Please see previous stack trace";
		}
		return maskedXml;
	}

	private String maskAddress(LoggingMessage loggingMessage)
	{
		if (masker == null)
			return "Address could not be logged because there was a problem reading "
					+ elementMaskFileName;
		return masker.maskAddressElements(loggingMessage.getAddress().toString());
	}

	private String maskHeader(LoggingMessage loggingMessage)
	{
		if (masker == null)
			return "Header could not be logged because there was a problem reading "
					+ elementMaskFileName;
		return masker.maskHeaderElements(loggingMessage.getHeader().toString());
	}

	private void initMasker()
	{
		try
		{
			if (masker == null)
			{
				InputStream elementMaskStream = getClass().getClassLoader()
						.getResourceAsStream(elementMaskFileName);
				masker = new Masker(elementMaskStream);
			}
		}
		catch (IOException | NullPointerException e)
		{
			LOGGER.error("Error in initializing from " + elementMaskFileName, e);

		}
	}

	private void initXmlMasker()
	{
		try
		{
			if (xmlMasker == null)
			{
				InputStream styleSheetStream = getClass().getClassLoader()
						.getResourceAsStream(xslStylesheetFileName);
				xmlMasker = new XmlMasker(styleSheetStream);
			}
		}
		catch (TransformerConfigurationException | TransformerFactoryConfigurationError e)
		{
			LOGGER.error("Error initializing stylesheet " + xslStylesheetFileName, e);
		}
	}

	protected String maskForm(LoggingMessage loggingMessage)
	{
		if (masker == null)
			return "Payload could not be logged because there was a problem reading "
					+ elementMaskFileName;
		else
			return masker.maskFormElements(loggingMessage.getPayload().toString());
	}

	protected String maskJson(LoggingMessage loggingMessage)
	{
		String payload = null;
		if (masker == null)
			payload = "Payload could not be logged because there was a problem reading "
					+ elementMaskFileName;
		else
		{
			try
			{
				// Apparently it is possible that the payload will be an empty
				// string. In which case there is nothing to mask.

				payload = loggingMessage.getPayload().toString();
				if (payload != null && payload.trim().length() > 0)
				{
					payload = masker.maskJson(payload);
				}

			}
			catch (IOException e)
			{
				LOGGER.error("Error in transform: ", e);
				payload = "Error in " + getClass().getSimpleName()
						+ ". Please see previous stack trace";
			}
		}
		return payload;
	}

}