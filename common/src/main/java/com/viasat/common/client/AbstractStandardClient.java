package com.viasat.common.client;


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
 * The base abstract standard client which can be used to implement custom
 * client (endpoint handler) classes. The expected pattern involves creating a
 * singleton and using a getInstance() method.
 * 
 * @param <T>
 *            The service interface for this client handler.
 * 
 * @see {@link StandardClient}
 */
public abstract class AbstractStandardClient<T> extends AbstractClientBase implements
		StandardClient<T>
{
	// for normal clients with one endpiont
	protected T endpoint;

	/**
	 * Example implementation:
	 * 
	 * <pre>
	 * protected void updateEndpoint() throws EndpointInitException
	 * {
	 * 	if (privateEndpointUrl == null)
	 * 	{
	 * 		throw new EndpointInitException(&quot;Endpoint URL is not defined, check configuration!&quot;);
	 * 	}
	 * 
	 * 	// create the endpoint if one doesn't exist
	 * 	if (endpoint == null)
	 * 	{
	 * 		LOGGER.info(&quot;Creating new client.&quot;);
	 * 
	 * 		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
	 * 		endpoint = factory.create(ExampleService.class);
	 * 	}
	 * 
	 * 	// update the endpoint address if it's changed
	 * 	if (updateEndpoint.get())
	 * 	{
	 * 		String url = trimWSDLSuffix(privateEndpointUrl);
	 * 
	 * 		LOGGER.info(&quot;Updating endpoint URL  to: &quot; + url);
	 * 
	 * 		BindingProvider bp = (BindingProvider) endpoint;
	 * 		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url);
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
