package com.viasat.wildblue.common.validator;

import com.viasat.wildblue.common.commondata.Contact;
import com.viasat.wildblue.common.commondata.ContactInfo;
import com.viasat.wildblue.common.commondata.Person;
import com.viasat.wildblue.common.commondata.ValidationError;
import com.viasat.wildblue.common.location.Address;
import com.viasat.wildblue.common.location.GeoPosition;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.List;


public class CommonValidatorToolTest extends BaseValidatorTest
{
    @Test public void testEmailValidator()
    {
        try
        {
            List<ValidationError> errors = null;

            Contact acctContact = new Contact();
            acctContact.setContactInfo(new ContactInfo());
            acctContact.setPerson(new Person());
            acctContact.getContactInfo().setContactType("Lousy");
            acctContact.getContactInfo().setPrimaryPhone("3033333333");
            acctContact.getContactInfo().setEmailAddress("Lousy@junit.com");
            acctContact.getPerson().setFirstName("Ben");
            acctContact.getPerson().setLastName("A'CustB4'n DidntPay");

            errors = getValidatorTool().perform(acctContact);

            assertTrue((errors != null) && errors.isEmpty());
        }
        catch (Exception e)
        {
            assertNull("Unexpected exception occured: " + String.valueOf(e), e);
        }

        try
        {
            List<ValidationError> errors = null;

            Contact acctContact = new Contact();
            acctContact.setContactInfo(new ContactInfo());
            acctContact.setPerson(new Person());
            acctContact.getContactInfo().setContactType("Lousy");
            acctContact.getContactInfo().setPrimaryPhone("3033333333");
            acctContact.getContactInfo().setEmailAddress("blahginger");
            acctContact.getPerson().setFirstName("Ben");
            acctContact.getPerson().setLastName("A'CustB4'n DidntPay");
            acctContact.getPerson().setLastName("A'CustB4'n DidntPay");

            errors = getValidatorTool().perform(acctContact);
            assertTrue((errors != null) && !errors.isEmpty());
            assertTrue(errors.size() == 1);

            assertEquals(errors.get(0).getErrorCode(), "VALIDATION_ERROR");

            // In test-applicationResources.properties, the value of
            // "ContactInfo.emailAddress.displayname"
            assertTrue(errors.get(0).getMessage().startsWith("emailAddress"));
        }
        catch (Exception e)
        {
            assertNull("Unexpected exception occured: " + String.valueOf(e), e);
        }
    }

    @Test public void testIsIntegerValidator()
    {
        ValidationBean bean = new ValidationBean("integerForm");
        bean.setField("theProperty", 1L);

        List<ValidationError> validationErrors = getValidatorTool().perform(
                bean);
        assertTrue((validationErrors != null) && validationErrors.isEmpty());

        bean = new ValidationBean("integerForm");
        bean.setField("theProperty", 1.0);
        validationErrors = getValidatorTool().perform(bean);
        assertTrue((validationErrors != null) && !validationErrors.isEmpty());
        assertTrue(validationErrors.size() == 1);

        assertEquals(validationErrors.get(0).getErrorCode(),
            "VALIDATION_ERROR");
        //IntegerForm.theProperty.displayname
        assertTrue(validationErrors.get(0).getMessage().startsWith(
                "theProperty"));
    }

    @Test public void testIsLongValidator()
    {
        ValidationBean bean = new ValidationBean("longForm");
        bean.setField("theProperty", 1L);

        List<ValidationError> validationErrors = getValidatorTool().perform(
                bean);
        assertTrue((validationErrors != null) && validationErrors.isEmpty());

        bean = new ValidationBean("longForm");
        bean.setField("theProperty", 1.0);
        validationErrors = getValidatorTool().perform(bean);
        assertTrue((validationErrors != null) && !validationErrors.isEmpty());

        assertTrue(validationErrors.size() == 1);

        assertEquals(validationErrors.get(0).getErrorCode(),
            "VALIDATION_ERROR");
        //LongForm.theProperty.displayname
        assertTrue(validationErrors.get(0).getMessage().startsWith(
                "theProperty"));
    }

