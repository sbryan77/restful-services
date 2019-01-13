package com.viasat.wildblue.common.exception.contingency;

import static org.hamcrest.CoreMatchers.is;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.Test;



/**
 * Provides tests for the <code>AccountNotFoundException</code> class.
 */
public class AccountNotFoundExceptionTest
{
    /** First test source system reference. */
    private static final String SOURCE_SYSTEM_REF0 = "someSourceSystem";

    /** First test account reference. */
    private static final String ACCOUNT_REF0 = "someAccount";

    /** First test message. */
    private static final String MESSAGE0 = "someMessage";

    /**
     * Test the <code>AccountNotFoundException()</code> method.
     */
    @Test public void wildBlueContingencyException0()
    {
        AccountNotFoundException e = new AccountNotFoundException(
                SOURCE_SYSTEM_REF0, ACCOUNT_REF0);

        assertThat(e.getSourceSystemReference(), is(SOURCE_SYSTEM_REF0));
        assertThat(e.getAccountReference(), is(ACCOUNT_REF0));
    }

    /**
     * Test the <code>AccountNotFoundException()</code> method.
     */
    @Test public void wildBlueContingencyException1()
    {
        AccountNotFoundException e = new AccountNotFoundException(
                SOURCE_SYSTEM_REF0, ACCOUNT_REF0, MESSAGE0);

        assertThat(e.getSourceSystemReference(), is(SOURCE_SYSTEM_REF0));
        assertThat(e.getAccountReference(), is(ACCOUNT_REF0));
        assertThat(e.getMessage(), is(MESSAGE0));
    }

    /**
     * Test the <code>AccountNotFoundException()</code> method.
     */
    @Test public void wildBlueContingencyException2()
    {
        Throwable throwable = new RuntimeException();
        AccountNotFoundException e = new AccountNotFoundException(
                SOURCE_SYSTEM_REF0, ACCOUNT_REF0, throwable);

        assertThat(e.getSourceSystemReference(), is(SOURCE_SYSTEM_REF0));
        assertThat(e.getAccountReference(), is(ACCOUNT_REF0));
        assertNotNull(e.getCause());
        assertThat(e.getCause().getClass().getName(),
            is(RuntimeException.class.getName()));
    }

    /**
     * Test the <code>AccountNotFoundException()</code> method.
     */
    @Test public void wildBlueContingencyException3()
    {
        Throwable throwable = new RuntimeException();
        AccountNotFoundException e = new AccountNotFoundException(
                SOURCE_SYSTEM_REF0, ACCOUNT_REF0, MESSAGE0, throwable);

        assertThat(e.getSourceSystemReference(), is(SOURCE_SYSTEM_REF0));
        assertThat(e.getAccountReference(), is(ACCOUNT_REF0));
        assertThat(e.getMessage(), is(MESSAGE0));
        assertThat(e.getCause().getClass().getName(),
            is(RuntimeException.class.getName()));
    }
}
