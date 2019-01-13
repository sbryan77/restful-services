package com.viasat.wildblue.common.validator;

import org.apache.commons.validator.Field;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.util.ValidatorUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Validation class to perform US zipCode/PostalCode validation(s).
 */
public class ZipCodeValidator
{
    /**
     * valid US zipCode pattern.  Valid formats are: XXXXX, XXXXX-XXXX,
     * XXXXXXXXX
     */
    private static final Pattern ZIP_PATTERN = Pattern.compile(
            "\\d{5}([\\-]?\\d{4})?");

    /**
     * US zipCode with zip4 code pattern. Valid formats are: XXXXX-XXXX,
     * XXXXXXXXX
     */
    private static final Pattern ZIP4_PATTERN = Pattern.compile(
            "\\d{5}[\\-]?\\d{4}");

    /**
     * Get the zip4 code. Valid formats are: XXXXX-XXXX, XXXXXXXXX
     *
     * @param   postalCode  Full zipCode
     *
     * @return  the zip4 code, or null if postalCode is not valid
     */
    public static String getZip4(String postalCode)
    {
        if ((postalCode == null) || (postalCode.length() < 9)
            || (postalCode.length() > 10))
        {
            return null;
        }

        String zip4 = null;
        Matcher m = ZIP4_PATTERN.matcher(postalCode);

        if (m.matches())
        {
            zip4 = postalCode.substring(postalCode.length() - 4);
        }

        return zip4;
    }

    /**
     * Validate US zipCode.  Valid formats are: XXXXX, XXXXX-XXXX, XXXXXXXXX
     *
     * @param   zipCode  US zipCode
     *
     * @return  A value of true/false indicating if the zipCode is valid
     */
    public static boolean validate(String zipCode)
    {
        boolean isValid = true;

        if (zipCode != null)
        {
            Matcher m = ZIP_PATTERN.matcher(zipCode);
            isValid = m.matches();
        }

        return isValid;
    }

    /**
     * Validate US zipCode.  Valid formats are: XXXXX, XXXXX-XXXX, XXXXXXXXX
     *
     * @param   bean   The Object having the field/property to check.
     * @param   field  The Field object specifying which field/property to check
     *                 on the given bean.
     *
     * @return  A value of true/false indicating if the value is valid.
     */
    public boolean validateZipcode(Object bean, Field field)
    {
        String value = ValidatorUtils.getValueAsString(bean,
                field.getProperty());

        if (!GenericValidator.isBlankOrNull(value))
        {
            return validate(value);
        }
        else
        {
            // Null is valid, this is not a required field check, just a zipcode validation.
            return true;
        }
    }
}
