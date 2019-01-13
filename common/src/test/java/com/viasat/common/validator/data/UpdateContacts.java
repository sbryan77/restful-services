
package com.viasat.common.validator.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UpdateContacts complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UpdateContacts">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="externalAccountReference" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="externalSystemName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="externalTransactionReference" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="correctedContact" type="{http://www.viasat.com/XMLSchema/PublicWebService/v1/PublicContactService}CorrectedContact"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UpdateContacts", propOrder = {
    "externalAccountReference",
    "externalSystemName",
    "externalTransactionReference",
    "correctedContact"
})
public class UpdateContacts {

    @XmlElement(required = true)
    protected String externalAccountReference;
    @XmlElement(required = true)
    protected String externalSystemName;
    @XmlElement(required = true)
    protected String externalTransactionReference;
    @XmlElement(required = true)
    protected CorrectedContact correctedContact;

    /**
     * Gets the value of the externalAccountReference property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExternalAccountReference() {
        return externalAccountReference;
    }

    /**
     * Sets the value of the externalAccountReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExternalAccountReference(String value) {
        this.externalAccountReference = value;
    }

    /**
     * Gets the value of the externalSystemName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExternalSystemName() {
        return externalSystemName;
    }

    /**
     * Sets the value of the externalSystemName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExternalSystemName(String value) {
        this.externalSystemName = value;
    }

    /**
     * Gets the value of the externalTransactionReference property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExternalTransactionReference() {
        return externalTransactionReference;
    }

    /**
     * Sets the value of the externalTransactionReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExternalTransactionReference(String value) {
        this.externalTransactionReference = value;
    }

    /**
     * Gets the value of the correctedContact property.
     * 
     * @return
     *     possible object is
     *     {@link CorrectedContact }
     *     
     */
    public CorrectedContact getCorrectedContact() {
        return correctedContact;
    }

    /**
     * Sets the value of the correctedContact property.
     * 
     * @param value
     *     allowed object is
     *     {@link CorrectedContact }
     *     
     */
    public void setCorrectedContact(CorrectedContact value) {
        this.correctedContact = value;
    }

}
