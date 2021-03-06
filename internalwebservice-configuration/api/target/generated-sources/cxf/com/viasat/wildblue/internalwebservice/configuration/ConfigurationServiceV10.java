package com.viasat.wildblue.internalwebservice.configuration;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.1.7
 * 2019-01-13T15:08:31.667-07:00
 * Generated source version: 3.1.7
 * 
 */
@WebService(targetNamespace = "http://www.wildblue.viasat.com/WSDL/v1.0/ConfigurationWebService", name = "ConfigurationService_v1.0")
@XmlSeeAlso({com.viasat.wildblue.common.header.ObjectFactory.class, com.viasat.wildblue.common.exception.ObjectFactory.class, com.viasat.wildblue.internalwebservice.configuration.data.ObjectFactory.class})
public interface ConfigurationServiceV10 {

    @WebMethod
    @RequestWrapper(localName = "getConfigurationItemInt", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", className = "com.viasat.wildblue.internalwebservice.configuration.data.GetConfigurationItem")
    @ResponseWrapper(localName = "getConfigurationItemIntResponse", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", className = "com.viasat.wildblue.internalwebservice.configuration.data.GetConfigurationItemIntResponse")
    @WebResult(name = "value", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
    public java.lang.Integer getConfigurationItemInt(
        @WebParam(name = "documentName", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
        java.lang.String documentName,
        @WebParam(name = "key", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
        java.lang.String key,
        @WebParam(name = "wildBlueHeader", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/Header", header = true)
        com.viasat.wildblue.common.header.WildBlueHeader wildBlueHeader
    ) throws com.viasat.wildblue.common.exception.WebServiceException;

    @WebMethod
    @RequestWrapper(localName = "getCompleteConfiguration", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", className = "com.viasat.wildblue.internalwebservice.configuration.data.GetCompleteConfiguration")
    @ResponseWrapper(localName = "getCompleteConfigurationResponse", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", className = "com.viasat.wildblue.internalwebservice.configuration.data.GetCompleteConfigurationResponse")
    @WebResult(name = "configuration", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
    public java.util.List<com.viasat.wildblue.internalwebservice.configuration.data.ConfigurationItem> getCompleteConfiguration(
        @WebParam(name = "documentName", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
        java.lang.String documentName,
        @WebParam(name = "wildBlueHeader", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/Header", header = true)
        com.viasat.wildblue.common.header.WildBlueHeader wildBlueHeader
    ) throws com.viasat.wildblue.common.exception.WebServiceException;

    @WebMethod
    @RequestWrapper(localName = "replaceConfigurationDocument", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", className = "com.viasat.wildblue.internalwebservice.configuration.data.ReplaceConfigurationDocument")
    @ResponseWrapper(localName = "replaceConfigurationDocumentResponse", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", className = "com.viasat.wildblue.internalwebservice.configuration.data.ReplaceConfigurationDocumentResponse")
    public void replaceConfigurationDocument(
        @WebParam(name = "documentName", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
        java.lang.String documentName,
        @WebParam(name = "xml", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
        java.lang.String xml,
        @WebParam(name = "wildBlueHeader", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/Header", header = true)
        com.viasat.wildblue.common.header.WildBlueHeader wildBlueHeader
    ) throws com.viasat.wildblue.common.exception.WebServiceException;

    @WebMethod
    @RequestWrapper(localName = "getConfigurationItems", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", className = "com.viasat.wildblue.internalwebservice.configuration.data.GetConfigurationItems")
    @ResponseWrapper(localName = "getConfigurationItemsResponse", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", className = "com.viasat.wildblue.internalwebservice.configuration.data.ConfigurationList")
    @WebResult(name = "configuration", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
    public java.util.List<com.viasat.wildblue.internalwebservice.configuration.data.ConfigurationItem> getConfigurationItems(
        @WebParam(name = "documentName", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
        java.lang.String documentName,
        @WebParam(name = "key", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
        java.util.List<java.lang.String> key,
        @WebParam(name = "wildBlueHeader", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/Header", header = true)
        com.viasat.wildblue.common.header.WildBlueHeader wildBlueHeader
    ) throws com.viasat.wildblue.common.exception.WebServiceException;

    @WebMethod
    @RequestWrapper(localName = "getConfigurationItemDoubleDefault", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", className = "com.viasat.wildblue.internalwebservice.configuration.data.GetConfigurationItemDoubleDefault")
    @ResponseWrapper(localName = "getConfigurationItemDoubleResponse", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", className = "com.viasat.wildblue.internalwebservice.configuration.data.GetConfigurationItemDoubleResponse")
    @WebResult(name = "value", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
    public java.lang.Double getConfigurationItemDoubleDefault(
        @WebParam(name = "documentName", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
        java.lang.String documentName,
        @WebParam(name = "key", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
        java.lang.String key,
        @WebParam(name = "defaultDouble", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
        java.lang.Double defaultDouble,
        @WebParam(name = "wildBlueHeader", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/Header", header = true)
        com.viasat.wildblue.common.header.WildBlueHeader wildBlueHeader
    ) throws com.viasat.wildblue.common.exception.WebServiceException;

    @WebMethod
    @RequestWrapper(localName = "getConfigurationDocument", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", className = "com.viasat.wildblue.internalwebservice.configuration.data.GetConfigurationDocument")
    @ResponseWrapper(localName = "getConfigurationDocumentResponse", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", className = "com.viasat.wildblue.internalwebservice.configuration.data.GetConfigurationDocumentResponse")
    @WebResult(name = "configurationDocument", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
    public java.lang.String getConfigurationDocument(
        @WebParam(name = "documentName", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
        java.lang.String documentName,
        @WebParam(name = "wildBlueHeader", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/Header", header = true)
        com.viasat.wildblue.common.header.WildBlueHeader wildBlueHeader
    ) throws com.viasat.wildblue.common.exception.WebServiceException;

    @WebMethod
    @RequestWrapper(localName = "getDocumentNames", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", className = "com.viasat.wildblue.internalwebservice.configuration.data.GetDocumentNames")
    @ResponseWrapper(localName = "getDocumentNamesResponse", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", className = "com.viasat.wildblue.internalwebservice.configuration.data.GetDocumentNamesResponse")
    @WebResult(name = "documentName", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
    public java.util.List<java.lang.String> getDocumentNames(
        @WebParam(name = "wildBlueHeader", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/Header", header = true)
        com.viasat.wildblue.common.header.WildBlueHeader wildBlueHeader
    ) throws com.viasat.wildblue.common.exception.WebServiceException;

    @WebMethod
    @RequestWrapper(localName = "deleteConfigurationDocument", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", className = "com.viasat.wildblue.internalwebservice.configuration.data.DeleteConfigurationDocument")
    @ResponseWrapper(localName = "deleteConfigurationDocumentResponse", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", className = "com.viasat.wildblue.internalwebservice.configuration.data.DeleteConfigurationDocumentResponse")
    public void deleteConfigurationDocument(
        @WebParam(name = "documentName", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
        java.lang.String documentName,
        @WebParam(name = "wildBlueHeader", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/Header", header = true)
        com.viasat.wildblue.common.header.WildBlueHeader wildBlueHeader
    ) throws com.viasat.wildblue.common.exception.WebServiceException;

    @WebMethod
    @RequestWrapper(localName = "getConfigurationItemList", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", className = "com.viasat.wildblue.internalwebservice.configuration.data.GetConfigurationItem")
    @ResponseWrapper(localName = "getConfigurationItemListResponse", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", className = "com.viasat.wildblue.internalwebservice.configuration.data.ConfigurationList")
    @WebResult(name = "configuration", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
    public java.util.List<com.viasat.wildblue.internalwebservice.configuration.data.ConfigurationItem> getConfigurationItemList(
        @WebParam(name = "documentName", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
        java.lang.String documentName,
        @WebParam(name = "key", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
        java.lang.String key,
        @WebParam(name = "wildBlueHeader", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/Header", header = true)
        com.viasat.wildblue.common.header.WildBlueHeader wildBlueHeader
    ) throws com.viasat.wildblue.common.exception.WebServiceException;

    @WebMethod
    @RequestWrapper(localName = "getConfigurationItemDefault", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", className = "com.viasat.wildblue.internalwebservice.configuration.data.GetConfigurationItemDefault")
    @ResponseWrapper(localName = "getConfigurationItemResponse", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", className = "com.viasat.wildblue.internalwebservice.configuration.data.GetConfigurationItemResponse")
    @WebResult(name = "value", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
    public java.lang.String getConfigurationItemDefault(
        @WebParam(name = "documentName", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
        java.lang.String documentName,
        @WebParam(name = "key", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
        java.lang.String key,
        @WebParam(name = "defaultString", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
        java.lang.String defaultString,
        @WebParam(name = "wildBlueHeader", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/Header", header = true)
        com.viasat.wildblue.common.header.WildBlueHeader wildBlueHeader
    ) throws com.viasat.wildblue.common.exception.WebServiceException;

    @WebMethod
    @RequestWrapper(localName = "getConfigurationItemDouble", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", className = "com.viasat.wildblue.internalwebservice.configuration.data.GetConfigurationItem")
    @ResponseWrapper(localName = "getConfigurationItemDoubleResponse", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", className = "com.viasat.wildblue.internalwebservice.configuration.data.GetConfigurationItemDoubleResponse")
    @WebResult(name = "value", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
    public java.lang.Double getConfigurationItemDouble(
        @WebParam(name = "documentName", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
        java.lang.String documentName,
        @WebParam(name = "key", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
        java.lang.String key,
        @WebParam(name = "wildBlueHeader", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/Header", header = true)
        com.viasat.wildblue.common.header.WildBlueHeader wildBlueHeader
    ) throws com.viasat.wildblue.common.exception.WebServiceException;

    @WebMethod
    @RequestWrapper(localName = "getConfigurationItemIntDefault", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", className = "com.viasat.wildblue.internalwebservice.configuration.data.GetConfigurationItemIntDefault")
    @ResponseWrapper(localName = "getConfigurationItemIntResponse", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", className = "com.viasat.wildblue.internalwebservice.configuration.data.GetConfigurationItemIntResponse")
    @WebResult(name = "value", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
    public java.lang.Integer getConfigurationItemIntDefault(
        @WebParam(name = "documentName", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
        java.lang.String documentName,
        @WebParam(name = "key", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
        java.lang.String key,
        @WebParam(name = "defaultInt", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
        java.lang.Integer defaultInt,
        @WebParam(name = "wildBlueHeader", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/Header", header = true)
        com.viasat.wildblue.common.header.WildBlueHeader wildBlueHeader
    ) throws com.viasat.wildblue.common.exception.WebServiceException;

    @WebMethod
    @RequestWrapper(localName = "getConfigurationItem", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", className = "com.viasat.wildblue.internalwebservice.configuration.data.GetConfigurationItem")
    @ResponseWrapper(localName = "getConfigurationItemResponse", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", className = "com.viasat.wildblue.internalwebservice.configuration.data.GetConfigurationItemResponse")
    @WebResult(name = "value", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
    public java.lang.String getConfigurationItem(
        @WebParam(name = "documentName", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
        java.lang.String documentName,
        @WebParam(name = "key", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService")
        java.lang.String key,
        @WebParam(name = "wildBlueHeader", targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/Header", header = true)
        com.viasat.wildblue.common.header.WildBlueHeader wildBlueHeader
    ) throws com.viasat.wildblue.common.exception.WebServiceException;
}
