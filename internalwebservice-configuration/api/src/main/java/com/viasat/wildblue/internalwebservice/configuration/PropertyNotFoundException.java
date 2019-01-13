/* ****************************************************************************
 * File:             $Source: /SoftwareDev/WildBlue/InternalWebService/Configuration/api/src/main/java/com/viasat/wildblue/internalwebservice/configuration/PropertyNotFoundException.java,v $
 * Revision:         $Revision: 1.3 $
 * Last Modified By: $Author: VIASAT\jkent $
 * Last Modified On: $Date: 2013/01/30 22:22:49 $
 *
 * ------------------------------------------------------------------
 *  Copyright (c) 2004-2010 WildBlue Communications. All rights reserved.
 * ------------------------------------------------------------------
 *
 * **************************************************************************** */


package com.viasat.wildblue.internalwebservice.configuration;

import com.viasat.wildblue.common.exception.contingency.WildBlueContingencyException;


/**
 * Exception thrown when a requested property cannot be found in persistent
 * storage.
 */
public class PropertyNotFoundException extends WildBlueContingencyException
{
    /** serial version uid */
    private static final long serialVersionUID = 1L;

    public PropertyNotFoundException()
    {
    }

    public PropertyNotFoundException(String msg)
    {
        super(msg);
    }
}
