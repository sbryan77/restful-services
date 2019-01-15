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
import javax.xml.bind.annotation.XmlSeeAlso;
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
 * <p>Java class for Contact complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Contact">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="person" type="{http://www.wildblue.viasat.com/XMLSchema/v3.0/CommonData}Person"/>
 *         &lt;element name="contactInfo" type="{http://www.wildblue.viasat.com/XMLSchema/v3.0/CommonData}ContactInfo"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Contact", propOrder = {
    "person",
    "contactInfo"
})
@XmlSeeAlso({
    ContactWithDateOfBirth.class
})
public class Contact
    implements Cloneable, CopyTo, Equals, HashCode, ToString
{

    @XmlElement(required = true)
    protected Person person;
    @XmlElement(required = true)
    protected ContactInfo contactInfo;

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
            Person thePerson;
            thePerson = this.getPerson();
            strategy.appendField(locator, this, "person", buffer, thePerson);
        }
        {
            ContactInfo theContactInfo;
            theContactInfo = this.getContactInfo();
            strategy.appendField(locator, this, "contactInfo", buffer, theContactInfo);
        }
        return buffer;
    }

    public boolean equals(ObjectLocator thisLocator, ObjectLocator thatLocator, Object object, EqualsStrategy strategy) {
        if (!(object instanceof Contact)) {
            return false;
        }
        if (this == object) {
            return true;
        }
        final Contact that = ((Contact) object);
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
            ContactInfo lhsContactInfo;
            lhsContactInfo = this.getContactInfo();
            ContactInfo rhsContactInfo;
            rhsContactInfo = that.getContactInfo();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "contactInfo", lhsContactInfo), LocatorUtils.property(thatLocator, "contactInfo", rhsContactInfo), lhsContactInfo, rhsContactInfo)) {
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
            Person thePerson;
            thePerson = this.getPerson();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "person", thePerson), currentHashCode, thePerson);
        }
        {
            ContactInfo theContactInfo;
            theContactInfo = this.getContactInfo();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "contactInfo", theContactInfo), currentHashCode, theContactInfo);
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
        if (draftCopy instanceof Contact) {
            final Contact copy = ((Contact) draftCopy);
            if (this.person!= null) {
                Person sourcePerson;
                sourcePerson = this.getPerson();
                Person copyPerson = ((Person) strategy.copy(LocatorUtils.property(locator, "person", sourcePerson), sourcePerson));
                copy.setPerson(copyPerson);
            } else {
                copy.person = null;
            }
            if (this.contactInfo!= null) {
                ContactInfo sourceContactInfo;
                sourceContactInfo = this.getContactInfo();
                ContactInfo copyContactInfo = ((ContactInfo) strategy.copy(LocatorUtils.property(locator, "contactInfo", sourceContactInfo), sourceContactInfo));
                copy.setContactInfo(copyContactInfo);
            } else {
                copy.contactInfo = null;
            }
        }
        return draftCopy;
    }

    public Object createNewInstance() {
        return new Contact();
    }

}
