package com.viasat.common.client;

public interface StandardClient<T>
{
	/**
	 * Example implementation:
	 * 
	 * <pre>
	 * public ExampleService getEndpoint() throws EndpointInitException
	 * {
	 * 	synchronized (this)
	 * 	{
	 * 		loadPrivateEndpointUrl();
	 * 		updateEndpoint();
	 * 	}
	 * 	return endpoint;
	 * }
	 * </pre>
	 * 
	 * Gets the current endpoint instance.
	 * 
	 * @return The endpiont object for making service calls
	 */
	public T getEndpoint() throws EndpointInitException;
}
