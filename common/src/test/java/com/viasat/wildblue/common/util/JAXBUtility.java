package com.viasat.wildblue.common.util;
import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class JAXBUtility<T>
{

	public T unmarshalSoapEnvelope(InputStream reader, Class<T> cls) throws XMLStreamException,
			FactoryConfigurationError, JAXBException
	{
		XMLStreamReader xsr = XMLInputFactory.newFactory().createXMLStreamReader(reader);

		while (xsr.next() != XMLStreamReader.START_ELEMENT
				|| !"Body".equalsIgnoreCase(xsr.getLocalName()))
		{
			// Skip everything before the body tag
		}

		xsr.nextTag(); // Advance to the first tag within "Body"

		Unmarshaller unmarshaller = JAXBContext.newInstance(cls).createUnmarshaller();
		JAXBElement<T> je = unmarshaller.unmarshal(xsr, cls);

		T response = je.getValue();
		return response;
	}

	public String marshal(T object, Class<T> cls) throws JAXBException
	{

		StringWriter sw = new StringWriter();
		JAXBContext jaxbContext = JAXBContext.newInstance(cls);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.marshal(object, sw);

		return sw.toString();

	}

}