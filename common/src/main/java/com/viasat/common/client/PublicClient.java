package com.viasat.common.client;

public interface PublicClient<T>
{
	/**
	 * Adds the user name and password to the password call back for the
	 * service. This will trigger a security update on the service endpoint(s).
	 * 
	 * @param user
	 *            user name for web service call
	 * @param password
	 *            password for the user name
	 */
	public void putCredential(String user, String password);

	/**
	 * Get the password for the given user. This is useful in your
	 * CallBackHandler class.
	 * 
	 * @param user
	 *            user name for the desired password
	 * @return the password for the user
	 */
	public String getPassword(String user);

	/**
	 * Example implementation:
	 * 
	 * <pre>
	 * public ExampleService getEndpoint(String endpointKey) throws EndpointInitException
	 * {
	 * 	synchronized (this)
	 * 	{
	 * 		loadPrivateEndpointUrl();
	 * 		updateEndpoint();
	 * 	}
	 * 	return endpointMap.get(endpointKey);
	 * }
	 * </pre>
	 * 
	 * This can be used to maintain a map of endpoint objects in the event
	 * different ones are needed to serve different purposes. For example: a
	 * public web service may need to have different instances for different
	 * user operations. The String can be used as a key to retrieve a specific
	 * endpoint instance.
	 * 
	 * @param string
	 *            endpoint key
	 * @return the endpoint object for making service calls
	 */
	public T getEndpoint(String endpointKey) throws EndpointInitException;
}
