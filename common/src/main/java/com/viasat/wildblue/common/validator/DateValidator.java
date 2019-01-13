package com.viasat.wildblue.common.validator;

import com.viasat.common.fault.AbstractValidatorTool;

import org.apache.commons.validator.Field;

import java.util.Calendar;
import java.util.Date;


/**
 * Validator class with methods for performing validations on Date objects.
 */
public class DateValidator
{
    /**
     * Validates that a given Date object is greater than or equal to today
     * (where 'today' is considered the start of this day).
     *
     * @param   bean   The Object having the field/property to check.
     * @param   field  The Field object specifying which field/property to check
     *                 on the given bean.
     *
     * @return  A value of true/false indicating if the value is valid.
     */
    public static boolean validateCurrentOrFutureDate(Object bean, Field field)
    {
        boolean isValid = true;
        Object value = AbstractValidatorTool.getPropertyValue(bean,
                field.getProperty());

        if (value != null)
        {
            Calendar todayStartOfDay = Calendar.getInstance();
            todayStartOfDay.set(Calendar.MILLISECOND, 0);
            todayStartOfDay.set(Calendar.SECOND, 0);
            todayStartOfDay.set(Calendar.MINUTE, 0);
            todayStartOfDay.set(Calendar.HOUR_OF_DAY, 0);

            if (value instanceof Date)
            {
                if (((Date)value).before(todayStartOfDay.getTime()))
                {
                    isValid = false;
                }
            }
            else if (value instanceof Calendar)
            {
                if (((Calendar)value).before(todayStartOfDay))
                {
                    isValid = false;
                }
            }
            else
            {
                isValid = false;
            }
        }

        return isValid;
    }

    /**
     * Validate current or future month.
     *
     * @param   bean   the bean
     * @param   field  the field
     *
     * @return  true, if successful
     */
    public static boolean validateCurrentOrFutureMonth(Object bean, Field field)
    {
        boolean isValid = true;
        Object value = AbstractValidatorTool.getPropertyValue(bean,
                field.getProperty());

        if (value != null)
        {
            if (value instanceof Date)
            {
                Calendar dateToValidate = Calendar.getInstance();
                dateToValidate.setTime((Date)value);

                int todaysYear = Calendar.getInstance().get(Calendar.YEAR);

                if (dateToValidate.get(Calendar.YEAR) < todaysYear)
                {
                    isValid = false;
                }
                else if (dateToValidate.get(Calendar.YEAR) == todaysYear)
                {
                    int todaysMonth = Calendar.getInstance().get(
                            Calendar.MONTH);

                    if (dateToValidate.get(Calendar.MONTH) < todaysMonth)
                    {
                        isValid = false;
                    }
                }
            }
            else if (value instanceof Calendar)
            {
                Calendar dateToValidate = (Calendar)value;
                int todaysYear = Calendar.getInstance().get(Calendar.YEAR);

                if (dateToValidate.get(Calendar.YEAR) < todaysYear)
                {
                    isValid = false;
                }
                else if (dateToValidate.get(Calendar.YEAR) == todaysYear)
                {
                    int todaysMonth = Calendar.getInstance().get(
                            Calendar.MONTH);

                    if (dateToValidate.get(Calendar.MONTH) < todaysMonth)
                    {
                        isValid = false;
                    }
                }
            }
            else
            {
                isValid = false;
            }
        }

        return isValid;
    }

    /**
     * Validates that a given Date object is less than or equal to today (where
     * 'today' is considered the start of this day).
     *
     * @param   bean   The Object having the field/property to check.
     * @param   field  The Field object specifying which field/property to check
     *                 on the given bean.
     *
     * @return  A value of true/false indicating if the value is valid.
     */
    public static boolean validateCurrentOrPastDate(Object bean, Field field)
    {
        boolean isValid = true;
        Object value = AbstractValidatorTool.getPropertyValue(bean,
                field.getProperty());

        if (value != null)
        {
            Calendar todayStartOfDay = Calendar.getInstance();
            todayStartOfDay.set(Calendar.MILLISECOND, 0);
            todayStartOfDay.set(Calendar.SECOND, 0);
            todayStartOfDay.set(Calendar.MINUTE, 0);
            todayStartOfDay.set(Calendar.HOUR_OF_DAY, 0);

            if (value instanceof Date)
            {
                Calendar inputDate = Calendar.getInstance();
                inputDate.setTime((Date)value);
                inputDate.set(Calendar.MILLISECOND, 0);
                inputDate.set(Calendar.SECOND, 0);
                inputDate.set(Calendar.MINUTE, 0);
                inputDate.set(Calendar.HOUR_OF_DAY, 0);

                if (inputDate.after(todayStartOfDay))
                {
                    isValid = false;
                }
            }
            else if (value instanceof Calendar)
            {
                Calendar inputDate = (Calendar)value;

                if (inputDate.after(todayStartOfDay))
                {
                    isValid = false;
                }
            }
            else
            {
                isValid = false;
            }
        }

        return isValid;
    }
}
