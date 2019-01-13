package com.viasat.common.rs.providers;

import com.github.kristofa.brave.ServerRequestInterceptor;
import com.github.kristofa.brave.ServerResponseInterceptor;
import com.github.kristofa.brave.http.HttpResponse;
import com.github.kristofa.brave.http.HttpServerRequest;
import com.github.kristofa.brave.http.HttpServerRequestAdapter;
import com.github.kristofa.brave.http.HttpServerResponseAdapter;
import com.github.kristofa.brave.http.SpanNameProvider;
import com.github.kristofa.brave.jaxrs2.JaxRs2HttpServerRequest;
import java.io.IOException;
import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Provider
@PreMatching
@Priority(0)
public class CustomContainerFilter implements ContainerRequestFilter, ContainerResponseFilter {
    static ThreadLocal<ContainerRequestInfo> CONTAINERREQUESTINFO = new ThreadLocal();
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomContainerFilter.class);
    private final ServerRequestInterceptor requestInterceptor;
    private final ServerResponseInterceptor responseInterceptor;
    private final SpanNameProvider spanNameProvider;

    public static ContainerRequestInfo getContainerRequestInfo() {
        ContainerRequestInfo info = (ContainerRequestInfo)CONTAINERREQUESTINFO.get();
        return info.getUsername() != null && !info.getUsername().isEmpty() ? info : null;
    }

    public CustomContainerFilter() {
        BraveConfig braveConfig = this.getBraveConfig();
        this.requestInterceptor = new ServerRequestInterceptor(braveConfig.brave().serverTracer());
        this.spanNameProvider = braveConfig.spanNameProvider();
        this.responseInterceptor = new ServerResponseInterceptor(this.getBraveConfig().brave().serverTracer());
    }

    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        try {
            String application = containerRequestContext.getHeaderString("viasat_app");
            String username = containerRequestContext.getHeaderString("viasat_user");
            ContainerRequestInfo headers = new ContainerRequestInfo();
            headers.setUsername(username);
            headers.setApplication(application);
            CONTAINERREQUESTINFO.set(headers);
            HttpServerRequest request = new JaxRs2HttpServerRequest(containerRequestContext);
            this.requestInterceptor.handle(new HttpServerRequestAdapter(request, this.spanNameProvider));
        } catch (Exception var6) {
            LOGGER.error("Julie error ", var6);
        }

    }

    public void filter(ContainerRequestContext containerRequestContext, final ContainerResponseContext containerResponseContext) throws IOException {
        CONTAINERREQUESTINFO.remove();
        HttpResponse httpResponse = new HttpResponse() {
            public int getHttpStatusCode() {
                return containerResponseContext.getStatus();
            }
        };
        this.responseInterceptor.handle(new HttpServerResponseAdapter(httpResponse));
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
