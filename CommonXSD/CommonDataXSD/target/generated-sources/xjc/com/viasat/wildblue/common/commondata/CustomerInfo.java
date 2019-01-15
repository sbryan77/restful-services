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
 * <p>Java class for CustomerInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CustomerInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="accountNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="accountOID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="contactInfo" type="{http://www.wildblue.viasat.com/XMLSchema/v3.0/CommonData}ContactInfo"/>
 *         &lt;element name="customerType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ipAddress" type="{http://www.wildblue.viasat.com/XMLSchema/v3.0/CommonData}IPAddress" minOccurs="0"/>
 *         &lt;element name="macAddress" type="{http://www.wildblue.viasat.com/XMLSchema/v3.0/CommonData}MACAddress" minOccurs="0"/>
 *         &lt;element name="marketSegment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="person" type="{http://www.wildblue.viasat.com/XMLSchema/v3.0/CommonData}Person"/>
 *         &lt;element name="serialNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ticketNumber" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustomerInfo", propOrder = {
    "accountNumber",
    "accountOID",
    "contactInfo",
    "customerType",
    "ipAddress",
    "macAddress",
    "marketSegment",
    "person",
    "serialNumber",
    "ticketNumber"
})
public class CustomerInfo
    implements Cloneable, CopyTo, Equals, HashCode, ToString
{

    protected String accountNumber;
    protected String accountOID;
    @XmlElement(required = true, nillable = true)
    protected ContactInfo contactInfo;
    protected String customerType;
    protected String ipAddress;
    protected String macAddress;
    protected String marketSegment;
    @XmlElement(required = true, nillable = true)
    protected Person person;
    protected String serialNumber;
    protected Long ticketNumber;

    /**
     * Gets the value of the accountNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * Sets the value of the accountNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountNumber(String value) {
        this.accountNumber = value;
    }

    /**
     * Gets the value of the accountOID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountOID() {
        return accountOID;
    }

    /**
     * Sets the value of the accountOID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountOID(String value) {
        this.accountOID = value;
    }

    /**
     * Gets the value of the contactInfo property.
     * 
     * @return
     *     possible object is
     *     {@link ContactInfo }
     *     
     */
    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    /**
     * Sets the value of the contactInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContactInfo }
     *     
     */
    public void setContactInfo(ContactInfo value) {
        this.contactInfo = value;
    }

    /**
     * Gets the value of the customerType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerType() {
        return customerType;
    }

    /**
     * Sets the value of the customerType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerType(String value) {
        this.customerType = value;
    }

    /**
     * Gets the value of the ipAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * Sets the value of the ipAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIpAddress(String value) {
        this.ipAddress = value;
    }

    /**
     * Gets the value of the macAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMacAddress() {
        return macAddress;
    }

    /**
     * Sets the value of the macAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMacAddress(String value) {
        this.macAddress = value;
    }

    /**
     * Gets the value of the marketSegment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMarketSegment() {
        return marketSegment;
    }

    /**
     * Sets the value of the marketSegment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMarketSegment(String value) {
        this.marketSegment = value;
    }

    /**
     * Gets the value of the person property.
     * 
     * @return
     *     possible object is
     *     {@link Person }
     *     
     */
    public Person getPerson() {
        return person;
    }

    /**
     * Sets the value of the person property.
     * 
     * @param value
     *     allowed object is
     *     {@link Person }
     *     
     */
    public void setPerson(Person value) {
        this.person = value;
    }

    /**
     * Gets the value of the serialNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     * Sets the value of the serialNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSerialNumber(String value) {
        this.serialNumber = value;
    }

    /**
     * Gets the value of the ticketNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getTicketNumber() {
        return ticketNumber;
    }

    /**
     * Sets the value of the ticketNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setTicketNumber(Long value) {
        this.ticketNumber = value;
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
            String theAccountNumber;
            theAccountNumber = this.getAccountNumber();
            strategy.appendField(locator, this, "accountNumber", buffer, theAccountNumber);
        }
        {
            String theAccountOID;
            theAccountOID = this.getAccountOID();
            strategy.appendField(locator, this, "accountOID", buffer, theAccountOID);
        }
        {
            ContactInfo theContactInfo;
            theContactInfo = this.getContactInfo();
            strategy.appendField(locator, this, "contactInfo", buffer, theContactInfo);
        }
        {
            String theCustomerType;
            theCustomerType = this.getCustomerType();
            strategy.appendField(locator, this, "customerType", buffer, theCustomerType);
        }
        {
            String theIpAddress;
            theIpAddress = this.getIpAddress();
            strategy.appendField(locator, this, "ipAddress", buffer, theIpAddress);
        }
        {
            String theMacAddress;
            theMacAddress = this.getMacAddress();
            strategy.appendField(locator, this, "macAddress", buffer, theMacAddress);
        }
        {
            String theMarketSegment;
            theMarketSegment = this.getMarketSegment();
            strategy.appendField(locator, this, "marketSegment", buffer, theMarketSegment);
        }
        {
            Person thePerson;
            thePerson = this.getPerson();
            strategy.appendField(locator, this, "person", buffer, thePerson);
        }
        {
            String theSerialNumber;
            theSerialNumber = this.getSerialNumber();
            strategy.appendField(locator, this, "serialNumber", buffer, theSerialNumber);
        }
        {
            Long theTicketNumber;
            theTicketNumber = this.getTicketNumber();
            strategy.appendField(locator, this, "ticketNumber", buffer, theTicketNumber);
        }
        return buffer;
    }

    public boolean equals(ObjectLocator thisLocator, ObjectLocator thatLocator, Object object, EqualsStrategy strategy) {
        if (!(object instanceof CustomerInfo)) {
            return false;
        }
        if (this == object) {
            return true;
        }
        final CustomerInfo that = ((CustomerInfo) object);
        {
            String lhsAccountNumber;
            lhsAccountNumber = this.getAccountNumber();
            String rhsAccountNumber;
            rhsAccountNumber = that.getAccountNumber();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "accountNumber", lhsAccountNumber), LocatorUtils.property(thatLocator, "accountNumber", rhsAccountNumber), lhsAccountNumber, rhsAccountNumber)) {
                return false;
            }
        }
        {
            String lhsAccountOID;
            lhsAccountOID = this.getAccountOID();
            String rhsAccountOID;
            rhsAccountOID = that.getAccountOID();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "accountOID", lhsAccountOID), LocatorUtils.property(thatLocator, "accountOID", rhsAccountOID), lhsAccountOID, rhsAccountOID)) {
                return false;
            }
        }
        {
            ContactInfo lhsContactInfo;
            lhsContactInfo = this.getContactInfo();
            ContactInfo rhsContactInfo;
            rhsContactInfo = that.getContactInfo();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "contactInfo", lhsContactInfo), LocatorUtils.property(thatLocator, "contactInfo", rhsContactInfo), lhsContactInfo, rhsContactInfo)) {
                return false;
            }
        }
        {
            String lhsCustomerType;
            lhsCustomerType = this.getCustomerType();
            String rhsCustomerType;
            rhsCustomerType = that.getCustomerType();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "customerType", lhsCustomerType), LocatorUtils.property(thatLocator, "customerType", rhsCustomerType), lhsCustomerType, rhsCustomerType)) {
                return false;
            }
        }
        {
            String lhsIpAddress;
            lhsIpAddress = this.getIpAddress();
            String rhsIpAddress;
            rhsIpAddress = that.getIpAddress();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "ipAddress", lhsIpAddress), LocatorUtils.property(thatLocator, "ipAddress", rhsIpAddress), lhsIpAddress, rhsIpAddress)) {
                return false;
            }
        }
        {
            String lhsMacAddress;
            lhsMacAddress = this.getMacAddress();
            String rhsMacAddress;
            rhsMacAddress = that.getMacAddress();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "macAddress", lhsMacAddress), LocatorUtils.property(thatLocator, "macAddress", rhsMacAddress), lhsMacAddress, rhsMacAddress)) {
                return false;
            }
        }
        {
            String lhsMarketSegment;
            lhsMarketSegment = this.getMarketSegment();
            String rhsMarketSegment;
            rhsMarketSegment = that.getMarketSegment();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "marketSegment", lhsMarketSegment), LocatorUtils.property(thatLocator, "marketSegment", rhsMarketSegment), lhsMarketSegment, rhsMarketSegment)) {
                return false;
            }
        }
        {
            Person lhsPerson;
            lhsPerson = this.getPerson();
            Person rhsPerson;
            rhsPerson = that.getPerson();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "person", lhsPerson), LocatorUtils.property(thatLocator, "person", rhsPerson), lhsPerson, rhsPerson)) {
                return false;
            }
        }
        {
            String lhsSerialNumber;
            lhsSerialNumber = this.getSerialNumber();
            String rhsSerialNumber;
            rhsSerialNumber = that.getSerialNumber();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "serialNumber", lhsSerialNumber), LocatorUtils.property(thatLocator, "serialNumber", rhsSerialNumber), lhsSerialNumber, rhsSerialNumber)) {
                return false;
            }
        }
        {
            Long lhsTicketNumber;
            lhsTicketNumber = this.getTicketNumber();
            Long rhsTicketNumber;
            rhsTicketNumber = that.getTicketNumber();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "ticketNumber", lhsTicketNumber), LocatorUtils.property(thatLocator, "ticketNumber", rhsTicketNumber), lhsTicketNumber, rhsTicketNumber)) {
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
            String theAccountNumber;
            theAccountNumber = this.getAccountNumber();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "accountNumber", theAccountNumber), currentHashCode, theAccountNumber);
        }
        {
            String theAccountOID;
            theAccountOID = this.getAccountOID();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "accountOID", theAccountOID), currentHashCode, theAccountOID);
        }
        {
            ContactInfo theContactInfo;
            theContactInfo = this.getContactInfo();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "contactInfo", theContactInfo), currentHashCode, theContactInfo);
        }
        {
            String theCustomerType;
            theCustomerType = this.getCustomerType();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "customerType", theCustomerType), currentHashCode, theCustomerType);
        }
        {
            String theIpAddress;
            theIpAddress = this.getIpAddress();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "ipAddress", theIpAddress), currentHashCode, theIpAddress);
        }
        {
            String theMacAddress;
            theMacAddress = this.getMacAddress();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "macAddress", theMacAddress), currentHashCode, theMacAddress);
        }
        {
            String theMarketSegment;
            theMarketSegment = this.getMarketSegment();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "marketSegment", theMarketSegment), currentHashCode, theMarketSegment);
        }
        {
            Person thePerson;
            thePerson = this.getPerson();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "person", thePerson), currentHashCode, thePerson);
        }
        {
            String theSerialNumber;
            theSerialNumber = this.getSerialNumber();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "serialNumber", theSerialNumber), currentHashCode, theSerialNumber);
        }
        {
            Long theTicketNumber;
            theTicketNumber = this.getTicketNumber();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "ticketNumber", theTicketNumber), currentHashCode, theTicketNumber);
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
        if (draftCopy instanceof CustomerInfo) {
            final CustomerInfo copy = ((CustomerInfo) draftCopy);
            if (this.accountNumber!= null) {
                String sourceAccountNumber;
                sourceAccountNumber = this.getAccountNumber();
                String copyAccountNumber = ((String) strategy.copy(LocatorUtils.property(locator, "accountNumber", sourceAccountNumber), sourceAccountNumber));
                copy.setAccountNumber(copyAccountNumber);
            } else {
                copy.accountNumber = null;
            }
            if (this.accountOID!= null) {
                String sourceAccountOID;
                sourceAccountOID = this.getAccountOID();
                String copyAccountOID = ((String) strategy.copy(LocatorUtils.property(locator, "accountOID", sourceAccountOID), sourceAccountOID));
                copy.setAccountOID(copyAccountOID);
            } else {
                copy.accountOID = null;
            }
            if (this.contactInfo!= null) {
                ContactInfo sourceContactInfo;
                sourceContactInfo = this.getContactInfo();
                ContactInfo copyContactInfo = ((ContactInfo) strategy.copy(LocatorUtils.property(locator, "contactInfo", sourceContactInfo), sourceContactInfo));
                copy.setContactInfo(copyContactInfo);
            } else {
                copy.contactInfo = null;
            }
            if (this.customerType!= null) {
                String sourceCustomerType;
                sourceCustomerType = this.getCustomerType();
                String copyCustomerType = ((String) strategy.copy(LocatorUtils.property(locator, "customerType", sourceCustomerType), sourceCustomerType));
                copy.setCustomerType(copyCustomerType);
            } else {
                copy.customerType = null;
            }
            if (this.ipAddress!= null) {
                String sourceIpAddress;
                sourceIpAddress = this.getIpAddress();
                String copyIpAddress = ((String) strategy.copy(LocatorUtils.property(locator, "ipAddress", sourceIpAddress), sourceIpAddress));
                copy.setIpAddress(copyIpAddress);
            } else {
                copy.ipAddress = null;
            }
            if (this.macAddress!= null) {
                String sourceMacAddress;
                sourceMacAddress = this.getMacAddress();
                String copyMacAddress = ((String) strategy.copy(LocatorUtils.property(locator, "macAddress", sourceMacAddress), sourceMacAddress));
                copy.setMacAddress(copyMacAddress);
            } else {
                copy.macAddress = null;
            }
            if (this.marketSegment!= null) {
                String sourceMarketSegment;
                sourceMarketSegment = this.getMarketSegment();
                String copyMarketSegment = ((String) strategy.copy(LocatorUtils.property(locator, "marketSegment", sourceMarketSegment), sourceMarketSegment));
                copy.setMarketSegment(copyMarketSegment);
            } else {
                copy.marketSegment = null;
            }
            if (this.person!= null) {
                Person sourcePerson;
                sourcePerson = this.getPerson();
                Person copyPerson = ((Person) strategy.copy(LocatorUtils.property(locator, "person", sourcePerson), sourcePerson));
                copy.setPerson(copyPerson);
            } else {
                copy.person = null;
            }
            if (this.serialNumber!= null) {
                String sourceSerialNumber;
                sourceSerialNumber = this.getSerialNumber();
                String copySerialNumber = ((String) strategy.copy(LocatorUtils.property(locator, "serialNumber", sourceSerialNumber), sourceSerialNumber));
                copy.setSerialNumber(copySerialNumber);
            } else {
                copy.serialNumber = null;
            }
            if (this.ticketNumber!= null) {
                Long sourceTicketNumber;
                sourceTicketNumber = this.getTicketNumber();
                Long copyTicketNumber = ((Long) strategy.copy(LocatorUtils.property(locator, "ticketNumber", sourceTicketNumber), sourceTicketNumber));
                copy.setTicketNumber(copyTicketNumber);
            } else {
                copy.ticketNumber = null;
            }
        }
        return draftCopy;
    }

    public Object createNewInstance() {
        return new CustomerInfo();
    }

}
