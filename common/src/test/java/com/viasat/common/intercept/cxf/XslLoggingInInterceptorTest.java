package com.viasat.common.intercept.cxf;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.interceptor.LoggingMessage;
import org.junit.Test;

public class XslLoggingInInterceptorTest
{
	@Test
	public void testPasswordMasking() throws Exception
	{

		LoggingMessage lm = new LoggingMessage("----------------------------", "2");
		lm.getAddress().append(
				"http://pws-finance01.sandbox.dev.wdc1.wildblue.net:8080/FinanceService/v3/services/FinanceService");
		lm.getEncoding().append("UTF-8");
		lm.getHeader().append(
				"{accept-encoding=[gzip,deflate], connection=[Keep-Alive], Content-Length=[1593], content-type=[text/xml;charset=UTF-8], host=[pws-finance01.sandbox.dev.wdc1.wildblue.net:8080], SOAPAction=[\"urn:#NewOperation\"], user-agent=[Apache-HttpClient/4.1.1 (java 1.5)]}");
		lm.getContentType().append("text/xml;charset=UTF-8");
		lm.getPayload().append(
				getTestFileStream("/requests/getSubscriberLedgerSummaryInboundMessage.xml"));

		XslLoggingInInterceptor in = new XslLoggingInInterceptor();
		String transform = in.formatLoggingMessage(lm);

		assertTrue(transform.contains("<Password>******</Password>"));
	}

	@Test
	public void testPasswordTestMasking() throws Exception
	{
		LoggingMessage lm = new LoggingMessage("----------------------------", "2");
		lm.getAddress().append(
				"http://pws-finance01.sandbox.dev.wdc1.wildblue.net:8080/FinanceService/v3/services/FinanceService");
		lm.getEncoding().append("UTF-8");
		lm.getHeader().append(
				"{accept-encoding=[gzip,deflate], connection=[Keep-Alive], Content-Length=[1593], content-type=[text/xml;charset=UTF-8], host=[pws-finance01.sandbox.dev.wdc1.wildblue.net:8080], SOAPAction=[\"urn:#NewOperation\"], user-agent=[Apache-HttpClient/4.1.1 (java 1.5)]}");
		lm.getContentType().append("text/xml;charset=UTF-8");
		lm.getPayload().append(
				getTestFileStream("/requests/getSubscriberInvoiceSummariesInboundMessage.xml"));

		XslLoggingInInterceptor in = new XslLoggingInInterceptor();
		String transform = in.formatLoggingMessage(lm);

		assertTrue(transform.contains("PasswordText\">******</wsse:Password>"));
	}

	@Test
	public void testRoleMasking() throws Exception
	{
		LoggingMessage lm = new LoggingMessage("----------------------------", "2");
		lm.getAddress().append(
				"http://pws-finance01.sandbox.dev.wdc1.wildblue.net:8080/FinanceService/v3/services/FinanceService");
		lm.getEncoding().append("UTF-8");
		lm.getHeader().append(
				"{accept-encoding=[gzip,deflate], connection=[Keep-Alive], Content-Length=[1593], content-type=[text/xml;charset=UTF-8], host=[pws-finance01.sandbox.dev.wdc1.wildblue.net:8080], SOAPAction=[\"urn:#NewOperation\"], user-agent=[Apache-HttpClient/4.1.1 (java 1.5)]}");
		lm.getContentType().append("text/xml;charset=UTF-8");
		lm.getPayload()
				.append(getTestFileStream("/requests/authenticateUserResponseInboundMessage.xml"));

		XslLoggingInInterceptor in = new XslLoggingInInterceptor();
		String transform = in.formatLoggingMessage(lm);
		assertTrue(transform.contains("<role>******</role>"));
	}

	@Test
	public void testInvalidInputMasking() throws Exception
	{
		LoggingMessage lm = new LoggingMessage("----------------------------", "2");
		lm.getAddress().append(
				"http://pws-finance01.sandbox.dev.wdc1.wildblue.net:8080/FinanceService/v3/services/FinanceService");
		lm.getEncoding().append("UTF-8");
		lm.getHeader().append(
				"{accept-encoding=[gzip,deflate], connection=[Keep-Alive], Content-Length=[1593], content-type=[text/xml;charset=UTF-8], host=[pws-finance01.sandbox.dev.wdc1.wildblue.net:8080], SOAPAction=[\"urn:#NewOperation\"], user-agent=[Apache-HttpClient/4.1.1 (java 1.5)]}");
		lm.getContentType().append("text/xml;charset=UTF-8");
		lm.getPayload().append(getTestFileStream("/requests/garbageInboundMessage.xml"));

		XslLoggingInInterceptor in = new XslLoggingInInterceptor();
		String transform = in.formatLoggingMessage(lm);
		assertTrue(
				transform.contains("Error in LoggingProcessor. Please see previous stack trace"));
	}

