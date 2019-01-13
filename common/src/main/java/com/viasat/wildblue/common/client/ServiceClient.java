package com.viasat.wildblue.common.client;

import com.viasat.wildblue.common.exception.WebServiceException;

public interface ServiceClient<T>
{
	T getEndpoint() throws WebServiceException;
}
