package com.viasat.common.rs.providers;

import com.github.kristofa.brave.SpanCollector;
import com.github.kristofa.brave.internal.Util;
import com.twitter.zipkin.gen.BinaryAnnotation;
import com.twitter.zipkin.gen.Endpoint;
import com.twitter.zipkin.gen.Span;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomSpanCollector implements SpanCollector {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomSpanCollector.class);
    private final Set<BinaryAnnotation> defaultAnnotations = new LinkedHashSet();

    public CustomSpanCollector() {
    }

    public void collect(Span span) {
        Util.checkNotNull(span, "Null span", new Object[0]);
        if (!this.defaultAnnotations.isEmpty()) {
            Iterator var2 = this.defaultAnnotations.iterator();

            while(var2.hasNext()) {
                BinaryAnnotation ba = (BinaryAnnotation)var2.next();
                span.addToBinary_annotations(ba);
            }
        }

        LOGGER.debug(span.toString());
    }

    public void addDefaultAnnotation(String key, String value) {
        this.defaultAnnotations.add(BinaryAnnotation.create(key, value, (Endpoint)null));
    }
}
