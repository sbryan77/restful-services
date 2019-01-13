package com.viasat.wildblue.facade.billing.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.cxf.xjc.runtime.JAXBToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

/**
 * <p>
 * Java class for EFT complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="EFT">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="bankAccountHolder" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="bankAccountNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="bankCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="paymentType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EFT", propOrder =
{ "bankAccountHolder", "bankAccountNumber", "bankCode", "paymentType" })
public class EFT
{

	@XmlElement(required = true)
	protected String bankAccountHolder;
	@XmlElement(required = true)
	protected String bankAccountNumber;
	@XmlElement(required = true)
	protected String bankCode;
	@XmlElement(required = true)
	protected String paymentType;

	/**
	 * Gets the value of the bankAccountHolder property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getBankAccountHolder()
	{
		return bankAccountHolder;
	}

	/**
	 * Sets the value of the bankAccountHolder property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setBankAccountHolder(String value)
	{
		this.bankAccountHolder = value;
	}

	/**
	 * Gets the value of the bankAccountNumber property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getBankAccountNumber()
	{
		return bankAccountNumber;
	}

	/**
	 * Sets the value of the bankAccountNumber property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setBankAccountNumber(String value)
	{
		this.bankAccountNumber = value;
	}

	/**
	 * Gets the value of the bankCode property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getBankCode()
	{
		return bankCode;
	}

	/**
	 * Sets the value of the bankCode property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setBankCode(String value)
	{
		this.bankCode = value;
	}

	/**
	 * Gets the value of the paymentType property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPaymentType()
	{
		return paymentType;
	}

	/**
	 * Sets the value of the paymentType property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPaymentType(String value)
	{
		this.paymentType = value;
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
