package com.viasat.wildblue.common.exception;

import static org.hamcrest.CoreMatchers.is;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;


/**
 * Provides tests for the <code>ExceptionInfoProcessor</code> class.
 */
public class ExceptionInfoProcessorTest
{
    /** First test key. */
    private static final String KEY0 =
        "com.viasat.wildblue.bogus.package.SomeException";

    /** First test value. */
    private static final String VALUE0 = "SOME_EXCEPTION";

    /** First test resource key. */
    private static final String RESOURCE_KEY0 = "STRING_" + VALUE0;

    /** First test resource. */
    private static final String RESOURCE0 = "Some exception occurred.";

    /** Second test key. */
    private static final String KEY1 =
        "com.viasat.wildblue.bogus.package.AnotherException";

    /** Second test value. */
    private static final String VALUE1 = "ANOTHER_EXCEPTION";

    /** Second test resource key. */
    private static final String RESOURCE_KEY1 = "STRING_" + VALUE1;

    /** Second test resource. */
    private static final String RESOURCE1 = "Another exception occurred.";

    /**
     * Test the <code>getAllExceptionInfos()</code> method.
     */
    @Test public void getAllExceptionInfos()
    {
        Locale clientLocale = Locale.getDefault();
        ExceptionInfoProcessor processor = new ExceptionInfoProcessor(
                new TestExceptionInfoProvider());

        List<ExceptionInfo> exceptionInfos = processor.getAllExceptionInfos(
                clientLocale);

        assertNotNull(exceptionInfos);
        assertThat(exceptionInfos.size(), is(2));

        ExceptionInfo exceptionInfo0 = exceptionInfos.get(0);
        assertNotNull(exceptionInfo0);
        assertThat(exceptionInfo0.getCode(), is(VALUE0));
        assertThat(exceptionInfo0.getExceptionClassname(), is(KEY0));
        assertThat(exceptionInfo0.getReason(), is(RESOURCE0));

        ExceptionInfo exceptionInfo1 = exceptionInfos.get(1);
        assertNotNull(exceptionInfo1);
        assertThat(exceptionInfo1.getCode(), is(VALUE1));
        assertThat(exceptionInfo1.getExceptionClassname(), is(KEY1));
        assertThat(exceptionInfo1.getReason(), is(RESOURCE1));
    }

    /**
     * Test the <code>getExceptionInfo()</code> method.
     */
    @Test public void getExceptionInfo()
    {
        Locale clientLocale = Locale.getDefault();
        ExceptionInfoProcessor processor = new ExceptionInfoProcessor(
                new TestExceptionInfoProvider());

        ExceptionInfo exceptionInfo0 = processor.getExceptionInfo(KEY0,
                clientLocale);

        assertNotNull(exceptionInfo0);
        assertThat(exceptionInfo0.getCode(), is(VALUE0));
        assertThat(exceptionInfo0.getExceptionClassname(), is(KEY0));
        assertThat(exceptionInfo0.getReason(), is(RESOURCE0));

        ExceptionInfo exceptionInfo1 = processor.getExceptionInfo(KEY1,
                clientLocale);
        assertNotNull(exceptionInfo1);
        assertThat(exceptionInfo1.getCode(), is(VALUE1));
        assertThat(exceptionInfo1.getExceptionClassname(), is(KEY1));
        assertThat(exceptionInfo1.getReason(), is(RESOURCE1));
    }

    /**
     * Provides a test implementation of an ExceptionInfoProvider.
     */
    static class TestExceptionInfoProvider implements ExceptionInfoProvider
    {
        /**
         * @see  com.viasat.wildblue.common.exception.ExceptionInfoProvider#getExceptionMap()
         */
        @Override public Map<String, String> getExceptionMap()
        {
            Map<String, String> answer = new LinkedHashMap<String, String>();

            answer.put(KEY0 + ".code", VALUE0);
            answer.put(KEY1 + ".code", VALUE1);

            return answer;
        }

        /**
         * @see  com.viasat.wildblue.common.exception.ExceptionInfoProvider#getResourceBundle()
         */
        @Override public ResourceBundle getResourceBundle()
        {
            ResourceBundle answer = new ResourceBundle()
                {
                    @Override public Enumeration<String> getKeys()
                    {
                        List<String> list = new ArrayList<String>();
                        list.add(RESOURCE_KEY0);
                        list.add(RESOURCE_KEY1);

                        return Collections.enumeration(list);
                    }

                    @Override protected Object handleGetObject(String key)
                    {
                        String resource = null;

                        if (RESOURCE_KEY0.equals(key))
                        {
                            resource = RESOURCE0;
                        }
                        else if (RESOURCE_KEY1.equals(key))
                        {
                            resource = RESOURCE1;
                        }

                        return resource;
                    }
                };

            return answer;
        }
    }
}
