package com.viasat.wildblue.common.exception;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.viasat.wildblue.common.exception.fault.WildBlueFaultException;

/**
 * Provides a file-based implementation of a fault info data provider.
 */
public class FileBasedExceptionInfoProvider implements ExceptionInfoProvider
{
	/** Exception map filename. */
	private String EXCEPTION_MAP_FILENAME = "exceptionMap.properties";

	private String WEBSERVICE_EXCEPTION_MAP_FILENAME = "webserviceExceptionMap.properties";

	/** Map of exception classname to error code. */
	private Map<String, String> m_exceptionMap;

	/** Resource bundle base name. */
	private String RESOURCE_BUNDLE_BASE_NAME = "exceptionResources";

	/** Resource bundle base name. */
	private String WEBSERVICE_RESOURCE_BUNDLE_BASE_NAME = "webserviceExceptionResources";

	/**
	 * @see com.viasat.wildblue.common.exception.ExceptionInfoProvider#getExceptionMap()
	 */
	@Override
	public Map<String, String> getExceptionMap()
	{
		if (m_exceptionMap == null)
		{
			m_exceptionMap = new LinkedHashMap<String, String>();

			Reader exceptionMapReader = createExceptionMapReader(EXCEPTION_MAP_FILENAME);

			if (exceptionMapReader == null)
			{
				ExceptionDetail exceptionDetail = new ExceptionDetail();
				throw new WildBlueFaultException(exceptionDetail, "m_exceptionMapReader == null");
			}
			updateExceptionMap(exceptionMapReader);
			exceptionMapReader = createExceptionMapReader(WEBSERVICE_EXCEPTION_MAP_FILENAME);
			if (exceptionMapReader != null)
			{
				updateExceptionMap(exceptionMapReader);
			}
		}

		return m_exceptionMap;
	}

	private void updateExceptionMap(Reader exceptionMapReader)
	{
		BufferedReader reader = new BufferedReader(exceptionMapReader);
		String line = null;

		try
		{
			while ((line = reader.readLine()) != null)
			{
				String delimiter = "=";

				if (line.indexOf(delimiter) >= 0)
				{
					String[] entry = line.split(delimiter);
					String key = entry[0].trim();
					String value = entry[1].trim();

					m_exceptionMap.put(key, value);
				}
			}
		}
		catch (IOException e)
		{
			ExceptionDetail exceptionDetail = new ExceptionDetail();
			throw new WildBlueFaultException(exceptionDetail, e.getMessage());
		}
	}

	/**
	 * @see com.viasat.wildblue.common.exception.ExceptionInfoProvider#getResourceBundle()
	 */
	@Override
	public ResourceBundle getResourceBundle()
	{
		WBResourceBundle answer = null;
		try
		{
			answer = new WBResourceBundle(RESOURCE_BUNDLE_BASE_NAME,
					WEBSERVICE_RESOURCE_BUNDLE_BASE_NAME);
		}
		catch (MissingResourceException e)
		{

		}

		return answer;
	}

	/**
	 * @return a new exceptionMapReader
	 */
	private Reader createExceptionMapReader(String exceptionMapFilename)
	{
		Reader answer = null;

		InputStream inputStream = getClass().getResourceAsStream(exceptionMapFilename);
		if (inputStream == null)
		{
			inputStream = getClass().getClassLoader().getResourceAsStream(exceptionMapFilename);
		}
		if (inputStream != null)
		{
			answer = new InputStreamReader(inputStream);
		}

		return answer;
	}
}
