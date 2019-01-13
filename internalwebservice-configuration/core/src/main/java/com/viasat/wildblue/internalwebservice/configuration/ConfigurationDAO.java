/* ****************************************************************************
 * File:             $Source: /SoftwareDev/WildBlue/InternalWebService/Configuration/core/src/main/java/com/viasat/wildblue/internalwebservice/configuration/ConfigurationDAO.java,v $
 * Revision:         $Revision: 1.7 $
 * Last Modified By: $Author: VIASAT\lchapman $
 * Last Modified On: $Date: 2014/08/20 23:06:36 $
 *
 * ------------------------------------------------------------------
 *  Copyright (c) 2004-2010 WildBlue Communications. All rights reserved.
 * ------------------------------------------------------------------
 *
 * **************************************************************************** */

package com.viasat.wildblue.internalwebservice.configuration;

import org.w3c.dom.Document;

/**
 * Interface for Configuration Data Access Objects.
 */
public interface ConfigurationDAO
{
	/**
	 * Deletes all configuration properties, for the named application, from the
	 * persistent store.
	 * 
	 * @param appName
	 *            Name of the application.
	 */
	void delete(String appName);

	/**
	 * Fetches all configuration properties, for the named application, from the
	 * persistent store.
	 * 
	 * @param appName
	 *            Name of the application.
	 * 
	 * @return A Document containing the XML configuration.
	 * 
	 * @throws DocumentNotFoundException
	 *             if the document cannot be found.
	 */
	Document query(String appName) throws DocumentNotFoundException;

	/**
	 * Fetches all configuration properties, for the named application, from the
	 * persistent store.
	 * 
	 * @param appName
	 *            Name of the application.
	 * 
	 * @return XML string containing the configuration.
	 * 
	 * @throws DocumentNotFoundException
	 *             if the document cannot be found.
	 */
	String getConfigurationDocument(String appName) throws DocumentNotFoundException;

	/**
	 * Replaces all configuration properties, for the named application, in the
	 * persistent store.
	 * 
	 * @param appName
	 *            Name of the application.
	 */

	void update(String appName, String xml);
}
