//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.01.13 at 02:35:24 PM MST 
//


package com.viasat.wildblue.common.commondata;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
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
 * <p>Java class for Beam complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Beam">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="beamTechnicalInfo" type="{http://www.wildblue.viasat.com/XMLSchema/v3.0/CommonData}BeamTechnicalInfo"/>
 *         &lt;element name="beamSalesInfo" type="{http://www.wildblue.viasat.com/XMLSchema/v3.0/CommonData}BeamSalesInfo"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Beam", propOrder = {
    "beamTechnicalInfo",
    "beamSalesInfo"
})
public class Beam
    implements Cloneable, CopyTo, Equals, HashCode, ToString
{

    @XmlElement(required = true)
    protected BeamTechnicalInfo beamTechnicalInfo;
    @XmlElement(required = true)
    protected BeamSalesInfo beamSalesInfo;

    /**
     * Gets the value of the beamTechnicalInfo property.
     * 
     * @return
     *     possible object is
     *     {@link BeamTechnicalInfo }
     *     
     */
    public BeamTechnicalInfo getBeamTechnicalInfo() {
        return beamTechnicalInfo;
    }

    /**
     * Sets the value of the beamTechnicalInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BeamTechnicalInfo }
     *     
     */
    public void setBeamTechnicalInfo(BeamTechnicalInfo value) {
        this.beamTechnicalInfo = value;
    }

    /**
     * Gets the value of the beamSalesInfo property.
     * 
     * @return
     *     possible object is
     *     {@link BeamSalesInfo }
     *     
     */
    public BeamSalesInfo getBeamSalesInfo() {
        return beamSalesInfo;
    }

    /**
     * Sets the value of the beamSalesInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BeamSalesInfo }
     *     
     */
    public void setBeamSalesInfo(BeamSalesInfo value) {
        this.beamSalesInfo = value;
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
            BeamTechnicalInfo theBeamTechnicalInfo;
            theBeamTechnicalInfo = this.getBeamTechnicalInfo();
            strategy.appendField(locator, this, "beamTechnicalInfo", buffer, theBeamTechnicalInfo);
        }
        {
            BeamSalesInfo theBeamSalesInfo;
            theBeamSalesInfo = this.getBeamSalesInfo();
            strategy.appendField(locator, this, "beamSalesInfo", buffer, theBeamSalesInfo);
        }
        return buffer;
    }

    public boolean equals(ObjectLocator thisLocator, ObjectLocator thatLocator, Object object, EqualsStrategy strategy) {
        if (!(object instanceof Beam)) {
            return false;
        }
        if (this == object) {
            return true;
        }
        final Beam that = ((Beam) object);
        {
            BeamTechnicalInfo lhsBeamTechnicalInfo;
            lhsBeamTechnicalInfo = this.getBeamTechnicalInfo();
            BeamTechnicalInfo rhsBeamTechnicalInfo;
            rhsBeamTechnicalInfo = that.getBeamTechnicalInfo();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "beamTechnicalInfo", lhsBeamTechnicalInfo), LocatorUtils.property(thatLocator, "beamTechnicalInfo", rhsBeamTechnicalInfo), lhsBeamTechnicalInfo, rhsBeamTechnicalInfo)) {
                return false;
            }
        }
        {
            BeamSalesInfo lhsBeamSalesInfo;
            lhsBeamSalesInfo = this.getBeamSalesInfo();
            BeamSalesInfo rhsBeamSalesInfo;
            rhsBeamSalesInfo = that.getBeamSalesInfo();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "beamSalesInfo", lhsBeamSalesInfo), LocatorUtils.property(thatLocator, "beamSalesInfo", rhsBeamSalesInfo), lhsBeamSalesInfo, rhsBeamSalesInfo)) {
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
            BeamTechnicalInfo theBeamTechnicalInfo;
            theBeamTechnicalInfo = this.getBeamTechnicalInfo();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "beamTechnicalInfo", theBeamTechnicalInfo), currentHashCode, theBeamTechnicalInfo);
        }
        {
            BeamSalesInfo theBeamSalesInfo;
            theBeamSalesInfo = this.getBeamSalesInfo();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "beamSalesInfo", theBeamSalesInfo), currentHashCode, theBeamSalesInfo);
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
        if (draftCopy instanceof Beam) {
            final Beam copy = ((Beam) draftCopy);
            if (this.beamTechnicalInfo!= null) {
                BeamTechnicalInfo sourceBeamTechnicalInfo;
                sourceBeamTechnicalInfo = this.getBeamTechnicalInfo();
                BeamTechnicalInfo copyBeamTechnicalInfo = ((BeamTechnicalInfo) strategy.copy(LocatorUtils.property(locator, "beamTechnicalInfo", sourceBeamTechnicalInfo), sourceBeamTechnicalInfo));
                copy.setBeamTechnicalInfo(copyBeamTechnicalInfo);
            } else {
                copy.beamTechnicalInfo = null;
            }
            if (this.beamSalesInfo!= null) {
                BeamSalesInfo sourceBeamSalesInfo;
                sourceBeamSalesInfo = this.getBeamSalesInfo();
                BeamSalesInfo copyBeamSalesInfo = ((BeamSalesInfo) strategy.copy(LocatorUtils.property(locator, "beamSalesInfo", sourceBeamSalesInfo), sourceBeamSalesInfo));
                copy.setBeamSalesInfo(copyBeamSalesInfo);
            } else {
                copy.beamSalesInfo = null;
            }
        }
        return draftCopy;
    }

    public Object createNewInstance() {
        return new Beam();
    }

}
