//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.12.25 at 01:06:39 PM MST 
//


package com.viasat.common.fault;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.viasat.commonxsd.adapter.XmlDateTimeToJavaDate;
import org.jvnet.jaxb2_commons.lang.CopyStrategy;
import org.jvnet.jaxb2_commons.lang.CopyTo;
import org.jvnet.jaxb2_commons.lang.Equals;
import org.jvnet.jaxb2_commons.lang.EqualsStrategy;
import org.jvnet.jaxb2_commons.lang.HashCode;
import org.jvnet.jaxb2_commons.lang.HashCodeStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBCopyStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBEqualsStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBHashCodeStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBToStringStrategy;
import org.jvnet.jaxb2_commons.lang.ToString;
import org.jvnet.jaxb2_commons.lang.ToStringStrategy;
import org.jvnet.jaxb2_commons.locator.ObjectLocator;
import org.jvnet.jaxb2_commons.locator.util.LocatorUtils;


/**
 * <p>Java class for FaultDetail complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FaultDetail">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="node" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="timestamp" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="trackingKey" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="method" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="contextPath" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="message" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="validationError" type="{http://www.viasat.com/XMLSchema/v1/Fault}ValidationError" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="faultDetail" type="{http://www.viasat.com/XMLSchema/v1/Fault}FaultDetail" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FaultDetail", propOrder = {
    "node",
    "timestamp",
    "trackingKey",
    "method",
    "contextPath",
    "message",
    "validationError",
    "faultDetail"
})
public class FaultDetail
    implements Cloneable, CopyTo, Equals, HashCode, ToString
{

    @XmlElement(required = true)
    protected String node;
    @XmlElement(required = true, type = String.class)
    @XmlJavaTypeAdapter(XmlDateTimeToJavaDate.class)
    @XmlSchemaType(name = "dateTime")
    protected Date timestamp;
    @XmlElement(required = true)
    protected String trackingKey;
    @XmlElement(required = true)
    protected String method;
    @XmlElement(required = true)
    protected String contextPath;
    protected String message;
    protected List<ValidationError> validationError;
    protected FaultDetail faultDetail;

    /**
     * Gets the value of the node property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNode() {
        return node;
    }

    /**
     * Sets the value of the node property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNode(String value) {
        this.node = value;
    }

    /**
     * Gets the value of the timestamp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the value of the timestamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTimestamp(Date value) {
        this.timestamp = value;
    }

    /**
     * Gets the value of the trackingKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTrackingKey() {
        return trackingKey;
    }

    /**
     * Sets the value of the trackingKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTrackingKey(String value) {
        this.trackingKey = value;
    }

    /**
     * Gets the value of the method property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMethod() {
        return method;
    }

    /**
     * Sets the value of the method property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMethod(String value) {
        this.method = value;
    }

    /**
     * Gets the value of the contextPath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContextPath() {
        return contextPath;
    }

    /**
     * Sets the value of the contextPath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContextPath(String value) {
        this.contextPath = value;
    }

    /**
     * Gets the value of the message property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the value of the message property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessage(String value) {
        this.message = value;
    }

    /**
     * Gets the value of the validationError property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the validationError property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getValidationError().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ValidationError }
     * 
     * 
     */
    public List<ValidationError> getValidationError() {
        if (validationError == null) {
            validationError = new ArrayList<ValidationError>();
        }
        return this.validationError;
    }

    /**
     * Gets the value of the faultDetail property.
     * 
     * @return
     *     possible object is
     *     {@link FaultDetail }
     *     
     */
    public FaultDetail getFaultDetail() {
        return faultDetail;
    }

    /**
     * Sets the value of the faultDetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link FaultDetail }
     *     
     */
    public void setFaultDetail(FaultDetail value) {
        this.faultDetail = value;
    }

    public String toString() {
        final ToStringStrategy strategy = JAXBToStringStrategy.INSTANCE;
        final StringBuilder buffer = new StringBuilder();
        append(null, buffer, strategy);
        return buffer.toString();
    }

    public StringBuilder append(ObjectLocator locator, StringBuilder buffer, ToStringStrategy strategy) {
        strategy.appendStart(locator, this, buffer);
        appendFields(locator, buffer, strategy);
        strategy.appendEnd(locator, this, buffer);
        return buffer;
    }

    public StringBuilder appendFields(ObjectLocator locator, StringBuilder buffer, ToStringStrategy strategy) {
        {
            String theNode;
            theNode = this.getNode();
            strategy.appendField(locator, this, "node", buffer, theNode);
        }
        {
            Date theTimestamp;
            theTimestamp = this.getTimestamp();
            strategy.appendField(locator, this, "timestamp", buffer, theTimestamp);
        }
        {
            String theTrackingKey;
            theTrackingKey = this.getTrackingKey();
            strategy.appendField(locator, this, "trackingKey", buffer, theTrackingKey);
        }
        {
            String theMethod;
            theMethod = this.getMethod();
            strategy.appendField(locator, this, "method", buffer, theMethod);
        }
        {
            String theContextPath;
            theContextPath = this.getContextPath();
            strategy.appendField(locator, this, "contextPath", buffer, theContextPath);
        }
        {
            String theMessage;
            theMessage = this.getMessage();
            strategy.appendField(locator, this, "message", buffer, theMessage);
        }
        {
            List<ValidationError> theValidationError;
            theValidationError = this.getValidationError();
            strategy.appendField(locator, this, "validationError", buffer, theValidationError);
        }
        {
            FaultDetail theFaultDetail;
            theFaultDetail = this.getFaultDetail();
            strategy.appendField(locator, this, "faultDetail", buffer, theFaultDetail);
        }
        return buffer;
    }

    public boolean equals(ObjectLocator thisLocator, ObjectLocator thatLocator, Object object, EqualsStrategy strategy) {
        if (!(object instanceof FaultDetail)) {
            return false;
        }
        if (this == object) {
            return true;
        }
        final FaultDetail that = ((FaultDetail) object);
        {
            String lhsNode;
            lhsNode = this.getNode();
            String rhsNode;
            rhsNode = that.getNode();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "node", lhsNode), LocatorUtils.property(thatLocator, "node", rhsNode), lhsNode, rhsNode)) {
                return false;
            }
        }
        {
            Date lhsTimestamp;
            lhsTimestamp = this.getTimestamp();
            Date rhsTimestamp;
            rhsTimestamp = that.getTimestamp();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "timestamp", lhsTimestamp), LocatorUtils.property(thatLocator, "timestamp", rhsTimestamp), lhsTimestamp, rhsTimestamp)) {
                return false;
            }
        }
        {
            String lhsTrackingKey;
            lhsTrackingKey = this.getTrackingKey();
            String rhsTrackingKey;
            rhsTrackingKey = that.getTrackingKey();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "trackingKey", lhsTrackingKey), LocatorUtils.property(thatLocator, "trackingKey", rhsTrackingKey), lhsTrackingKey, rhsTrackingKey)) {
                return false;
            }
        }
        {
            String lhsMethod;
            lhsMethod = this.getMethod();
            String rhsMethod;
            rhsMethod = that.getMethod();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "method", lhsMethod), LocatorUtils.property(thatLocator, "method", rhsMethod), lhsMethod, rhsMethod)) {
                return false;
            }
        }
        {
            String lhsContextPath;
            lhsContextPath = this.getContextPath();
            String rhsContextPath;
            rhsContextPath = that.getContextPath();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "contextPath", lhsContextPath), LocatorUtils.property(thatLocator, "contextPath", rhsContextPath), lhsContextPath, rhsContextPath)) {
                return false;
            }
        }
        {
            String lhsMessage;
            lhsMessage = this.getMessage();
            String rhsMessage;
            rhsMessage = that.getMessage();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "message", lhsMessage), LocatorUtils.property(thatLocator, "message", rhsMessage), lhsMessage, rhsMessage)) {
                return false;
            }
        }
        {
            List<ValidationError> lhsValidationError;
            lhsValidationError = this.getValidationError();
            List<ValidationError> rhsValidationError;
            rhsValidationError = that.getValidationError();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "validationError", lhsValidationError), LocatorUtils.property(thatLocator, "validationError", rhsValidationError), lhsValidationError, rhsValidationError)) {
                return false;
            }
        }
        {
            FaultDetail lhsFaultDetail;
            lhsFaultDetail = this.getFaultDetail();
            FaultDetail rhsFaultDetail;
            rhsFaultDetail = that.getFaultDetail();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "faultDetail", lhsFaultDetail), LocatorUtils.property(thatLocator, "faultDetail", rhsFaultDetail), lhsFaultDetail, rhsFaultDetail)) {
                return false;
            }
        }
        return true;
    }

    public boolean equals(Object object) {
        final EqualsStrategy strategy = JAXBEqualsStrategy.INSTANCE;
        return equals(null, null, object, strategy);
    }

    public int hashCode(ObjectLocator locator, HashCodeStrategy strategy) {
        int currentHashCode = 1;
        {
            String theNode;
            theNode = this.getNode();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "node", theNode), currentHashCode, theNode);
        }
        {
            Date theTimestamp;
            theTimestamp = this.getTimestamp();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "timestamp", theTimestamp), currentHashCode, theTimestamp);
        }
        {
            String theTrackingKey;
            theTrackingKey = this.getTrackingKey();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "trackingKey", theTrackingKey), currentHashCode, theTrackingKey);
        }
        {
            String theMethod;
            theMethod = this.getMethod();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "method", theMethod), currentHashCode, theMethod);
        }
        {
            String theContextPath;
            theContextPath = this.getContextPath();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "contextPath", theContextPath), currentHashCode, theContextPath);
        }
        {
            String theMessage;
            theMessage = this.getMessage();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "message", theMessage), currentHashCode, theMessage);
        }
        {
            List<ValidationError> theValidationError;
            theValidationError = this.getValidationError();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "validationError", theValidationError), currentHashCode, theValidationError);
        }
        {
            FaultDetail theFaultDetail;
            theFaultDetail = this.getFaultDetail();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "faultDetail", theFaultDetail), currentHashCode, theFaultDetail);
        }
        return currentHashCode;
    }

    public int hashCode() {
        final HashCodeStrategy strategy = JAXBHashCodeStrategy.INSTANCE;
        return this.hashCode(null, strategy);
    }

    public Object clone() {
        return copyTo(createNewInstance());
    }

    public Object copyTo(Object target) {
        final CopyStrategy strategy = JAXBCopyStrategy.INSTANCE;
        return copyTo(null, target, strategy);
    }

    public Object copyTo(ObjectLocator locator, Object target, CopyStrategy strategy) {
        final Object draftCopy = ((target == null)?createNewInstance():target);
        if (draftCopy instanceof FaultDetail) {
            final FaultDetail copy = ((FaultDetail) draftCopy);
            if (this.node!= null) {
                String sourceNode;
                sourceNode = this.getNode();
                String copyNode = ((String) strategy.copy(LocatorUtils.property(locator, "node", sourceNode), sourceNode));
                copy.setNode(copyNode);
            } else {
                copy.node = null;
            }
            if (this.timestamp!= null) {
                Date sourceTimestamp;
                sourceTimestamp = this.getTimestamp();
                Date copyTimestamp = ((Date) strategy.copy(LocatorUtils.property(locator, "timestamp", sourceTimestamp), sourceTimestamp));
                copy.setTimestamp(copyTimestamp);
            } else {
                copy.timestamp = null;
            }
            if (this.trackingKey!= null) {
                String sourceTrackingKey;
                sourceTrackingKey = this.getTrackingKey();
                String copyTrackingKey = ((String) strategy.copy(LocatorUtils.property(locator, "trackingKey", sourceTrackingKey), sourceTrackingKey));
                copy.setTrackingKey(copyTrackingKey);
            } else {
                copy.trackingKey = null;
            }
            if (this.method!= null) {
                String sourceMethod;
                sourceMethod = this.getMethod();
                String copyMethod = ((String) strategy.copy(LocatorUtils.property(locator, "method", sourceMethod), sourceMethod));
                copy.setMethod(copyMethod);
            } else {
                copy.method = null;
            }
            if (this.contextPath!= null) {
                String sourceContextPath;
                sourceContextPath = this.getContextPath();
                String copyContextPath = ((String) strategy.copy(LocatorUtils.property(locator, "contextPath", sourceContextPath), sourceContextPath));
                copy.setContextPath(copyContextPath);
            } else {
                copy.contextPath = null;
            }
            if (this.message!= null) {
                String sourceMessage;
                sourceMessage = this.getMessage();
                String copyMessage = ((String) strategy.copy(LocatorUtils.property(locator, "message", sourceMessage), sourceMessage));
                copy.setMessage(copyMessage);
            } else {
                copy.message = null;
            }
            if ((this.validationError!= null)&&(!this.validationError.isEmpty())) {
                List<ValidationError> sourceValidationError;
                sourceValidationError = this.getValidationError();
                @SuppressWarnings("unchecked")
                List<ValidationError> copyValidationError = ((List<ValidationError> ) strategy.copy(LocatorUtils.property(locator, "validationError", sourceValidationError), sourceValidationError));
                copy.validationError = null;
                List<ValidationError> uniqueValidationErrorl = copy.getValidationError();
                uniqueValidationErrorl.addAll(copyValidationError);
            } else {
                copy.validationError = null;
            }
            if (this.faultDetail!= null) {
                FaultDetail sourceFaultDetail;
                sourceFaultDetail = this.getFaultDetail();
                FaultDetail copyFaultDetail = ((FaultDetail) strategy.copy(LocatorUtils.property(locator, "faultDetail", sourceFaultDetail), sourceFaultDetail));
                copy.setFaultDetail(copyFaultDetail);
            } else {
                copy.faultDetail = null;
            }
        }
        return draftCopy;
    }

    public Object createNewInstance() {
        return new FaultDetail();
    }

}