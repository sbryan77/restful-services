package com.viasat.wildblue.common.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.validator.Field;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.util.ValidatorUtils;


/**
 * Validation class to perform region (in Address) validation.
 */
public class RegionValidator
{
    /**
     * valid region pattern.  Valid formats are: XX, XXX.
     */
    private static final Pattern REGION_PATTERN = Pattern.compile("[A-Z]{2,3}");

    /**
     * Validate region.  Valid formats are: XX, XXX.
     *
     * @param   region  region
     *
     * @return  A value of true/false indicating if the region is valid
     */
    public static boolean validate(String region)
    {
        boolean isValid = true;

        if (region != null)
        {
            Matcher m = REGION_PATTERN.matcher(region);
            isValid = m.matches();
        }

        return isValid;
    }

    /**
     * Validate region.  Valid formats are: XX, XXX.
     *
     * @param   bean   The Object having the field/property to check.
     * @param   field  The Field object specifying which field/property to check
     *                 on the given bean.
     *
     * @return  A value of true/false indicating if the value is valid.
     */
    public boolean validateRegion(Object bean, Field field)
    {
        String value = ValidatorUtils.getValueAsString(bean,
                field.getProperty());

        if (!GenericValidator.isBlankOrNull(value))
        {
            return validate(value);
        }
        else
        {
            // Null is valid, this is not a required field check, just a region validation.
            return true;
        }
    }
}
