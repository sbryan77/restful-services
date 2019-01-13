package com.viasat.wildblue.common.exception.contingency;

import static org.hamcrest.CoreMatchers.is;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.Test;



/**
 * Provides tests for the <code>ServiceAgreementNotFoundException</code> class.
 */
public class ServiceAgreementNotFoundExceptionTest
{
    /** First test source system reference. */
    private static final String SOURCE_SYSTEM_REF0 = "someSourceSystem";

    /** First test service agreement reference. */
    private static final String SERVICE_AGREEMENT_REF0 = "someServiceAgreement";

    /** First test message. */
    private static final String MESSAGE0 = "someMessage";

    /**
     * Test the <code>ServiceAgreementNotFoundException()</code> method.
     */
    @Test public void wildBlueContingencyException0()
    {
        ServiceAgreementNotFoundException e =
            new ServiceAgreementNotFoundException(SOURCE_SYSTEM_REF0,
                SERVICE_AGREEMENT_REF0);
        assertNotNull(e);
    }

    /**
     * Test the <code>ServiceAgreementNotFoundException()</code> method.
     */
    @Test public void wildBlueContingencyException1()
    {
        ServiceAgreementNotFoundException e =
            new ServiceAgreementNotFoundException(SOURCE_SYSTEM_REF0,
                SERVICE_AGREEMENT_REF0, MESSAGE0);
        assertThat(e.getMessage(), is(MESSAGE0));
    }

    /**
     * Test the <code>ServiceAgreementNotFoundException()</code> method.
     */
    @Test public void wildBlueContingencyException2()
    {
        Throwable throwable = new RuntimeException();
        ServiceAgreementNotFoundException e =
            new ServiceAgreementNotFoundException(SOURCE_SYSTEM_REF0,
                SERVICE_AGREEMENT_REF0, throwable);
        assertNotNull(e.getCause());
        assertThat(e.getCause().getClass().getName(),
            is(RuntimeException.class.getName()));
    }

    /**
     * Test the <code>ServiceAgreementNotFoundException()</code> method.
     */
    @Test public void wildBlueContingencyException3()
    {
        Throwable throwable = new RuntimeException();
        ServiceAgreementNotFoundException e =
            new ServiceAgreementNotFoundException(SOURCE_SYSTEM_REF0,
                SERVICE_AGREEMENT_REF0, MESSAGE0, throwable);
        assertThat(e.getMessage(), is(MESSAGE0));
        assertThat(e.getCause().getClass().getName(),
            is(RuntimeException.class.getName()));
    }
}
