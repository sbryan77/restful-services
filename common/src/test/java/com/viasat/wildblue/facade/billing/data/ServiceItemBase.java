package com.viasat.wildblue.facade.billing.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

import org.apache.cxf.xjc.runtime.JAXBToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

/**
 * <p>
 * Java class for ServiceItemBase complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="ServiceItemBase">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="serviceAgreementReference" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="serviceItemReference" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="serviceItemType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="parentServiceItemId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="serviceItemId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="serviceItemPricePlanId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="deviceIdentifier" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ratingEventType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="contract" type="{http://www.wildblue.viasat.com/XMLSchema/v4/webservice/BillingFacade}Contract" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceItemBase", propOrder =
{ "serviceAgreementReference", "serviceItemReference", "serviceItemType", "parentServiceItemId",
		"serviceItemId", "serviceItemPricePlanId", "deviceIdentifier", "ratingEventType",
		"contract" })
@XmlSeeAlso(
{ ServiceItem.class })
public class ServiceItemBase
{

	@XmlElement(required = true)
	protected String serviceAgreementReference;
	@XmlElement(required = true)
	protected String serviceItemReference;
	@XmlElement(required = true)
	protected String serviceItemType;
	protected Integer parentServiceItemId;
	protected int serviceItemId;
	protected int serviceItemPricePlanId;
	protected String deviceIdentifier;
	protected String ratingEventType;
	protected Contract contract;

	/**
	 * Gets the value of the serviceAgreementReference property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getServiceAgreementReference()
	{
		return serviceAgreementReference;
	}

	/**
	 * Sets the value of the serviceAgreementReference property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setServiceAgreementReference(String value)
	{
		this.serviceAgreementReference = value;
	}

	/**
	 * Gets the value of the serviceItemReference property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getServiceItemReference()
	{
		return serviceItemReference;
	}

	/**
	 * Sets the value of the serviceItemReference property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setServiceItemReference(String value)
	{
		this.serviceItemReference = value;
	}

	/**
	 * Gets the value of the serviceItemType property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getServiceItemType()
	{
		return serviceItemType;
	}

	/**
	 * Sets the value of the serviceItemType property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setServiceItemType(String value)
	{
		this.serviceItemType = value;
	}

	/**
	 * Gets the value of the parentServiceItemId property.
	 * 
	 * @return possible object is {@link Integer }
	 * 
	 */
	public Integer getParentServiceItemId()
	{
		return parentServiceItemId;
	}

	/**
	 * Sets the value of the parentServiceItemId property.
	 * 
	 * @param value
	 *            allowed object is {@link Integer }
	 * 
	 */
	public void setParentServiceItemId(Integer value)
	{
		this.parentServiceItemId = value;
	}

	/**
	 * Gets the value of the serviceItemId property.
	 * 
	 */
	public int getServiceItemId()
	{
		return serviceItemId;
	}

	/**
	 * Sets the value of the serviceItemId property.
	 * 
	 */
	public void setServiceItemId(int value)
	{
		this.serviceItemId = value;
	}

	/**
	 * Gets the value of the serviceItemPricePlanId property.
	 * 
	 */
	public int getServiceItemPricePlanId()
	{
		return serviceItemPricePlanId;
	}

	/**
	 * Sets the value of the serviceItemPricePlanId property.
	 * 
	 */
	public void setServiceItemPricePlanId(int value)
	{
		this.serviceItemPricePlanId = value;
	}

	/**
	 * Gets the value of the deviceIdentifier property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getDeviceIdentifier()
	{
		return deviceIdentifier;
	}

	/**
	 * Sets the value of the deviceIdentifier property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setDeviceIdentifier(String value)
	{
		this.deviceIdentifier = value;
	}

	/**
	 * Gets the value of the ratingEventType property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getRatingEventType()
	{
		return ratingEventType;
	}

	/**
	 * Sets the value of the ratingEventType property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setRatingEventType(String value)
	{
		this.ratingEventType = value;
	}

	/**
	 * Gets the value of the contract property.
	 * 
	 * @return possible object is {@link Contract }
	 * 
	 */
	public Contract getContract()
	{
		return contract;
	}

	/**
	 * Sets the value of the contract property.
	 * 
	 * @param value
	 *            allowed object is {@link Contract }
	 * 
	 */
	public void setContract(Contract value)
	{
		this.contract = value;
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
