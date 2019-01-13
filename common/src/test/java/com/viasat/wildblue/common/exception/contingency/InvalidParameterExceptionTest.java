package com.viasat.wildblue.common.exception.contingency;

import static org.hamcrest.CoreMatchers.is;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.Test;


/**
 * Provides tests for the <code>InvalidParameterException</code> class.
 */
public class InvalidParameterExceptionTest
{
    /** First test message. */
    private static final String MESSAGE0 = "Some message.";

    /** First test parameter name. */
    private static final String PARAMETER_NAME0 = "someName";

    /** First test parameter value. */
    private static final Object PARAMETER_VALUE0 = "someValue";

    /** First test cause. */
    private static final Exception CAUSE0 = new RuntimeException(
            "Something went horribly wrong.");

    /**
     * Test the <code>InvalidParameterException(String, String)</code> method.
     */
    @Test public void invalidParameterException0()
    {
        InvalidParameterException exception = new InvalidParameterException(
                PARAMETER_NAME0, PARAMETER_VALUE0);
        assertNotNull(exception);
        assertThat(exception.getParameterName(), is(PARAMETER_NAME0));
        assertThat(exception.getParameterValue(), is(PARAMETER_VALUE0));
    }

    /**
     * Test the <code>InvalidParameterException(String, String, String,
     * Exception)</code> method.
     */
    @Test public void invalidParameterException1()
    {
        InvalidParameterException exception = new InvalidParameterException(
                PARAMETER_NAME0, PARAMETER_VALUE0, MESSAGE0);
        assertNotNull(exception);
        assertThat(exception.getParameterName(), is(PARAMETER_NAME0));
        assertThat(exception.getParameterValue(), is(PARAMETER_VALUE0));
        assertThat(exception.getMessage(), is(MESSAGE0));
    }

    /**
     * Test the <code>InvalidParameterException(String, String, String,
     * Exception)</code> method.
     */
    @Test public void invalidParameterException2()
    {
        InvalidParameterException exception = new InvalidParameterException(
                PARAMETER_NAME0, PARAMETER_VALUE0, CAUSE0);
        assertNotNull(exception);
        assertThat(exception.getParameterName(), is(PARAMETER_NAME0));
        assertThat(exception.getParameterValue(), is(PARAMETER_VALUE0));
        assertThat(exception.getCause().toString(), is(CAUSE0.toString()));
    }

    /**
     * Test the <code>InvalidParameterException(String, String, String,
     * Exception)</code> method.
     */
    @Test public void invalidParameterException3()
    {
        InvalidParameterException exception = new InvalidParameterException(
                PARAMETER_NAME0, PARAMETER_VALUE0, MESSAGE0, CAUSE0);
        assertNotNull(exception);
        assertThat(exception.getParameterName(), is(PARAMETER_NAME0));
        assertThat(exception.getParameterValue(), is(PARAMETER_VALUE0));
        assertThat(exception.getMessage(), is(MESSAGE0));
        assertThat(exception.getCause().toString(), is(CAUSE0.toString()));
    }

    /**
     * Test the <code>toString()</code> method.
     */
    @Test public void testToString()
    {
        InvalidParameterException exception = new InvalidParameterException(
                PARAMETER_NAME0, PARAMETER_VALUE0);
        String expected =
            "com.viasat.wildblue.common.exception.contingency.InvalidParameterException: parameter name = someName, parameter value = someValue";
        String result = exception.toString();
        assertThat(result, is(expected));

        exception = new InvalidParameterException(PARAMETER_NAME0,
                PARAMETER_VALUE0, MESSAGE0);
        expected =
            "com.viasat.wildblue.common.exception.contingency.InvalidParameterException: Some message. parameter name = someName, parameter value = someValue";
        result = exception.toString();
        assertThat(result, is(expected));

        exception = new InvalidParameterException(PARAMETER_NAME0,
                PARAMETER_VALUE0, CAUSE0);
        expected =
            "com.viasat.wildblue.common.exception.contingency.InvalidParameterException: parameter name = someName, parameter value = someValue";
        result = exception.toString();
        assertThat(result, is(expected));

        exception = new InvalidParameterException(PARAMETER_NAME0,
                PARAMETER_VALUE0, MESSAGE0, CAUSE0);
        expected =
            "com.viasat.wildblue.common.exception.contingency.InvalidParameterException: Some message. parameter name = someName, parameter value = someValue";
        result = exception.toString();
        assertThat(result, is(expected));
    }
}
