
package com.viasat.wildblue.internalwebservice.configuration.data;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.viasat.wildblue.internalwebservice.configuration.data package. 
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

    private final static QName _GetCompleteConfiguration_QNAME = new QName("http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", "getCompleteConfiguration");
    private final static QName _GetConfigurationItemDouble_QNAME = new QName("http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", "getConfigurationItemDouble");
    private final static QName _GetConfigurationItemInt_QNAME = new QName("http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", "getConfigurationItemInt");
    private final static QName _GetConfigurationItem_QNAME = new QName("http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", "getConfigurationItem");
    private final static QName _GetConfigurationItemList_QNAME = new QName("http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", "getConfigurationItemList");
    private final static QName _GetConfigurationItemDefault_QNAME = new QName("http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", "getConfigurationItemDefault");
    private final static QName _GetConfigurationItemDoubleDefault_QNAME = new QName("http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", "getConfigurationItemDoubleDefault");
    private final static QName _GetConfigurationItemIntDefault_QNAME = new QName("http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", "getConfigurationItemIntDefault");
    private final static QName _GetConfigurationItems_QNAME = new QName("http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", "getConfigurationItems");
    private final static QName _GetConfigurationItemsResponse_QNAME = new QName("http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", "getConfigurationItemsResponse");
    private final static QName _GetConfigurationItemListResponse_QNAME = new QName("http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", "getConfigurationItemListResponse");
    private final static QName _ReplaceConfigurationDocument_QNAME = new QName("http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", "replaceConfigurationDocument");
    private final static QName _DeleteConfigurationDocument_QNAME = new QName("http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", "deleteConfigurationDocument");
    private final static QName _GetDocumentNames_QNAME = new QName("http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", "getDocumentNames");
    private final static QName _GetConfigurationDocument_QNAME = new QName("http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", "getConfigurationDocument");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.viasat.wildblue.internalwebservice.configuration.data
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetConfigurationItemDoubleResponse }
     * 
     */
    public GetConfigurationItemDoubleResponse createGetConfigurationItemDoubleResponse() {
        return new GetConfigurationItemDoubleResponse();
    }

    /**
     * Create an instance of {@link GetConfigurationItemIntResponse }
     * 
     */
    public GetConfigurationItemIntResponse createGetConfigurationItemIntResponse() {
        return new GetConfigurationItemIntResponse();
    }

    /**
     * Create an instance of {@link GetCompleteConfiguration }
     * 
     */
    public GetCompleteConfiguration createGetCompleteConfiguration() {
        return new GetCompleteConfiguration();
    }

    /**
     * Create an instance of {@link GetCompleteConfigurationResponse }
     * 
     */
    public GetCompleteConfigurationResponse createGetCompleteConfigurationResponse() {
        return new GetCompleteConfigurationResponse();
    }

    /**
     * Create an instance of {@link ConfigurationItem }
     * 
     */
    public ConfigurationItem createConfigurationItem() {
        return new ConfigurationItem();
    }

    /**
     * Create an instance of {@link GetConfigurationItem }
     * 
     */
    public GetConfigurationItem createGetConfigurationItem() {
        return new GetConfigurationItem();
    }

    /**
     * Create an instance of {@link GetConfigurationItemDefault }
     * 
     */
    public GetConfigurationItemDefault createGetConfigurationItemDefault() {
        return new GetConfigurationItemDefault();
    }

    /**
     * Create an instance of {@link GetConfigurationItemDoubleDefault }
     * 
     */
    public GetConfigurationItemDoubleDefault createGetConfigurationItemDoubleDefault() {
        return new GetConfigurationItemDoubleDefault();
    }

    /**
     * Create an instance of {@link GetConfigurationItemIntDefault }
     * 
     */
    public GetConfigurationItemIntDefault createGetConfigurationItemIntDefault() {
        return new GetConfigurationItemIntDefault();
    }

    /**
     * Create an instance of {@link GetConfigurationItemResponse }
     * 
     */
    public GetConfigurationItemResponse createGetConfigurationItemResponse() {
        return new GetConfigurationItemResponse();
    }

    /**
     * Create an instance of {@link GetConfigurationItems }
     * 
     */
    public GetConfigurationItems createGetConfigurationItems() {
        return new GetConfigurationItems();
    }

    /**
     * Create an instance of {@link ConfigurationList }
     * 
     */
    public ConfigurationList createConfigurationList() {
        return new ConfigurationList();
    }

    /**
     * Create an instance of {@link ReplaceConfigurationDocument }
     * 
     */
    public ReplaceConfigurationDocument createReplaceConfigurationDocument() {
        return new ReplaceConfigurationDocument();
    }

    /**
     * Create an instance of {@link ReplaceConfigurationDocumentResponse }
     * 
     */
    public ReplaceConfigurationDocumentResponse createReplaceConfigurationDocumentResponse() {
        return new ReplaceConfigurationDocumentResponse();
    }

    /**
     * Create an instance of {@link DeleteConfigurationDocument }
     * 
     */
    public DeleteConfigurationDocument createDeleteConfigurationDocument() {
        return new DeleteConfigurationDocument();
    }

    /**
     * Create an instance of {@link DeleteConfigurationDocumentResponse }
     * 
     */
    public DeleteConfigurationDocumentResponse createDeleteConfigurationDocumentResponse() {
        return new DeleteConfigurationDocumentResponse();
    }

    /**
     * Create an instance of {@link GetDocumentNames }
     * 
     */
    public GetDocumentNames createGetDocumentNames() {
        return new GetDocumentNames();
    }

    /**
     * Create an instance of {@link GetDocumentNamesResponse }
     * 
     */
    public GetDocumentNamesResponse createGetDocumentNamesResponse() {
        return new GetDocumentNamesResponse();
    }

    /**
     * Create an instance of {@link GetConfigurationDocument }
     * 
     */
    public GetConfigurationDocument createGetConfigurationDocument() {
        return new GetConfigurationDocument();
    }

    /**
     * Create an instance of {@link GetConfigurationDocumentResponse }
     * 
     */
    public GetConfigurationDocumentResponse createGetConfigurationDocumentResponse() {
        return new GetConfigurationDocumentResponse();
    }

    /**
     * Create an instance of {@link ConfigurationItemDouble }
     * 
     */
    public ConfigurationItemDouble createConfigurationItemDouble() {
        return new ConfigurationItemDouble();
    }

    /**
     * Create an instance of {@link ConfigurationItemInt }
     * 
     */
    public ConfigurationItemInt createConfigurationItemInt() {
        return new ConfigurationItemInt();
    }

    /**
     * Create an instance of {@link ConfigurationItems }
     * 
     */
    public ConfigurationItems createConfigurationItems() {
        return new ConfigurationItems();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCompleteConfiguration }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", name = "getCompleteConfiguration")
    public JAXBElement<GetCompleteConfiguration> createGetCompleteConfiguration(GetCompleteConfiguration value) {
        return new JAXBElement<GetCompleteConfiguration>(_GetCompleteConfiguration_QNAME, GetCompleteConfiguration.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetConfigurationItem }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", name = "getConfigurationItemDouble")
    public JAXBElement<GetConfigurationItem> createGetConfigurationItemDouble(GetConfigurationItem value) {
        return new JAXBElement<GetConfigurationItem>(_GetConfigurationItemDouble_QNAME, GetConfigurationItem.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetConfigurationItem }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", name = "getConfigurationItemInt")
    public JAXBElement<GetConfigurationItem> createGetConfigurationItemInt(GetConfigurationItem value) {
        return new JAXBElement<GetConfigurationItem>(_GetConfigurationItemInt_QNAME, GetConfigurationItem.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetConfigurationItem }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", name = "getConfigurationItem")
    public JAXBElement<GetConfigurationItem> createGetConfigurationItem(GetConfigurationItem value) {
        return new JAXBElement<GetConfigurationItem>(_GetConfigurationItem_QNAME, GetConfigurationItem.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetConfigurationItem }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", name = "getConfigurationItemList")
    public JAXBElement<GetConfigurationItem> createGetConfigurationItemList(GetConfigurationItem value) {
        return new JAXBElement<GetConfigurationItem>(_GetConfigurationItemList_QNAME, GetConfigurationItem.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetConfigurationItemDefault }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", name = "getConfigurationItemDefault")
    public JAXBElement<GetConfigurationItemDefault> createGetConfigurationItemDefault(GetConfigurationItemDefault value) {
        return new JAXBElement<GetConfigurationItemDefault>(_GetConfigurationItemDefault_QNAME, GetConfigurationItemDefault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetConfigurationItemDoubleDefault }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", name = "getConfigurationItemDoubleDefault")
    public JAXBElement<GetConfigurationItemDoubleDefault> createGetConfigurationItemDoubleDefault(GetConfigurationItemDoubleDefault value) {
        return new JAXBElement<GetConfigurationItemDoubleDefault>(_GetConfigurationItemDoubleDefault_QNAME, GetConfigurationItemDoubleDefault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetConfigurationItemIntDefault }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", name = "getConfigurationItemIntDefault")
    public JAXBElement<GetConfigurationItemIntDefault> createGetConfigurationItemIntDefault(GetConfigurationItemIntDefault value) {
        return new JAXBElement<GetConfigurationItemIntDefault>(_GetConfigurationItemIntDefault_QNAME, GetConfigurationItemIntDefault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetConfigurationItems }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", name = "getConfigurationItems")
    public JAXBElement<GetConfigurationItems> createGetConfigurationItems(GetConfigurationItems value) {
        return new JAXBElement<GetConfigurationItems>(_GetConfigurationItems_QNAME, GetConfigurationItems.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConfigurationList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", name = "getConfigurationItemsResponse")
    public JAXBElement<ConfigurationList> createGetConfigurationItemsResponse(ConfigurationList value) {
        return new JAXBElement<ConfigurationList>(_GetConfigurationItemsResponse_QNAME, ConfigurationList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConfigurationList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", name = "getConfigurationItemListResponse")
    public JAXBElement<ConfigurationList> createGetConfigurationItemListResponse(ConfigurationList value) {
        return new JAXBElement<ConfigurationList>(_GetConfigurationItemListResponse_QNAME, ConfigurationList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReplaceConfigurationDocument }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", name = "replaceConfigurationDocument")
    public JAXBElement<ReplaceConfigurationDocument> createReplaceConfigurationDocument(ReplaceConfigurationDocument value) {
        return new JAXBElement<ReplaceConfigurationDocument>(_ReplaceConfigurationDocument_QNAME, ReplaceConfigurationDocument.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteConfigurationDocument }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", name = "deleteConfigurationDocument")
    public JAXBElement<DeleteConfigurationDocument> createDeleteConfigurationDocument(DeleteConfigurationDocument value) {
        return new JAXBElement<DeleteConfigurationDocument>(_DeleteConfigurationDocument_QNAME, DeleteConfigurationDocument.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDocumentNames }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", name = "getDocumentNames")
    public JAXBElement<GetDocumentNames> createGetDocumentNames(GetDocumentNames value) {
        return new JAXBElement<GetDocumentNames>(_GetDocumentNames_QNAME, GetDocumentNames.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetConfigurationDocument }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/webservice/ConfigurationWebService", name = "getConfigurationDocument")
    public JAXBElement<GetConfigurationDocument> createGetConfigurationDocument(GetConfigurationDocument value) {
        return new JAXBElement<GetConfigurationDocument>(_GetConfigurationDocument_QNAME, GetConfigurationDocument.class, null, value);
    }

}
