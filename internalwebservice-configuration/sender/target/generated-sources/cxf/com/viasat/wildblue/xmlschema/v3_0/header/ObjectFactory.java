
package com.viasat.wildblue.xmlschema.v3_0.header;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.viasat.wildblue.xmlschema.v3_0.header package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _WildBlueHeader_QNAME = new QName("http://www.wildblue.viasat.com/XMLSchema/v3.0/Header", "wildBlueHeader");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.viasat.wildblue.xmlschema.v3_0.header
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link WildBlueHeader }
     * 
     */
    public WildBlueHeader createWildBlueHeader() {
        return new WildBlueHeader();
    }

    /**
     * Create an instance of {@link InvokedBy }
     * 
     */
    public InvokedBy createInvokedBy() {
        return new InvokedBy();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WildBlueHeader }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/Header", name = "wildBlueHeader")
    public JAXBElement<WildBlueHeader> createWildBlueHeader(WildBlueHeader value) {
        return new JAXBElement<WildBlueHeader>(_WildBlueHeader_QNAME, WildBlueHeader.class, null, value);
    }

}
