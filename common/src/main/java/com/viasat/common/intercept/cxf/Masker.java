package com.viasat.common.intercept.cxf;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Masker
{
	private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

	private static final String ELEMENT_NAMES = "elementNames";
	private static final String HEADER_NAMES = "headerNames";
	private static final String ADDRESS_NAMES ="addressNames";
	public static final String REPLACEMENT_MASK = "******";

	private String[] elementNames;
	private String[] headerNames;
	private String[] addressNames;

	private static String[] splitAndTrim(String elementNameString)
	{
		String[] elementNames = null;
		if (elementNameString != null && elementNameString.trim().length() > 0)
		{
			elementNames = elementNameString.split(",");
			for (int i = 0; i < elementNames.length; i++)
			{
				elementNames[i] = elementNames[i].trim();
			}
		}
		return elementNames;
	}

	public Masker(InputStream elementMaskStream) throws IOException
	{
		Properties props = new Properties();
		props.load(elementMaskStream);
		elementNames = splitAndTrim(props.getProperty(ELEMENT_NAMES));
		headerNames = splitAndTrim(props.getProperty(HEADER_NAMES));
		addressNames = splitAndTrim(props.getProperty(ADDRESS_NAMES));
		elementMaskStream.close();
	}

	public String maskFormElements(String payload)
	{
		return maskFormElements(payload, elementNames);
	}
	
	public String maskAddressElements(String address)
	{
		return maskFormElements(address, addressNames);
	}

	public String maskHeaderElements(String headers)
	{
		return maskHeaderElements(headers, headerNames);
	}

	public String maskJson(String payload) throws IOException
	{
		return maskJson(payload, elementNames);
	}

	static String maskFormElements(String formString, String[] elementNamesToMask)
	{
		if (formString != null && elementNamesToMask != null && elementNamesToMask.length > 0)
		{
			for (String element : elementNamesToMask)
			{
				// this RegEx will look for the element name, followed by "=",
				// followed by any character up to "&" or a newline
				formString = formString.replaceAll(element + "=[^&\\n]*",
						element + "=" + REPLACEMENT_MASK);
			}
		}
		return formString;
	}

	static String maskHeaderElements(String payload, String[] elementNames)
	{
		if (payload != null && elementNames != null && elementNames.length > 0)
		{
			for (String element : elementNames)
			{
				// this method presumes that the entire length of the value is
				// between two brackets [] and that the first bracket is the
				// ending bracket.
				// In other words, do not have lists of strings that contain
				// list of strings and we will be ok
				payload = payload.replaceAll(element + "=[^]\\n]*",
						element + "=[" + REPLACEMENT_MASK);
			}
		}
		return payload;
	}

	static String maskJson(String payload, String elementNamesToMask[]) throws IOException
	{
		if (elementNamesToMask == null)
			return payload;
		JsonNode root = JSON_MAPPER.readTree(payload);
		for (String element : elementNamesToMask)
		{
			java.util.List<JsonNode> nodes = root.findParents(element);

			for (JsonNode node : nodes)
			{
				((ObjectNode) node).put(element, REPLACEMENT_MASK);
			}
		}
		return root.toString();
	}
	
	boolean isAddressMask() {
		return addressNames != null && addressNames.length > 0;
	}
}