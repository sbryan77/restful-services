package com.viasat.wildblue.common.exception.contingency;

import static org.hamcrest.CoreMatchers.is;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.Test;



/**
 * Provides tests for the <code>CustomerNotFoundException</code> class.
 */
public class CustomerNotFoundExceptionTest
{
    /** First test source system reference. */
    private static final String SOURCE_SYSTEM_REF0 = "someSourceSystem";

    /** First test customer reference. */
    private static final String CUSTOMER_REF0 = "someCustomer";

    /** First test message. */
    private static final String MESSAGE0 = "someMessage";

    /**
     * Test the <code>CustomerNotFoundException()</code> method.
     */
    @Test public void wildBlueContingencyException0()
    {
        CustomerNotFoundException e = new CustomerNotFoundException(
                SOURCE_SYSTEM_REF0, CUSTOMER_REF0);

        assertThat(e.getSourceSystemReference(), is(SOURCE_SYSTEM_REF0));
        assertThat(e.getCustomerReference(), is(CUSTOMER_REF0));
    }

    /**
     * Test the <code>CustomerNotFoundException()</code> method.
     */
    @Test public void wildBlueContingencyException1()
    {
        CustomerNotFoundException e = new CustomerNotFoundException(
                SOURCE_SYSTEM_REF0, CUSTOMER_REF0, MESSAGE0);

        assertThat(e.getSourceSystemReference(), is(SOURCE_SYSTEM_REF0));
        assertThat(e.getCustomerReference(), is(CUSTOMER_REF0));
        assertThat(e.getMessage(), is(MESSAGE0));
    }

    /**
     * Test the <code>CustomerNotFoundException()</code> method.
     */
    @Test public void wildBlueContingencyException2()
    {
        Throwable throwable = new RuntimeException();
        CustomerNotFoundException e = new CustomerNotFoundException(
                SOURCE_SYSTEM_REF0, CUSTOMER_REF0, throwable);

        assertThat(e.getSourceSystemReference(), is(SOURCE_SYSTEM_REF0));
        assertThat(e.getCustomerReference(), is(CUSTOMER_REF0));
        assertNotNull(e.getCause());
        assertThat(e.getCause().getClass().getName(),
            is(RuntimeException.class.getName()));
    }

    /**
     * Test the <code>CustomerNotFoundException()</code> method.
     */
    @Test public void wildBlueContingencyException3()
    {
        Throwable throwable = new RuntimeException();
        CustomerNotFoundException e = new CustomerNotFoundException(
                SOURCE_SYSTEM_REF0, CUSTOMER_REF0, MESSAGE0, throwable);

        assertThat(e.getSourceSystemReference(), is(SOURCE_SYSTEM_REF0));
        assertThat(e.getCustomerReference(), is(CUSTOMER_REF0));
        assertThat(e.getMessage(), is(MESSAGE0));
        assertThat(e.getCause().getClass().getName(),
            is(RuntimeException.class.getName()));
    }
}
