package com.viasat.common.rs.providers;

import com.github.kristofa.brave.Brave;
import com.github.kristofa.brave.ClientRequestInterceptor;
import com.github.kristofa.brave.ClientResponseInterceptor;
import com.github.kristofa.brave.http.HttpClientRequest;
import com.github.kristofa.brave.http.HttpClientRequestAdapter;
import com.github.kristofa.brave.http.HttpClientResponseAdapter;
import com.github.kristofa.brave.http.HttpResponse;
import com.github.kristofa.brave.http.SpanNameProvider;
import com.github.kristofa.brave.jaxrs2.JaxRs2HttpClientRequest;
import com.github.kristofa.brave.jaxrs2.JaxRs2HttpResponse;
import java.io.IOException;
import javax.annotation.Priority;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Provider
@Priority(0)
public class CustomClientFilter implements ClientRequestFilter, ClientResponseFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomContainerFilter.class);
    private final ClientRequestInterceptor requestInterceptor;
    private final ClientResponseInterceptor responseInterceptor;
    private final SpanNameProvider spanNameProvider;

    public CustomClientFilter() {
        BraveConfig braveConfig = this.getBraveConfig();
        Brave brave = braveConfig.brave();
        this.requestInterceptor = brave.clientRequestInterceptor();
        this.responseInterceptor = brave.clientResponseInterceptor();
        this.spanNameProvider = braveConfig.spanNameProvider();
    }

    public void filter(ClientRequestContext requestContext) throws IOException {
        ContainerRequestInfo info = (ContainerRequestInfo)CustomContainerFilter.CONTAINERREQUESTINFO.get();
        if (info != null && info.getApplication() != null && info.getUsername() != null) {
            requestContext.getHeaders().add("viasat_app", info.getApplication());
            requestContext.getHeaders().add("viasat_user", info.getUsername());
        }

        HttpClientRequest req = new JaxRs2HttpClientRequest(requestContext);
        this.requestInterceptor.handle(new HttpClientRequestAdapter(req, this.spanNameProvider));
    }

    public void filter(ClientRequestContext clientRequestContext, ClientResponseContext clientResponseContext) throws IOException {
        HttpResponse response = new JaxRs2HttpResponse(clientResponseContext);
        this.responseInterceptor.handle(new HttpClientResponseAdapter(response));
    }

    private BraveConfig getBraveConfig() {
        AnnotationConfigApplicationContext ctx = null;

        BraveConfig var3;
        try {
            ctx = new AnnotationConfigApplicationContext();
            ctx.register(new Class[]{BraveConfig.class});
            ctx.refresh();
            BraveConfig myBean = (BraveConfig)ctx.getBean(BraveConfig.class);
            var3 = myBean;
        } finally {
            ctx.close();
        }

        return var3;
    }
}