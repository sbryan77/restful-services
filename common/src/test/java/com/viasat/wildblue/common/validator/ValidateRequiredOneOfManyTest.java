package com.viasat.wildblue.common.validator;

import com.viasat.wildblue.common.commondata.ValidationError;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;


public class ValidateRequiredOneOfManyTest extends BaseValidatorTest
{
    @BeforeClass public static void setTestValidationXml()
    {
        ValidatorTool.setConfigurationResource(
            "test-requiredOneOfMany-validation.xml");
    }

    @Test public void testRequiredOneOfMany1Error()
    {
        ValidationBean form;
        List<ValidationError> validationErrors;

        // Should fail
        form = new ValidationBean("testRequiredOneOfMany1");
        form.setField("fieldA", null);
        form.setField("fieldB", null);
        validationErrors = getValidatorTool().perform(form);
        assertNotNull(
            "Address should have validation errors. Did not receive any.",
            parseValidationErrors(validationErrors));

        assertTrue("2 validation errors expected, received: "
            + validationErrors.size(), validationErrors.size() == 2);

        // System.out.println(parseValidationErrors(validationErrors));
    }

    @Test public void testRequiredOneOfMany1Success()
    {
        try
        {
            ValidationBean form;
            List<ValidationError> validationErrors;

            // Should be valid... no validation errors
            form = new ValidationBean("testRequiredOneOfMany1");
            form.setField("fieldA", null);
            form.setField("fieldB", "valueB");
            validationErrors = getValidatorTool().perform(form);
            assertNull("ValidationErrors not expected",
                parseValidationErrors(validationErrors));

            // Should be valid... no validation errors
            form = new ValidationBean("testRequiredOneOfMany1");
            form.setField("fieldA", "valueA");
            form.setField("fieldB", "valueB");
            validationErrors = getValidatorTool().perform(form);
            assertNull("ValidationErrors not expected",
                parseValidationErrors(validationErrors));

            // Should be valid... no validation errors
            form = new ValidationBean("testRequiredOneOfMany1");
            form.setField("fieldB", "valueB");
            validationErrors = getValidatorTool().perform(form);
            assertNull("ValidationErrors not expected",
                parseValidationErrors(validationErrors));

            // Should be valid... no validation errors
            form = new ValidationBean("testRequiredOneOfMany1");
            form.setField("fieldA", "valueA");
            validationErrors = getValidatorTool().perform(form);
            assertNull("ValidationErrors not expected",
                parseValidationErrors(validationErrors));
        }
        catch (Exception e)
        {
            assertNull("Unexpected exception occured: " + String.valueOf(e), e);
        }
    }

    @Test public void testRequiredOneOfMany2Error()
    {
        try
        {
            ValidationBean form;
            List<ValidationError> validationErrors;

            // Should fail
            form = new ValidationBean("testRequiredOneOfMany2");
            form.setField("fieldA", null);
            form.setField("fieldB", null);
            form.setField("bogus", "asdasd");
            validationErrors = getValidatorTool().perform(form);
            assertNotNull(
                "Address should have validation errors. Did not receive any.",
                parseValidationErrors(validationErrors));

            assertTrue("1 validation errors expected, received: "
                + validationErrors.size(), validationErrors.size() == 1);

            //System.out.println(parseValidationErrors(validationErrors));
        }
        catch (Exception e)
        {
            assertNull("Unexpected exception occured: " + String.valueOf(e), e);
        }
    }

    @Test public void testRequiredOneOfMany2Success()
    {
        try
        {
            ValidationBean form;
            List<ValidationError> validationErrors;

            // Should be valid... no validation errors
            form = new ValidationBean("testRequiredOneOfMany2");
            form.setField("fieldA", null);
            form.setField("fieldD", "asdasdasdadB");
            validationErrors = getValidatorTool().perform(form);
            assertNull("ValidationErrors not expected",
                parseValidationErrors(validationErrors));

            // Should be valid... no validation errors
            form = new ValidationBean("testRequiredOneOfMany2");
            form.setField("fieldD", "valueD");
            validationErrors = getValidatorTool().perform(form);
            assertNull("ValidationErrors not expected",
                parseValidationErrors(validationErrors));

            // Should be valid... no validation errors
            form = new ValidationBean("testRequiredOneOfMany2");
            form.setField("fieldA", "valueA");
            form.setField("fieldB", "valueB");
            form.setField("fieldC", "valueC");
            form.setField("fieldD", "valueD");
            validationErrors = getValidatorTool().perform(form);
            assertNull("ValidationErrors not expected",
                parseValidationErrors(validationErrors));

            // Should be valid... no validation errors
            form = new ValidationBean("testRequiredOneOfMany2");
            form.setField("fieldA", "valueA");
            form.setField("fieldB", "valueB");
            form.setField("fieldC", "valueC");
            form.setField("fieldD", null);
            validationErrors = getValidatorTool().perform(form);
            assertNull("ValidationErrors not expected",
                parseValidationErrors(validationErrors));
        }
        catch (Exception e)
        {
            assertNull("Unexpected exception occured: " + String.valueOf(e), e);
        }
    }
}
