//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-833 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.02.10 at 09:07:51 AM MST 
//


package com.viasat.internalservice.mbeancollector.persistence.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for collectionInfoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="collectionInfoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="collectionSource" type="{}collectionSourceType"/>
 *       &lt;/sequence>
 *       &lt;attribute name="collectorType" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="assignedCollector" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "collectionInfoType", propOrder = {
    "collectionSource"
})
public class CollectionInfoType {

    @XmlElement(required = true)
    protected CollectionSourceType collectionSource;
    @XmlAttribute
    protected String collectorType;
    @XmlAttribute
    protected String assignedCollector;

    /**
     * Gets the value of the collectionSource property.
     * 
     * @return
     *     possible object is
     *     {@link CollectionSourceType }
     *     
     */
    public CollectionSourceType getCollectionSource() {
        return collectionSource;
    }

    /**
     * Sets the value of the collectionSource property.
     * 
     * @param value
     *     allowed object is
     *     {@link CollectionSourceType }
     *     
     */
    public void setCollectionSource(CollectionSourceType value) {
        this.collectionSource = value;
    }

    /**
     * Gets the value of the collectorType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCollectorType() {
        return collectorType;
    }

    /**
     * Sets the value of the collectorType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCollectorType(String value) {
        this.collectorType = value;
    }

    /**
     * Gets the value of the assignedCollector property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAssignedCollector() {
        return assignedCollector;
    }

    /**
     * Sets the value of the assignedCollector property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAssignedCollector(String value) {
        this.assignedCollector = value;
    }

}
