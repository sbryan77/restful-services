package com.viasat.wildblue.common.validator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class PhoneValidatorTest extends BaseValidatorTest
{
    @Test public void testGetPhoneExtension() throws Exception
    {
        String phoneExt = PhoneValidator.getPhoneExtension(
                "123.456.7890x 12345");
        assertEquals("Get Phone Extension 123.456.7890x 12345", "12345",
            phoneExt);

        phoneExt = PhoneValidator.getPhoneExtension("123-456-7890x 12345");
        assertEquals("Get Phone Extension", "12345", phoneExt);

        phoneExt = PhoneValidator.getPhoneExtension("(123)456-7890x 12345");
        assertEquals("Get Phone Extension (123)456-7890x 12345", "12345",
            phoneExt);
    }

    @Test public void testGetPhoneNumber() throws Exception
    {
        String phoneNumber = PhoneValidator.getPhoneNumber(
                "123.456.7890x 12345");
        assertEquals("Get Phone Number 123.456.7890x 12345", "123-456-7890",
            phoneNumber);

        phoneNumber = PhoneValidator.getPhoneNumber("123-456-7890x 12345");
        assertEquals("Get Phone Number 123-456-7890x 12345", "123-456-7890",
            phoneNumber);

        phoneNumber = PhoneValidator.getPhoneNumber("(123)456-7890x 12345");
        assertEquals("Get Phone Number (123)456-7890x 12345", "123-456-7890",
            phoneNumber);
    }

    @Test public void testValidate() throws Exception
    {
        boolean result = PhoneValidator.validate("1234567890");
        assertTrue("Match 1234567890", result);

        result = PhoneValidator.validate("123-456-7890");
        assertTrue("Match 123-456-7890", result);

        result = PhoneValidator.validate("(123)456-7890");
        assertTrue("Match 123-456-7890", result);

        result = PhoneValidator.validate("123 456 7890");
        assertTrue("Match 123 456 7890", result);

        result = PhoneValidator.validate("123.456.7890");
        assertTrue("Match 123.456.7890", result);

        result = PhoneValidator.validate("1234567890x12345");
        assertTrue("Match 1234567890x12345", result);

        result = PhoneValidator.validate("123-456-7890 x1234");
        assertTrue("Match 123-456-7890 x1234", result);

        result = PhoneValidator.validate("123.456.7890x 12345");
        assertTrue("Match 123.456.7890x 12345", result);

        result = PhoneValidator.validate("23-456-7890");
        assertFalse("Not Match 23-456-7890", result);

        result = PhoneValidator.validate("123.456.7890x 123456");
        assertTrue("Match 123.456.7890x 123456", result);

        result = PhoneValidator.validate("123.456.7890x 1234567");
        assertFalse("Not Match 123.456.7890x 1234567", result);

        result = PhoneValidator.validate("123.456.7890x");
        assertFalse("Not Match 123.456.7890x", result);
    }
}
