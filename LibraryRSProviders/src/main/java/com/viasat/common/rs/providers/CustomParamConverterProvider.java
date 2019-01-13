package com.viasat.common.rs.providers;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.OffsetDateTime;
import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;

public class CustomParamConverterProvider implements ParamConverterProvider {
    public CustomParamConverterProvider() {
    }

    public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
        if (OffsetDateTime.class.equals(rawType)) {
            ParamConverter<T> paramConverter = null;
                    //new OffsetDateTimeParamConverter();
            return paramConverter;
        } else {
            return null;
        }
    }
}