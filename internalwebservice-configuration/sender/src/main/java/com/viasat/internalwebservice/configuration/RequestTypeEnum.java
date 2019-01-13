/* ****************************************************************************
 * File:             $Source: /SoftwareDev/WildBlue/InternalWebService/Configuration/sender/src/main/java/com/viasat/internalwebservice/configuration/RequestTypeEnum.java,v $
 * Revision:         $Revision: 1.2 $
 * Last Modified By: $Author: VIASAT\lchapman $
 * Last Modified On: $Date: 2014/08/20 23:06:38 $
 *
 * ------------------------------------------------------------------
 *  Copyright (c) 2004-2010 WildBlue Communications. All rights reserved.
 * ------------------------------------------------------------------
 *
 * **************************************************************************** */

package com.viasat.internalwebservice.configuration;

/**
 * Provides an enumeration of request types used by the config sender.
 */
public enum RequestTypeEnum
{
	QUERY("query"), REPLACE("replace"), DELETE("delete");

	/** Display name. */
	private String m_displayName;

	/**
	 * Construct this object.
	 * 
	 * @param displayName
	 *            Display name.
	 */
	private RequestTypeEnum(String displayName)
	{
		m_displayName = displayName;
	}

	/**
	 * @param displayName
	 *            Display name.
	 * 
	 * @return the enum value with the given display name.
	 */
	public static RequestTypeEnum toEnumValue(String displayName)
	{
		RequestTypeEnum answer = null;

		if (displayName != null)
		{
			RequestTypeEnum[] values = values();
			int i = 0;

			while ((answer == null) && (i < values.length))
			{
				if (values[i].getDisplayName().equalsIgnoreCase(displayName))
				{
					answer = values[i];
				}

				i++;
			}
		}

		return answer;
	}

	/**
	 * @return the display name
	 */
	public String getDisplayName()
	{
		return m_displayName;
	}
}
