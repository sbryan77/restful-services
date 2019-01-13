package com.viasat.common.applicationstatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.viasat.common.applicationstatus.utility.AppStatusConstants;

/**
 * This class wraps the basic property set for easy access and to isolate other
 * classes from the need to load, and interact with a ResourceBundle. The
 * default properties file name is defined as a constant value (@see
 * {@link AppStatusConstants#DEFAULT_VERSION_PROPERTIES}), but can be overridden at
 * construction time.
 */
public class VersionProperties
{
	private static Logger LOGGER = LoggerFactory.getLogger(VersionProperties.class);

	/** The m_properties resource name. */
	private String m_propertiesResourceName;

	/** The version properties ResourceBundle. */
	private ResourceBundle m_resourceBundle;

	/**
	 * Instantiates a new VersionProperties instance.
	 */
	public VersionProperties()
	{
		init(AppStatusConstants.DEFAULT_VERSION_PROPERTIES);
	}

	/**
	 * Instantiates a new VersionProperties instance, specifying a properties
	 * file name.
	 * 
	 * @param propertiesResourceName
	 *            the properties resource name
	 */
	public VersionProperties(String propertiesResourceName)
	{
		init(propertiesResourceName);
	}

	/**
	 * Gets the default properties resource name.
	 * 
	 * @return the default properties resource name
	 */
	public String getDefaultPropertiesResourceName()
	{
		return AppStatusConstants.DEFAULT_VERSION_PROPERTIES;
	}

	/**
	 * Gets the properties resource name.
	 * 
	 * @return the properties resource name
	 */
	public String getPropertiesResourceName()
	{
		return m_propertiesResourceName;
	}

	/**
	 * Read application name.
	 * 
	 * @return the string
	 */
	public String readApplicationName()
	{
		return readVersionProperty(AppStatusConstants.APP_NAME_PROP);
	}

	/**
	 * Read artifact group id.
	 * 
	 * @return the string
	 */
	public String readArtifactGroupId()
	{
		return readVersionProperty(AppStatusConstants.ARTIFACT_GID_PROP);
	}

	/**
	 * Read artifact id.
	 * 
	 * @return the string
	 */
	public String readArtifactId()
	{
		return readVersionProperty(AppStatusConstants.ARTIFACT_ID_PROP);
	}

	/**
	 * Read build time stamp.
	 * 
	 * @return the string
	 */
	public Calendar readBuildTimestamp()
	{
		Calendar buildTimestamp = null;

		String buildTimestampString = readVersionProperty(AppStatusConstants.BUILD_TS_PROP);

		try
		{
			SimpleDateFormat dateFormatIn = new SimpleDateFormat(
					AppStatusConstants.DF_VERSION_PROPERTIES);

			Date buildDate = dateFormatIn.parse(buildTimestampString);

			buildTimestamp = Calendar.getInstance();

			buildTimestamp.setTime(buildDate);
		}
		catch (ParseException e)
		{
			LOGGER.warn("buildTimestamp is out of date.");

			try
			{
				// try and parse with the older time stamp format
				SimpleDateFormat dateFormatIn = new SimpleDateFormat(
						AppStatusConstants.DF_VERSION_PROPERTIES_OLD);

				Date buildDate = dateFormatIn.parse(buildTimestampString);

				buildTimestamp = Calendar.getInstance();

				buildTimestamp.setTime(buildDate);
			}
			catch (ParseException ee)
			{
				LOGGER.error("Failed to parse buildTimestamp:", ee);
			}
		}

		return buildTimestamp;
	}

	/**
	 * Read version.
	 * 
	 * @return the string
	 */
	public String readVersion()
	{
		return readVersionProperty(AppStatusConstants.VERSION_PROP);
	}

	/**
	 * Inits the.
	 * 
	 * @param propertiesResourceName
	 *            the properties resource name
	 */
	private void init(String propertiesResourceName)
	{
		if (propertiesResourceName.endsWith(".properties"))
		{
			propertiesResourceName = propertiesResourceName.substring(0,
					propertiesResourceName.lastIndexOf(".properties"));
		}

		m_propertiesResourceName = propertiesResourceName;

		try
		{
			m_resourceBundle = ResourceBundle.getBundle(propertiesResourceName);
		}
		catch (Throwable t)
		{
			LOGGER.error("Unable to load application version properties. ResourceBundle name="
					+ propertiesResourceName, t);
		}
	}

	/**
	 * Read version property.
	 * 
	 * @param propertyKey
	 *            the property key
	 * 
	 * @return the string
	 */
	private String readVersionProperty(String propertyKey)
	{
		String propertyValue = null;

		if (m_resourceBundle != null)
		{
			try
			{
				propertyValue = m_resourceBundle.getString(propertyKey);
			}
			catch (Throwable t)
			{
				LOGGER.error("Unable to read version properties: " + propertyKey, t);
			}

			if ((propertyValue == null) || (propertyValue.trim().length() == 0))
			{
				propertyValue = "Property '" + propertyKey + "' not found.";
				LOGGER.error(propertyValue);
			}
		}
		else
		{
			propertyValue = "Resource '" + m_propertiesResourceName + ".properties' not found.";
			LOGGER.error(propertyValue);
		}

		return propertyValue;
	}
}
