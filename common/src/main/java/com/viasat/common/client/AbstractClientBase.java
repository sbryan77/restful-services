package com.viasat.common.client;

import java.util.concurrent.atomic.AtomicBoolean;

import com.viasat.wildblue.common.header.InvokedBy;
import com.viasat.wildblue.common.header.WildBlueHeader;

public abstract class AbstractClientBase
{
	// default config doc
	protected static final String SERVICE_LOCATOR_DOC = "SERVICE_LOCATOR";

	// wsdl suffix
	private static final String WSDL_SFX = "?WSDL";

	// This is static because of the way the ConfigManager works. It builds a
	// cache map where the config document name is the key. So whatever is the
	// 1st wbHeader that comes in, is the one it will always use for
	// SERVICE_LOCATOR lookups. It's pointless to have each service client
	// define their own header, and will cause confusion.
	protected static final WildBlueHeader WB_HEADER;

	static
	{
		WB_HEADER = new WildBlueHeader();
		InvokedBy ib = new InvokedBy();
		ib.setApplication("ClientUtility");
		ib.setUsername("ServiceLocator");
		WB_HEADER.setInvokedBy(ib);
	}

	// thread-safe flag for requesting URL updates
	protected AtomicBoolean updateEndpoint = new AtomicBoolean(true);

	// private endpoint URL
	protected String privateEndpointUrl;

	/**
	 * Override this method to return a different configuration document.
	 * 
	 * @return The configuration document name. Default is "SERVICE_LOCATOR".
	 */
	protected String getConfigDoc()
	{
		return SERVICE_LOCATOR_DOC;
	}

	/**
	 * Override this method to return a different WildBlue Header.
	 * 
	 * @return The WildBlueHeader used by Configuration Service calls. Default
	 *         is the static WB_HEADER.
	 */
	protected WildBlueHeader getWildBlueHeader()
	{
		return WB_HEADER;
	}

	/**
	 * Trims the "?WSDL" or "?wsdl" suffix from the end of the url.
	 * 
	 * @param endpointUrl
	 *            URL String to trim
	 * @return trimmed version of the URL String
	 */
	protected String trimWSDLSuffix(String endpointUrl)
	{
		if (endpointUrl == null)
			return null;

		String url = endpointUrl.trim();

		if (url.toUpperCase().endsWith(WSDL_SFX))
			url = url.substring(0, url.length() - 5);

		return url;
	}

	/**
	 * Example implementation using SERVICE_LOCATOR:
	 * 
	 * <pre>
	 * protected void loadPrivateEndpointUrl()
	 * {
	 * 	String url = null;
	 * 
	 * 	try
	 * 	{
	 * 		ConfigurationProxy config = ConfigManager.getConfigurationProxy(getConfigDoc(),
	 * 				getWildBlueHeader());
	 * 
	 * 		url = config.getConfigurationItem(CONFIG_KEY);
	 * 	}
	 * 	catch (Exception e)
	 * 	{
	 * 		// we want to catch most exceptions here since the config service
	 * 		// can throw unchecked exceptions in the event of timeouts and
	 * 		// connection failures
	 * 		LOGGER.error(&quot;Error getting &quot; + CONFIG_KEY + &quot; from &quot; + getConfigDoc(), e);
	 * 	}
	 * 
	 * 	if (LOGGER.isDebugEnabled())
	 * 		LOGGER.debug(CONFIG_KEY + &quot; from &quot; + getConfigDoc() + &quot; set to: &quot; + url);
	 * 
	 * 	// trim the URL string for more safety on .endsWith() checks
	 * 	if (url != null)
	 * 		url = url.trim();
	 * 
	 * 	if (url == null &amp;&amp; privateEndpointUrl != null)
	 * 	{
	 * 		LOGGER.warn(&quot;Failed to update config, using cached value for endpoint URL!&quot;);
	 * 	}
	 * 	else if (url == null)
	 * 	{
	 * 		// implied privateEndpointUrl == null
	 * 		LOGGER.error(&quot;Failed to update config, and no cached value present for endpoint URL!&quot;);
	 * 	}
	 * 	else if (privateEndpointUrl == null || !privateEndpointUrl.equals(url))
	 * 	{
	 * 		if (LOGGER.isDebugEnabled())
	 * 			LOGGER.debug(&quot;Updating endpoint URL to: &quot; + url);
	 * 
	 * 		privateEndpointUrl = url;
	 * 		updateEndpoint.set(true);
	 * 	}
	 * }
	 * </pre>
	 * 
	 * Sets or updates privateEndpointUrl member on this client. This should be
	 * called by a synchronized block in getEndpoint().
	 */
	protected abstract void loadPrivateEndpointUrl();
}
