package com.viasat.wildblue.common.formatter;

import static org.hamcrest.CoreMatchers.is;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


/**
 * The Class MacAddressFormatterTest.
 */
public class MacAddressFormatterTest
{
    /**
     * Test invalid mac address.
     */
    @Test public void invalidMacAddress1()
    {
        performInvalidMacAddressTest("MAC with non-hex", "AXXS12900s");
    }

    /**
     * Test invalid mac address.
     */
    @Test public void invalidMacAddress2()
    {
        performInvalidMacAddressTest("MAC too short", "a0-ef-01-b7");
    }

    /**
     * Test invalid mac address.
     */
    @Test public void invalidMacAddress3()
    {
        performInvalidMacAddressTest("MAC too long", "a0-ef-01-b7-ae0fb712");
    }

    /**
     * Test null mac address.
     */
    @Test public void invalidNullMacAddress()
    {
        performInvalidMacAddressTest("NULL MAC Address", null);
    }

    /**
     * Test valid mac address.
     */
    @Test public void testValidMacAddress1()
    {
        performValidMacAddressTest("AA:BB:CC:DD:EE:FF");
    }

    /**
     * Test valid mac address.
     */
    @Test public void testValidMacAddress2()
    {
        performValidMacAddressTest("aa:bF:Cc:DD:00:99");
    }

    /**
     * Test valid mac address.
     */
    @Test public void testValidMacAddress3()
    {
        performValidMacAddressTest("aa.bF.Cc.DD.00.99");
    }

    /**
     * Test valid mac address.
     */
    @Test public void testValidMacAddress4()
    {
        performValidMacAddressTest("a1bFCc.D033d0");
    }

    /**
     * Test valid mac address.
     */
    @Test public void testValidMacAddress5()
    {
        performValidMacAddressTest("aa-bF-Cc-DD-00-99");
    }

    /**
     * Test valid mac address.
     */
    @Test public void testValidMacAddress6()
    {
        performValidMacAddressTest("B1a0fe0006FA");
    }

    /**
     * Test valid mac address.
     */
    @Test public void testValidMacAddress7()
    {
        performValidMacAddressTest("00A0.BC.26-4F.8A");
    }

    /**
     * Perform invalid mac address test.
     *
     * @param  invalidCondition  the invalid condition
     * @param  testMacAddress    the test mac address
     */
    private void performInvalidMacAddressTest(final String invalidCondition,
        final String testMacAddress)
    {
        System.out.print("InvalidMacTest [" + testMacAddress + "] "
            + invalidCondition);

        Exception exception = null;

        try
        {
            InvalidMacAddressException invalidMacException = null;

            try
            {
                MacAddressFormatter.formatMacAddress(testMacAddress);
            }
            catch (final InvalidMacAddressException ex1)
            {
                invalidMacException = ex1;
            }
            catch (final Exception ex)
            {
                exception = ex;
            }

            assertNotNull(invalidCondition + " should be invalid",
                invalidMacException);
            assertNull("MacAddressFormatter threw Exception: "
                + ((exception != null)
                    ? (exception.getClass().getName() + ", message: "
                        + exception.getMessage()) : ""), exception);

            final String message = invalidMacException.getMessage();
            System.out.println(" Result: " + message);
        }
        finally
        {
            if (exception != null)
            {
                exception.printStackTrace();
            }
        }
    }

    /**
     * Perform valid mac address test.
     *
     * @param  testMacAddress  the test mac address
     */
    private void performValidMacAddressTest(final String testMacAddress)
    {
        System.out.print("ValidMacTest [" + testMacAddress + "] ");

        Exception exception = null;

        try
        {
            InvalidMacAddressException invalidMacException = null;
            String macAddress = null;

            try
            {
                macAddress = MacAddressFormatter.formatMacAddress(
                        testMacAddress);
            }
            catch (final InvalidMacAddressException ex1)
            {
                invalidMacException = ex1;
            }
            catch (final Exception ex)
            {
                exception = ex;
                ex.printStackTrace();
            }

            assertNull("Valid MAC Address, caught InvalidMacAddressException",
                invalidMacException);
            assertNull("MacAddressFormatter threw Exception: "
                + ((exception != null)
                    ? (exception.getClass().getName() + ", message: "
                        + exception.getMessage()) : ""), exception);

            assertNotNull("Formatted MAC Address should not be null",
                macAddress);

            final int length = macAddress.length();
            assertThat("Formatted MAC should be 17 digits.", length, is(17));
            assertThat("Formatted MAC without delimiters should be 12 digits.",
                macAddress.replaceAll("[" + MacAddressFormatter.DELIMITER + "]",
                    "").length(), is(12));
            assertThat("Formatted MAC should contain be 6 blocks.",
                macAddress.split(MacAddressFormatter.DELIMITER).length, is(6));
            assertTrue(
                "Formatted MAC should contain numbers and uppercase letters with delimiters.",
                String.valueOf(macAddress).matches(
                    "^([0-9A-F]{2}:){5}([0-9A-F]{2})$"));

            System.out.println(" Result: " + macAddress);
        }
        finally
        {
            if (exception != null)
            {
                exception.printStackTrace();
            }
        }
    }
}
