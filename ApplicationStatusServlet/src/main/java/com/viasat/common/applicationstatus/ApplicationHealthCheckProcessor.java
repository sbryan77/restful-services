package com.viasat.common.applicationstatus;

import com.viasat.common.applicationstatus.data.ApplicationStatus;

public interface ApplicationHealthCheckProcessor
{

	public abstract ApplicationStatus execute(ApplicationStatus appStatus);

}