package com.viasat.wildblue.common.formatter;

/**
 * Format a phone number.
 */
public class PhoneFormatter
{
    /**
     * Format a phone and extension to a format: phone, plus "x", plus extension
     *
     * @param   phone      10 digits phone number
     * @param   extension  phone extension
     *
     * @return
     */
    public static String format(String phone, String extension)
    {
        String phoneNumber = phone;

        if (extension != null)
        {
            phoneNumber = phoneNumber + "x" + extension;
        }

        return phoneNumber;
    }
}
