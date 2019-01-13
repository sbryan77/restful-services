
package com.viasat.wildblue.xmlschema.v3_0.header;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for wildBlueHeader complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="wildBlueHeader"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="invokedBy" type="{http://www.wildblue.viasat.com/XMLSchema/v3.0/Header}invokedBy"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "wildBlueHeader", propOrder = {
    "invokedBy"
})
public class WildBlueHeader {

    @XmlElement(required = true)
    protected InvokedBy invokedBy;

    /**
     * Gets the value of the invokedBy property.
     * 
     * @return
     *     possible object is
     *     {@link InvokedBy }
     *     
     */
    public InvokedBy getInvokedBy() {
        return invokedBy;
    }

    /**
     * Sets the value of the invokedBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvokedBy }
     *     
     */
    public void setInvokedBy(InvokedBy value) {
        this.invokedBy = value;
    }

}
