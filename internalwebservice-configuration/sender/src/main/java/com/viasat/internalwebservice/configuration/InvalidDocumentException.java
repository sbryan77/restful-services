/* ****************************************************************************
 * File:             $Source: /SoftwareDev/WildBlue/InternalWebService/Configuration/sender/src/main/java/com/viasat/internalwebservice/configuration/InvalidDocumentException.java,v $
 * Revision:         $Revision: 1.1 $
 * Last Modified By: $Author: VIASAT\jkent $
 * Last Modified On: $Date: 2013/04/30 19:02:53 $
 *
 * ------------------------------------------------------------------
 *  Copyright (c) 2004-2010 WildBlue Communications. All rights reserved.
 * ------------------------------------------------------------------
 *
 * **************************************************************************** */


package com.viasat.internalwebservice.configuration;

/**
 * Exception thrown when a document name cannot be found in persistent storage.
 */
public class InvalidDocumentException extends Exception
{
    /** serial version uid */
    private static final long serialVersionUID = 1L;

    public InvalidDocumentException()
    {
    }

    public InvalidDocumentException(String message)
    {
        super(message);
    }

    public InvalidDocumentException(Throwable cause)
    {
        super(cause);
    }

    public InvalidDocumentException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
