/* ****************************************************************************
 * File:             $Source: /SoftwareDev/WildBlue/InternalWebService/Configuration/api/src/main/java/com/viasat/wildblue/internalwebservice/configuration/ConfigDaoAdapter.java,v $
 * Revision:         $Revision: 1.6 $
 * Last Modified By: $Author: VIASAT\lchapman $
 * Last Modified On: $Date: 2014/08/20 23:06:35 $
 *
 * ------------------------------------------------------------------
 *  Copyright (c) 2004-2010 WildBlue Communications. All rights reserved.
 * ------------------------------------------------------------------
 *
 * **************************************************************************** */

package com.viasat.wildblue.internalwebservice.configuration;

import java.util.List;

import com.viasat.wildblue.common.header.WildBlueHeader;
import com.viasat.wildblue.internalwebservice.configuration.data.ConfigurationItem;

/**
 * Interface to fetch configuration items
 */
public interface ConfigDaoAdapter
{
	/**
	 * Delete the document with the given document name
	 * 
	 * @param documentName
	 * 
	 * @throws UpdateFailedException
	 */
	void delete(String documentName) throws UpdateFailedException;

	/**
	 * Get the configuration document in xml format with the given document name
	 * 
	 * @param documentName
	 * 
	 * @return document in xml format
	 * 
	 * @throws DocumentNotFoundException
	 */
	String getConfigurationDocument(String documentName) throws DocumentNotFoundException;

	/**
	 * Get all configuration items
	 * 
	 * @param document
	 *            document name
	 * @param wildBlueHeader
	 *            WildBlue header
	 * 
	 * @return configuration items
	 * 
	 * @throws DocumentNotFoundException
	 *             if configuration items for this document cannot be found.
	 */
	List<ConfigurationItem> getConfigurationItems(String document, WildBlueHeader wildBlueHeader)
			throws DocumentNotFoundException;

	/**
	 * Get a list of all document names in the system
	 * 
	 * @return document names
	 * 
	 * @throws QueryFailedException
	 */
	List<String> getDocumentNames() throws QueryFailedException;

	/**
	 * Update or insert an xml document with the given document name
	 * 
	 * @param documentName
	 * @param xml
	 * 
	 * @throws UpdateFailedException
	 */
	void update(String documentName, String xml) throws UpdateFailedException;
}
