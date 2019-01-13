/* ****************************************************************************
 * File:             $Source: /SoftwareDev/WildBlue/InternalWebService/Configuration/api/src/main/java/com/viasat/wildblue/internalwebservice/configuration/QueryFailedException.java,v $
 * Revision:         $Revision: 1.1 $
 * Last Modified By: $Author: VIASAT\jkent $
 * Last Modified On: $Date: 2013/04/25 20:22:04 $
 *
 * ------------------------------------------------------------------
 *  Copyright (c) 2004-2010 WildBlue Communications. All rights reserved.
 * ------------------------------------------------------------------
 *
 * **************************************************************************** */


package com.viasat.wildblue.internalwebservice.configuration;

import com.viasat.wildblue.common.exception.contingency.WildBlueContingencyException;


/**
 * Exception thrown when a document name cannot be found in persistent storage.
 */
public class QueryFailedException extends WildBlueContingencyException
{
    /** serial version uid */
    private static final long serialVersionUID = 1L;

    public QueryFailedException()
    {
    }

    public QueryFailedException(String message)
    {
        super(message);
    }

    public QueryFailedException(Throwable cause)
    {
        super(cause);
    }

    public QueryFailedException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
