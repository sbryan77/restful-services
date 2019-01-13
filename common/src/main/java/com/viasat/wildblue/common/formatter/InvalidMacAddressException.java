package com.viasat.wildblue.common.formatter;

/**
 * The Class InvalidMacAddressException.
 */
public class InvalidMacAddressException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new InvalidMacAddressException.
     *
     * @param  macAddress  the MAC address
     */
    public InvalidMacAddressException(String reason, String macAddress)
    {
        super("MAC Address '" + macAddress + "' is invalid. " + reason);
    }
}
