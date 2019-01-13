package com.viasat.common.rs.providers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

public class CustomJacksonJaxbJsonProvider extends JacksonJaxbJsonProvider {
    public CustomJacksonJaxbJsonProvider() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new JavaTimeModule());
        this.setMapper(mapper);
        this.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }
}