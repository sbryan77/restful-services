package com.viasat.wildblue.common.validator;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.validator.Field;
import org.apache.commons.validator.ValidatorException;

import com.viasat.common.fault.AbstractValidatorTool;

public class DateRangeValidator
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
    public static Date getDate(Object bean, String property)
    {

        Object value = AbstractValidatorTool.getPropertyValue(bean,
               property);

        if (value != null)
        {
            Calendar todayStartOfDay = Calendar.getInstance();
            todayStartOfDay.set(Calendar.MILLISECOND, 0);
            todayStartOfDay.set(Calendar.SECOND, 0);
            todayStartOfDay.set(Calendar.MINUTE, 0);
            todayStartOfDay.set(Calendar.HOUR_OF_DAY, 0);

            if (value instanceof Date)
				return (Date)value;

            if (value instanceof Calendar)
            	return ((Calendar)value).getTime();
        }

        return null;
    }

	public boolean validateDateBefore(Object bean, Field field)
			throws ValidatorException
	{
		Date fromDate = getDate(bean, field.getProperty());

		if (fromDate != null) {
			String conditionField = field.getVarValue("conditionField");
			Date toDate = getDate(bean, conditionField);
			if (toDate != null)
				return (toDate.after(fromDate));
		}

		return true;
	}
}
