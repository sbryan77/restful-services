//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.12.25 at 01:06:37 PM MST 
//


package com.viasat.wildblue.common.commondata;

import java.math.BigInteger;
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
 * <p>Java class for BeamTechnicalInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BeamTechnicalInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="beamNumber" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="satelliteName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="polarization" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="antennaLookAngles" type="{http://www.wildblue.viasat.com/XMLSchema/v3.0/CommonData}AntennaLookAngles"/>
 *         &lt;element name="antennaPointingAid" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="gatewayId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="gatewayName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="modemInstallCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="customerCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="equipmentType" type="{http://www.wildblue.viasat.com/XMLSchema/v3.0/CommonData}EquipmentType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BeamTechnicalInfo", propOrder = {
    "beamNumber",
    "satelliteName",
    "polarization",
    "antennaLookAngles",
    "antennaPointingAid",
    "gatewayId",
    "gatewayName",
    "modemInstallCode",
    "customerCode",
    "equipmentType"
})
public class BeamTechnicalInfo
    implements Cloneable, CopyTo, Equals, HashCode, ToString
{

    @XmlElement(required = true)
    protected BigInteger beamNumber;
    @XmlElement(required = true)
    protected String satelliteName;
    @XmlElement(required = true)
    protected String polarization;
    @XmlElement(required = true)
    protected AntennaLookAngles antennaLookAngles;
    @XmlElement(required = true)
    protected String antennaPointingAid;
    protected String gatewayId;
    protected String gatewayName;
    @XmlElement(required = true)
    protected String modemInstallCode;
    protected String customerCode;
    @XmlElement(required = true)
    protected EquipmentType equipmentType;

    /**
     * Gets the value of the beamNumber property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getBeamNumber() {
        return beamNumber;
    }

    /**
     * Sets the value of the beamNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setBeamNumber(BigInteger value) {
        this.beamNumber = value;
    }

    /**
     * Gets the value of the satelliteName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSatelliteName() {
        return satelliteName;
    }

    /**
     * Sets the value of the satelliteName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSatelliteName(String value) {
        this.satelliteName = value;
    }

    /**
     * Gets the value of the polarization property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPolarization() {
        return polarization;
    }

    /**
     * Sets the value of the polarization property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPolarization(String value) {
        this.polarization = value;
    }

    /**
     * Gets the value of the antennaLookAngles property.
     * 
     * @return
     *     possible object is
     *     {@link AntennaLookAngles }
     *     
     */
    public AntennaLookAngles getAntennaLookAngles() {
        return antennaLookAngles;
    }

    /**
     * Sets the value of the antennaLookAngles property.
     * 
     * @param value
     *     allowed object is
     *     {@link AntennaLookAngles }
     *     
     */
    public void setAntennaLookAngles(AntennaLookAngles value) {
        this.antennaLookAngles = value;
    }

    /**
     * Gets the value of the antennaPointingAid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAntennaPointingAid() {
        return antennaPointingAid;
    }

    /**
     * Sets the value of the antennaPointingAid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAntennaPointingAid(String value) {
        this.antennaPointingAid = value;
    }

    /**
     * Gets the value of the gatewayId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGatewayId() {
        return gatewayId;
    }

    /**
     * Sets the value of the gatewayId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGatewayId(String value) {
        this.gatewayId = value;
    }

    /**
     * Gets the value of the gatewayName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGatewayName() {
        return gatewayName;
    }

    /**
     * Sets the value of the gatewayName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGatewayName(String value) {
        this.gatewayName = value;
    }

    /**
     * Gets the value of the modemInstallCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModemInstallCode() {
        return modemInstallCode;
    }

    /**
     * Sets the value of the modemInstallCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModemInstallCode(String value) {
        this.modemInstallCode = value;
    }

    /**
     * Gets the value of the customerCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerCode() {
        return customerCode;
    }

    /**
     * Sets the value of the customerCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerCode(String value) {
        this.customerCode = value;
    }

    /**
     * Gets the value of the equipmentType property.
     * 
     * @return
     *     possible object is
     *     {@link EquipmentType }
     *     
     */
    public EquipmentType getEquipmentType() {
        return equipmentType;
    }

    /**
     * Sets the value of the equipmentType property.
     * 
     * @param value
     *     allowed object is
     *     {@link EquipmentType }
     *     
     */
    public void setEquipmentType(EquipmentType value) {
        this.equipmentType = value;
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
            BigInteger theBeamNumber;
            theBeamNumber = this.getBeamNumber();
            strategy.appendField(locator, this, "beamNumber", buffer, theBeamNumber);
        }
        {
            String theSatelliteName;
            theSatelliteName = this.getSatelliteName();
            strategy.appendField(locator, this, "satelliteName", buffer, theSatelliteName);
        }
        {
            String thePolarization;
            thePolarization = this.getPolarization();
            strategy.appendField(locator, this, "polarization", buffer, thePolarization);
        }
        {
            AntennaLookAngles theAntennaLookAngles;
            theAntennaLookAngles = this.getAntennaLookAngles();
            strategy.appendField(locator, this, "antennaLookAngles", buffer, theAntennaLookAngles);
        }
        {
            String theAntennaPointingAid;
            theAntennaPointingAid = this.getAntennaPointingAid();
            strategy.appendField(locator, this, "antennaPointingAid", buffer, theAntennaPointingAid);
        }
        {
            String theGatewayId;
            theGatewayId = this.getGatewayId();
            strategy.appendField(locator, this, "gatewayId", buffer, theGatewayId);
        }
        {
            String theGatewayName;
            theGatewayName = this.getGatewayName();
            strategy.appendField(locator, this, "gatewayName", buffer, theGatewayName);
        }
        {
            String theModemInstallCode;
            theModemInstallCode = this.getModemInstallCode();
            strategy.appendField(locator, this, "modemInstallCode", buffer, theModemInstallCode);
        }
        {
            String theCustomerCode;
            theCustomerCode = this.getCustomerCode();
            strategy.appendField(locator, this, "customerCode", buffer, theCustomerCode);
        }
        {
            EquipmentType theEquipmentType;
            theEquipmentType = this.getEquipmentType();
            strategy.appendField(locator, this, "equipmentType", buffer, theEquipmentType);
        }
        return buffer;
    }

    public boolean equals(ObjectLocator thisLocator, ObjectLocator thatLocator, Object object, EqualsStrategy strategy) {
        if (!(object instanceof BeamTechnicalInfo)) {
            return false;
        }
        if (this == object) {
            return true;
        }
        final BeamTechnicalInfo that = ((BeamTechnicalInfo) object);
        {
            BigInteger lhsBeamNumber;
            lhsBeamNumber = this.getBeamNumber();
            BigInteger rhsBeamNumber;
            rhsBeamNumber = that.getBeamNumber();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "beamNumber", lhsBeamNumber), LocatorUtils.property(thatLocator, "beamNumber", rhsBeamNumber), lhsBeamNumber, rhsBeamNumber)) {
                return false;
            }
        }
        {
            String lhsSatelliteName;
            lhsSatelliteName = this.getSatelliteName();
            String rhsSatelliteName;
            rhsSatelliteName = that.getSatelliteName();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "satelliteName", lhsSatelliteName), LocatorUtils.property(thatLocator, "satelliteName", rhsSatelliteName), lhsSatelliteName, rhsSatelliteName)) {
                return false;
            }
        }
        {
            String lhsPolarization;
            lhsPolarization = this.getPolarization();
            String rhsPolarization;
            rhsPolarization = that.getPolarization();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "polarization", lhsPolarization), LocatorUtils.property(thatLocator, "polarization", rhsPolarization), lhsPolarization, rhsPolarization)) {
                return false;
            }
        }
        {
            AntennaLookAngles lhsAntennaLookAngles;
            lhsAntennaLookAngles = this.getAntennaLookAngles();
            AntennaLookAngles rhsAntennaLookAngles;
            rhsAntennaLookAngles = that.getAntennaLookAngles();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "antennaLookAngles", lhsAntennaLookAngles), LocatorUtils.property(thatLocator, "antennaLookAngles", rhsAntennaLookAngles), lhsAntennaLookAngles, rhsAntennaLookAngles)) {
                return false;
            }
        }
        {
            String lhsAntennaPointingAid;
            lhsAntennaPointingAid = this.getAntennaPointingAid();
            String rhsAntennaPointingAid;
            rhsAntennaPointingAid = that.getAntennaPointingAid();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "antennaPointingAid", lhsAntennaPointingAid), LocatorUtils.property(thatLocator, "antennaPointingAid", rhsAntennaPointingAid), lhsAntennaPointingAid, rhsAntennaPointingAid)) {
                return false;
            }
        }
        {
            String lhsGatewayId;
            lhsGatewayId = this.getGatewayId();
            String rhsGatewayId;
            rhsGatewayId = that.getGatewayId();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "gatewayId", lhsGatewayId), LocatorUtils.property(thatLocator, "gatewayId", rhsGatewayId), lhsGatewayId, rhsGatewayId)) {
                return false;
            }
        }
        {
            String lhsGatewayName;
            lhsGatewayName = this.getGatewayName();
            String rhsGatewayName;
            rhsGatewayName = that.getGatewayName();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "gatewayName", lhsGatewayName), LocatorUtils.property(thatLocator, "gatewayName", rhsGatewayName), lhsGatewayName, rhsGatewayName)) {
                return false;
            }
        }
        {
            String lhsModemInstallCode;
            lhsModemInstallCode = this.getModemInstallCode();
            String rhsModemInstallCode;
            rhsModemInstallCode = that.getModemInstallCode();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "modemInstallCode", lhsModemInstallCode), LocatorUtils.property(thatLocator, "modemInstallCode", rhsModemInstallCode), lhsModemInstallCode, rhsModemInstallCode)) {
                return false;
            }
        }
        {
            String lhsCustomerCode;
            lhsCustomerCode = this.getCustomerCode();
            String rhsCustomerCode;
            rhsCustomerCode = that.getCustomerCode();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "customerCode", lhsCustomerCode), LocatorUtils.property(thatLocator, "customerCode", rhsCustomerCode), lhsCustomerCode, rhsCustomerCode)) {
                return false;
            }
        }
        {
            EquipmentType lhsEquipmentType;
            lhsEquipmentType = this.getEquipmentType();
            EquipmentType rhsEquipmentType;
            rhsEquipmentType = that.getEquipmentType();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "equipmentType", lhsEquipmentType), LocatorUtils.property(thatLocator, "equipmentType", rhsEquipmentType), lhsEquipmentType, rhsEquipmentType)) {
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
            BigInteger theBeamNumber;
            theBeamNumber = this.getBeamNumber();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "beamNumber", theBeamNumber), currentHashCode, theBeamNumber);
        }
        {
            String theSatelliteName;
            theSatelliteName = this.getSatelliteName();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "satelliteName", theSatelliteName), currentHashCode, theSatelliteName);
        }
        {
            String thePolarization;
            thePolarization = this.getPolarization();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "polarization", thePolarization), currentHashCode, thePolarization);
        }
        {
            AntennaLookAngles theAntennaLookAngles;
            theAntennaLookAngles = this.getAntennaLookAngles();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "antennaLookAngles", theAntennaLookAngles), currentHashCode, theAntennaLookAngles);
        }
        {
            String theAntennaPointingAid;
            theAntennaPointingAid = this.getAntennaPointingAid();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "antennaPointingAid", theAntennaPointingAid), currentHashCode, theAntennaPointingAid);
        }
        {
            String theGatewayId;
            theGatewayId = this.getGatewayId();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "gatewayId", theGatewayId), currentHashCode, theGatewayId);
        }
        {
            String theGatewayName;
            theGatewayName = this.getGatewayName();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "gatewayName", theGatewayName), currentHashCode, theGatewayName);
        }
        {
            String theModemInstallCode;
            theModemInstallCode = this.getModemInstallCode();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "modemInstallCode", theModemInstallCode), currentHashCode, theModemInstallCode);
        }
        {
            String theCustomerCode;
            theCustomerCode = this.getCustomerCode();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "customerCode", theCustomerCode), currentHashCode, theCustomerCode);
        }
        {
            EquipmentType theEquipmentType;
            theEquipmentType = this.getEquipmentType();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "equipmentType", theEquipmentType), currentHashCode, theEquipmentType);
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
        if (draftCopy instanceof BeamTechnicalInfo) {
            final BeamTechnicalInfo copy = ((BeamTechnicalInfo) draftCopy);
            if (this.beamNumber!= null) {
                BigInteger sourceBeamNumber;
                sourceBeamNumber = this.getBeamNumber();
                BigInteger copyBeamNumber = ((BigInteger) strategy.copy(LocatorUtils.property(locator, "beamNumber", sourceBeamNumber), sourceBeamNumber));
                copy.setBeamNumber(copyBeamNumber);
            } else {
                copy.beamNumber = null;
            }
            if (this.satelliteName!= null) {
                String sourceSatelliteName;
                sourceSatelliteName = this.getSatelliteName();
                String copySatelliteName = ((String) strategy.copy(LocatorUtils.property(locator, "satelliteName", sourceSatelliteName), sourceSatelliteName));
                copy.setSatelliteName(copySatelliteName);
            } else {
                copy.satelliteName = null;
            }
            if (this.polarization!= null) {
                String sourcePolarization;
                sourcePolarization = this.getPolarization();
                String copyPolarization = ((String) strategy.copy(LocatorUtils.property(locator, "polarization", sourcePolarization), sourcePolarization));
                copy.setPolarization(copyPolarization);
            } else {
                copy.polarization = null;
            }
            if (this.antennaLookAngles!= null) {
                AntennaLookAngles sourceAntennaLookAngles;
                sourceAntennaLookAngles = this.getAntennaLookAngles();
                AntennaLookAngles copyAntennaLookAngles = ((AntennaLookAngles) strategy.copy(LocatorUtils.property(locator, "antennaLookAngles", sourceAntennaLookAngles), sourceAntennaLookAngles));
                copy.setAntennaLookAngles(copyAntennaLookAngles);
            } else {
                copy.antennaLookAngles = null;
            }
            if (this.antennaPointingAid!= null) {
                String sourceAntennaPointingAid;
                sourceAntennaPointingAid = this.getAntennaPointingAid();
                String copyAntennaPointingAid = ((String) strategy.copy(LocatorUtils.property(locator, "antennaPointingAid", sourceAntennaPointingAid), sourceAntennaPointingAid));
                copy.setAntennaPointingAid(copyAntennaPointingAid);
            } else {
                copy.antennaPointingAid = null;
            }
            if (this.gatewayId!= null) {
                String sourceGatewayId;
                sourceGatewayId = this.getGatewayId();
                String copyGatewayId = ((String) strategy.copy(LocatorUtils.property(locator, "gatewayId", sourceGatewayId), sourceGatewayId));
                copy.setGatewayId(copyGatewayId);
            } else {
                copy.gatewayId = null;
            }
            if (this.gatewayName!= null) {
                String sourceGatewayName;
                sourceGatewayName = this.getGatewayName();
                String copyGatewayName = ((String) strategy.copy(LocatorUtils.property(locator, "gatewayName", sourceGatewayName), sourceGatewayName));
                copy.setGatewayName(copyGatewayName);
            } else {
                copy.gatewayName = null;
            }
            if (this.modemInstallCode!= null) {
                String sourceModemInstallCode;
                sourceModemInstallCode = this.getModemInstallCode();
                String copyModemInstallCode = ((String) strategy.copy(LocatorUtils.property(locator, "modemInstallCode", sourceModemInstallCode), sourceModemInstallCode));
                copy.setModemInstallCode(copyModemInstallCode);
            } else {
                copy.modemInstallCode = null;
            }
            if (this.customerCode!= null) {
                String sourceCustomerCode;
                sourceCustomerCode = this.getCustomerCode();
                String copyCustomerCode = ((String) strategy.copy(LocatorUtils.property(locator, "customerCode", sourceCustomerCode), sourceCustomerCode));
                copy.setCustomerCode(copyCustomerCode);
            } else {
                copy.customerCode = null;
            }
            if (this.equipmentType!= null) {
                EquipmentType sourceEquipmentType;
                sourceEquipmentType = this.getEquipmentType();
                EquipmentType copyEquipmentType = ((EquipmentType) strategy.copy(LocatorUtils.property(locator, "equipmentType", sourceEquipmentType), sourceEquipmentType));
                copy.setEquipmentType(copyEquipmentType);
            } else {
                copy.equipmentType = null;
            }
        }
        return draftCopy;
    }

    public Object createNewInstance() {
        return new BeamTechnicalInfo();
    }

}
