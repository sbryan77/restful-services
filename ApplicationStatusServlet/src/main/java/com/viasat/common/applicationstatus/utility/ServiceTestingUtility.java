package com.viasat.common.applicationstatus.utility;

import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.util.Base64;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.ws.BindingProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.viasat.common.applicationstatus.HealthCheckStatus;

public class ServiceTestingUtility
{
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceTestingUtility.class);

	// default connect and read timeout settings (10s)
	private static int connectTimeoutMillis = 10000;
	private static int readTimeoutMillis = 10000;

	public static int getConnectTimeoutMillis()
	{
		return connectTimeoutMillis;
	}

	public static void setConnectTimeoutMillis(int connectTimeoutMillis)
	{
		ServiceTestingUtility.connectTimeoutMillis = connectTimeoutMillis;
	}

	public static int getReadTimeoutMillis()
	{
		return readTimeoutMillis;
	}

	public static void setReadTimeoutMillis(int readTimeoutMillis)
	{
		ServiceTestingUtility.readTimeoutMillis = readTimeoutMillis;
	}

	/**
	 * This utility method will do a simple HTTP connection to the provided URL
	 * string and check for an HTTP 200 response code. It will accept both http
	 * and https URLs.
	 * 
	 * @param urlString
	 *            URL to test in string form.
	 * @return {@link HealthCheckStatus}.ERROR is returned for non-200 responses
	 *         and exceptions; {@link HealthCheckStatus}.OK is returned for 200
	 *         response codes.
	 */
	public static HealthCheckStatus testHTTPConnection(String urlString)
	{
		return testHTTPConnection(urlString, null, null, null, null);
	}

	/**
	 * This utility method will do a simple HTTP connection to the provided URL
	 * string and check for an HTTP 200 response code. It will accept both http
	 * and https URLs.
	 * 
	 * @param urlString
	 *            URL to test in string form.
	 * @param user
	 *            Specify a user name for basic authentication.
	 * @param password
	 *            Specify a password for basic authentication.
	 * 
	 * @return {@link HealthCheckStatus}.ERROR is returned for non-200 responses
	 *         and exceptions; {@link HealthCheckStatus}.OK is returned for 200
	 *         response codes.
	 */
	public static HealthCheckStatus testHTTPConnection(String urlString, String user,
			String password)
	{
		return testHTTPConnection(urlString, null, user, password, null);
	}
	
	/**
	 * This utility method will do a simple HTTP connection to the provided URL
	 * string and check for an HTTP 200 response code. It will accept both http
	 * and https URLs.
	 * 
	 * @param urlString
	 *            URL to test in string form.
	 * @param user
	 *            Specify a user name for basic authentication.
	 * @param password
	 *            Specify a password for basic authentication.
	 * @param authCharset
	 *            Specify the charset used to encode the user name and password.
	 *            Default if not provided is "ISO-8859-1".
	 *            {@link AppStatusConstants.ISO_8859_1} or
	 *            {@link AppStatusConstants.UTF_8}
	 * 
	 * @return {@link HealthCheckStatus}.ERROR is returned for non-200 responses
	 *         and exceptions; {@link HealthCheckStatus}.OK is returned for 200
	 *         response codes.
	 */
	public static HealthCheckStatus testHTTPConnection(String urlString, String user, String password, String authCharset)
	{
		return testHTTPConnection(urlString, null, user, password, authCharset);
	}

	/**
	 * This utility method will do a simple HTTP connection to the provided URL
	 * string and check for an HTTP 200 response code. It will accept both http
	 * and https URLs.
	 * 
	 * @param urlString
	 *            URL to test in string form.
	 * @param proxy
	 *            Specify a {@link Proxy} to use or pass null for none.
	 * @return {@link HealthCheckStatus}.ERROR is returned for non-200 responses
	 *         and exceptions; {@link HealthCheckStatus}.OK is returned for 200
	 *         response codes.
	 */
	public static HealthCheckStatus testHTTPConnection(String urlString, Proxy proxy)
	{
		return testHTTPConnection(urlString, proxy, null, null, null);
	}

	/**
	 * This utility method will do a simple HTTP connection to the provided URL
	 * string and check for an HTTP 200 response code. It will accept both http
	 * and https URLs.
	 * 
	 * @param urlString
	 *            URL to test in string form.
	 * @param proxy
	 *            Specify a {@link Proxy} to use or pass null for none.
	 * @param user
	 *            Specify a user name for basic authentication.
	 * @param password
	 *            Specify a password for basic authentication.
	 * 
	 * @return {@link HealthCheckStatus}.ERROR is returned for non-200 responses
	 *         and exceptions; {@link HealthCheckStatus}.OK is returned for 200
	 *         response codes.
	 */
	public static HealthCheckStatus testHTTPConnection(String urlString, Proxy proxy, String user,
			String password)
	{
		return testHTTPConnection(urlString, proxy, user, password, null);
	}

	/**
	 * This utility method will do a simple HTTP connection to the provided URL
	 * string and check for an HTTP 200 response code. It will accept both http
	 * and https URLs.
	 * 
	 * @param urlString
	 *            URL to test in string form.
	 * @param proxy
	 *            Specify a {@link Proxy} to use or pass null for none.
	 * @param user
	 *            Specify a user name for basic authentication.
	 * @param password
	 *            Specify a password for basic authentication.
	 * @param authCharset
	 *            Specify the charset used to encode the user name and password.
	 *            Default if not provided is "ISO-8859-1".
	 *            {@link AppStatusConstants.ISO_8859_1} or
	 *            {@link AppStatusConstants.UTF_8}
	 * 
	 * @return {@link HealthCheckStatus}.ERROR is returned for non-200 responses
	 *         and exceptions; {@link HealthCheckStatus}.OK is returned for 200
	 *         response codes.
	 */
	public static HealthCheckStatus testHTTPConnection(String urlString, Proxy proxy, String user,
			String password, String authCharset)
	{
		HealthCheckStatus status = HealthCheckStatus.ERROR;

		if (LOGGER.isDebugEnabled())
			LOGGER.debug("Testing URL: " + urlString);

		try
		{
			int respCode = 0;

			if (proxy == null)
				proxy = Proxy.NO_PROXY;

			HttpURLConnection con = null;

			// create the connection
			if (urlString.startsWith(AppStatusConstants.HTTPS_PREFIX)
					|| urlString.startsWith(AppStatusConstants.HTTPS_PREFIX.toUpperCase()))
			{
				// SSL Connection
				con = (HttpsURLConnection) new URL(urlString).openConnection(proxy);
			}
			else
			{
				con = (HttpURLConnection) new URL(urlString).openConnection(proxy);
			}

			// add timeouts
			addTimeouts(con);

			// add basic auth if needed
			if (user != null)
				addBasicAuthHeader(con, user, password, authCharset);

			// test the connection
			respCode = testConnection(con);

			if (LOGGER.isDebugEnabled())
				LOGGER.debug("Received HTTP response code: " + respCode + " from: " + urlString);

			if (respCode == HttpURLConnection.HTTP_OK)
			{
				status = HealthCheckStatus.OK;
			}
		}
		catch (Exception e)
		{
			LOGGER.error("Failed to get HTTP connection health status for: " + urlString, e);
		}

		return status;
	}

	/**
	 * Use this method to obtain the web service endpoint URL from the provided
	 * web service interface object. Typically adding {@link AppStatusConstants}.WSDL_SFX
	 * on the return from this will give you the WSDL location.
	 * 
	 * @param serviceEndpointInterface
	 *            Web service interface object.
	 * @return Endpoint URL as a String.
	 */
	public static String getEndpointURLFromSvcInterface(Object serviceEndpointInterface)
	{
		String epURLString = null;

		BindingProvider bp = (BindingProvider) serviceEndpointInterface;

		epURLString = (String) bp.getRequestContext()
				.get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY);

		return epURLString;
	}

	private static void addTimeouts(HttpURLConnection con)
	{
		con.setConnectTimeout(connectTimeoutMillis);
		con.setReadTimeout(readTimeoutMillis);
	}

	private static void addBasicAuthHeader(HttpURLConnection con, String user, String password,
			String charset) throws Exception
	{
		if (charset == null)
			charset = AppStatusConstants.ISO_8859_1;

		// Base64 encoder is expecting ISO-8859-1
		byte[] auth = (user + ":" + password).getBytes(charset);
		con.setRequestProperty("Authorization", "Basic " + Base64.getEncoder().encodeToString(auth));
	}

	private static int testConnection(HttpURLConnection con) throws Exception
	{
		int respCode = 0;

		try
		{
			con.connect();
			respCode = con.getResponseCode();
		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			try
			{
				con.disconnect();
			}
			catch (Exception ee)
			{
				if (LOGGER.isDebugEnabled())
					LOGGER.debug("Failed to close connection.", ee);
			}
		}

		return respCode;
	}
}
