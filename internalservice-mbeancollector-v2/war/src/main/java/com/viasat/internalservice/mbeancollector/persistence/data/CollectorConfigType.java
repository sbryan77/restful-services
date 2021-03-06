//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-833 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.02.10 at 09:07:51 AM MST 
//


package com.viasat.internalservice.mbeancollector.persistence.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for collectorConfigType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="collectorConfigType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="collectorKey" type="{}collectorKeyType"/>
 *         &lt;element name="dataBusConfig" type="{}dataBusConfigType"/>
 *         &lt;element name="metricDefinitions" type="{}metricDefinitionsType"/>
 *         &lt;element name="credentials" type="{}credentialsType"/>
 *         &lt;element name="collectionInfos" type="{}collectionInfosType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "collectorConfigType", propOrder = {
    "collectorKey",
    "dataBusConfig",
    "metricDefinitions",
    "credentials",
    "collectionInfos"
})
public class CollectorConfigType {

    @XmlElement(required = true)
    protected CollectorKeyType collectorKey;
    @XmlElement(required = true)
    protected DataBusConfigType dataBusConfig;
    @XmlElement(required = true)
    protected MetricDefinitionsType metricDefinitions;
    @XmlElement(required = true)
    protected CredentialsType credentials;
    @XmlElement(required = true)
    protected CollectionInfosType collectionInfos;

    /**
     * Gets the value of the collectorKey property.
     * 
     * @return
     *     possible object is
     *     {@link CollectorKeyType }
     *     
     */
    public CollectorKeyType getCollectorKey() {
        return collectorKey;
    }

    /**
     * Sets the value of the collectorKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link CollectorKeyType }
     *     
     */
    public void setCollectorKey(CollectorKeyType value) {
        this.collectorKey = value;
    }

    /**
     * Gets the value of the dataBusConfig property.
     * 
     * @return
     *     possible object is
     *     {@link DataBusConfigType }
     *     
     */
    public DataBusConfigType getDataBusConfig() {
        return dataBusConfig;
    }

    /**
     * Sets the value of the dataBusConfig property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataBusConfigType }
     *     
     */
    public void setDataBusConfig(DataBusConfigType value) {
        this.dataBusConfig = value;
    }

    /**
     * Gets the value of the metricDefinitions property.
     * 
     * @return
     *     possible object is
     *     {@link MetricDefinitionsType }
     *     
     */
    public MetricDefinitionsType getMetricDefinitions() {
        return metricDefinitions;
    }

    /**
     * Sets the value of the metricDefinitions property.
     * 
     * @param value
     *     allowed object is
     *     {@link MetricDefinitionsType }
     *     
     */
    public void setMetricDefinitions(MetricDefinitionsType value) {
        this.metricDefinitions = value;
    }

    /**
     * Gets the value of the credentials property.
     * 
     * @return
     *     possible object is
     *     {@link CredentialsType }
     *     
     */
    public CredentialsType getCredentials() {
        return credentials;
    }

    /**
     * Sets the value of the credentials property.
     * 
     * @param value
     *     allowed object is
     *     {@link CredentialsType }
     *     
     */
    public void setCredentials(CredentialsType value) {
        this.credentials = value;
    }

    /**
     * Gets the value of the collectionInfos property.
     * 
     * @return
     *     possible object is
     *     {@link CollectionInfosType }
     *     
     */
    public CollectionInfosType getCollectionInfos() {
        return collectionInfos;
    }

    /**
     * Sets the value of the collectionInfos property.
     * 
     * @param value
     *     allowed object is
     *     {@link CollectionInfosType }
     *     
     */
    public void setCollectionInfos(CollectionInfosType value) {
        this.collectionInfos = value;
    }

}