	@Test
	public void testFormPost()
	{

		LoggingMessage lm = new LoggingMessage("----------------------------", "2381");
		lm.getAddress().append(
				"http://iws-authentication.sandbox.dev.wdc1.wildblue.net/AuthenticationWebService/services/Authentication/authenticate");
		lm.getHttpMethod().append("POST");

		lm.getHeader().append(
				"{Content-Type=[application/x-www-form-urlencoded], Accept=[application/xml, application/json], viasat_user=[PublicCatalogClientTest], viasat_app=[PublicCatalog]}");
		lm.getContentType().append("application/x-www-form-urlencoded");
		lm.getPayload().append("userName=wball&password=blahblahginger&includeMembership=true");

		XslLoggingInInterceptor in = new XslLoggingInInterceptor();
		String transform = in.formatLoggingMessage(lm);

		assertTrue(transform.contains(
				"{Content-Type=[application/x-www-form-urlencoded], Accept=[application/xml, application/json], viasat_user=[******], viasat_app=[PublicCatalog]}"));
		assertTrue(transform.contains("userName=wball&password=******&includeMembership=true"));
	}

	@Test
	public void testXslStylesheetNotFound() throws IOException
	{
		LoggingMessage lm = new LoggingMessage("----------------------------", "2");
		lm.getAddress().append(
				"http://pws-finance01.sandbox.dev.wdc1.wildblue.net:8080/FinanceService/v3/services/FinanceService");
		lm.getEncoding().append("UTF-8");
		lm.getHeader().append(
				"{accept-encoding=[gzip,deflate], connection=[Keep-Alive], Content-Length=[1593], content-type=[text/xml;charset=UTF-8], host=[pws-finance01.sandbox.dev.wdc1.wildblue.net:8080], SOAPAction=[\"urn:#NewOperation\"], user-agent=[Apache-HttpClient/4.1.1 (java 1.5)]}");
		lm.getContentType().append("text/xml;charset=UTF-8");
		lm.getPayload().append(
				getTestFileStream("/requests/getSubscriberLedgerSummaryInboundMessage.xml"));

		XslLoggingInInterceptor in = new XslLoggingInInterceptor();
		in.setXslStylesheetFileName("blah");
		String transform = in.formatLoggingMessage(lm);

		assertTrue(transform
				.contains("Payload could not be logged because there was a problem reading blah"));
	}

	@Test
	public void testElementMaskNotFound() throws IOException
	{
		LoggingMessage lm = new LoggingMessage("----------------------------", "2");
		lm.getAddress().append(
				"http://pws-finance01.sandbox.dev.wdc1.wildblue.net:8080/FinanceService/v3/services/FinanceService");
		lm.getEncoding().append("UTF-8");
		lm.getHeader().append(
				"{accept-encoding=[gzip,deflate], connection=[Keep-Alive], Content-Length=[1593], content-type=[text/xml;charset=UTF-8], host=[pws-finance01.sandbox.dev.wdc1.wildblue.net:8080], SOAPAction=[\"urn:#NewOperation\"], user-agent=[Apache-HttpClient/4.1.1 (java 1.5)]}");
		lm.getContentType().append("text/xml;charset=UTF-8");
		lm.getPayload().append(
				getTestFileStream("/requests/getSubscriberLedgerSummaryInboundMessage.xml"));

		XslLoggingInInterceptor in = new XslLoggingInInterceptor();
		in.setElementMaskFileName("blah");
		String transform = in.formatLoggingMessage(lm);

		System.out.println(transform);
		assertTrue(transform
				.contains("Header could not be logged because there was a problem reading blah"));
	}

	@Test
	public void testElementMaskWIthNoElementNames() throws IOException
	{
		LoggingMessage lm = new LoggingMessage("----------------------------", "2381");
		String address = "http://iws-authentication.sandbox.dev.wdc1.wildblue.net/AuthenticationWebService/services/Authentication/authenticate";
		lm.getAddress().append(
				address);
		lm.getHttpMethod().append("POST");

		lm.getHeader().append(
				"{Content-Type=[application/x-www-form-urlencoded], Accept=[application/xml, application/json], viasat_user=[PublicCatalogClientTest], viasat_app=[PublicCatalog]}");
		lm.getContentType().append("application/x-www-form-urlencoded");
		lm.getPayload().append("userName=wball&password=blahblahginger&includeMembership=true");

		XslLoggingInInterceptor in = new XslLoggingInInterceptor();
		in.setElementMaskFileName("elementMaskNoElementNames.properties");
		String transform = in.formatLoggingMessage(lm);

		assertTrue(transform.contains(
				"{Content-Type=[application/x-www-form-urlencoded], Accept=[application/xml, application/json], viasat_user=[PublicCatalogClientTest], viasat_app=[PublicCatalog]}"));
		assertTrue(transform
				.contains("userName=wball&password=blahblahginger&includeMembership=true"));
		assertTrue(transform.contains(address));
	}

