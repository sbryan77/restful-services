//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.01.13 at 02:35:26 PM MST 
//


package com.viasat.common.fault;

import java.util.ArrayList;
import java.util.List;
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
 * <p>Java class for ValidationError complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ValidationError">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="validationCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="input" type="{http://www.viasat.com/XMLSchema/v1/Fault}Input" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ValidationError", propOrder = {
    "validationCode",
    "input"
})
public class ValidationError
    implements Cloneable, CopyTo, Equals, HashCode, ToString
{

    @XmlElement(required = true)
    protected String validationCode;
    @XmlElement(required = true)
    protected List<Input> input;

    /**
     * Gets the value of the validationCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValidationCode() {
        return validationCode;
    }

    /**
     * Sets the value of the validationCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValidationCode(String value) {
        this.validationCode = value;
    }

    /**
     * Gets the value of the input property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the input property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInput().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Input }
     * 
     * 
     */
    public List<Input> getInput() {
        if (input == null) {
            input = new ArrayList<Input>();
        }
        return this.input;
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
            String theValidationCode;
            theValidationCode = this.getValidationCode();
            strategy.appendField(locator, this, "validationCode", buffer, theValidationCode);
        }
        {
            List<Input> theInput;
            theInput = this.getInput();
            strategy.appendField(locator, this, "input", buffer, theInput);
        }
        return buffer;
    }

    public boolean equals(ObjectLocator thisLocator, ObjectLocator thatLocator, Object object, EqualsStrategy strategy) {
        if (!(object instanceof ValidationError)) {
            return false;
        }
        if (this == object) {
            return true;
        }
        final ValidationError that = ((ValidationError) object);
        {
            String lhsValidationCode;
            lhsValidationCode = this.getValidationCode();
            String rhsValidationCode;
            rhsValidationCode = that.getValidationCode();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "validationCode", lhsValidationCode), LocatorUtils.property(thatLocator, "validationCode", rhsValidationCode), lhsValidationCode, rhsValidationCode)) {
                return false;
            }
        }
        {
            List<Input> lhsInput;
            lhsInput = this.getInput();
            List<Input> rhsInput;
            rhsInput = that.getInput();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "input", lhsInput), LocatorUtils.property(thatLocator, "input", rhsInput), lhsInput, rhsInput)) {
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
            String theValidationCode;
            theValidationCode = this.getValidationCode();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "validationCode", theValidationCode), currentHashCode, theValidationCode);
        }
        {
            List<Input> theInput;
            theInput = this.getInput();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "input", theInput), currentHashCode, theInput);
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
        if (draftCopy instanceof ValidationError) {
            final ValidationError copy = ((ValidationError) draftCopy);
            if (this.validationCode!= null) {
                String sourceValidationCode;
                sourceValidationCode = this.getValidationCode();
                String copyValidationCode = ((String) strategy.copy(LocatorUtils.property(locator, "validationCode", sourceValidationCode), sourceValidationCode));
                copy.setValidationCode(copyValidationCode);
            } else {
                copy.validationCode = null;
            }
            if ((this.input!= null)&&(!this.input.isEmpty())) {
                List<Input> sourceInput;
                sourceInput = this.getInput();
                @SuppressWarnings("unchecked")
                List<Input> copyInput = ((List<Input> ) strategy.copy(LocatorUtils.property(locator, "input", sourceInput), sourceInput));
                copy.input = null;
                List<Input> uniqueInputl = copy.getInput();
                uniqueInputl.addAll(copyInput);
            } else {
                copy.input = null;
            }
        }
        return draftCopy;
    }

    public Object createNewInstance() {
        return new ValidationError();
    }

}
