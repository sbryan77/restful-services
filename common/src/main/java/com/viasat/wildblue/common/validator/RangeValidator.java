package com.viasat.wildblue.common.validator;

import org.apache.commons.validator.Field;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.util.ValidatorUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Validation class to perform macAddress validation(s).
 */
public class RangeValidator
{
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LoggerFactory.getLogger(
            RangeValidator.class.getName());

    /**
     * Check if the given double value is within specified range. The xml
     * configuration for the field validation must include a var named
     * "minValue" with a double value, and a var named "maxValue" with a double
     * value.
     *
     * @param   bean   The Object having the field/property to check.
     * @param   field  The Field object specifying which field/property to check
     *                 on the given bean.
     *
     * @return  A value of true/false indicating if the value is in the range.
     */
    public boolean isInRange(Object bean, Field field)
    {
        boolean isInRange = false;
        String valueStr = ValidatorUtils.getValueAsString(bean,
                field.getProperty());

        if ((valueStr == null) || (valueStr.trim().length() == 0))
        {
            return true;
        }

        try
        {
            int value = new Integer(valueStr);
            int minValue = new Integer(field.getVarValue("minValue"));
            int maxValue = new Integer(field.getVarValue("maxValue"));
            isInRange = GenericValidator.isInRange(value, minValue, maxValue);
        }
        catch (Exception e)
        {
            isInRange = false;
        }

        return isInRange;
    }
}
