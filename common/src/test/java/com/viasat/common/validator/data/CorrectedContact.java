package com.viasat.common.validator.data;

import com.viasat.wildblue.common.commondata.Person;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CorrectedContact complex type.
 *
 * <p>The following schema fragment specifies the expected content contained
 * within this class.
 *
 * <pre>
   &lt;complexType name="CorrectedContact">
     &lt;complexContent>
       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         &lt;sequence>
           &lt;element name="person" type="{http://www.viasat.com/XMLSchema/PublicWebService/v2/PWSCommonData}Person" minOccurs="0"/>
           &lt;element name="emailAddress" type="{http://www.viasat.com/XMLSchema/PublicWebService/v2/PWSCommonData}EmailAddress" minOccurs="0"/>
           &lt;element name="primaryPhone" type="{http://www.viasat.com/XMLSchema/PublicWebService/v2/PWSCommonData}PhoneNumber" minOccurs="0"/>
           &lt;element name="secondaryPhone" type="{http://www.viasat.com/XMLSchema/PublicWebService/v2/PWSCommonData}PhoneNumber" minOccurs="0"/>
           &lt;element name="billingAddress" type="{http://www.viasat.com/XMLSchema/PublicWebService/v1/PublicContactService}CorrectedAddress" minOccurs="0"/>
           &lt;element name="serviceAddress" type="{http://www.viasat.com/XMLSchema/PublicWebService/v1/PublicContactService}CorrectedAddress" minOccurs="0"/>
         &lt;/sequence>
       &lt;/restriction>
     &lt;/complexContent>
   &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "CorrectedContact",
    propOrder =
        {
            "person", "emailAddress", "primaryPhone", "secondaryPhone",
            "billingAddress", "serviceAddress"
        }
)
public class CorrectedContact
{
    protected CorrectedAddress billingAddress;
    protected String emailAddress;

    protected Person person;
    protected String primaryPhone;
    protected String secondaryPhone;
    protected CorrectedAddress serviceAddress;

    /**
     * Gets the value of the billingAddress property.
     *
     * @return  possible object is {@link CorrectedAddress }
     */
    public CorrectedAddress getBillingAddress()
    {
        return billingAddress;
    }

    /**
     * Gets the value of the emailAddress property.
     *
     * @return  possible object is {@link String }
     */
    public String getEmailAddress()
    {
        return emailAddress;
    }

    /**
     * Gets the value of the person property.
     *
     * @return  possible object is {@link Person }
     */
    public Person getPerson()
    {
        return person;
    }

    /**
     * Gets the value of the primaryPhone property.
     *
     * @return  possible object is {@link String }
     */
    public String getPrimaryPhone()
    {
        return primaryPhone;
    }

    /**
     * Gets the value of the secondaryPhone property.
     *
     * @return  possible object is {@link String }
     */
    public String getSecondaryPhone()
    {
        return secondaryPhone;
    }

    /**
     * Gets the value of the serviceAddress property.
     *
     * @return  possible object is {@link CorrectedAddress }
     */
    public CorrectedAddress getServiceAddress()
    {
        return serviceAddress;
    }

    /**
     * Sets the value of the billingAddress property.
     *
     * @param  value  allowed object is {@link CorrectedAddress }
     */
    public void setBillingAddress(CorrectedAddress value)
    {
        this.billingAddress = value;
    }

    /**
     * Sets the value of the emailAddress property.
     *
     * @param  value  allowed object is {@link String }
     */
    public void setEmailAddress(String value)
    {
        this.emailAddress = value;
    }

    /**
     * Sets the value of the person property.
     *
     * @param  value  allowed object is {@link Person }
     */
    public void setPerson(Person value)
    {
        this.person = value;
    }

    /**
     * Sets the value of the primaryPhone property.
     *
     * @param  value  allowed object is {@link String }
     */
    public void setPrimaryPhone(String value)
    {
        this.primaryPhone = value;
    }

    /**
     * Sets the value of the secondaryPhone property.
     *
     * @param  value  allowed object is {@link String }
     */
    public void setSecondaryPhone(String value)
    {
        this.secondaryPhone = value;
    }

    /**
     * Sets the value of the serviceAddress property.
     *
     * @param  value  allowed object is {@link CorrectedAddress }
     */
    public void setServiceAddress(CorrectedAddress value)
    {
        this.serviceAddress = value;
    }
}
