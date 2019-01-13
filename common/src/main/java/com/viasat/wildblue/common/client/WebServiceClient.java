package com.viasat.wildblue.common.client;

import com.viasat.wildblue.common.util.EndpointWrapper;

/**
 * An abstract web service client that can be used to create a specific client.
 *
 * Example:
 *
 *    public class NotificationClient extends WebServiceClient<NotificationService> {
 *        public static final String CONFIG_DOC_NAME = "SERVICE_LOCATOR";
 *        public static final String ENDPOINT_URL_KEY = "notificationServiceUrl";
 *
 *        private static NotificationClient instance = null;
 *        private ConfigurationProxy configProxy;
 *
 *        private NotificationClient() {
 *            configProxy = ConfigManager.getConfigurationProxy(CONFIG_DOC_NAME, new WildBlueHeader());
 *        }
 *
 *        public static NotificationClient getInstance() {
 *            if(instance == null) {
 *                instance = new NotificationClient();
 *            }
 *            return instance;
 *        }
 *
 *        @Override
 *        protected String getEndpointUrl() {
 *            try {
 *                return configProxy.getConfigurationItem(ENDPOINT_URL_KEY);
 *            } catch (Throwable t) {
 *                throw new WildBlueFaultException("Failed to get configuration value for: " +
 *                    ENDPOINT_URL_KEY + " from " + CONFIG_DOC_NAME, t);
 *            }
 *        }
 *    }
 *
 * @author Xiao-Li Yang
 */
public abstract class WebServiceClient<T> {
    private EndpointWrapper<T> epWrapper = new EndpointWrapper<T>(){};

    public T getEndpoint() {
        return epWrapper.getEndpoint(getEndpointUrl());
    }

    public T setProxyServer(String proxyServerUrl, int proxyServerPort, String proxyServerType) {
        return epWrapper.setProxyServer(proxyServerUrl, proxyServerPort, proxyServerType);
    }

    public T setTimeouts(long connectionTimeout, long receiveTimeout) {
        return epWrapper.setTimeouts(connectionTimeout, receiveTimeout);
    }

    protected abstract String getEndpointUrl();
}
