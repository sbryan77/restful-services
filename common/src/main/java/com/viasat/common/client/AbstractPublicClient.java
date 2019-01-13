package com.viasat.common.client;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Example singleton implementation:
 * 
 * <pre>
 * // config key
 * private static final String CONFIG_KEY = &quot;exampleServiceUrl&quot;;
 * 
 * // singleton
 * protected static ExampleServiceClient instance;
 * 
 * // no exceptions here, spring can call private constructors via reflection
 * protected ExampleServiceClient()
 * {
 * 	synchronized (this)
 * 	{
 * 		if (instance == null)
 * 		{
 * 			instance = this;
 * 		}
 * 	}
 * }
 * 
 * // singleton static factory method
 * public static ExampleServiceClient getInstance()
 * {
 * 	if (instance == null)
 * 	{
 * 		new ExampleServiceClient();
 * 	}
 * 
 * 	return instance;
 * }
 * </pre>
 *
 * The public abstract standard client which can be used to implement custom
 * client (endpoint handler) classes. The expected pattern involves creating a
 * singleton and using a getInstance() method.
 * 
 * @param <T>
 *            The service interface for this client handler.
 * 
 * @see {@link PublicClient}
 */
public abstract class AbstractPublicClient<T> extends AbstractClientBase implements PublicClient<T>
{
	// Default WSS TTL
	protected static final String DEFAULT_WSS_TTL = "300";

	// for implementations with multiple endpoints
	protected Map<String, T> endpointMap;

	// thread-safe flag for requesting endpoint map updates
	protected AtomicBoolean updateEndpointMap = new AtomicBoolean(true);

	// credential map
	protected Map<String, String> credentials;

	// thread-safe flag for requesting security update
	protected AtomicBoolean updateSecurity = new AtomicBoolean(true);

	@Override
	public void putCredential(String user, String password)
	{
		if (credentials == null)
		{
			synchronized (this)
			{
				if (credentials == null)
				{
					credentials = new HashMap<String, String>(2);
				}
			}
		}

		synchronized (this)
		{
			credentials.put(user, password);
		}

		updateSecurity.set(true);
	}

	@Override
	public String getPassword(String user)
	{
		if (credentials == null)
			return null;

		return credentials.get(user);
	}

	/**
	 * Example method implementation for public services:
	 * 
	 * <pre>
	 * protected void updateEndpoint() throws EndpointInitException
	 * {
	 * 	if (credentials == null || credentials.isEmpty())
	 * 	{
	 * 		throw new EndpointInitException(&quot;At least one set of credentials must be provided!&quot;);
	 * 	}
	 * 
	 * 	if (privateEndpointUrl == null)
	 * 	{
	 * 		throw new EndpointInitException(&quot;Endpoint URL is not defined, check configuration!&quot;);
	 * 	}
	 * 
	 * 	// create the endpointMap if one doesn't exist
	 * 	if (endpointMap == null)
	 * 	{
	 * 		endpointMap = new HashMap&lt;String, ExampleService&gt;();
	 * 	}
	 * 
	 * 	// update security settings if needed
	 * 	if (updateSecurity.get())
	 * 	{
	 * 		for (String user : credentials.keySet())
	 * 		{
	 * 			JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
	 * 			factory.setAddress(trimWSDLSuffix(privateEndpointUrl));
	 * 
	 * 			LOGGER.info(&quot;Creating or updating WSS-enabled endpoint for: &quot; + user);
	 * 
	 * 			// build the WSS config properties map
	 * 			Map&lt;String, Object&gt; wssProps = new HashMap&lt;String, Object&gt;();
	 * 			wssProps.put(WSHandlerConstants.ACTION, WSHandlerConstants.USERNAME_TOKEN + &quot; &quot;
	 * 					+ WSHandlerConstants.TIMESTAMP);
	 * 			wssProps.put(WSHandlerConstants.TTL_TIMESTAMP, DEFAULT_WSS_TTL);
	 * 			wssProps.put(WSHandlerConstants.USER, user);
	 * 			wssProps.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_TEXT);
	 * 			wssProps.put(WSHandlerConstants.PW_CALLBACK_CLASS,
	 * 					ExampleClientPasswordCallback.class.getName());
	 * 
	 * 			// construct and add the interceptor
	 * 			WSS4JOutInterceptor wssOut = new WSS4JOutInterceptor(wssProps);
	 * 			factory.getOutInterceptors().add(wssOut);
	 * 
	 * 			// add the endpoint to the map
	 * 			endpointMap.put(user, factory.create(ExampleService.class));
	 * 		}
	 * 
	 * 		updateSecurity.set(false);
	 * 	}
	 * 
	 * 	// update the endpoint address if it's changed
	 * 	if (updateEndpoint.get())
	 * 	{
	 * 		for (String user : endpointMap.keySet())
	 * 		{
	 * 			String url = trimWSDLSuffix(privateEndpointUrl);
	 * 
	 * 			LOGGER.info(&quot;Updating endpoint URL for: &quot; + user + &quot; to: &quot; + url);
	 * 
	 * 			BindingProvider bp = (BindingProvider) endpointMap.get(user);
	 * 			bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url);
	 * 		}
	 * 
	 * 		updateEndpoint.set(false);
	 * 	}
	 * }
	 * </pre>
	 * 
	 * Initializes and or updates the endpoint(s) on this client. This should be
	 * called by a synchronized block in getEndpoint() after
	 * loadPrivateEndpointUrl().
	 * 
	 * @throws EndpointInitException
	 *             Re-throw checked exceptions or define custom exceptions.
	 */
	protected abstract void updateEndpoint() throws EndpointInitException;
}
