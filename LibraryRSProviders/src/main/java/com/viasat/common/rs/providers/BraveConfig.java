//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.viasat.common.rs.providers;

import com.github.kristofa.brave.Brave;
import com.github.kristofa.brave.Brave.Builder;
import com.github.kristofa.brave.http.DefaultSpanNameProvider;
import com.github.kristofa.brave.http.SpanNameProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class BraveConfig {
    public BraveConfig() {
    }

    @Bean
    @Scope("singleton")
    public Brave brave() {
        Builder builder = new Builder("is-contact");
        builder.spanCollector(new CustomSpanCollector());
        return builder.build();
    }

    @Bean
    @Scope("singleton")
    public SpanNameProvider spanNameProvider() {
        return new DefaultSpanNameProvider();
    }
}
