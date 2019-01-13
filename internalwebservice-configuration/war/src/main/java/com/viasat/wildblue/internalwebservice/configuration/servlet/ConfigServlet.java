/* ****************************************************************************
 * File:             $Source: /SoftwareDev/WildBlue/InternalWebService/Configuration/war/src/main/java/com/viasat/wildblue/internalwebservice/configuration/servlet/ConfigServlet.java,v $
 * Revision:         $Revision: 1.3 $
 * Last Modified By: $Author: ybhyri $
 * Last Modified On: $Date: 2016/04/06 22:53:24 $
 *
 * ------------------------------------------------------------------
 *  Copyright (c) 2004-2010 WildBlue Communications. All rights reserved.
 * ------------------------------------------------------------------
 *
 * **************************************************************************** */


package com.viasat.wildblue.internalwebservice.configuration.servlet;

import com.viasat.wildblue.internalwebservice.configuration.AppConfig;

import com.viasat.wildblue.internalwebservice.configuration.ConfigurationDocumentReaderImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.w3c.dom.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ConfigServlet extends HttpServlet
{
    /**  */
    private static final long serialVersionUID = 1L;
    
	/** Line separator. */
	protected static final String LINE_SEPARATOR = System.getProperty("line.separator");

    /** Logger * */
    private static final Logger LOGGER = LoggerFactory.getLogger(
            ConfigurationDocumentReaderImpl.class);

    /** Form input parameter name. */
    public static final String INPUT_PARAMETER = "input";

    /** XML content type. */
    public static final String CONTENT_TYPE_XML = "text/xml";
    private ConfigServletProcessor processor = null;

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug("ConfigServlet.doPost()");
        }

        if (processor == null)
        {
            // jkent - Couldn't get the HttpRequestHandlerServlet to work,
            // perhaps due to the contextConfigLocation being set to the
            // AppConfig class. See
            // http://www.codeproject.com/Tips/251636/How-to-inject-Spring-beans-into-Servlets

            AppConfig config = new AppConfig();
            processor = new ConfigServletProcessor();
            processor.setConfigServiceProcessor(
                config.getConfigurationServiceProcessor());
        }

        try
        {
        	
            // The XML may have been submitted from an HTML form. Read the XML
            // from the input parameter.
            String input = request.getParameter(INPUT_PARAMETER);
            
            if (input == null)
            {
            	input = readFile(request.getReader());
            }
            
            LOGGER.info("input=" + input);
            
            
            Document document = XMLUtilities.loadXMLFrom(input);
            
            if (document == null)
            {
                // for backwards compatibility
                IllegalArgumentException e = new IllegalArgumentException(
                        "request document == null");
                LOGGER.error("", e);
                throw new ServletException(e);
            }

            String responseString = processor.getXMLResponse(document);

            writeResponse(response, responseString);
        }
        catch (Exception e)
        {
            writeExceptionToResponse(response, e);
        }
    }
    
	private String readFile(Reader reader) throws IOException
	{
		BufferedReader bufferedReader = new BufferedReader(reader);
		return read(bufferedReader);
	}
    
    protected String read(BufferedReader reader) throws IOException
	{
		StringBuffer sb = new StringBuffer();
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

    public void setConfigServletProcessor(ConfigServletProcessor processor)
    {
        this.processor = processor;
    }

    private static void writeExceptionToResponse(HttpServletResponse response,
        Exception e) throws IOException
    {
        LOGGER.error("", e);

        String linefeed = System.getProperty("line.separator");
        StringBuilder sb = new StringBuilder(ConfigServletProcessor.XML_HEADER);
        sb.append("<exception>").append(linefeed);
        sb.append(e.getMessage()).append(linefeed);

        StackTraceElement[] stack = e.getStackTrace();

        for (StackTraceElement element : stack)
        {
            sb.append(element).append(linefeed);
        }

        sb.append("</exception>");
        writeResponse(response, sb.toString());
    }

    /**
     * Write the response.
     *
     * @param   response        HTTP response.
     * @param   responseString  Response XML string.
     * @param   contentType     Response content type.
     *
     * @throws  IOException
     */
    private static void writeResponse(HttpServletResponse response,
        String responseString) throws IOException
    {
        // Log the response.
        if (LOGGER.isInfoEnabled())
        {
            LOGGER.info(responseString);
        }

        // Set the content type.
        response.setContentType(CONTENT_TYPE_XML);

        PrintWriter writer = response.getWriter();
        writer.print(responseString);
    }
}
