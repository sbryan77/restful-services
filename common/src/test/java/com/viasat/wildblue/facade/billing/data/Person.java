package com.viasat.wildblue.facade.billing.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.cxf.xjc.runtime.JAXBToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

/**
 * <p>
 * Java class for Person complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="Person">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="firstName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="middleName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lastName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="suffix" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Person", propOrder =
{ "firstName", "middleName", "lastName", "suffix" })
public class Person
{

	@XmlElement(required = true)
	protected String firstName;
	protected String middleName;
	@XmlElement(required = true)
	protected String lastName;
	protected String suffix;

	/**
	 * Gets the value of the firstName property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getFirstName()
	{
		return firstName;
	}

	/**
	 * Sets the value of the firstName property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setFirstName(String value)
	{
		this.firstName = value;
	}

	/**
	 * Gets the value of the middleName property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getMiddleName()
	{
		return middleName;
	}

	/**
	 * Sets the value of the middleName property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setMiddleName(String value)
	{
		this.middleName = value;
	}

	/**
	 * Gets the value of the lastName property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getLastName()
	{
		return lastName;
	}

	/**
	 * Sets the value of the lastName property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setLastName(String value)
	{
		this.lastName = value;
	}

	/**
	 * Gets the value of the suffix property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getSuffix()
	{
		return suffix;
	}

	/**
	 * Sets the value of the suffix property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setSuffix(String value)
	{
		this.suffix = value;
	}

	/**
	 * Generates a String representation of the contents of this type. This is
	 * an extension method, produced by the 'ts' xjc plugin
	 * 
	 */
	@Override
	public String toString()
	{
		return JAXBToStringBuilder.valueOf(this, JAXBToStringStyle.DEFAULT_STYLE);
	}

}
