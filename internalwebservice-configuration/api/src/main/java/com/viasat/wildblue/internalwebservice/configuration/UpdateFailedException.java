/* ****************************************************************************
 * File:             $Source: /SoftwareDev/WildBlue/InternalWebService/Configuration/api/src/main/java/com/viasat/wildblue/internalwebservice/configuration/UpdateFailedException.java,v $
 * Revision:         $Revision: 1.1 $
 * Last Modified By: $Author: VIASAT\jkent $
 * Last Modified On: $Date: 2013/04/24 23:00:53 $
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
public class UpdateFailedException extends WildBlueContingencyException
{
    /** serial version uid */
    private static final long serialVersionUID = 1L;

    public UpdateFailedException()
    {
    }

    public UpdateFailedException(String message)
    {
        super(message);
    }

    public UpdateFailedException(Throwable cause)
    {
        super(cause);
    }

    public UpdateFailedException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
