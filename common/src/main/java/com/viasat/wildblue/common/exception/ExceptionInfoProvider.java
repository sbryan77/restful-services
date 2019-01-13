package com.viasat.wildblue.common.exception;

import java.util.Map;
import java.util.ResourceBundle;


/**
 * Defines methods required by an exception info provider.
 */
public interface ExceptionInfoProvider
{
    /**
     * @return  the map of exception name to code.
     */
    Map<String, String> getExceptionMap();

    /**
     * @return  the resource bundle.
     */
    ResourceBundle getResourceBundle();
}
