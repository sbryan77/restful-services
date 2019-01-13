/* ****************************************************************************
 * File:             $Source: /SoftwareDev/WildBlue/InternalWebService/Configuration/core/src/main/java/com/viasat/wildblue/internalwebservice/configuration/servlet/ConfigServletProcessor.java,v $
 * Revision:         $Revision: 1.2 $
 * Last Modified By: $Author: VIASAT\jkent $
 * Last Modified On: $Date: 2013/06/05 22:23:23 $
 *
 * ------------------------------------------------------------------
 *  Copyright (c) 2004-2010 WildBlue Communications. All rights reserved.
 * ------------------------------------------------------------------
 *
 * **************************************************************************** */


package com.viasat.wildblue.internalwebservice.configuration.servlet;

import com.viasat.wildblue.internalwebservice.configuration.ConfigurationServiceV10Processor;
import com.viasat.wildblue.internalwebservice.configuration.DocumentNotFoundException;
import com.viasat.wildblue.internalwebservice.configuration.QueryFailedException;
import com.viasat.wildblue.internalwebservice.configuration.RequestTypeEnum;
import com.viasat.wildblue.internalwebservice.configuration.UpdateFailedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;


public class ConfigServletProcessor
{
    /** Line separator. */
    private static final String LINE_SEPARATOR = System.getProperty(
            "line.separator");

    /** Logger * */
    private static final Logger LOGGER = LoggerFactory.getLogger(
            ConfigServletProcessor.class);

    /** XML header. */
    public static final String XML_HEADER =
        "<?xml version='1.0' encoding='UTF-8'?>";

    private ConfigurationServiceV10Processor m_configServiceProcessor;

    /**
     * Return the contents of the file at the given path.
     *
     * @param   filepath  Path to the file.
     *
     * @return  The contents of the file.
     *
     * @throws  FileNotFoundException
     * @throws  IOException
     */
    public static String readFile(String filepath) throws FileNotFoundException,
        IOException
    {
        if (filepath == null)
        {
            throw new IllegalArgumentException("filepath == null");
        }

        FileReader fileReader = null;

        try
        {
            fileReader = new FileReader(filepath);

            BufferedReader reader = new BufferedReader(fileReader);

            return read(reader);
        }
        finally
        {
            if (fileReader != null)
            {
                fileReader.close();
            }
        }
    }

    public String getXMLResponse(Document document)
        throws DocumentNotFoundException, UpdateFailedException,
            QueryFailedException
    {
        ConfigRequest configRequest = new ConfigRequest(document);

        String responseString = null;

        if (RequestTypeEnum.QUERY.equals(configRequest.getType()))
        {
            responseString = m_configServiceProcessor.getConfigurationDocument(
                    configRequest.getAppName());
        }
        else if (RequestTypeEnum.REPLACE.equals(configRequest.getType()))
        {
            String xmlRequest = configRequest.getXmlRequest();

            if (LOGGER.isDebugEnabled())
            {
                LOGGER.debug("Replacing " + configRequest.getAppName() + ": "
                    + xmlRequest);
            }

            m_configServiceProcessor.replaceConfigurationDocument(
                configRequest.getAppName(), xmlRequest);

            responseString = m_configServiceProcessor.getConfigurationDocument(
                    configRequest.getAppName());
        }
        else if (RequestTypeEnum.DELETE.equals(configRequest.getType()))
        {
            m_configServiceProcessor.deleteConfigurationDocument(
                configRequest.getAppName());

            responseString = XML_HEADER + "<deleted/>";
        }
        else if (RequestTypeEnum.QUERYAPPNAMES.equals(configRequest.getType()))
        {
            List<String> documentNames =
                m_configServiceProcessor.getDocumentNames();
            Set<String> nameSet = new TreeSet<String>();
            nameSet.addAll(documentNames);

            StringBuilder sb = new StringBuilder(XML_HEADER);
            sb.append("<configResponse>");

            for (String name : nameSet)
            {
                sb.append("<appName>" + name + "</appName>");
            }

            sb.append("</configResponse>");
            responseString = sb.toString();
        }

        try
        {
            responseString = stripWhitespace(responseString);
        }
        catch (XPathExpressionException e)
        {
            LOGGER.error("Error stripping whitespace from response", e);
        }
        catch (SAXException e)
        {
            LOGGER.error("Error stripping whitespace from response", e);
        }
        catch (IOException e)
        {
            LOGGER.error("Error stripping whitespace from response", e);
        }
        catch (ParserConfigurationException e)
        {
            LOGGER.error("Error stripping whitespace from response", e);
        }
        catch (TransformerException e)
        {
            LOGGER.error("Error stripping whitespace from response", e);
        }

        return responseString;
    }

    public void setConfigServiceProcessor(
        final ConfigurationServiceV10Processor configServiceProcessor)
    {
        this.m_configServiceProcessor = configServiceProcessor;
    }

    /**
     * Return the contents of the given reader.
     *
     * @param   reader  Buffered reader.
     *
     * @return  The contents of the reader.
     *
     * @throws  IOException
     */
    private static String read(BufferedReader reader) throws IOException
    {
        StringBuilder sb = new StringBuilder();
        String line = reader.readLine();

        while (line != null)
        {
            sb.append(line);
            line = reader.readLine();

            if (line != null)
            {
                sb.append(LINE_SEPARATOR);
            }
        }

        return sb.toString();
    }

    private String stripWhitespace(String xml) throws SAXException, IOException,
        ParserConfigurationException, XPathExpressionException,
        TransformerException
    {
        Node node = XMLUtilities.loadXMLFrom(xml);
        node = XMLUtilities.stripWhitespace(node);

        String output = XMLUtilities.nodeToString(node);

        //The original config servlet returns single quotes around attribute names
        //Adding this for backwards compatibility
        return output.replace("\"", "\'");
    }
}
