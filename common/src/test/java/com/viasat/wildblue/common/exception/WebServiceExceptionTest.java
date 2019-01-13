package com.viasat.wildblue.common.exception;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;


public class WebServiceExceptionTest
{
    @Test public void testConstructor()
    {
        WebServiceException e = new WebServiceException();
        //System.out.println(e);
        assertNull(e.getMessage());
        assertNotNull(e.getFaultInfo());
        assertEquals("UNKNOWN_CODE", e.getFaultInfo().getCode());
        assertEquals("Unknown Reason", e.getFaultInfo().getReason());
        assertNotNull(e.getFaultInfo().getNode());
        assertNull(e.getFaultInfo().getRole());
        assertEquals("Unhandled Exeption", e.getFaultInfo().getDetail());
        assertNotNull(e.getFaultInfo().getTimestamp());
        assertNotNull(e.getFaultInfo().getTrackingKey());
    }

    @Test public void testConstructorWithMessage()
    {
        WebServiceException e = new WebServiceException("message");
        //System.out.println(e);
        assertEquals("message", e.getMessage());
        assertNotNull(e.getFaultInfo());
        assertEquals("UNKNOWN_CODE", e.getFaultInfo().getCode());
        assertEquals("Unknown Reason", e.getFaultInfo().getReason());
        assertNotNull(e.getFaultInfo().getNode());
        assertNull(e.getFaultInfo().getRole());
        assertEquals("Unhandled Exeption", e.getFaultInfo().getDetail());
        assertNotNull(e.getFaultInfo().getTimestamp());
        assertNotNull(e.getFaultInfo().getTrackingKey());
    }

    @Test public void testConstructorWithMessageAndExceptionDetail()
    {
        ExceptionDetail detail = new ExceptionDetail();
        detail.setCode("NEW_CODE");
        detail.setDetail("NEW_DETAIL");

        WebServiceException e = new WebServiceException("message", detail);
        //System.out.println(e);
        assertEquals("message", e.getMessage());

        //Exception detail is not fully populated because the detail passed in wasn't
        assertNotNull(e.getFaultInfo());
        assertEquals("NEW_CODE", e.getFaultInfo().getCode());
        assertNull(e.getFaultInfo().getReason());
        assertNull(e.getFaultInfo().getNode());
        assertNull(e.getFaultInfo().getRole());
        assertEquals("NEW_DETAIL", e.getFaultInfo().getDetail());
        assertNull(e.getFaultInfo().getTimestamp());
        assertNull(e.getFaultInfo().getTrackingKey());
    }

    @Test public void testConstructorWithMessageAndThrowable()
    {
        WebServiceException e = new WebServiceException("message",
                new Exception());
        //System.out.println(e);
        assertEquals("message", e.getMessage());

        //Because an exception was passed, no faultInfo is created
        assertNull(e.getFaultInfo());
    }

    @Test public void testConstructorWithMessageExceptionDetailAndThrowable()
    {
        ExceptionDetail detail = new ExceptionDetail();
        detail.setCode("NEW_CODE");
        detail.setDetail("NEW_DETAIL");

        WebServiceException e = new WebServiceException("message", detail,
                new Exception());
        //System.out.println(e);
        assertEquals("message", e.getMessage());

        //Exception detail is not fully populated because the detail passed in wasn't
        assertNotNull(e.getFaultInfo());
        assertEquals("NEW_CODE", e.getFaultInfo().getCode());
        assertNull(e.getFaultInfo().getReason());
        assertNull(e.getFaultInfo().getNode());
        assertNull(e.getFaultInfo().getRole());
        assertEquals("NEW_DETAIL", e.getFaultInfo().getDetail());
        assertNull(e.getFaultInfo().getTimestamp());
        assertNull(e.getFaultInfo().getTrackingKey());
    }
}
