/* ****************************************************************************
 * File:             $Source: /SoftwareDev/WildBlue/InternalWebService/Configuration/sender/src/main/java/com/viasat/internalwebservice/configuration/ConfigurationClient.java,v $
 * Revision:         $Revision: 1.3 $
 * Last Modified By: $Author: VIASAT\lchapman $
 * Last Modified On: $Date: 2014/08/20 23:06:37 $
 *
 * ------------------------------------------------------------------
 *  Copyright (c) 2004-2010 WildBlue Communications. All rights reserved.
 * ------------------------------------------------------------------
 *
 * **************************************************************************** */

package com.viasat.internalwebservice.configuration;

import com.viasat.wildblue.wsdl.v1_0.configurationwebservice.WebServiceException;

public interface ConfigurationClient
{
	void deleteConfigurationFile(String documentName, String user) throws WebServiceException;

	String getConfigurationDocument(String documentName, String user) throws WebServiceException;

	void replaceConfigurationFile(String documentName, String xml, String user)
			throws WebServiceException;
}