    @Test public void testListMaxSizeValidator()
    {
        Address address = new Address();
        address.setMunicipality("Denver");
        address.setRegion("CO");
        address.setPostalCode("99912");
        address.setCountryCode("US");
        address.getAddressLine().add("Line1");
        address.getAddressLine().add("Line2");

        List<ValidationError> validationErrors = getValidatorTool().perform(
                address);

        assertNotNull(
            "Address should have validation errors. Did not receive any.",
            parseValidationErrors(validationErrors));

        assertTrue(validationErrors.size() == 1);
        assertEquals(validationErrors.get(0).getErrorCode(),
            "VALIDATION_ERROR");
        assertTrue(validationErrors.get(0).getMessage().contains(
                "AddressLine"));
    }

    @Test public void testMaxLengthAndMinLengthValidator()
    {
        Address address = new Address();

        StringBuilder sb = new StringBuilder("1234567890");

        for (int i = 0; i < 10; i++)
        {
            sb.append("1234567890");
        }

        address.setMunicipality(sb.toString());
        address.setRegion("CO");
        address.setPostalCode("99912");
        address.setCountryCode("U");
        address.getAddressLine().add("Line1");

        List<ValidationError> validationErrors = getValidatorTool().perform(
                address);

        assertNotNull(
            "Address should have validation errors. Did not receive any.",
            parseValidationErrors(validationErrors));

        boolean countryErrorFound = false;
        boolean cityErrorFound = false;

        for (ValidationError error : validationErrors)
        {
            assertEquals(error.getErrorCode(), "VALIDATION_ERROR");

            if (error.getMessage().startsWith("Country"))
            {
                countryErrorFound = true;
            }
            else if (error.getMessage().startsWith("City"))
            {
                cityErrorFound = true;
            }
        }

        assertTrue(countryErrorFound);
        assertTrue(cityErrorFound);
    }

    @Test public void testNestedRequiredValidators()
    {
        try
        {
            List<ValidationError> errors = null;

            Contact custContact = new Contact();
            custContact.setContactInfo(new ContactInfo());
            custContact.getContactInfo().setContactType("Cust");
            custContact.setPerson(new Person());
            custContact.getPerson().setFirstName("Customer");

            // customerContact.lastName not set
            errors = getValidatorTool().perform(custContact);

            // Should get an error, Contact type has required children which are
            // null.
            assertNotNull("Required field (lastName)"
                + " sent as null. Did not receive any validation error.",
                parseValidationErrors(errors));
            // assertTrue("1 validation errors expected, received: "
            // + errors.size(), errors.size() == 1);

            // Make sure we have precisely the error messages we expected.
            assertErrorMessageExists(errors,
                getExpectedValidationError("Person", "lastName", "required"));

            Contact acctContact = new Contact();
            acctContact.setContactInfo(new ContactInfo());
            acctContact.setPerson(new Person());
            acctContact.getPerson().setFirstName("Ben");
            acctContact.getPerson().setLastName("A'CustB4'n DidntPay");

            // customerContact.lastName not set
            errors = getValidatorTool().perform(acctContact);

            // Should get an error, Contact is missing ContactType.
            assertNotNull(
                "Required field (contactType) sent as null. Did not receive any validation error.",
                parseValidationErrors(errors));
            // assertTrue("1 validation errors expected, received: "
            // + errors.size(), errors.size() == 1);
            // Make sure we have precisely the error messages we expected.
            assertErrorMessageExists(errors,
                getExpectedValidationError("ContactInfo", "contactType",
                    "required"));
        }
        catch (Exception e)
        {
            assertNull("Unexpected exception occured: " + String.valueOf(e), e);
        }
    }

    @Test public void testNestedRequiredValidatorsThree()
    {
        try
        {
            Person person = new Person();

            // Should get an error, Contact type has required children which are
            // null.
            List<ValidationError> errors = getValidatorTool().perform(person);
            assertNotNull(
                "Required field (person.??) sent with null required children. Did not receive any validation error.",
                parseValidationErrors(errors));

            // Fill up everything which is required,... should then work.
            person.setFirstName("Ben");
            person.setLastName("A'CustB4'n DidntPay");
            errors = getValidatorTool().perform(person);
            assertNull("TestCase expected no errors,... but received: "
                + parseValidationErrors(errors), parseValidationErrors(errors));
        }
        catch (Exception e)
        {
            assertNull("Unexpected exception occured: " + String.valueOf(e), e);
        }
    }

