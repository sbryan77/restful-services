package com.viasat.wildblue.facade.billing.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.cxf.xjc.runtime.JAXBToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

/**
 * <p>
 * Java class for PaymentMethod complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="PaymentMethod">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="paymentMethodType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="eft" type="{http://www.wildblue.viasat.com/XMLSchema/v4/webservice/BillingFacade}EFT" minOccurs="0"/>
 *         &lt;element name="creditCard" type="{http://www.wildblue.viasat.com/XMLSchema/v4/webservice/BillingFacade}CreditCard" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaymentMethod", propOrder =
{ "paymentMethodType", "eft", "creditCard" })
public class PaymentMethod
{

	@XmlElement(required = true)
	protected String paymentMethodType;
	protected EFT eft;
	protected CreditCard creditCard;

	/**
	 * Gets the value of the paymentMethodType property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPaymentMethodType()
	{
		return paymentMethodType;
	}

	/**
	 * Sets the value of the paymentMethodType property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPaymentMethodType(String value)
	{
		this.paymentMethodType = value;
	}

	/**
	 * Gets the value of the eft property.
	 * 
	 * @return possible object is {@link EFT }
	 * 
	 */
	public EFT getEft()
	{
		return eft;
	}

	/**
	 * Sets the value of the eft property.
	 * 
	 * @param value
	 *            allowed object is {@link EFT }
	 * 
	 */
	public void setEft(EFT value)
	{
		this.eft = value;
	}

	/**
	 * Gets the value of the creditCard property.
	 * 
	 * @return possible object is {@link CreditCard }
	 * 
	 */
	public CreditCard getCreditCard()
	{
		return creditCard;
	}

	/**
	 * Sets the value of the creditCard property.
	 * 
	 * @param value
	 *            allowed object is {@link CreditCard }
	 * 
	 */
	public void setCreditCard(CreditCard value)
	{
		this.creditCard = value;
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
