package com.viasat.common.validator;

import com.viasat.wildblue.common.validator.ZipCodeValidator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class ZipCodeValidatorTest extends BaseValidatorTest
{
    @Test public void testGetZip4() throws Exception
    {
        String result = ZipCodeValidator.getZip4("12345-6789");
        assertEquals("Get Zip 4 from 12345-6789", "6789", result);

        result = ZipCodeValidator.getZip4("123456789");
        assertEquals("Get Zip 4 from 123456789", "6789", result);

        result = ZipCodeValidator.getZip4("12345678");
        assertNull("Get Zip 4 from 12345678", result);

        result = ZipCodeValidator.getZip4("1234567890");
        assertNull("Get Zip 4 from 1234567890", result);

        result = ZipCodeValidator.getZip4("12345+6789");
        assertNull("Get Zip 4 from 12345+6789", result);
    }

    @Test public void testValidate() throws Exception
    {
        boolean result = ZipCodeValidator.validate("12345");
        assertTrue("Match 12345", result);

        result = ZipCodeValidator.validate("12345-6789");
        assertTrue("Match 12345-6789", result);

        result = ZipCodeValidator.validate("123456789");
        assertTrue("Match 123456789", result);

        result = ZipCodeValidator.validate("12345+6789");
        assertFalse("No Match 12345+6789", result);

        result = ZipCodeValidator.validate("1234");
        assertFalse("No Match 1234", result);

        result = ZipCodeValidator.validate("12345-678");
        assertFalse("No Match 12345-678", result);

        result = ZipCodeValidator.validate("12345--6789");
        assertFalse("No Match 12345--6789", result);

        result = ZipCodeValidator.validate("12345--");
        assertFalse("No Match 12345--", result);
    }
}
