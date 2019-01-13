/* ****************************************************************************
 * File:             $Source: /SoftwareDev/WildBlue/InternalWebService/Configuration/api/src/main/java/com/viasat/wildblue/internalwebservice/configuration/DocumentUtils.java,v $
 * Revision:         $Revision: 1.1 $
 * Last Modified By: $Author: VIASAT\jkent $
 * Last Modified On: $Date: 2013/04/24 23:00:53 $
 *
 * ------------------------------------------------------------------
 *  Copyright (c) 2004-2010 WildBlue Communications. All rights reserved.
 * ------------------------------------------------------------------
 *
 * **************************************************************************** */


package com.viasat.wildblue.internalwebservice.configuration;

import org.w3c.dom.Document;

import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


/**
 * Helper class for DOM Documents.
 */
public class DocumentUtils
{
    public static String loadStringFrom(Document doc)
        throws TransformerException
    {
        DOMSource domSource = new DOMSource(doc);
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.transform(domSource, result);
        return writer.toString();
    }

    /**
     * Reads XML from an input stream and creates a DOM Document.
     *
     * @param   is  Input stream.
     *
     * @return  Configuration XML as a Document.
     *
     * @throws  SAXException
     * @throws  IOException
     * @throws  ParserConfigurationException
     */
    public static Document loadXMLFrom(InputStream is) throws SAXException,
        IOException, ParserConfigurationException
    {
        Document doc;
        DocumentBuilder builder;

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);

        builder = factory.newDocumentBuilder();
        doc = builder.parse(is);
        is.close();

        return doc;
    }

    public static Document loadXMLFrom(String xml) throws SAXException,
        IOException, ParserConfigurationException
    {
        InputStream istrm = new ByteArrayInputStream(xml.getBytes());

        return loadXMLFrom(istrm);
    }
}
