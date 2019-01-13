package com.viasat.wildblue.facade.billing.data;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

import org.apache.cxf.xjc.runtime.JAXBToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

/**
 * <p>
 * Java class for Contract complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 *    &lt;complexType name="Contract">
 *      &lt;complexContent>
 *        &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *          &lt;sequence>
 *            &lt;element name="serviceAgreementReference" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *            &lt;element name="contractType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *            &lt;element name="contractTerm" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *            &lt;element name="contractTermUnits" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *            &lt;element name="contractSequence" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *            &lt;element name="serviceItemPricePlanName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *            &lt;element name="startDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *            &lt;element name="endDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *          &lt;/sequence>
 *        &lt;/restriction>
 *      &lt;/complexContent>
 *    &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Contract", propOrder =
{ "serviceAgreementReference", "contractType", "contractTerm", "contractTermUnits",
		"contractSequence", "serviceItemPricePlanName", "startDate", "endDate" })
public class Contract
{
	protected Integer contractSequence;
	protected int contractTerm;
	@XmlElement(required = true)
	protected String contractTermUnits;
	@XmlElement(required = true)
	protected String contractType;
	@XmlElement(type = String.class)
	@XmlSchemaType(name = "date")
	protected Date endDate;

	@XmlElement(required = true)
	protected String serviceAgreementReference;
	protected String serviceItemPricePlanName;
	@XmlElement(type = String.class)
	@XmlSchemaType(name = "date")
	protected Date startDate;

	/**
	 * Gets the value of the contractSequence property.
	 * 
	 * @return possible object is {@link Integer }
	 */
	public Integer getContractSequence()
	{
		return contractSequence;
	}

	/**
	 * Gets the value of the contractTerm property.
	 */
	public int getContractTerm()
	{
		return contractTerm;
	}

	/**
	 * Gets the value of the contractTermUnits property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getContractTermUnits()
	{
		return contractTermUnits;
	}

	/**
	 * Gets the value of the contractType property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getContractType()
	{
		return contractType;
	}

	/**
	 * Gets the value of the endDate property.
	 * 
	 * @return possible object is {@link String }
	 */
	public Date getEndDate()
	{
		return endDate;
	}

	/**
	 * Gets the value of the serviceAgreementReference property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getServiceAgreementReference()
	{
		return serviceAgreementReference;
	}

	/**
	 * Gets the value of the serviceItemPricePlanName property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getServiceItemPricePlanName()
	{
		return serviceItemPricePlanName;
	}

	/**
	 * Gets the value of the startDate property.
	 * 
	 * @return possible object is {@link String }
	 */
	public Date getStartDate()
	{
		return startDate;
	}

	/**
	 * Sets the value of the contractSequence property.
	 * 
	 * @param value
	 *            allowed object is {@link Integer }
	 */
	public void setContractSequence(Integer value)
	{
		this.contractSequence = value;
	}

	/**
	 * Sets the value of the contractTerm property.
	 */
	public void setContractTerm(int value)
	{
		this.contractTerm = value;
	}

	/**
	 * Sets the value of the contractTermUnits property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setContractTermUnits(String value)
	{
		this.contractTermUnits = value;
	}

	/**
	 * Sets the value of the contractType property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setContractType(String value)
	{
		this.contractType = value;
	}

	/**
	 * Sets the value of the endDate property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setEndDate(Date value)
	{
		this.endDate = value;
	}

	/**
	 * Sets the value of the serviceAgreementReference property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setServiceAgreementReference(String value)
	{
		this.serviceAgreementReference = value;
	}

	/**
	 * Sets the value of the serviceItemPricePlanName property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setServiceItemPricePlanName(String value)
	{
		this.serviceItemPricePlanName = value;
	}

	/**
	 * Sets the value of the startDate property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setStartDate(Date value)
	{
		this.startDate = value;
	}

	/**
	 * Generates a String representation of the contents of this type. This is
	 * an extension method, produced by the 'ts' xjc plugin
	 */
	@Override
	public String toString()
	{
		return JAXBToStringBuilder.valueOf(this, JAXBToStringStyle.DEFAULT_STYLE);
	}
}
