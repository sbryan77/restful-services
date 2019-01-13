package com.viasat.wildblue.common.exception.contingency;

import com.viasat.wildblue.common.exception.WildBlueContingencyException;

import static org.hamcrest.CoreMatchers.is;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.Test;


/**
 * Provides tests for the <code>WildBlueContingencyException</code> class.
 */
@SuppressWarnings("deprecation")
public class ServiceItemNotFoundExceptionTest
{
    /**
     * Test the <code>WildBlueContingencyException()</code> method.
     */
    @Test public void wildBlueContingencyException0()
    {
        WildBlueContingencyException e = new WildBlueContingencyException();
        assertNotNull(e);
    }

    /**
     * Test the <code>WildBlueContingencyException()</code> method.
     */
    @Test public void wildBlueContingencyException1()
    {
        String message = "test message";
        WildBlueContingencyException e = new WildBlueContingencyException(
                message);
        assertThat(e.getMessage(), is(message));
    }

    /**
     * Test the <code>WildBlueContingencyException()</code> method.
     */
    @Test public void wildBlueContingencyException2()
    {
        Throwable throwable = new RuntimeException();
        WildBlueContingencyException e = new WildBlueContingencyException(
                throwable);
        assertNotNull(e.getCause());
        assertThat(e.getCause().getClass().getName(),
            is(RuntimeException.class.getName()));
    }

    /**
     * Test the <code>WildBlueContingencyException()</code> method.
     */
    @Test public void wildBlueContingencyException3()
    {
        String message = "test message";
        Throwable throwable = new RuntimeException();
        WildBlueContingencyException e = new WildBlueContingencyException(
                message, throwable);
        assertThat(e.getMessage(), is(message));
        assertThat(e.getCause().getClass().getName(),
            is(RuntimeException.class.getName()));
    }
}
