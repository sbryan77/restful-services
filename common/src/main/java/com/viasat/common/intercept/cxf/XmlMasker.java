package com.viasat.common.intercept.cxf;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class XmlMasker
{
	private Transformer transformer = null;

	public XmlMasker(InputStream styleSheetStream)
			throws TransformerConfigurationException, TransformerFactoryConfigurationError
	{
		StreamSource stylesource = new StreamSource(styleSheetStream);
		transformer = TransformerFactory.newInstance().newTransformer(stylesource);
	}

	public String maskXml(String xmlString) throws TransformerException
	{
		if (xmlString == null || xmlString.trim().length() == 0)
			return "";
		InputStream inputStream = new ByteArrayInputStream(xmlString.getBytes());
		StringWriter writer = new StringWriter();
		transformer.transform(new StreamSource(inputStream), new StreamResult(writer));
		return writer.getBuffer().toString();
	}
}
