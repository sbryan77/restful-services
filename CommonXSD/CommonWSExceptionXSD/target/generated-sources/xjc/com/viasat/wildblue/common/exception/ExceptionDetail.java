//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.01.13 at 02:35:25 PM MST 
//


package com.viasat.wildblue.common.exception;

import java.util.Date;
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
 * Data elements based upon a SOAP fault in SOAP 1.2.
 * 
 * <p>Java class for ExceptionDetail complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ExceptionDetail">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="reason" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="node" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="role" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="detail" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="timestamp" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="trackingKey" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExceptionDetail", propOrder = {
    "code",
    "reason",
    "node",
    "role",
    "detail",
    "timestamp",
    "trackingKey"
})
public class ExceptionDetail
    implements Cloneable, CopyTo, Equals, HashCode, ToString
{

    @XmlElement(required = true)
    protected String code;
    @XmlElement(required = true)
    protected String reason;
    @XmlElement(required = true)
    protected String node;
    @XmlElement(required = true)
    protected String role;
    @XmlElement(required = true)
    protected String detail;
    @XmlElement(required = true, type = String.class)
    @XmlJavaTypeAdapter(XmlDateTimeToJavaDate.class)
    @XmlSchemaType(name = "dateTime")
    protected Date timestamp;
    @XmlElement(required = true)
    protected String trackingKey;

    /**
     * Gets the value of the code property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCode(String value) {
        this.code = value;
    }

    /**
     * Gets the value of the reason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReason() {
        return reason;
    }

    /**
     * Sets the value of the reason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReason(String value) {
        this.reason = value;
    }

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
     * Gets the value of the role property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the value of the role property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRole(String value) {
        this.role = value;
    }

    /**
     * Gets the value of the detail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDetail() {
        return detail;
    }

    /**
     * Sets the value of the detail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDetail(String value) {
        this.detail = value;
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
            String theCode;
            theCode = this.getCode();
            strategy.appendField(locator, this, "code", buffer, theCode);
        }
        {
            String theReason;
            theReason = this.getReason();
            strategy.appendField(locator, this, "reason", buffer, theReason);
        }
        {
            String theNode;
            theNode = this.getNode();
            strategy.appendField(locator, this, "node", buffer, theNode);
        }
        {
            String theRole;
            theRole = this.getRole();
            strategy.appendField(locator, this, "role", buffer, theRole);
        }
        {
            String theDetail;
            theDetail = this.getDetail();
            strategy.appendField(locator, this, "detail", buffer, theDetail);
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
        return buffer;
    }

    public boolean equals(ObjectLocator thisLocator, ObjectLocator thatLocator, Object object, EqualsStrategy strategy) {
        if (!(object instanceof ExceptionDetail)) {
            return false;
        }
        if (this == object) {
            return true;
        }
        final ExceptionDetail that = ((ExceptionDetail) object);
        {
            String lhsCode;
            lhsCode = this.getCode();
            String rhsCode;
            rhsCode = that.getCode();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "code", lhsCode), LocatorUtils.property(thatLocator, "code", rhsCode), lhsCode, rhsCode)) {
                return false;
            }
        }
        {
            String lhsReason;
            lhsReason = this.getReason();
            String rhsReason;
            rhsReason = that.getReason();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "reason", lhsReason), LocatorUtils.property(thatLocator, "reason", rhsReason), lhsReason, rhsReason)) {
                return false;
            }
        }
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
            String lhsRole;
            lhsRole = this.getRole();
            String rhsRole;
            rhsRole = that.getRole();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "role", lhsRole), LocatorUtils.property(thatLocator, "role", rhsRole), lhsRole, rhsRole)) {
                return false;
            }
        }
        {
            String lhsDetail;
            lhsDetail = this.getDetail();
            String rhsDetail;
            rhsDetail = that.getDetail();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "detail", lhsDetail), LocatorUtils.property(thatLocator, "detail", rhsDetail), lhsDetail, rhsDetail)) {
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
        return true;
    }

    public boolean equals(Object object) {
        final EqualsStrategy strategy = JAXBEqualsStrategy.INSTANCE;
        return equals(null, null, object, strategy);
    }

    public int hashCode(ObjectLocator locator, HashCodeStrategy strategy) {
        int currentHashCode = 1;
        {
            String theCode;
            theCode = this.getCode();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "code", theCode), currentHashCode, theCode);
        }
        {
            String theReason;
            theReason = this.getReason();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "reason", theReason), currentHashCode, theReason);
        }
        {
            String theNode;
            theNode = this.getNode();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "node", theNode), currentHashCode, theNode);
        }
        {
            String theRole;
            theRole = this.getRole();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "role", theRole), currentHashCode, theRole);
        }
        {
            String theDetail;
            theDetail = this.getDetail();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "detail", theDetail), currentHashCode, theDetail);
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
        if (draftCopy instanceof ExceptionDetail) {
            final ExceptionDetail copy = ((ExceptionDetail) draftCopy);
            if (this.code!= null) {
                String sourceCode;
                sourceCode = this.getCode();
                String copyCode = ((String) strategy.copy(LocatorUtils.property(locator, "code", sourceCode), sourceCode));
                copy.setCode(copyCode);
            } else {
                copy.code = null;
            }
            if (this.reason!= null) {
                String sourceReason;
                sourceReason = this.getReason();
                String copyReason = ((String) strategy.copy(LocatorUtils.property(locator, "reason", sourceReason), sourceReason));
                copy.setReason(copyReason);
            } else {
                copy.reason = null;
            }
            if (this.node!= null) {
                String sourceNode;
                sourceNode = this.getNode();
                String copyNode = ((String) strategy.copy(LocatorUtils.property(locator, "node", sourceNode), sourceNode));
                copy.setNode(copyNode);
            } else {
                copy.node = null;
            }
            if (this.role!= null) {
                String sourceRole;
                sourceRole = this.getRole();
                String copyRole = ((String) strategy.copy(LocatorUtils.property(locator, "role", sourceRole), sourceRole));
                copy.setRole(copyRole);
            } else {
                copy.role = null;
            }
            if (this.detail!= null) {
                String sourceDetail;
                sourceDetail = this.getDetail();
                String copyDetail = ((String) strategy.copy(LocatorUtils.property(locator, "detail", sourceDetail), sourceDetail));
                copy.setDetail(copyDetail);
            } else {
                copy.detail = null;
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
        }
        return draftCopy;
    }

    public Object createNewInstance() {
        return new ExceptionDetail();
    }

}
