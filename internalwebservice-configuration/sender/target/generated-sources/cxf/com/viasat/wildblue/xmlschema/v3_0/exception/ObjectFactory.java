
package com.viasat.wildblue.xmlschema.v3_0.exception;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.viasat.wildblue.xmlschema.v3_0.exception package. 
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

    private final static QName _ExceptionDetail_QNAME = new QName("http://www.wildblue.viasat.com/XMLSchema/v3.0/Exception", "ExceptionDetail");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.viasat.wildblue.xmlschema.v3_0.exception
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ExceptionDetail }
     * 
     */
    public ExceptionDetail createExceptionDetail() {
        return new ExceptionDetail();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExceptionDetail }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/Exception", name = "ExceptionDetail")
    public JAXBElement<ExceptionDetail> createExceptionDetail(ExceptionDetail value) {
        return new JAXBElement<ExceptionDetail>(_ExceptionDetail_QNAME, ExceptionDetail.class, null, value);
    }

}
