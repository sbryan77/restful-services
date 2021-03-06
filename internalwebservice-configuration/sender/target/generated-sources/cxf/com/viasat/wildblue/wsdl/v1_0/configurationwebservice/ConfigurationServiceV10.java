package com.viasat.wildblue.wsdl.v1_0.configurationwebservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.1.7
 * 2019-01-13T15:08:42.289-07:00
 * Generated source version: 3.1.7
 * 
 */
@WebService(targetNamespace = "http://www.wildblue.viasat.com/WSDL/v1.0/ConfigurationWebService", name = "ConfigurationService_v1.0")
@XmlSeeAlso({com.viasat.wildblue.xmlschema.v3_0.exception.ObjectFactory.class, com.viasat.wildblue.xmlschema.v3_0.header.ObjectFactory.class, com.viasat.wildblue.xmlschema.v3_0.webservice.configurationwebservice.ObjectFactory.class})
public interface ConfigurationServiceV10 {

    @WebMethod
    @RequestWrapper(localName = "getConfigurationItemInt", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", className = "com.viasat.wildblue.xmlschema.v3_0.webservice.configurationwebservice.GetConfigurationItem")
    @ResponseWrapper(localName = "getConfigurationItemIntResponse", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", className = "com.viasat.wildblue.xmlschema.v3_0.webservice.configurationwebservice.GetConfigurationItemIntResponse")
    @WebResult(name = "value", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
    public java.lang.Integer getConfigurationItemInt(
        @WebParam(name = "documentName", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
        java.lang.String documentName,
        @WebParam(name = "key", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
        java.lang.String key
    ) throws WebServiceException;

    @WebMethod
    @RequestWrapper(localName = "getCompleteConfiguration", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", className = "com.viasat.wildblue.xmlschema.v3_0.webservice.configurationwebservice.GetCompleteConfiguration")
    @ResponseWrapper(localName = "getCompleteConfigurationResponse", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", className = "com.viasat.wildblue.xmlschema.v3_0.webservice.configurationwebservice.GetCompleteConfigurationResponse")
    @WebResult(name = "configuration", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
    public java.util.List<com.viasat.wildblue.xmlschema.v3_0.webservice.configurationwebservice.ConfigurationItem> getCompleteConfiguration(
        @WebParam(name = "documentName", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
        java.lang.String documentName
    ) throws WebServiceException;

    @WebMethod
    @RequestWrapper(localName = "replaceConfigurationDocument", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", className = "com.viasat.wildblue.xmlschema.v3_0.webservice.configurationwebservice.ReplaceConfigurationDocument")
    @ResponseWrapper(localName = "replaceConfigurationDocumentResponse", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", className = "com.viasat.wildblue.xmlschema.v3_0.webservice.configurationwebservice.ReplaceConfigurationDocumentResponse")
    public void replaceConfigurationDocument(
        @WebParam(name = "documentName", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
        java.lang.String documentName,
        @WebParam(name = "xml", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
        java.lang.String xml
    ) throws WebServiceException;

    @WebMethod
    @RequestWrapper(localName = "getConfigurationItems", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", className = "com.viasat.wildblue.xmlschema.v3_0.webservice.configurationwebservice.GetConfigurationItems")
    @ResponseWrapper(localName = "getConfigurationItemsResponse", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", className = "com.viasat.wildblue.xmlschema.v3_0.webservice.configurationwebservice.ConfigurationList")
    @WebResult(name = "configuration", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
    public java.util.List<com.viasat.wildblue.xmlschema.v3_0.webservice.configurationwebservice.ConfigurationItem> getConfigurationItems(
        @WebParam(name = "documentName", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
        java.lang.String documentName,
        @WebParam(name = "key", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
        java.util.List<java.lang.String> key
    ) throws WebServiceException;

    @WebMethod
    @RequestWrapper(localName = "getConfigurationItemDoubleDefault", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", className = "com.viasat.wildblue.xmlschema.v3_0.webservice.configurationwebservice.GetConfigurationItemDoubleDefault")
    @ResponseWrapper(localName = "getConfigurationItemDoubleResponse", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", className = "com.viasat.wildblue.xmlschema.v3_0.webservice.configurationwebservice.GetConfigurationItemDoubleResponse")
    @WebResult(name = "value", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
    public java.lang.Double getConfigurationItemDoubleDefault(
        @WebParam(name = "documentName", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
        java.lang.String documentName,
        @WebParam(name = "key", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
        java.lang.String key,
        @WebParam(name = "defaultDouble", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
        java.lang.Double defaultDouble
    ) throws WebServiceException;

    @WebMethod
    @RequestWrapper(localName = "getConfigurationDocument", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", className = "com.viasat.wildblue.xmlschema.v3_0.webservice.configurationwebservice.GetConfigurationDocument")
    @ResponseWrapper(localName = "getConfigurationDocumentResponse", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", className = "com.viasat.wildblue.xmlschema.v3_0.webservice.configurationwebservice.GetConfigurationDocumentResponse")
    @WebResult(name = "configurationDocument", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
    public java.lang.String getConfigurationDocument(
        @WebParam(name = "documentName", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
        java.lang.String documentName
    ) throws WebServiceException;

    @WebMethod
    @RequestWrapper(localName = "getDocumentNames", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", className = "com.viasat.wildblue.xmlschema.v3_0.webservice.configurationwebservice.GetDocumentNames")
    @ResponseWrapper(localName = "getDocumentNamesResponse", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", className = "com.viasat.wildblue.xmlschema.v3_0.webservice.configurationwebservice.GetDocumentNamesResponse")
    @WebResult(name = "documentName", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
    public java.util.List<java.lang.String> getDocumentNames() throws WebServiceException;

    @WebMethod
    @RequestWrapper(localName = "deleteConfigurationDocument", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", className = "com.viasat.wildblue.xmlschema.v3_0.webservice.configurationwebservice.DeleteConfigurationDocument")
    @ResponseWrapper(localName = "deleteConfigurationDocumentResponse", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", className = "com.viasat.wildblue.xmlschema.v3_0.webservice.configurationwebservice.DeleteConfigurationDocumentResponse")
    public void deleteConfigurationDocument(
        @WebParam(name = "documentName", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
        java.lang.String documentName
    ) throws WebServiceException;

    @WebMethod
    @RequestWrapper(localName = "getConfigurationItemList", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", className = "com.viasat.wildblue.xmlschema.v3_0.webservice.configurationwebservice.GetConfigurationItem")
    @ResponseWrapper(localName = "getConfigurationItemListResponse", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", className = "com.viasat.wildblue.xmlschema.v3_0.webservice.configurationwebservice.ConfigurationList")
    @WebResult(name = "configuration", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
    public java.util.List<com.viasat.wildblue.xmlschema.v3_0.webservice.configurationwebservice.ConfigurationItem> getConfigurationItemList(
        @WebParam(name = "documentName", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
        java.lang.String documentName,
        @WebParam(name = "key", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
        java.lang.String key
    ) throws WebServiceException;

    @WebMethod
    @RequestWrapper(localName = "getConfigurationItemDefault", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", className = "com.viasat.wildblue.xmlschema.v3_0.webservice.configurationwebservice.GetConfigurationItemDefault")
    @ResponseWrapper(localName = "getConfigurationItemResponse", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", className = "com.viasat.wildblue.xmlschema.v3_0.webservice.configurationwebservice.GetConfigurationItemResponse")
    @WebResult(name = "value", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
    public java.lang.String getConfigurationItemDefault(
        @WebParam(name = "documentName", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
        java.lang.String documentName,
        @WebParam(name = "key", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
        java.lang.String key,
        @WebParam(name = "defaultString", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
        java.lang.String defaultString
    ) throws WebServiceException;

    @WebMethod
    @RequestWrapper(localName = "getConfigurationItemDouble", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", className = "com.viasat.wildblue.xmlschema.v3_0.webservice.configurationwebservice.GetConfigurationItem")
    @ResponseWrapper(localName = "getConfigurationItemDoubleResponse", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", className = "com.viasat.wildblue.xmlschema.v3_0.webservice.configurationwebservice.GetConfigurationItemDoubleResponse")
    @WebResult(name = "value", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
    public java.lang.Double getConfigurationItemDouble(
        @WebParam(name = "documentName", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
        java.lang.String documentName,
        @WebParam(name = "key", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
        java.lang.String key
    ) throws WebServiceException;

    @WebMethod
    @RequestWrapper(localName = "getConfigurationItemIntDefault", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", className = "com.viasat.wildblue.xmlschema.v3_0.webservice.configurationwebservice.GetConfigurationItemIntDefault")
    @ResponseWrapper(localName = "getConfigurationItemIntResponse", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", className = "com.viasat.wildblue.xmlschema.v3_0.webservice.configurationwebservice.GetConfigurationItemIntResponse")
    @WebResult(name = "value", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
    public java.lang.Integer getConfigurationItemIntDefault(
        @WebParam(name = "documentName", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
        java.lang.String documentName,
        @WebParam(name = "key", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
        java.lang.String key,
        @WebParam(name = "defaultInt", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
        java.lang.Integer defaultInt
    ) throws WebServiceException;

    @WebMethod
    @RequestWrapper(localName = "getConfigurationItem", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", className = "com.viasat.wildblue.xmlschema.v3_0.webservice.configurationwebservice.GetConfigurationItem")
    @ResponseWrapper(localName = "getConfigurationItemResponse", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", className = "com.viasat.wildblue.xmlschema.v3_0.webservice.configurationwebservice.GetConfigurationItemResponse")
    @WebResult(name = "value", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
    public java.lang.String getConfigurationItem(
        @WebParam(name = "documentName", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
        java.lang.String documentName,
        @WebParam(name = "key", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
        java.lang.String key
    ) throws WebServiceException;
}
