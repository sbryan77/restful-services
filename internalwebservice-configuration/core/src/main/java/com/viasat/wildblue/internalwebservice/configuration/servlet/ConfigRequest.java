/* ****************************************************************************
 * File:             $Source: /SoftwareDev/WildBlue/InternalWebService/Configuration/core/src/main/java/com/viasat/wildblue/internalwebservice/configuration/servlet/ConfigRequest.java,v $
 * Revision:         $Revision: 1.1 $
 * Last Modified By: $Author: VIASAT\jkent $
 * Last Modified On: $Date: 2013/05/24 21:42:30 $
 *
 * ------------------------------------------------------------------
 *  Copyright (c) 2004-2010 WildBlue Communications. All rights reserved.
 * ------------------------------------------------------------------
 *
 * **************************************************************************** */


package com.viasat.wildblue.internalwebservice.configuration.servlet;

import com.viasat.wildblue.internalwebservice.configuration.RequestTypeEnum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;


public class ConfigRequest
{
    /** Logger * */
    private static final Logger LOGGER = LoggerFactory.getLogger(
            ConfigRequest.class);
    private String appName = null;
    private RequestTypeEnum type = null;
    private Node xmlRequest = null;

    public ConfigRequest(Document xml)
    {
        Node item = xml.getFirstChild();

        if ("configRequest".equals(item.getNodeName()))
        {
            NodeList children = item.getChildNodes();

            for (int i = 0; i < children.getLength(); i++)
            {
                Node child = children.item(i);

                if (child.getNodeName().equals("appName"))
                {
                    appName = child.getTextContent();
                }
                else if (child.getNodeName().equals("type"))
                {
                    type = RequestTypeEnum.toEnumValue(child.getTextContent());
                }
                else if (child.getNodeName().equals("config"))
                {
                    try
                    {
                        xmlRequest = XMLUtilities.stripWhitespace(child);
                    }
                    catch (XPathExpressionException e)
                    {
                        LOGGER.error("Error removing empty text nodes ", e);
                        xmlRequest = child;
                    }
                }
            }
        }
    }

    public String getAppName()
    {
        return appName;
    }

    public RequestTypeEnum getType()
    {
        return type;
    }

    public String getXmlRequest()
    {
        try
        {
            return XMLUtilities.nodeToString(xmlRequest);
        }
        catch (TransformerException e)
        {
            LOGGER.error("Error transforming xml node to string", e);
            return null;
        }
    }
}
