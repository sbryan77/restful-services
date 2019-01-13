/* ****************************************************************************
 * File:             $Source: /SoftwareDev/WildBlue/InternalWebService/Configuration/core/src/main/java/com/viasat/wildblue/internalwebservice/configuration/RequestTypeEnum.java,v $
 * Revision:         $Revision: 1.1 $
 * Last Modified By: $Author: VIASAT\jkent $
 * Last Modified On: $Date: 2013/05/24 21:42:30 $
 *
 * ------------------------------------------------------------------
 *  Copyright (c) 2004-2010 WildBlue Communications. All rights reserved.
 * ------------------------------------------------------------------
 *
 * **************************************************************************** */


package com.viasat.wildblue.internalwebservice.configuration;

/**
 * Provides an enumeration of request types used by the config sender.
 */
public enum RequestTypeEnum
{
    QUERY("query"), REPLACE("replace"), DELETE("delete"),
    QUERYAPPNAMES("queryAppNames");

    /** Display name. */
    private String m_displayName;

    /**
     * Construct this object.
     *
     * @param  displayName  Display name.
     */
    private RequestTypeEnum(String displayName)
    {
        m_displayName = displayName;
    }

    /**
     * @param   displayName  Display name.
     *
     * @return  the enum value with the given display name.
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
     * @return  the display name
     */
    public String getDisplayName()
    {
        return m_displayName;
    }
}
