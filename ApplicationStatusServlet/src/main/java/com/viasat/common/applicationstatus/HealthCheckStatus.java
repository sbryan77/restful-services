package com.viasat.common.applicationstatus;

public enum HealthCheckStatus
{
	ADMIN("ADMIN", 503, 1, "The status is set to administration mode."), //
	ERROR("ERROR", 500, 2, "An error exists which prevents the service from operating correctly."), //
	OVERLOAD("OVERLOAD", 429, 3, "A resource is overloaded which may result in performance issues."), //
	OK("OK", 200, 99, "System behavior is nominal.");

	private int rank;
	private int httpCode;
	private String name;
	private String description;

	private HealthCheckStatus(String name, int httpCode, int rank, String description)
	{
		setName(name);
		setHttpCode(httpCode);
		setRank(rank);
		setDescription(description);
	}

	public int getRank()
	{
		return rank;
	}

	public void setRank(int rank)
	{
		this.rank = rank;
	}

	public int getHttpCode()
	{
		return httpCode;
	}

	public void setHttpCode(int httpCode)
	{
		this.httpCode = httpCode;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

}
