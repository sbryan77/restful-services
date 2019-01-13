package com.viasat.common.intercept.cxf;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.interceptor.LoggingMessage;
import org.junit.Test;

public class XslLoggingOutInterceptorTest extends XslLoggingOutInterceptor
{
	@Test
	public void testJsonResponse() throws Exception
	{
		LoggingMessage lm = new LoggingMessage("----------------------------", "622");
		lm.getResponseCode().append("200");


		lm.getHeader()
				.append("{Content-Type=[application/json], Date=[Wed, 08 Feb 2017 00:00:37 GMT]}");

		lm.getContentType().append("application/json");
		lm.getPayload().append(getTestFileStream("/requests/jsonResponse.xml"));

		XslLoggingOutInterceptor in = new XslLoggingOutInterceptor();
		String transform = in.formatLoggingMessage(lm);

		System.out.println(transform);
		assertTrue(transform.contains("\"firstName\":\"******\""));
		assertTrue(transform.contains("\"contactMediumId\":\"******\""));
		assertTrue(transform.contains("\"accountId\":\"******\""));
	}
	
	@Test
	public void testJWTResponse() throws Exception
	{
		LoggingMessage lm = new LoggingMessage("----------------------------", "622");
		lm.getResponseCode().append("200");


		lm.getHeader()
				.append("{Content-Type=[application/json], Date=[Wed, 08 Feb 2017 00:00:37 GMT]}");

		lm.getContentType().append("application/jwt");
		lm.getPayload().append(getTestFileStream("/requests/jsonResponse.xml"));

		XslLoggingOutInterceptor in = new XslLoggingOutInterceptor();
		String transform = in.formatLoggingMessage(lm);

		System.out.println(transform);
		assertTrue(transform.contains("Payload: ******"));
	}
	
	@Test
	public void testUnknownContentType() throws Exception
	{
		LoggingMessage lm = new LoggingMessage("----------------------------", "622");
		lm.getResponseCode().append("200");


		lm.getHeader()
				.append("{Content-Type=[application/json], Date=[Wed, 08 Feb 2017 00:00:37 GMT]}");

		lm.getContentType().append("application/blah");
		lm.getPayload().append(getTestFileStream("/requests/jsonResponse.xml"));

		XslLoggingOutInterceptor in = new XslLoggingOutInterceptor();
		String transform = in.formatLoggingMessage(lm);

		System.out.println(transform);
		assertTrue(transform.contains("Payload: Unhandled content type application/blah. Payload not logged."));
	}
	

	@Test
	public void testMissingElementMask() throws Exception
	{
		LoggingMessage lm = new LoggingMessage("----------------------------", "622");
		lm.getResponseCode().append("200");


		lm.getHeader()
				.append("{Content-Type=[application/json], Date=[Wed, 08 Feb 2017 00:00:37 GMT]}");

		lm.getContentType().append("application/json");
		lm.getPayload().append(getTestFileStream("/requests/jsonResponse.xml"));

		XslLoggingOutInterceptor in = new XslLoggingOutInterceptor();
		in.setElementMaskFileName("blah");
		String transform = in.formatLoggingMessage(lm);

		assertTrue(transform
				.contains("Header could not be logged because there was a problem reading blah"));
		assertTrue(transform
				.contains("Payload could not be logged because there was a problem reading blah"));
	}

	private String getTestFileStream(String testFileName) throws IOException
	{
		InputStream stream = getClass().getResourceAsStream(testFileName);
		String string = IOUtils.toString(stream);
		return string;
	}

}
