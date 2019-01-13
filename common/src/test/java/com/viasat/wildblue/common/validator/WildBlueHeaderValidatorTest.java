package com.viasat.wildblue.common.validator;

import com.viasat.wildblue.common.commondata.ValidationError;
import com.viasat.wildblue.common.header.InvokedBy;
import com.viasat.wildblue.common.header.WildBlueHeader;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.List;


public class WildBlueHeaderValidatorTest extends BaseValidatorTest
{
    @Test public void testValidationFails()
    {
        try
        {
            // InvokedBy.application is missing.
            WildBlueHeader wbh = new WildBlueHeader();
            wbh.setInvokedBy(new InvokedBy());
            wbh.getInvokedBy().setUsername("GumbyPokieMahn");

            List<ValidationError> validationErrors;

            WildBlueHeaderValidator wbhValidator =
                new WildBlueHeaderValidator();
            validationErrors = wbhValidator.validate(wbh);

            assertNotNull("Should have validation errors. Did not receive any.",
                parseValidationErrors(validationErrors));

            assertTrue("1 validation error expected, received: "
                + validationErrors.size(), validationErrors.size() == 1);

            // Make sure we have precisely the error messages we expected.
            assertErrorMessageExists(validationErrors,
                getExpectedValidationError("InvokedBy", "application",
                    "required"));

            // InvokedBy.application & username are missing.
            wbh = new WildBlueHeader();
            wbh.setInvokedBy(new InvokedBy());
            wbh.getInvokedBy().setUsername("");
            validationErrors = wbhValidator.validate(wbh);

            assertNotNull("Should have validation errors. Did not receive any.",
                parseValidationErrors(validationErrors));

            assertTrue("2 validation error expected, received: "
                + validationErrors.size(), validationErrors.size() == 2);

            // Make sure we have precisely the error messages we expected.
            assertErrorMessageExists(validationErrors,
                getExpectedValidationError("InvokedBy", "application",
                    "required"));
            assertErrorMessageExists(validationErrors,
                getExpectedValidationError("InvokedBy", "username",
                    "required"));

            // InvokedBy.application & username are missing.
            wbh = new WildBlueHeader();
            wbh.setInvokedBy(new InvokedBy());
            validationErrors = wbhValidator.validate(wbh);

            assertNotNull("Should have validation errors. Did not receive any.",
                parseValidationErrors(validationErrors));

            assertTrue("2 validation error expected, received: "
                + validationErrors.size(), validationErrors.size() == 2);

            // Make sure we have precisely the error messages we expected.
            assertErrorMessageExists(validationErrors,
                getExpectedValidationError("InvokedBy", "application",
                    "required"));
            assertErrorMessageExists(validationErrors,
                getExpectedValidationError("InvokedBy", "username",
                    "required"));

            // WildBlueHeader.InvokedBy is null.
            wbh = new WildBlueHeader();
            validationErrors = wbhValidator.validate(wbh);

            assertNotNull("Should have validation errors. Did not receive any.",
                parseValidationErrors(validationErrors));

            //  for (ValidationError error : validationErrors)
            // {
            //   System.out.println(error);
            //}

            assertTrue("1 validation error expected, received: "
                + validationErrors.size(), validationErrors.size() == 1);

            // Make sure we have precisely the error messages we expected.
            assertErrorMessageExists(validationErrors,
                getExpectedValidationError("WildBlueHeader", "invokedBy",
                    "required"));

            // Case for when whole header is null.
            wbh = null;
            validationErrors = wbhValidator.validate(wbh);
            assertNotNull(
                "WBH is null. Should have validation error(s). Did not receive any.",
                parseValidationErrors(validationErrors));
        }
        catch (Exception e)
        {
            assertNull("Unexpected exception occured: " + String.valueOf(e), e);
        }
    }

    @Test public void testValidationSuccess()
    {
        try
        {
            WildBlueHeader wbh = new WildBlueHeader();
            wbh.setInvokedBy(new InvokedBy());
            wbh.getInvokedBy().setApplication("UnitTestApp");
            wbh.getInvokedBy().setUsername("GumbyPokieMahn");

            WildBlueHeaderValidator wbhValidator =
                new WildBlueHeaderValidator();
            List<ValidationError> validationErrors = wbhValidator.validate(wbh);

            assertNull(
                "Validation errors not expected, WildBlueHeader object is supposed to be valid in this test case.",
                parseValidationErrors(validationErrors));
        }
        catch (Exception e)
        {
            assertNull("Unexpected exception occured: " + String.valueOf(e), e);
        }
    }
}
