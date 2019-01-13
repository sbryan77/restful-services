package com.viasat.wildblue.common.validator;

import com.viasat.wildblue.common.commondata.ValidationError;
import com.viasat.wildblue.common.exception.WebServiceException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class WebServiceValidationExceptionTest
{
    private static final String VALIDATION_ERROR = "VALIDATION_ERROR";

    /**
     * This test fails and shouldn't
     */
    @Test public void testConstructor0()
    {
        List<ValidationError> errors = new ArrayList<ValidationError>();
        ValidationError error1 = new ValidationError();
        error1.setErrorCode(VALIDATION_ERROR);
        error1.setMessage("Message 1");

        ValidationError error2 = new ValidationError();
        error2.setErrorCode(VALIDATION_ERROR);
        error2.setMessage("Message 2");
        errors.add(error1);
        errors.add(error2);

        WebServiceException e = new WebServiceValidationException(errors);

        assertNotNull(e.getFaultInfo());
        assertEquals(VALIDATION_ERROR, e.getFaultInfo().getCode());

        assertNull(e.getFaultInfo().getDetail());
        assertNotNull(e.getFaultInfo().getNode());
        assertEquals(VALIDATION_ERROR + ":Message 1; " + VALIDATION_ERROR
            + ":Message 2; ", e.getFaultInfo().getReason());
        assertNull(e.getFaultInfo().getRole());
        assertNotNull(e.getFaultInfo().getTimestamp());

        assertNotNull(e.getFaultInfo().getTrackingKey());
    }

    @Test public void testConstructor1()
    {
        try
        {
            WebServiceException e = new WebServiceValidationException(null);
            fail("Should have thrown an IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            //This is what it is supposed to do
        }
    }

    @Test public void testConstructor2()
    {
        try
        {
            WebServiceException e = new WebServiceValidationException(
                    new ArrayList<ValidationError>());
            fail("Should have thrown an IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            //This is what it is supposed to do
        }
        //        System.out.println(e);
        //        assertNull(e.getMessage());
        //        assertNotNull(e.getFaultInfo());
        //        assertEquals("UNKNOWN_CODE", e.getFaultInfo().getCode());
        //        assertEquals("Unknown Reason", e.getFaultInfo().getReason());
        //        assertNotNull(e.getFaultInfo().getNode());
        //        assertNull(e.getFaultInfo().getRole());
        //        assertEquals("Unhandled Exeption", e.getFaultInfo().getDetail());
        //        assertNotNull(e.getFaultInfo().getTimestamp());
        //        assertNotNull(e.getFaultInfo().getTrackingKey());
    }
}
