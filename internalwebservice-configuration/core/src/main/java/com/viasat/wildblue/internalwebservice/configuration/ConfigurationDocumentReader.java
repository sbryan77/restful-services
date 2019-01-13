/* ****************************************************************************
 * File:             $Source: /SoftwareDev/WildBlue/InternalWebService/Configuration/core/src/main/java/com/viasat/wildblue/internalwebservice/configuration/ConfigurationDocumentReader.java,v $
 * Revision:         $Revision: 1.3 $
 * Last Modified By: $Author: gfidler $
 * Last Modified On: $Date: 2010/11/02 14:52:56 $
 *
 * ------------------------------------------------------------------
 *  Copyright (c) 2004-2010 WildBlue Communications. All rights reserved.
 * ------------------------------------------------------------------
 *
 * **************************************************************************** */


package com.viasat.wildblue.internalwebservice.configuration;

import com.viasat.wildblue.internalwebservice.configuration.data.ConfigurationItem;

import org.w3c.dom.Document;

import java.util.List;


/**
 * Interface for Configuration Document Reader objects.
 */
public interface ConfigurationDocumentReader
{
    /**
     * Builds a list of configuration name-value pairs from a document.
     *
     * @param   doc  DOM Document to process.
     *
     * @return  Configuration list.
     */
    List<ConfigurationItem> getConfigItemList(Document doc);
}
