package com.viasat.common.applicationstatus.healthcheck;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.viasat.common.applicationstatus.HealthCheck;
import com.viasat.common.applicationstatus.HealthCheckStatus;
import com.viasat.common.applicationstatus.HealthCheckType;
import com.viasat.common.applicationstatus.utility.AppStatusConstants;

public class GenericDatabaseConnectionCheck implements HealthCheck
{
	private static final Logger LOGGER = LoggerFactory
			.getLogger(GenericDatabaseConnectionCheck.class);

	private EntityManagerFactory entityManagerFactory = null;
	private DataSource dataSource = null;
	private String validationQuery = null;

	private String healthCheckName = null;

	public EntityManagerFactory getEntityManagerFactory()
	{
		return entityManagerFactory;
	}

	public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory)
	{
		this.entityManagerFactory = entityManagerFactory;
	}

	public DataSource getDataSource()
	{
		return dataSource;
	}

	public void setDataSource(DataSource dataSource)
	{
		this.dataSource = dataSource;
	}

	public String getValidationQuery()
	{
		return this.validationQuery;
	}

	public void setValidationQuery(String validationQuery)
	{
		this.validationQuery = validationQuery;
	}

	public void setHealthCheckName(String healthCheckName)
	{
		this.healthCheckName = healthCheckName;
	}

	@Override
	public HealthCheckStatus execute()
	{
		// default to error unless the check passes
		HealthCheckStatus status = HealthCheckStatus.ERROR;

		Connection con = null;
		ResultSet rs = null;
		Statement stmt = null;

		try
		{
			// these connection initializations will throw an exception if there
			// is a database configuration problem
			if (this.getDataSource() == null)
			{
				// get connection from the entity manager
				con = this.getEntityManagerFactory().createEntityManager().unwrap(Connection.class);
			}
			else
			{
				// get connection from the data source
				con = this.getDataSource().getConnection();
			}

			// run the validation query if one was specified
			if (this.getValidationQuery() != null)
			{
				stmt = con.createStatement();
				rs = stmt.executeQuery(this.getValidationQuery());

				if (rs.next())
				{
					status = HealthCheckStatus.OK;
				}
				else
				{
					LOGGER.error("Validation query did not return a result!");
				}
			}
			else
			{
				status = HealthCheckStatus.OK;
			}

		}
		catch (Exception e)
		{
			LOGGER.error("Failed to get database health status for" + this.getName(), e);
		}
		finally
		{
			try
			{
				if (rs != null)
					rs.close();

				if (stmt != null)
					stmt.close();

				if (con != null)
					con.close();
			}
			catch (Exception e)
			{
				if (LOGGER.isTraceEnabled())
					LOGGER.trace("Error occurred closing result set, statement, or connection.", e);
			}
		}

		return status;
	}

	@Override
	public String getName()
	{
		if (this.healthCheckName != null)
		{
			return healthCheckName;
		}

		return AppStatusConstants.GEN_DB_CHECK;
	}

	@Override
	public HealthCheckType getType()
	{
		return HealthCheckType.DATASOURCE;
	}
}
