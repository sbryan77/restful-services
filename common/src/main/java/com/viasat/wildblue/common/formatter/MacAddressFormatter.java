package com.viasat.wildblue.common.formatter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * The Class MacAddressFormatter.
 */
public class MacAddressFormatter
{
    /** Delimiter. */
    public static String DELIMITER = ":";

    /**  */
    private static final String REGEX =
        "((?:(\\d{1,2}|[a-fA-F]{1,2}){2})(?::|-*)){6}";

    /**
     * valid mac address pattern. Valid format is: XX:XX:XX:XX:XX:XX, where X is
     * a digit or "a","A","b","B","c","C","d","D","e","E","f", or "F". Also,
     * instead of ":" as a delimiter, "-" is allowed. Unfortunately, a trailing
     * delimiter is also allowed by the regex pattern.
     */
    private static final Pattern PATTERN = Pattern.compile(REGEX);

    /**
     * Format mac address.
     *
     * @param   unformattedMac  the unformatted mac
     *
     * @return  the string
     *
     * @throws  InvalidMacAddressException  the invalid mac address exception
     */
    public static String formatMacAddress(final String unformattedMac)
        throws InvalidMacAddressException
    {
        if (unformattedMac == null)
        {
            throw new InvalidMacAddressException("Value is NULL.",
                unformattedMac);
        }

        final StringBuilder formattedMac = new StringBuilder(17);

        for (final char xdigit : unformattedMac.toCharArray())
        {
            // Hexadecimal character.. 0-9,
            if (String.valueOf(xdigit).matches("[0-9A-F]"))
            {
                formattedMac.append(xdigit);
            }
            // Lower case hexadecimal character.
            else if (String.valueOf(xdigit).matches("[a-f]"))
            {
                formattedMac.append(Character.toUpperCase(xdigit));
            }
            else if (!String.valueOf(xdigit).matches("[:.-]"))
            {
                throw new InvalidMacAddressException(
                    "Contains non-hexadecimal character [" + xdigit + "]",
                    unformattedMac);
            }
        }

        if (formattedMac.length() != 12)
        {
            throw new InvalidMacAddressException("Must be 12 characters long.",
                unformattedMac);
        }

        // all good! now insert the delimiters...
        formattedMac.insert(2, DELIMITER);
        formattedMac.insert(5, DELIMITER);
        formattedMac.insert(8, DELIMITER);
        formattedMac.insert(11, DELIMITER);
        formattedMac.insert(14, DELIMITER);

        return formattedMac.toString();
    }

    public static boolean validate(String macAddress)
    {
        boolean isValid = true;

        if (macAddress != null)
        {
            Matcher m = PATTERN.matcher(macAddress);
            isValid = m.matches();
        }

        return isValid;
    }
}
