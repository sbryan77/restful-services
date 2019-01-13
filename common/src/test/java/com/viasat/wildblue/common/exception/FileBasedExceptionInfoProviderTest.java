package com.viasat.wildblue.common.exception;

import com.viasat.wildblue.common.exception.fault.WildBlueFaultException;

import static org.hamcrest.CoreMatchers.is;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;


/**
 * Provides tests for the <code>FileBasedExceptionInfoProvider</code> class.
 */
public class FileBasedExceptionInfoProviderTest
{
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

    /**
     * Test the <code>getExceptionMap()</code> method.
     */
    @Test public void getExceptionMap()
    {
        FileBasedExceptionInfoProvider provider =
            new FileBasedExceptionInfoProvider();

        Map<String, String> exceptionMap = provider.getExceptionMap();
        assertNotNull(exceptionMap);
        //jkent - Don't test for size! This file will keep growing.
        //assertThat(exceptionMap.size(), is(7));

        Iterator<Entry<String, String>> iter = exceptionMap.entrySet()
            .iterator();
        assertTrue(iter.hasNext());

        Entry<String, String> entry0 = iter.next();
        assertNotNull(entry0);
        assertThat(entry0.getKey(),
            is("com.viasat.wildblue.common.exception.fault.WildBlueFaultException.code"));
        assertThat(entry0.getValue(), is("SYSTEM_ERROR"));

        assertTrue(iter.hasNext());

        Entry<String, String> entry1 = iter.next();
        assertNotNull(entry1);
        assertThat(entry1.getKey(),
            is("com.viasat.wildblue.common.exception.contingency.WildBlueContingencyException.code"));
        assertThat(entry1.getValue(), is("SYSTEM_CONTINGENCY"));

        assertTrue(iter.hasNext());

        Entry<String, String> entry2 = iter.next();
        assertNotNull(entry2);
        assertThat(entry2.getKey(),
            is("javax.persistence.NoResultException.code"));
        assertThat(entry2.getValue(), is("NO_DATA_FOUND"));

        assertTrue(iter.hasNext());
    }
}
