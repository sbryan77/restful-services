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
 * Java class for CreditCard complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 *    &lt;complexType name="CreditCard">
 *      &lt;complexContent>
 *        &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *          &lt;sequence>
 *            &lt;element name="cardNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *            &lt;element name="expirationDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *            &lt;element name="accountHolder" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *            &lt;element name="zipCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *            &lt;element name="methodOfPayment" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *            &lt;element name="CVV" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *          &lt;/sequence>
 *        &lt;/restriction>
 *      &lt;/complexContent>
 *    &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreditCard", propOrder =
{ "cardNumber", "expirationDate", "accountHolder", "zipCode", "methodOfPayment", "cvv" })
public class CreditCard
{
	@XmlElement(required = true)
	protected String accountHolder;

	@XmlElement(required = true)
	protected String cardNumber;
	@XmlElement(name = "CVV")
	protected String cvv;
	@XmlElement(required = true, type = String.class)
	@XmlSchemaType(name = "dateTime")
	protected Date expirationDate;
	@XmlElement(required = true)
	protected String methodOfPayment;
	protected String zipCode;

	/**
	 * Gets the value of the accountHolder property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getAccountHolder()
	{
		return accountHolder;
	}

	/**
	 * Gets the value of the cardNumber property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getCardNumber()
	{
		return cardNumber;
	}

	/**
	 * Gets the value of the cvv property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getCVV()
	{
		return cvv;
	}

	/**
	 * Gets the value of the expirationDate property.
	 * 
	 * @return possible object is {@link String }
	 */
	public Date getExpirationDate()
	{
		return expirationDate;
	}

	/**
	 * Gets the value of the methodOfPayment property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getMethodOfPayment()
	{
		return methodOfPayment;
	}

	/**
	 * Gets the value of the zipCode property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getZipCode()
	{
		return zipCode;
	}

	/**
	 * Sets the value of the accountHolder property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setAccountHolder(String value)
	{
		this.accountHolder = value;
	}

	/**
	 * Sets the value of the cardNumber property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setCardNumber(String value)
	{
		this.cardNumber = value;
	}

	/**
	 * Sets the value of the cvv property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setCVV(String value)
	{
		this.cvv = value;
	}

	/**
	 * Sets the value of the expirationDate property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setExpirationDate(Date value)
	{
		this.expirationDate = value;
	}

	/**
	 * Sets the value of the methodOfPayment property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setMethodOfPayment(String value)
	{
		this.methodOfPayment = value;
	}

	/**
	 * Sets the value of the zipCode property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setZipCode(String value)
	{
		this.zipCode = value;
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
