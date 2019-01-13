/* ****************************************************************************
 * File:             $Source: /SoftwareDev/WildBlue/InternalWebService/Configuration/api/src/main/java/com/viasat/wildblue/internalwebservice/configuration/DocumentNotFoundException.java,v $
 * Revision:         $Revision: 1.3 $
 * Last Modified By: $Author: VIASAT\jkent $
 * Last Modified On: $Date: 2013/01/30 22:22:48 $
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
public class DocumentNotFoundException extends WildBlueContingencyException
{
    /** serial version uid */
    private static final long serialVersionUID = 1L;

    public DocumentNotFoundException()
    {
    }

    public DocumentNotFoundException(String message)
    {
        super(message);
    }

    public DocumentNotFoundException(Throwable cause)
    {
        super(cause);
    }

    public DocumentNotFoundException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
