package com.viasat.wildblue.facade.billing.data;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.cxf.xjc.runtime.JAXBToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

/**
 * <p>
 * Java class for Address complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="Address">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="addressLine" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="municipality" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="region" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="postalCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="countryCode">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;pattern value="[A-Z]{3}"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="taxJurisdictionCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="isInCityLimits" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Address", propOrder =
{ "addressLine", "municipality", "region", "postalCode", "countryCode", "taxJurisdictionCode",
		"isInCityLimits" })
public class Address
{

	protected List<String> addressLine;
	protected String municipality;
	protected String region;
	protected String postalCode;
	@XmlElement(required = true)
	protected String countryCode;
	@XmlElement(required = true)
	protected String taxJurisdictionCode;
	protected Boolean isInCityLimits;

	/**
	 * Gets the value of the addressLine property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the addressLine property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getAddressLine().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link String }
	 * 
	 * 
	 */
	public List<String> getAddressLine()
	{
		if (addressLine == null)
		{
			addressLine = new ArrayList<String>();
		}
		return this.addressLine;
	}

	/**
	 * Gets the value of the municipality property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getMunicipality()
	{
		return municipality;
	}

	/**
	 * Sets the value of the municipality property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setMunicipality(String value)
	{
		this.municipality = value;
	}

	/**
	 * Gets the value of the region property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getRegion()
	{
		return region;
	}

	/**
	 * Sets the value of the region property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setRegion(String value)
	{
		this.region = value;
	}

	/**
	 * Gets the value of the postalCode property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPostalCode()
	{
		return postalCode;
	}

	/**
	 * Sets the value of the postalCode property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPostalCode(String value)
	{
		this.postalCode = value;
	}

	/**
	 * Gets the value of the countryCode property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getCountryCode()
	{
		return countryCode;
	}

	/**
	 * Sets the value of the countryCode property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setCountryCode(String value)
	{
		this.countryCode = value;
	}

	/**
	 * Gets the value of the taxJurisdictionCode property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getTaxJurisdictionCode()
	{
		return taxJurisdictionCode;
	}

	/**
	 * Sets the value of the taxJurisdictionCode property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setTaxJurisdictionCode(String value)
	{
		this.taxJurisdictionCode = value;
	}

	/**
	 * Gets the value of the isInCityLimits property.
	 * 
	 * @return possible object is {@link Boolean }
	 * 
	 */
	public Boolean isIsInCityLimits()
	{
		return isInCityLimits;
	}

	/**
	 * Sets the value of the isInCityLimits property.
	 * 
	 * @param value
	 *            allowed object is {@link Boolean }
	 * 
	 */
	public void setIsInCityLimits(Boolean value)
	{
		this.isInCityLimits = value;
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
