/**
 *
 */
package com.viasat.wildblue.common.util;

import java.lang.reflect.ParameterizedType;
import java.util.Date;

import javax.xml.ws.BindingProvider;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.apache.cxf.transports.http.configuration.ProxyServerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.viasat.wildblue.common.exception.fault.WildBlueFaultException;

/**
 * An abstract generic endpoint wrapper with the following features:
 *
 *   1. Encapsulate the creation of service endpoint (port).
 *   2. Provide caching for the service endpoint (if the endpoint URL remained the same).
 *
 * To use the class create a subclass that can be explicit or implicit (i.e., anonymous)
 * in most cases, see the examples below.
 *
 * Example 1 - Get Account using BusinessTransaction service:
 *
 *    EndpointWrapper<BusinessTransaction> btWrapper =
 *       new EndpointWrapper<BusinessTransaction>() {};  // anonymous subclass
 *
 *    ConfigurationProxy configProxy = ...;
 *
 *    public Account getAccountByInternalReference(String accountRef) {
 *       GetAccountByInternalReferenceRequest request = ...;
 *       GetAccountByInternalReferenceResponse response = btWrapper.getEndpoint(
 *          configProxy.getConfigurationItem(BTS_ENDPOINT_URL_KEY)).
 *          getAccountByInternalReference(request);
 *
 *       Account account = ...; // process response;
 *       return account;
 *    }
 *
 * Example 2 - Get Account types using Alianza AccontSoap service through
 *   a proxy server and set time out:
 *
 *   EndpointWrapper<AccountSoap> accountSoapWrapper =
 *      new EndpointWrapper<AccountSoap>() {};  // anonymous subclass
 *
 *   public List<AccountType> getAccountType() {
 *      ...
 *      GetAccountTypesResponse response = getAccountSoap().getAccountTypes(request);
 *      ...
 *   }
 *
 *   private AccountSoap getAccountSoap() {
 *      AccountSoap ep = accountSoapWrapper.getEndpoint(
 *         configProxy.getConfigurationItem(ACCOUNT_ENDPOINT_URL_KEY));
 *
 *      accountSoapWrapper.setProxyServer(
 *         configProxy.getConfigurationItem(PROXY_SERVER_URL_KEY),
 *         configProxy.getConfigurationItem(PROXY_SERVER_PORT_KEY),
 *         configProxy.getConfigurationItem(PROXY_SERVER_TYPE_KEY));
 *
 *      accountSoapWrapper.setTimeouts(
 *         configProxy.getConfigurationItem(CONNECTION_TIMEOUT_KEY),
 *         configProxy.getConfigurationItem(RECEIVE_TIMEOUT_KEY));
 *
 *      return ep;
 *   }
 *
 * Example 3 - Use an explicit subclass:
 *
 *   public class MyBTS extends EndpointWrapper<BusinessTransaction> {
 *     // other methods ...
 *   }
 *
 *   MyBTS myBTS = new MyBTS();
 *   response = myBTS.getEndpoint(url).getAccountByInternalReference(request);
 *   ...
 *
 * @author Xiao-Li Yang
 */
public abstract class EndpointWrapper<T>
{
    private static final Logger LOGGER = LoggerFactory.getLogger(EndpointWrapper.class);
    private static final String regExWSDL = "(?i)\\?wsdl";

    private T endpoint;
    private String endpointUrl;

    private long connectionTimeout, receiveTimeout;

    private boolean isProxied;
    private String proxyServerUrl, proxyServerType;
    private int proxyServerPort;

    public EndpointWrapper() {
        createEndpoint();
    }

    /**
     *  @return The cached (if serviceEndpointUrl is the same as in the previous
     *  call) or a new endpoint for the given serviceEndpointUrl.
     */
    public T getEndpoint(String serviceEndpointUrl) throws WildBlueFaultException {
        try {
            if (endpointUrl == null || !endpointUrl.equals(serviceEndpointUrl)) {
                setEndpointAddress(serviceEndpointUrl);
            }
        } catch (Exception e) {
            reset();
            String msg = "Failed to get service endpoint for: " + serviceEndpointUrl;
            LOGGER.error(msg, e);
            throw new WildBlueFaultException(msg, e);
        }
        return endpoint;
    }

