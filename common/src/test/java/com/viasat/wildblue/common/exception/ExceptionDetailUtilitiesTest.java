package com.viasat.wildblue.common.exception;

import com.viasat.wildblue.common.exception.fault.WildBlueFaultException;

import static org.hamcrest.CoreMatchers.is;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import org.junit.Test;


/**
 * Provides tests for the <code>ExceptionDetailUtilities</code> class.
 */
public class ExceptionDetailUtilitiesTest
{
    /**
     * Test the <code>createExceptionDetail()</code> method.
     */
    @Test public void createExceptionDetail0()
    {
        ExceptionDetailUtilities exceptionUtils = ExceptionDetailUtilities
            .getInstance();

        ExceptionDetail result = exceptionUtils.createExceptionDetail();
        assertNotNull(result);
        assertNull(result.getCode());
        assertNull(result.getDetail());
        assertNotNull(result.getNode());
        assertNull(result.getReason());
        assertNull(result.getRole());
        assertNotNull(result.getTimestamp());
        assertNotNull(result.getTrackingKey());
    }

    /**
     * Test the <code>createExceptionDetail()</code> method.
     */
    @Test public void createExceptionDetail1()
    {
        String message = "Some error happened.";
        Throwable cause = new WildBlueFaultException(message);
        ExceptionDetailUtilities exceptionUtils = ExceptionDetailUtilities
            .getInstance();

        ExceptionDetail result = exceptionUtils.createExceptionDetail(cause);
        assertNotNull(result);
        assertThat(result.getCode(), is("SYSTEM_ERROR"));
        assertThat(result.getDetail(), is("Unexpected exception"));
        assertNotNull(result.getNode());
        assertThat(result.getReason(), is("System unavailable."));
        assertNull(result.getRole());
        assertNotNull(result.getTimestamp());
        assertNotNull(result.getTrackingKey());
    }
}
