package com.viasat.wildblue.common.validator;

import org.apache.commons.validator.Field;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.util.ValidatorUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Validation class to perform North America phone number validation. Phone
 * Number must be at least 10 digits. PhoneNumber may or may not include an
 * extension Extension is delimited by an upper or lower case X Extension
 */
public class PhoneValidator
{
    /**
     * North America Phone Number Pattern. Phone Number must be at least 10
     * digits. PhoneNumber may or may not include an extension Extension is
     * delimited by an upper or lower case X Extension may be 1 to 5 digits the
     * X may have zero or more spaced before and/or after the X Sample formats:
     * 1234567890 123-456-7890 123.456.7890 1234567890x12345 123-456-7890 x1234
     * 123.456.7890x 12345 123-456-7890 x 123
     */
    private static final Pattern PHONE_PATTERN = Pattern.compile(
            "[(]?\\d{3}[)]?[-|.|\\s]?\\d{3}[-|.|\\s]?\\d{4}\\s*([x|X]{1}\\s*\\d{1,6})?");

    /**
     * Get phone extension
     *
     * @param   phoneNumber  full phone number with extension
     *
     * @return  extension number
     */
    public static String getPhoneExtension(String phoneNumber)
    {
        if ((phoneNumber == null) || (phoneNumber.length() < 12))
        {
            return null;
        }

        if (!validate(phoneNumber))
        {
            return null;
        }

        String phone = getPhoneDigits(phoneNumber);

        if (phone.length() < 11)
        {
            return null;
        }
        else
        {
            return phone.substring(10);
        }
    }

    /**
     * Get the phone number exclude extension in the format xxx-xxx-xxxx
     *
     * @param   phoneNumber
     *
     * @return  phone number exclude extension
     */
    public static String getPhoneNumber(String phoneNumber)
    {
        if ((phoneNumber == null) || (phoneNumber.length() < 10))
        {
            return null;
        }

        if (!validate(phoneNumber))
        {
            return null;
        }

        String phone = getPhoneDigits(phoneNumber);
        StringBuilder sb = new StringBuilder(12);
        sb.append(phone.substring(0, 3));
        sb.append("-");
        sb.append(phone.substring(3, 6));
        sb.append("-");
        sb.append(phone.substring(6, 10));
        return sb.toString();
    }

    /**
     * Validate a North America phone number
     *
     * @param   phoneNumber  Phone Number
     *
     * @return  A value of true/false indicating if the phone number is valid
     */
    public static boolean validate(String phoneNumber)
    {
        boolean isValid = true;

        if (!GenericValidator.isBlankOrNull(phoneNumber))
        {
            Matcher m = PHONE_PATTERN.matcher(phoneNumber);
            isValid = m.matches();
        }

        return isValid;
    }

    /**
     * Validate North America phone number. Phone Number must be at least 10
     * digits. PhoneNumber may or may not include an extension Extension is
     * delimited by an upper or lower case X Extension may be 1 to 5 digits the
     * X may have zero or more spaced before and/or after the X Sample formats:
     * 1234567890 123-456-7890 123.456.7890 1234567890x12345 123-456-7890 x1234
     * 123.456.7890x 12345 123-456-7890 x 123
     *
     * @param   bean   The Object having the field/property to check.
     * @param   field  The Field object specifying which field/property to check
     *                 on the given bean.
     *
     * @return  A value of true/false indicating if the value is valid.
     */
    public boolean validatePhone(Object bean, Field field)
    {
        String value = ValidatorUtils.getValueAsString(bean,
                field.getProperty());
        return validate(value);
    }

    /**
     * Remove all the formatting, return phone number with only digits
     *
     * @param   phoneNumber  full phone number
     *
     * @return  all digits phone number
     */
    private static String getPhoneDigits(String phoneNumber)
    {
        if (phoneNumber == null)
        {
            return null;
        }

        return phoneNumber.replaceAll("[^0-9]", "");
    }
}
