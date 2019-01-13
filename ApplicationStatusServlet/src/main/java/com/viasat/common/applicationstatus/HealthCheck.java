package com.viasat.common.applicationstatus;

public interface HealthCheck
{
	public HealthCheckStatus execute();

	public String getName();

	public HealthCheckType getType();
}
