package com.viasat.wildblue.common.exception.fault;

import com.viasat.wildblue.common.exception.ExceptionDetail;
import com.viasat.wildblue.common.exception.contingency.WildBlueContingencyException;

import static org.hamcrest.CoreMatchers.is;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import org.junit.Test;


/**
 * Provides tests for the <code>WildBlueFaultException</code> class.
 */
public class WildBlueFaultExceptionTest
{
    /** Tracking key. */
    private static final String TRACKING_KEY0 = "1234567890";

    /** First test code. */
    private static final String CODE0 = "MISC_EXCEPTION";

    /** First test reason. */
    private static final String REASON0 = "Something went wrong.";

    /** First test node. */
    private static final String NODE0 = "http://www.wildblue.com/SIEBAS";

    /** First test role. */
    private static final String ROLE0 = "SERVER";

    /** First test detail. */
    private static final String DETAIL0 = "There was a problem blah blah blah";

    /**
     * Test the <code>WildBlueFaultException()</code> method.
     */
    @Test public void wildBlueFaultException0()
    {
        String message = "Some debug details";

        WildBlueFaultException exception = new WildBlueFaultException(message);

        assertNotNull(exception);
        assertThat(exception.getMessage(), is(message));
        assertNull(exception.getCause());

        ExceptionDetail exceptionDetail = exception.getExceptionDetail();
        assertNotNull(exceptionDetail);
        assertNull(exceptionDetail.getCode());
        assertNull(exceptionDetail.getDetail());
        assertNotNull(exceptionDetail.getNode());
        assertNull(exceptionDetail.getReason());
        assertNull(exceptionDetail.getRole());
        assertNotNull(exceptionDetail.getTimestamp());
        assertNotNull(exceptionDetail.getTrackingKey());
    }

    /**
     * Test the <code>WildBlueFaultException()</code> method.
     */
    @Test public void wildBlueFaultException1()
    {
        String message = "Some debug details";
        Exception cause = new WildBlueContingencyException();

        WildBlueFaultException exception = new WildBlueFaultException(message,
                cause);

        assertNotNull(exception);
        assertThat(exception.getMessage(), is(message));
        assertNotNull(exception.getCause());
        assertThat(exception.getCause().getClass().getName(),
            is(cause.getClass().getName()));

        ExceptionDetail exceptionDetail = exception.getExceptionDetail();
        assertNotNull(exceptionDetail);
        assertThat(exceptionDetail.getCode(), is("SYSTEM_CONTINGENCY"));
        assertThat(exceptionDetail.getDetail(), is("Unexpected exception"));
        assertNotNull(exceptionDetail.getNode());
        assertThat(exceptionDetail.getReason(),
            is("A contingency wasn't handled by the caller."));
        assertNull(exceptionDetail.getRole());
        assertNotNull(exceptionDetail.getTimestamp());
        assertNotNull(exceptionDetail.getTrackingKey());
    }

    /**
     * Test the <code>WildBlueFaultException()</code> method.
     */
    @Test public void wildBlueFaultException2()
    {
        ExceptionDetail exceptionDetail = createExceptionDetail();
        String message = "Some debug details";
        Exception cause = new WildBlueContingencyException();

        WildBlueFaultException exception = new WildBlueFaultException(
                exceptionDetail, message, cause);

        assertNotNull(exception);
        assertThat(exception.getMessage(), is(message));
        assertNotNull(exception.getCause());
        assertThat(exception.getCause().getClass().getName(),
            is(cause.getClass().getName()));
        assertThat(exception.getExceptionDetail(), is(exceptionDetail));
    }

    /**
     * Test the <code>WildBlueFaultException()</code> method.
     */
    @Test public void wildBlueFaultException3()
    {
        ExceptionDetail exceptionDetail = createExceptionDetail();
        String message = "test message";
        WildBlueFaultException e = new WildBlueFaultException(exceptionDetail,
                message);
        assertThat(e.getMessage(), is(message));
    }

    /**
     * Test the <code>WildBlueFaultException()</code> method.
     */
    @Test public void wildBlueFaultExceptionNulls()
    {
        ExceptionDetail exceptionDetail = null;
        String message = null;
        Exception cause = null;

        WildBlueFaultException exception = new WildBlueFaultException(
                exceptionDetail, message, cause);

        assertNotNull(exception);
        assertNull(exception.getExceptionDetail());
        assertNull(exception.getMessage());
        assertNull(exception.getCause());
    }

    /**
     * @return  a new exception detail.
     */
    private ExceptionDetail createExceptionDetail()
    {
        ExceptionDetail answer = new ExceptionDetail();

        answer.setTrackingKey(TRACKING_KEY0);
        answer.setCode(CODE0);
        answer.setReason(REASON0);
        answer.setNode(NODE0);
        answer.setRole(ROLE0);
        answer.setDetail(DETAIL0);

        return answer;
    }
}
