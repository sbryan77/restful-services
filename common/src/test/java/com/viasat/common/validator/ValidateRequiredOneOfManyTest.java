package com.viasat.common.validator;

import com.viasat.common.fault.Input;
import com.viasat.common.fault.ValidationError;
import com.viasat.common.fault.ValidatorTool;

import com.viasat.wildblue.common.validator.ValidationBean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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

    @Test public void testMutuallyExclusiveBoth()
    {
        List<ValidationError> validationErrors;
        ValidationBean form = new ValidationBean("PaymentMethod");
        form.setField("eft", "eft");
        // Only one is filled out so this test should pass
        form.setField("creditCard", "ccn");

        // But when both are filled out, we should see the same error as
        // when neither were filled out

        validationErrors = getValidatorTool().perform(form);

        assertNotNull(validationErrors);

        assertTrue(validationErrors.size() == 1);

        //System.out.println(validationErrors.get(0));
        assertEquals("INVALID_COMBINATION",
            validationErrors.get(0).getValidationCode());

        List<Input> inputs = validationErrors.get(0).getInput();

        assertTrue(inputs.size() == 2);

        boolean creditCardInputFound = false;
        boolean eftInputFound = false;

        for (Input input : inputs)
        {
            if ("creditCard".equals(input.getField()))
            {
                creditCardInputFound = true;
                assertEquals("ccn", input.getValue());
            }
            else if ("eft".equals(input.getField()))
            {
                eftInputFound = true;
                assertEquals("eft", input.getValue());
            }
        }

        assertTrue(creditCardInputFound);
        assertTrue(eftInputFound);
    }

    @Test public void testMutuallyExclusiveEither()
    {
        List<ValidationError> validationErrors;
        ValidationBean form = new ValidationBean("PaymentMethod");
        form.setField("eft", null);
        // Only one is filled out so this test should pass
        form.setField("creditCard", "ccn");

        validationErrors = getValidatorTool().perform(form);

        assertNotNull(validationErrors);
        assertTrue(validationErrors.isEmpty());
    }

    @Test public void testMutuallyExclusiveNeither()
    {
        List<ValidationError> validationErrors;

        ValidationBean form;

        // Should fail required one of many tests.
        form = new ValidationBean("PaymentMethod");
        form.setField("eft", null);
        form.setField("creditCard", null);
        validationErrors = getValidatorTool().perform(form);
        assertNotNull(validationErrors);

        assertTrue(validationErrors.size() == 1);

        //System.out.println(validationErrors.get(0));
        assertEquals("INVALID_COMBINATION",
            validationErrors.get(0).getValidationCode());

        List<Input> inputs = validationErrors.get(0).getInput();

        assertTrue(inputs.size() == 2);

        boolean creditCardInputFound = false;
        boolean eftInputFound = false;

        for (Input input : inputs)
        {
            if ("creditCard".equals(input.getField()))
            {
                creditCardInputFound = true;
            }
            else if ("eft".equals(input.getField()))
            {
                eftInputFound = true;
            }

            assertNull(input.getValue());
        }

        assertTrue(creditCardInputFound);
        assertTrue(eftInputFound);
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

        assertNotNull(validationErrors);
        assertEquals(1, validationErrors.size());
        assertEquals(2, validationErrors.get(0).getInput().size());

        boolean fieldAFound = false;
        boolean fieldBFound = false;

        for (Input input : validationErrors.get(0).getInput())
        {
            if ("fieldA".equals(input.getField()))
            {
                fieldAFound = true;
            }
            else if ("fieldB".equals(input.getField()))
            {
                fieldBFound = true;
            }

            assertNull(input.getValue());
        }

        assertTrue(fieldAFound);
        assertTrue(fieldBFound);
    }

    @Test public void testRequiredOneOfMany1Success()
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

    @Test public void testRequiredOneOfMany2Error()
    {
        ValidationBean form;
        List<ValidationError> validationErrors;

        // Should fail
        form = new ValidationBean("testRequiredOneOfMany2");
        form.setField("fieldA", null);
        form.setField("fieldB", null);
        form.setField("bogus", "asdasd");
        validationErrors = getValidatorTool().perform(form);

        assertNotNull(validationErrors);

        assertEquals(1, validationErrors.size());
        assertEquals(4, validationErrors.get(0).getInput().size());
        assertEquals("INVALID_COMBINATION",
            validationErrors.get(0).getValidationCode());

        Boolean[] foundCount = new Boolean[] { false, false, false, false };

        for (Input input : validationErrors.get(0).getInput())
        {
            if ("fieldA".equals(input.getField()))
            {
                foundCount[0] = true;
            }
            else if ("fieldB".equals(input.getField()))
            {
                foundCount[1] = true;
            }
            else if ("fieldC".equals(input.getField()))
            {
                foundCount[2] = true;
            }
            else if ("fieldD".equals(input.getField()))
            {
                foundCount[3] = true;
            }
            else
            {
                fail("Found input field " + input.getField());
            }

            assertNull(input.getValue());
        }

        for (Boolean found : foundCount)
        {
            assertTrue(found);
        }

        //System.out.println(validationErrors.get(0));
    }

    @Test public void testRequiredOneOfMany2Success()
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
}
