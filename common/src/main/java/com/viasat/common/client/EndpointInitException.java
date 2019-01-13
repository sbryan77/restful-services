package com.viasat.common.client;

public class EndpointInitException extends Exception
{
	private static final long serialVersionUID = 1L;

	public EndpointInitException(String message)
	{
		super(message);
	}

	public EndpointInitException(Throwable cause)
	{
		super(cause);
	}

	public EndpointInitException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
