/* ****************************************************************************
 * File:             $Source: /SoftwareDev/WildBlue/InternalWebService/Configuration/sender/src/main/java/com/viasat/internalwebservice/configuration/ConfigSender.java,v $
 * Revision:         $Revision: 1.4 $
 * Last Modified By: $Author: VIASAT\lchapman $
 * Last Modified On: $Date: 2014/08/20 23:06:37 $
 *
 * ------------------------------------------------------------------
 *  Copyright (c) 2004-2010 WildBlue Communications. All rights reserved.
 * ------------------------------------------------------------------
 *
 * **************************************************************************** */

package com.viasat.internalwebservice.configuration;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.viasat.wildblue.wsdl.v1_0.configurationwebservice.WebServiceException;

public class ConfigSender
{
	private static final String CONFIGURATION_ENDPOINT_URL = "ConfigurationService/services/ConfigurationService?wsdl";

	/**
	 * Application method.
	 * 
	 * <ol start='0'>
	 * <li>type - configuration request type [query | replace] (required)</li>
	 * <li>system - target system [dev | qa | perf | prod] (required)</li>
	 * <li>name - configuration document name (configRequest appName) (required)
	 * </li>
	 * <li>filepath - path to the XML file to send (required if a replace type)</li>
	 * </ol>
	 * 
	 * @param args
	 *            Arguments.
	 */
	public static void main(String[] args)
	{
		// args = new String[]
		// {
		// "query",
		// "http://apiint.dev.wdc1.wildblue.net:11251",
		// "CONFIGURATION"
		// };

		boolean success = false;

		if (args.length < 3)
		{
			writeUsage();
		}
		else
		{
			RequestTypeEnum type = null;

			try
			{
				type = getType(args[0]);
			}
			catch (IllegalArgumentException e1)
			{
				writeTypeError(args[0]);
			}

			String endpoint = getEndpointUrl(args[1]);
			String documentName = getDocumentName(args[2]);

			if ((type == null) || (endpoint == null) || (documentName == null)
					|| (RequestTypeEnum.REPLACE.equals(type) && (args.length != 4)))
			{
				writeUsage();
			}
			else
			{
				ConfigurationClient client = new ConfigurationClientImpl(endpoint);

				try
				{
					if (RequestTypeEnum.DELETE.equals(type))
					{
						deleteDocument(documentName, client);
					}
					else if (RequestTypeEnum.QUERY.equals(type))
					{
						String configDoc = getDocument(documentName, client);

						if ((configDoc == null))
						{
							System.out.println("Document not found: " + documentName);
						}
						else
						{
							System.out.println(configDoc);
						}
					}
					else if (RequestTypeEnum.REPLACE.equals(type))
					{
						replaceDocument(documentName, args[3], client);
					}

					success = true;
				}
				catch (WebServiceException e)
				{
					System.err.println(e.getMessage());
				}
				catch (InvalidDocumentException e)
				{
					System.err.println(e.getMessage());
				}
				catch (IOException e)
				{
					System.err.println(e.getMessage());
				}
				catch (SAXException e)
				{
					System.err.println(e.getMessage());
				}
				catch (ParserConfigurationException e)
				{
					System.err.println(e.getMessage());
				}
			}
		}

		if (success)
		{
			System.out.println("SUCCESS");
		}
		else
		{
			System.err.println("FAILED");
			System.exit(1);
		}
	}

	private static void deleteDocument(String documentName, ConfigurationClient client)
			throws WebServiceException
	{
		client.deleteConfigurationFile(documentName, ConfigSender.class.getName());
	}

	private static String getDocument(String documentName, ConfigurationClient client)
			throws WebServiceException
	{
		return client.getConfigurationDocument(documentName, ConfigSender.class.getName());
	}

	private static String getDocumentName(String documentName)
	{
		if (isNullOrEmpty(documentName))
		{
			return null;
		}

		return documentName.toUpperCase();
	}

	private static String getDocumentName(Document doc) throws InvalidDocumentException
	{
		NodeList node = doc.getChildNodes();
		String nodeName = node.item(0).getNodeName();
		String documentName = null;

		if ("config".equals(nodeName))
		{
			NamedNodeMap map = node.item(0).getAttributes();
			int len = map.getLength();

			for (int i = 0; i < len; i++)
			{
				String attributeName = map.item(i).getNodeName();

				if ("name".equals(attributeName))
				{
					documentName = map.item(i).getNodeValue().trim().toUpperCase();
				}
			}

			if (documentName == null)
			{
				throw new InvalidDocumentException("Invalid xml - documentName not found");
			}
		}
		else
		{
			throw new InvalidDocumentException("Invalid xml - first node name " + nodeName);
		}

		return documentName;
	}

