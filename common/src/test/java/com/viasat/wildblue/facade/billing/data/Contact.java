package com.viasat.wildblue.facade.billing.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.cxf.xjc.runtime.JAXBToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

/**
 * <p>
 * Java class for Contact complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="Contact">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="person" type="{http://www.wildblue.viasat.com/XMLSchema/v4/webservice/BillingFacade}Person"/>
 *         &lt;element name="address" type="{http://www.wildblue.viasat.com/XMLSchema/v4/webservice/BillingFacade}Address"/>
 *         &lt;element name="emailAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="primaryPhone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="secondaryPhone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Contact", propOrder =
{ "person", "address", "emailAddress", "primaryPhone", "secondaryPhone" })
public class Contact
{

	@XmlElement(required = true)
	protected Person person;
	@XmlElement(required = true)
	protected Address address;
	@XmlElement(nillable = true)
	protected String emailAddress;
	@XmlElement(nillable = true)
	protected String primaryPhone;
	@XmlElement(nillable = true)
	protected String secondaryPhone;

	/**
	 * Gets the value of the person property.
	 * 
	 * @return possible object is {@link Person }
	 * 
	 */
	public Person getPerson()
	{
		return person;
	}

	/**
	 * Sets the value of the person property.
	 * 
	 * @param value
	 *            allowed object is {@link Person }
	 * 
	 */
	public void setPerson(Person value)
	{
		this.person = value;
	}

	/**
	 * Gets the value of the address property.
	 * 
	 * @return possible object is {@link Address }
	 * 
	 */
	public Address getAddress()
	{
		return address;
	}

	/**
	 * Sets the value of the address property.
	 * 
	 * @param value
	 *            allowed object is {@link Address }
	 * 
	 */
	public void setAddress(Address value)
	{
		this.address = value;
	}

	/**
	 * Gets the value of the emailAddress property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getEmailAddress()
	{
		return emailAddress;
	}

	/**
	 * Sets the value of the emailAddress property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setEmailAddress(String value)
	{
		this.emailAddress = value;
	}

	/**
	 * Gets the value of the primaryPhone property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPrimaryPhone()
	{
		return primaryPhone;
	}

	/**
	 * Sets the value of the primaryPhone property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPrimaryPhone(String value)
	{
		this.primaryPhone = value;
	}

	/**
	 * Gets the value of the secondaryPhone property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getSecondaryPhone()
	{
		return secondaryPhone;
	}

	/**
	 * Sets the value of the secondaryPhone property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setSecondaryPhone(String value)
	{
		this.secondaryPhone = value;
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
