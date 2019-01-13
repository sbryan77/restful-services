package com.viasat.wildblue.common.validator;

import com.viasat.wildblue.common.formatter.MacAddressFormatter;

import org.apache.commons.validator.Field;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.util.ValidatorUtils;


/**
 * Validation class to perform macAddress validation(s).
 */
public class MacAddressFormatValidator
{
    /**
     * Validate MAC Address
     *
     * @param   bean   The Object having the field/property to check.
     * @param   field  The Field object specifying which field/property to check
     *                 on the given bean.
     *
     * @return  A value of true/false indicating if the value is valid.
     */
    public boolean validateMacAddress(Object bean, Field field)
    {
        String value = ValidatorUtils.getValueAsString(bean,
                field.getProperty());

        if (!GenericValidator.isBlankOrNull(value))
        {
            return MacAddressFormatter.validate(value);
        }
        else
        {
            // Null is valid, this is not a required field check, just a zipcode
            // validation.
            return true;
        }
    }
}