	private static String getEndpointUrl(String endpoint)
	{
		String url = null;

		if (!isNullOrEmpty(endpoint))
		{
			if (endpoint.endsWith("/"))
			{
				url = endpoint + CONFIGURATION_ENDPOINT_URL;
			}
			else
			{
				url = endpoint + "/" + CONFIGURATION_ENDPOINT_URL;
			}
		}

		return url;
	}

	private static RequestTypeEnum getType(String typeString)
	{
		RequestTypeEnum type = null;

		if (!isNullOrEmpty(typeString))
		{
			type = RequestTypeEnum.toEnumValue(typeString);

			if (type == null)
			{
				throw new IllegalArgumentException("invalid type " + typeString);
			}
		}

		return type;
	}

	/**
	 * Tests if the given string is null or empty (length is zero).
	 * 
	 * @param stringToTest
	 *            String to test for null or empty.
	 * 
	 * @return true if the string is null or empty, otherwise false.
	 */
	private static boolean isNullOrEmpty(String stringToTest)
	{
		boolean response = false;

		if ((stringToTest == null) || (stringToTest.length() == 0))
		{
			response = true;
		}

		return response;
	}

	private static Document loadXMLFrom(String xml) throws SAXException, IOException,
			ParserConfigurationException
	{
		InputStream istrm = new ByteArrayInputStream(xml.getBytes());

		return loadXMLFrom(istrm);
	}

	/**
	 * Reads XML from an input stream and creates a DOM Document.
	 * 
	 * @param is
	 *            Input stream.
	 * 
	 * @return Configuration XML as a Document.
	 * 
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	private static Document loadXMLFrom(InputStream is) throws SAXException, IOException,
			ParserConfigurationException
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

	/**
	 * Return the contents of the given reader.
	 * 
	 * @param reader
	 *            Buffered reader.
	 * 
	 * @return The contents of the reader.
	 * 
	 * @throws IOException
	 */
	private static String read(BufferedReader reader) throws IOException
	{
		StringBuilder sb = new StringBuilder();
		String line = reader.readLine();

		while (line != null)
		{
			sb.append(line);
			line = reader.readLine();
		}

		return sb.toString();
	}

	/**
	 * Return the contents of the file at the given path.
	 * 
	 * @param filepath
	 *            Path to the file.
	 * 
	 * @return The contents of the file.
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static String readFile(String filepath) throws FileNotFoundException, IOException
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

	private static void replaceDocument(String documentName, String filepath,
			ConfigurationClient client) throws FileNotFoundException, IOException, SAXException,
			ParserConfigurationException, InvalidDocumentException, WebServiceException
	{
		String xml = ConfigSender.readFile(filepath);
		Document doc = loadXMLFrom(xml);

		String documentNameInDocument = getDocumentName(doc);

		if ((documentName != null) && !documentName.equals(documentNameInDocument))
		{
			throw new InvalidDocumentException();
		}

		client.replaceConfigurationFile(documentName, xml, ConfigSender.class.getName());
	}

	/**
	 * Write an error message for an unrecognized system input.
	 * 
	 * @param typeString
	 *            System input.
	 */
	private static void writeTypeError(String typeString)
	{
		StringBuilder sb = new StringBuilder("[");
		RequestTypeEnum[] requestTypes = RequestTypeEnum.values();

		for (int i = 0; i < requestTypes.length; i++)
		{
			sb.append(requestTypes[i].getDisplayName());

			if (i < (requestTypes.length - 1))
			{
				sb.append(" | ");
			}
		}

		sb.append("]");
		System.err.print("Unrecognized type '" + typeString + "'. Valid values are ");
		System.err.println(sb.toString());
	}

	/**
	 * Write a usage message.
	 */
	private static void writeUsage()
	{
		System.err
				.println("Usage: java -jar InternalWebService-Configuration-SENDER-XXX.jar type system name [filepath]");
		// System.err.println(
		// "Alternate usage: java com.viasat.internalwebservice.configuration.ConfigSender type system name [filepath]");
		System.err.println("where");
		System.err.println("  type     - configRequest type [delete | query | replace] (required)");
		System.err
				.println("  system   - target system base url (required). Example: https://mapiint.test.wdc1.wildblue.net");
		System.err.println("  name     - configRequest appName (required)");
		System.err.println("  filepath - XML filepath (required for replace type)");
	}
}
