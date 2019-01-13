package com.viasat.common.applicationstatus;

import java.util.ArrayList;
import java.util.List;

import com.viasat.common.applicationstatus.data.ApplicationStatus;
import com.viasat.common.applicationstatus.data.Resource;

public class DefaultApplicationHealthCheckProcessor implements ApplicationHealthCheckProcessor
{
	private List<HealthCheck> checks;

	@Override
	public ApplicationStatus execute(ApplicationStatus appStatus)
	{
		if (appStatus == null)
		{
			appStatus = new ApplicationStatus();
		}

		HealthCheckStatus status = HealthCheckStatus.OK;
		appStatus.setStatus(status.getName());

		for (HealthCheck check : getChecks())
		{
			Resource resource = new Resource();
			appStatus.getResource().add(resource);

			status = check.execute();
			resource.setName(check.getName());
			resource.setStatus(status.name());
			resource.setType(check.getType().getTypeName());

			// set the most important status
			if (status.compareTo(HealthCheckStatus.valueOf(appStatus.getStatus())) < 0)
			{
				appStatus.setStatus(status.getName());
			}
		}

		return appStatus;
	}

	public List<HealthCheck> getChecks()
	{
		if (this.checks == null)
		{
			this.setChecks(new ArrayList<HealthCheck>());
		}

		return checks;
	}

	public void setChecks(List<HealthCheck> checks)
	{
		this.checks = checks;
	}

	public void addCheck(HealthCheck check)
	{
		this.getChecks().add(check);
	}
}
