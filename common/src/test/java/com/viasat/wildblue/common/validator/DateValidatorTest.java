package com.viasat.wildblue.common.validator;

import com.viasat.wildblue.common.commondata.ValidationError;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class DateValidatorTest extends BaseValidatorTest
{
    @Test public void testValidateCurrentOrFutureDate()
    {
        ValidationBean bean = new ValidationBean("testDates");
        bean.setField("date2", new Date());

        List<ValidationError> errors = getValidatorTool().perform(bean);
        assertTrue(errors.isEmpty());

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 2);

        bean.setField("date2", cal.getTime());

        errors = getValidatorTool().perform(bean);
        assertTrue(errors.isEmpty());

        cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -2);

        bean.setField("date2", cal.getTime());

        errors = getValidatorTool().perform(bean);
        assertFalse(errors.isEmpty());

        // for (ValidationError error : errors)
        // {
        //    System.out.println(error);
        // }

        assertTrue(errors.size() == 1);
        assertEquals(errors.get(0).getErrorCode(), "VALIDATION_ERROR");
        assertTrue(errors.get(0).getMessage().startsWith("date2"));
    }

    @Test public void testValidateCurrentOrFutureMonth()
    {
        //As near as I can tell, this validates if a date is in the same month or future.
        //Lazy here and only testing that today's date passes.

        ValidationBean bean = new ValidationBean("testDates");
        bean.setField("date3", new Date());

        List<ValidationError> errors = getValidatorTool().perform(bean);
        assertTrue(errors.isEmpty());
    }

    @Test public void testValidateCurrentOrPastDate()
    {
        ValidationBean bean = new ValidationBean("testDates");
        bean.setField("date1", new Date());

        List<ValidationError> errors = getValidatorTool().perform(bean);
        assertTrue(errors.isEmpty());

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -2);

        bean.setField("date1", cal.getTime());

        errors = getValidatorTool().perform(bean);
        assertTrue(errors.isEmpty());

        cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 2);

        bean.setField("date1", cal.getTime());

        errors = getValidatorTool().perform(bean);
        assertFalse(errors.isEmpty());

        //        for (ValidationError error : errors)
        //        {
        //            System.out.println(error);
        //        }

        assertTrue(errors.size() == 1);
        assertEquals(errors.get(0).getErrorCode(), "VALIDATION_ERROR");
        assertTrue(errors.get(0).getMessage().startsWith("date1"));
    }
}
