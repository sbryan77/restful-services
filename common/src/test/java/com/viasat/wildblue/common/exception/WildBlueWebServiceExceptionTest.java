package com.viasat.wildblue.common.exception;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class WildBlueWebServiceExceptionTest
{
    /**
     * public WildBlueWebServiceException() { super(); }
     */

    @Test public void testConstructor()
    {
        WebServiceException e = new WildBlueWebServiceException();
        assertTrue(e instanceof HasExceptionDetail);

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

    /**
     * public WildBlueWebServiceException(String message) { super(message); }
     */

    @Test public void testConstructorWithMessage()
    {
        WebServiceException e = new WildBlueWebServiceException("message");
        assertTrue(e instanceof HasExceptionDetail);

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
}
