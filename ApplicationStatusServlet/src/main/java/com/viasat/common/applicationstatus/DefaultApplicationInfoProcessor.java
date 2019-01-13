package com.viasat.common.applicationstatus;

import com.viasat.common.applicationstatus.data.ApplicationStatus;

public class DefaultApplicationInfoProcessor implements ApplicationInfoProcessor
{
	private VersionProperties versionProperties;

	@Override
	public ApplicationStatus execute(ApplicationStatus appStatus)
	{
		if (appStatus == null)
		{
			appStatus = new ApplicationStatus();
		}

		appStatus.setApplicationName(getVersionProperties().readApplicationName());
		appStatus.setVersion(getVersionProperties().readVersion());
		appStatus.setArtifactId(getVersionProperties().readArtifactId());
		appStatus.setArtifactGroupId(getVersionProperties().readArtifactGroupId());
		appStatus.setBuildTimestamp(getVersionProperties().readBuildTimestamp());

		return appStatus;
	}

	public VersionProperties getVersionProperties()
	{
		return versionProperties;
	}

	public void setVersionProperties(VersionProperties versionProperties)
	{
		this.versionProperties = versionProperties;
	}

}
