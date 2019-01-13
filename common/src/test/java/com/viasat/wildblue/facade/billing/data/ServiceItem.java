package com.viasat.wildblue.facade.billing.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import org.apache.cxf.xjc.runtime.JAXBToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

/**
 * <p>
 * Java class for ServiceItem complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="ServiceItem">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.wildblue.viasat.com/XMLSchema/v4/webservice/BillingFacade}ServiceItemBase">
 *       &lt;sequence>
 *         &lt;element name="serviceItemSequence" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceItem", propOrder =
{ "serviceItemSequence" })
public class ServiceItem extends ServiceItemBase
{

	protected int serviceItemSequence;

	/**
	 * Gets the value of the serviceItemSequence property.
	 * 
	 */
	public int getServiceItemSequence()
	{
		return serviceItemSequence;
	}

	/**
	 * Sets the value of the serviceItemSequence property.
	 * 
	 */
	public void setServiceItemSequence(int value)
	{
		this.serviceItemSequence = value;
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