    /**
     * Set a proxy server for the {@link #endpoint} initialized by the {@link #getEndpoint(String)} call.
     * If the call is the first time, then the logging interceptors will be also configured.
     *
     * @return The {@link #endpoint}.
     * @throws WildBlueFaultException If the {@link #getEndpoint(String)} is not called already.
     */
    public T setProxyServer(String proxyServerUrl, int proxyServerPort, String proxyServerType)
        throws WildBlueFaultException {
        Client client = ClientProxy.getClient(endpoint);

        if (!isProxied) {
            client.getInInterceptors().add(new LoggingInInterceptor());
            client.getOutInterceptors().add(new LoggingOutInterceptor());
            isProxied = true;
        }

        boolean urlChanged = proxyServerUrl != null && !proxyServerUrl.equals(this.proxyServerUrl);
        boolean portChanged = proxyServerPort != this.proxyServerPort;
        boolean typeChanged = proxyServerType != null && !proxyServerType.equals(this.proxyServerType);

        if (urlChanged || portChanged || typeChanged) {
            LOGGER.debug("Setting proxyServerUrl to " + proxyServerUrl + " and proxyServerPort to " +
                proxyServerPort + " and proxyServerType to " + proxyServerType);

            HTTPConduit httpConduit = (HTTPConduit)client.getConduit();
            HTTPClientPolicy httpClientPolicy = httpConduit.getClient();

            if (urlChanged) {
                httpClientPolicy.setProxyServer(proxyServerUrl);
                this.proxyServerUrl = proxyServerUrl;
            }
            if (portChanged) {
                httpClientPolicy.setProxyServerPort(proxyServerPort);
                this.proxyServerPort = proxyServerPort;
            }
            if (typeChanged) {
                httpClientPolicy.setProxyServerType(ProxyServerType.fromValue(proxyServerType));
                this.proxyServerType = proxyServerType;
            }
            httpClientPolicy.setAllowChunking(false);
        }
        return endpoint;
    }

    /**
     * Set the connection and receive timeouts for {@link #endpoint} initialized by
     * the {@link #getEndpoint(String)} call.
     *
     * @param connectionTimeout Connection timeout value in milliseconds.
     * @param receiveTimeout    Receive timeout value in milliseconds.
     * @return The {@link #endpoint}.
     * @throws WildBlueFaultException If the {@link #getEndpoint(String)} is not called already.
     */
    public T setTimeouts(long connectionTimeout, long receiveTimeout)
        throws WildBlueFaultException {
        boolean conTOChanged = connectionTimeout != this.connectionTimeout;
        boolean recTOChanged = receiveTimeout != this.receiveTimeout;

        if (conTOChanged || recTOChanged) {
            LOGGER.debug("Setting connectionTimeout to " + connectionTimeout +
                " and receive timeout to " + receiveTimeout);

            Client client = ClientProxy.getClient(endpoint);
            HTTPConduit httpConduit = (HTTPConduit) client.getConduit();
            HTTPClientPolicy httpClientPolicy = httpConduit.getClient();

            if (conTOChanged) {
                httpClientPolicy.setConnectionTimeout(connectionTimeout);
                this.connectionTimeout = connectionTimeout;
            }
            if (recTOChanged) {
                httpClientPolicy.setReceiveTimeout(receiveTimeout);
                this.receiveTimeout = receiveTimeout;
            }
            httpClientPolicy.setAllowChunking(false);
        }
        return endpoint;
    }

    private void reset() {
        endpointUrl = null;

        connectionTimeout = 0;
        receiveTimeout = 0;

        proxyServerUrl = null;
        proxyServerType = null;
        proxyServerPort = 0;
        isProxied = false;
    }

    @SuppressWarnings("unchecked")
    private void createEndpoint() {
        ParameterizedType parameterizedType = (ParameterizedType)getClass().getGenericSuperclass();
        Class<T> portClass = (Class<T>) parameterizedType.getActualTypeArguments()[0];

        LOGGER.info("Creating service endpoint for: " + portClass);

        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setServiceClass(portClass);
        Date start = new Date();
        endpoint = (T)factory.create();
        Date end = new Date();

        LOGGER.info("Created service endpoint for: " + portClass + " in " + (end.getTime() - start.getTime()) + " ms.");
    }

    private void setEndpointAddress(String serviceEndpointUrl) {
        LOGGER.debug("Setting endpoint address to: " + serviceEndpointUrl);

        endpointUrl = serviceEndpointUrl;
        BindingProvider bp = (BindingProvider) endpoint;
        bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointUrl.replaceAll(regExWSDL, ""));
    }
}
