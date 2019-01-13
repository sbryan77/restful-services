/* ****************************************************************************
 * File:             $Source: /SoftwareDev/WildBlue/InternalWebService/Configuration/core/src/main/java/com/viasat/wildblue/internalwebservice/configuration/ConfigurationFaultException.java,v $
 * Revision:         $Revision: 1.4 $
 * Last Modified By: $Author: VIASAT\jkent $
 * Last Modified On: $Date: 2013/01/30 22:23:47 $
 *
 * ------------------------------------------------------------------
 *  Copyright (c) 2004-2010 WildBlue Communications. All rights reserved.
 * ------------------------------------------------------------------
 *
 * **************************************************************************** */


package com.viasat.wildblue.internalwebservice.configuration;

import com.viasat.wildblue.common.exception.FaultException;


/**
 * Configuration applications's runtime exception.
 */
public class ConfigurationFaultException extends RuntimeException
    implements FaultException
{
    /**  */
    private static final long serialVersionUID = 1L;

    public ConfigurationFaultException()
    {
        super();
    }

    public ConfigurationFaultException(final String s)
    {
        super(s);
    }

    public ConfigurationFaultException(final Throwable throwable)
    {
        super(throwable);
    }

    public ConfigurationFaultException(final String s,
        final Throwable throwable)
    {
        super(s, throwable);
    }
}
