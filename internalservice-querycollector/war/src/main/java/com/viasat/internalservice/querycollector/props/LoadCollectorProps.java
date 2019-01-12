package com.viasat.internalservice.querycollector.props;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.viasat.common.loadprops.LoadSecureProperties;

public class LoadCollectorProps
{
	private static final Logger LOGGER = LoggerFactory.getLogger(LoadCollectorProps.class);
	private String region;
	private String oracleUser;
	private String oraclePassword;
	private String oraclePort;
	private String oracleHost;
	private String oracleService;
	private String awsCollectorTableName;
	@Value("#{environment['PATH_TO_FILE']}")
	private String pathToFile;
	@Value("#{environment['PROPERTIES_KEY']}")
	private String propertiesKey;

	public LoadCollectorProps()
	{

	}

	public void readProperties()
	{
		LOGGER.debug("Bucket [" + pathToFile + "]");
		LOGGER.debug("Properties Key [" + propertiesKey + "]");
		LoadSecureProperties loadSecureProperties = null;

		try
		{
			loadSecureProperties  = new LoadSecureProperties();
			Properties properties = loadSecureProperties.getProperties(pathToFile, propertiesKey,
					region);
			LOGGER.debug("Properties loaded. DynamoDB table ["+properties.getProperty("aws.collectionQueryTable")+"]");
			setOracleUser(properties.getProperty("db.username"));
			setOraclePassword(properties.getProperty("db.password"));
			setOracleHost(properties.getProperty("db.host"));
			setOraclePort(properties.getProperty("db.port"));
			setOracleService(properties.getProperty("db.service"));
			setAwsCollectorTableName(properties.getProperty("aws.collectionQueryTable"));
		}
		catch (Exception e)
		{
			LOGGER.error("Could not fetch properties file.",e);
		}
	}

	public String getRegion()
	{
		return region;
	}

	public void setRegion(String region)
	{
		this.region = region;
	}

	public String getOracleUser()
	{
		return oracleUser;
	}

	public void setOracleUser(String oracleUser)
	{
		this.oracleUser = oracleUser;
	}

	public String getOraclePassword()
	{
		return oraclePassword;
	}

	public void setOraclePassword(String oraclePassword)
	{
		this.oraclePassword = oraclePassword;
	}

	public String getOraclePort()
	{
		return oraclePort;
	}

	public void setOraclePort(String oraclePort)
	{
		this.oraclePort = oraclePort;
	}

	public String getOracleHost()
	{
		return oracleHost;
	}

	public void setOracleHost(String oracleHost)
	{
		this.oracleHost = oracleHost;
	}

	public String getOracleService()
	{
		return oracleService;
	}

	public void setOracleService(String oracleService)
	{
		this.oracleService = oracleService;
	}

	public String getAwsCollectorTableName()
	{
		return awsCollectorTableName;
	}

	public void setAwsCollectorTableName(String awsCollectorTableName)
	{
		this.awsCollectorTableName = awsCollectorTableName;
	}
}