    @Test public void testOptionalValidator()
    {
        List<ValidationError> errors = null;

        Contact acctContact = new Contact();
        acctContact.setContactInfo(new ContactInfo());

        acctContact.getContactInfo().setContactType("Lousy");
        acctContact.getContactInfo().setPrimaryPhone("3033333333");
        acctContact.getContactInfo().setEmailAddress("Lousy@junit.com");

        errors = getValidatorTool().perform(acctContact);

        //  for (ValidationError error : errors)
        //{
        //    System.out.println(error);
        //}

        assertTrue((errors != null) && errors.isEmpty());

        acctContact.setPerson(new Person());

        errors = getValidatorTool().perform(acctContact);

        assertNotNull(errors);
        assertTrue(errors.size() == 2);

        boolean lastNameErrorFound = false;
        boolean firstNameErrorFound = false;

        for (ValidationError error : errors)
        {
            assertEquals(error.getErrorCode(), "VALIDATION_ERROR");

            if (error.getMessage().startsWith("Last Name"))
            {
                lastNameErrorFound = true;
            }
            else if (error.getMessage().startsWith("First Name"))
            {
                firstNameErrorFound = true;
            }
        }

        assertTrue(lastNameErrorFound);
        assertTrue(firstNameErrorFound);
    }

    @Test public void testRequiredAndInRangeDoubleValidators()
    {
        try
        {
            GeoPosition geoPosition = new GeoPosition();
            geoPosition.setLatitude(-90.0000);
            geoPosition.setLongitude(-180.0000);

            List<ValidationError> errors = getValidatorTool().perform(
                    geoPosition);
            assertNull("TestCase expected no errors,... but received: "
                + parseValidationErrors(errors), parseValidationErrors(errors));
        }
        catch (Exception e)
        {
            assertNull("Unexpected exception occured: " + String.valueOf(e), e);
        }
    }

    @Test public void testRequiredAndZipCodeValidators()
    {
        Address address = new Address();
        address.setMunicipality("Denver");
        address.setRegion("CO");
        address.setPostalCode("999");

        List<ValidationError> validationErrors = getValidatorTool().perform(
                address);
        assertNotNull(
            "Address should have validation errors. Did not receive any.",
            parseValidationErrors(validationErrors));

        boolean postalErrorFound = false;
        boolean countryErrorFound = false;

        for (ValidationError error : validationErrors)
        {
            assertEquals(error.getErrorCode(), "VALIDATION_ERROR");

            if (error.getMessage().startsWith("Country"))
            {
                countryErrorFound = true;
            }
            else if (error.getMessage().startsWith("Postal Code"))
            {
                postalErrorFound = true;
            }
        }

        assertTrue(postalErrorFound);
        assertTrue(countryErrorFound);
    }

    @Test public void testRequiredConditional()
    {
        ValidationBean bean = new ValidationBean("testRequiredConditional");
        bean.setField("someField", null);
        bean.setField("someOtherField", null);

        List<ValidationError> errors = getValidatorTool().perform(bean);
        assertTrue(errors.isEmpty());

        bean.setField("someField", "blahGinger");

        errors = getValidatorTool().perform(bean);
        assertFalse(errors.isEmpty());
        assertTrue(errors.size() == 1);
        // assertEquals(errors.get(0).getValidationCode(),
        // "INVALID_COMBINATION");

        bean.setField("someOtherField", "blahGinger");
        errors = getValidatorTool().perform(bean);
        assertTrue(errors.isEmpty());
    }

    @Test public void testRequiredUnless()
    {
        // someOtherField is required, unless someField is "BlahGinger"
        ValidationBean bean = new ValidationBean("testRequiredUnless");
        bean.setField("someField", null);
        bean.setField("someOtherField", null);

        List<ValidationError> errors = getValidatorTool().perform(bean);

        assertTrue(errors.size() == 1);

        // assertEquals(errors.get(0).getValidationCode(), "REQUIRED");

        bean.setField("someField", "blahGinger");

        errors = getValidatorTool().perform(bean);
        assertFalse(errors.isEmpty());
        assertTrue(errors.size() == 1);
        // Still isn't "BlahGinger"
        // assertEquals(errors.get(0).getValidationCode(), "REQUIRED");

        // Now it is. Therefore someOtherField is not required
        bean.setField("someField", "BlahGinger");

        errors = getValidatorTool().perform(bean);

        assertTrue(errors.isEmpty());
    }
}
