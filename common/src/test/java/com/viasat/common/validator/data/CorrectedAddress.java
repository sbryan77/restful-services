package com.viasat.common.validator.data;

import com.viasat.wildblue.common.location.Address;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CorrectedAddress complex type.
 *
 * <p>The following schema fragment specifies the expected content contained
 * within this class.
 *
 * <pre>
   &lt;complexType name="CorrectedAddress">
     &lt;complexContent>
       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         &lt;sequence>
           &lt;element name="address" type="{http://www.viasat.com/XMLSchema/PublicWebService/v2/PWSCommonData}Address"/>
           &lt;element name="taxJurisdictionCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
         &lt;/sequence>
       &lt;/restriction>
     &lt;/complexContent>
   &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "CorrectedAddress",
    propOrder = { "address", "taxJurisdictionCode" }
)
public class CorrectedAddress
{
    @XmlElement(required = true)
    protected Address address;
    @XmlElement(required = true)
    protected String taxJurisdictionCode;

    /**
     * Gets the value of the address property.
     *
     * @return  possible object is {@link Address }
     */
    public Address getAddress()
    {
        return address;
    }

    /**
     * Gets the value of the taxJurisdictionCode property.
     *
     * @return  possible object is {@link String }
     */
    public String getTaxJurisdictionCode()
    {
        return taxJurisdictionCode;
    }

    /**
     * Sets the value of the address property.
     *
     * @param  value  allowed object is {@link Address }
     */
    public void setAddress(Address value)
    {
        this.address = value;
    }

    /**
     * Sets the value of the taxJurisdictionCode property.
     *
     * @param  value  allowed object is {@link String }
     */
    public void setTaxJurisdictionCode(String value)
    {
        this.taxJurisdictionCode = value;
    }
}