	@Test
	public void testElementMaskWIthEmptyElementNameList() throws IOException
	{
		LoggingMessage lm = new LoggingMessage("----------------------------", "2381");
		lm.getAddress().append(
				"http://iws-authentication.sandbox.dev.wdc1.wildblue.net/AuthenticationWebService/services/Authentication/authenticate");
		lm.getHttpMethod().append("POST");

		lm.getHeader().append(
				"{Content-Type=[application/x-www-form-urlencoded], Accept=[application/xml, application/json], viasat_user=[PublicCatalogClientTest], viasat_app=[PublicCatalog]}");
		lm.getContentType().append("application/x-www-form-urlencoded");
		lm.getPayload().append("userName=wball&password=blahblahginger&includeMembership=true");

		XslLoggingInInterceptor in = new XslLoggingInInterceptor();
		in.setElementMaskFileName("elementMaskEmptyElementNames.properties");
		String transform = in.formatLoggingMessage(lm);

		assertTrue(transform.contains(
				"{Content-Type=[application/x-www-form-urlencoded], Accept=[application/xml, application/json], viasat_user=[PublicCatalogClientTest], viasat_app=[PublicCatalog]}"));
		assertTrue(transform
				.contains("userName=wball&password=blahblahginger&includeMembership=true"));
	}

	@Test
	public void testNoMasking() throws Exception
	{
		LoggingMessage lm = new LoggingMessage("----------------------------", "2");
		lm.getAddress().append(
				"http://pws-finance01.sandbox.dev.wdc1.wildblue.net:8080/FinanceService/v3/services/FinanceService");
		lm.getEncoding().append("UTF-8");
		lm.getHeader().append(
				"{accept-encoding=[gzip,deflate], connection=[Keep-Alive], Content-Length=[1593], content-type=[text/xml;charset=UTF-8], host=[pws-finance01.sandbox.dev.wdc1.wildblue.net:8080], SOAPAction=[\"urn:#NewOperation\"], user-agent=[Apache-HttpClient/4.1.1 (java 1.5)]}");
		lm.getContentType().append("text/xml;charset=UTF-8");
		lm.getPayload().append(
				getTestFileStream("/requests/getSubscriberLedgerSummaryInboundMessage.xml"));

		XslLoggingInInterceptor in = new XslLoggingInInterceptor();
		in.setXslStylesheetFileName("nomasklogging.xsl");
		String transform = in.formatLoggingMessage(lm);

		assertTrue(transform.contains("<Password>blahblahblah</Password>"));
	}
	@Test
	public void testElementMaskAddressNames() throws IOException
	{
		LoggingMessage lm = new LoggingMessage("----------------------------", "2381");
		lm.getAddress().append(
				"https://viasat--devsup1.cs33.my.salesforce.com/services/oauth2/token?client_id=3MVG99fZdHp1UitRS4.Motyxh.SoCt1rS.93NG_kDxMPuyk0LzdQwOs59QKpiXMdXCWhkxHmANKj6bG2D7mUx&client_secret=1090004885593838785&grant_type=password&username=vps.exederes.integration%40viasat.com.devsup1&password=W3lcome17%23dOn0aiLav0e5q3X1ZekbNXxSu");
		lm.getHttpMethod().append("POST");

		lm.getHeader().append(
				"{Content-Type=[application/x-www-form-urlencoded], Accept=[application/xml, application/json], viasat_user=[PublicCatalogClientTest], viasat_app=[PublicCatalog]}");
		lm.getContentType().append("application/x-www-form-urlencoded");
		lm.getPayload().append("userName=wball&password=blahblahginger&includeMembership=true");

		XslLoggingInInterceptor in = new XslLoggingInInterceptor();
		in.setElementMaskFileName("elementMaskNoElementNames.properties");
		String transform = in.formatLoggingMessage(lm);

		System.out.println(transform);
		
		assertTrue(transform.contains(
				"{Content-Type=[application/x-www-form-urlencoded], Accept=[application/xml, application/json], viasat_user=[PublicCatalogClientTest], viasat_app=[PublicCatalog]}"));
		assertTrue(transform
				.contains("userName=wball&password=blahblahginger&includeMembership=true"));
		
	}
	private String getTestFileStream(String testFileName) throws IOException
	{
		InputStream stream = getClass().getResourceAsStream(testFileName);
		String string = IOUtils.toString(stream);
		return string;
	}
}
