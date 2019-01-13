package com.viasat.common.util;

import com.viasat.common.fault.FaultDetail;
import com.viasat.common.fault.WebServiceFault;
import com.viasat.wildblue.common.exception.ExceptionDetail;
import com.viasat.wildblue.common.exception.WebServiceException;

public class ExceptionUtilities
{
	public static FaultDetail convert(ExceptionDetail source)
	{
		if (source == null)
		{
			throw new IllegalArgumentException("arguments cannot be null");
		}

		FaultDetail dest = new FaultDetail();
		dest.setContextPath(EnvironmentContextUtility.getContextPath(ApplicationContextUtils
				.getApplicationContext()));

		if (source.getDetail() != null)
		{
			dest.setMessage(source.getDetail());
		}
		else
		{
			dest.setMessage(source.getReason());
		}
		dest.setNode(source.getNode());
		dest.setTimestamp(source.getTimestamp());
		dest.setTrackingKey(source.getTrackingKey());
		return dest;
	}

	public static WebServiceFault convert(WebServiceException source)
	{
		String message = source.getMessage();
		if (source.getFaultInfo() != null && source.getFaultInfo().getCode() != null
				&& source.getFaultInfo().getCode().indexOf("VALIDATION") >= 0)
		{
			message = WebServiceFault.CLIENT_FAULT;
		}
		FaultDetail detail = ExceptionUtilities.convert(source.getFaultInfo());

		WebServiceFault dest = new WebServiceFault(message, detail);
		return dest;
	}
}
