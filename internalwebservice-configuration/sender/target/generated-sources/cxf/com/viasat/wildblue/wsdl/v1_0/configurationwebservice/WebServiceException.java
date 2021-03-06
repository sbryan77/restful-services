
package com.viasat.wildblue.wsdl.v1_0.configurationwebservice;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 3.1.7
 * 2019-01-13T15:08:42.273-07:00
 * Generated source version: 3.1.7
 */

@WebFault(name = "ExceptionDetail", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/Exception")
public class WebServiceException extends Exception {
    
    private com.viasat.wildblue.xmlschema.v3_0.exception.ExceptionDetail exceptionDetail;

    public WebServiceException() {
        super();
    }
    
    public WebServiceException(String message) {
        super(message);
    }
    
    public WebServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public WebServiceException(String message, com.viasat.wildblue.xmlschema.v3_0.exception.ExceptionDetail exceptionDetail) {
        super(message);
        this.exceptionDetail = exceptionDetail;
    }

    public WebServiceException(String message, com.viasat.wildblue.xmlschema.v3_0.exception.ExceptionDetail exceptionDetail, Throwable cause) {
        super(message, cause);
        this.exceptionDetail = exceptionDetail;
    }

    public com.viasat.wildblue.xmlschema.v3_0.exception.ExceptionDetail getFaultInfo() {
        return this.exceptionDetail;
    }
}
