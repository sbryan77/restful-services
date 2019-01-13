package com.viasat.common.validator.data;

import com.viasat.wildblue.common.location.Address;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FindAddressAndTaxJurisdictions complex type.
 *
 * <p>The following schema fragment specifies the expected content contained
 * within this class.
 *
 * <pre>
   &lt;complexType name="FindAddressAndTaxJurisdictions">
     &lt;complexContent>
       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         &lt;sequence>
           &lt;element name="address" type="{http://www.viasat.com/XMLSchema/PublicWebService/v2/PWSCommonData}Address"/>
         &lt;/sequence>
       &lt;/restriction>
     &lt;/complexContent>
   &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "FindAddressAndTaxJurisdictions",
    propOrder = { "address" }
)
public class FindAddressAndTaxJurisdictions
{
    @XmlElement(required = true)
    protected Address address;

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
     * Sets the value of the address property.
     *
     * @param  value  allowed object is {@link Address }
     */
    public void setAddress(Address value)
    {
        this.address = value;
    }
}
