package com.viasat.common.applicationstatus;

public enum HealthCheckType
{
	DATASOURCE("DATASOURCE"), //
	SERVICE("SERVICE");

	private String typeName;

	private HealthCheckType(String typeName)
	{
		this.setTypeName(typeName);
	}

	public String getTypeName()
	{
		return typeName;
	}

	public void setTypeName(String typeName)
	{
		this.typeName = typeName;
	}
}
