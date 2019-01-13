package com.viasat.common.validator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.viasat.common.fault.Input;
import com.viasat.common.fault.ValidationError;
import com.viasat.wildblue.common.commondata.Contact;
import com.viasat.wildblue.common.commondata.ContactInfo;
import com.viasat.wildblue.common.commondata.Person;
import com.viasat.wildblue.common.location.Address;
import com.viasat.wildblue.common.location.GeoPosition;
import com.viasat.wildblue.common.validator.ValidationBean;

public class CommonValidatorToolTest extends BaseValidatorTest
{
	@Test
	public void testEmailValidator()
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
		assertNotNull(errors);
		assertEquals(1, errors.size());
		assertEquals(1, errors.get(0).getInput().size());
		assertEquals("INVALID_FORMAT", errors.get(0).getValidationCode());
		assertEquals("emailAddress", errors.get(0).getInput().get(0).getField());
		assertEquals("blahginger", errors.get(0).getInput().get(0).getValue());
	}

	@Test
	public void testEmailValidatorHappyPath()
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

	@Test
	public void testIsIntegerValidator()
	{
		ValidationBean bean = new ValidationBean("integerForm");
		bean.setField("theProperty", 1.0);

		List<ValidationError> validationErrors = getValidatorTool().perform(bean);
		assertNotNull(validationErrors);
		assertEquals(1, validationErrors.size());
		assertEquals(1, validationErrors.get(0).getInput().size());
		assertEquals("INVALID_FORMAT", validationErrors.get(0).getValidationCode());
		assertEquals("theProperty", validationErrors.get(0).getInput().get(0).getField());
		assertEquals("1.0", validationErrors.get(0).getInput().get(0).getValue());
	}

	@Test
	public void testIsIntegerValidatorHappyPath()
	{
		ValidationBean bean = new ValidationBean("integerForm");
		bean.setField("theProperty", 1L);

		List<ValidationError> validationErrors = getValidatorTool().perform(bean);
		assertTrue((validationErrors != null) && validationErrors.isEmpty());
	}

	@Test
	public void testIsLongValidator()
	{
		ValidationBean bean = new ValidationBean("longForm");
		bean.setField("theProperty", 1L);

		List<ValidationError> validationErrors = getValidatorTool().perform(bean);
		assertTrue((validationErrors != null) && validationErrors.isEmpty());

		bean = new ValidationBean("longForm");
		bean.setField("theProperty", 1.0);
		validationErrors = getValidatorTool().perform(bean);
		assertNotNull(validationErrors);
		assertEquals(1, validationErrors.size());
		assertEquals(1, validationErrors.get(0).getInput().size());
		assertEquals("INVALID_FORMAT", validationErrors.get(0).getValidationCode());
		assertEquals("theProperty", validationErrors.get(0).getInput().get(0).getField());
		assertEquals("1.0", validationErrors.get(0).getInput().get(0).getValue());
	}

	@Test
	public void testListMaxSizeValidator()
	{
		Address address = new Address();
		address.setMunicipality("Denver");
		address.setRegion("CO");
		address.setPostalCode("99912");
		address.setCountryCode("US");

		// Max lines is 1. This should cause a validation error
		address.getAddressLine().add("Line1");
		address.getAddressLine().add("Line2");

		List<ValidationError> validationErrors = getValidatorTool().perform(address);

		assertNotNull(validationErrors);
		assertEquals(1, validationErrors.size());
		assertEquals(1, validationErrors.get(0).getInput().size());
		assertEquals("INVALID_LIST_SIZE", validationErrors.get(0).getValidationCode());
		assertEquals("addressLine", validationErrors.get(0).getInput().get(0).getField());
		assertEquals("Line1", validationErrors.get(0).getInput().get(0).getValue());
	}

	@Test
	public void testMaxLengthAndMinLengthValidator()
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

		List<ValidationError> validationErrors = getValidatorTool().perform(address);

		Map<String, ValidationError> errorMap = new HashMap<String, ValidationError>();

		for (ValidationError error : validationErrors)
		{
			// System.out.println(error);
			errorMap.put(error.getInput().get(0).getField(), error);
		}

		assertNotNull("Address should have validation errors. Did not receive any.",
				parseValidationErrors(validationErrors));

		ValidationError error = errorMap.get("municipality");
		assertNotNull(error);
		assertEquals("INVALID_LENGTH", error.getValidationCode());
		assertEquals(1, error.getInput().size());
		assertEquals("municipality", error.getInput().get(0).getField());
		assertEquals(sb.toString(), error.getInput().get(0).getValue());

		error = errorMap.get("countryCode");

		assertNotNull(error);
		assertEquals("INVALID_LENGTH", error.getValidationCode());
		assertEquals(1, error.getInput().size());
		assertEquals("countryCode", error.getInput().get(0).getField());
		assertEquals("U", error.getInput().get(0).getValue());
	}

	@Test
	public void testNestedRequiredValidators()
	{
		List<ValidationError> errors;

		Contact acctContact = new Contact();
		acctContact.setContactInfo(new ContactInfo());
		acctContact.setPerson(new Person());
		acctContact.getPerson().setFirstName("Ben");
		acctContact.getPerson().setLastName("A'CustB4'n DidntPay");

		// customerContact.lastName not set
		errors = getValidatorTool().perform(acctContact);

		assertNotNull(errors);
		assertEquals(3, errors.size());

		boolean contactTypeFound = false;
		boolean primaryPhoneFound = false;
		boolean emailAddressFound = false;

		for (ValidationError error : errors)
		{
			assertEquals(1, error.getInput().size());
			assertEquals("REQUIRED", error.getValidationCode());
			assertEquals(null, error.getInput().get(0).getValue());

			if ("emailAddress".equals(error.getInput().get(0).getField()))
			{
				emailAddressFound = true;
			}
			else if ("contactType".equals(error.getInput().get(0).getField()))
			{
				contactTypeFound = true;
			}
			else if ("primaryPhone".equals(error.getInput().get(0).getField()))
			{
				primaryPhoneFound = true;
			}
		}

		assertTrue(contactTypeFound);
		assertTrue(emailAddressFound);
		assertTrue(primaryPhoneFound);
	}

	@Test
	public void testNestedRequiredValidatorsHappyPath()
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

		assertNotNull(errors);
		assertEquals(0, errors.size());
	}

	@Test
	public void testNestedRequiredValidatorsTwo()
	{
		List<ValidationError> errors = null;

		Contact custContact = new Contact();
		custContact.setContactInfo(new ContactInfo());
		custContact.getContactInfo().setContactType("Cust");
		custContact.setPerson(new Person());
		custContact.getPerson().setFirstName("Customer");

		// customerContact.lastName not set
		errors = getValidatorTool().perform(custContact);

		assertNotNull(errors);
		assertEquals(3, errors.size());

		boolean lastNameFound = false;
		boolean primaryPhoneFound = false;
		boolean emailAddressFound = false;

		for (ValidationError error : errors)
		{
			// System.out.println(error);
			assertEquals(1, error.getInput().size());
			assertEquals("REQUIRED", error.getValidationCode());
			assertEquals(null, error.getInput().get(0).getValue());

			if ("emailAddress".equals(error.getInput().get(0).getField()))
			{
				emailAddressFound = true;
			}
			else if ("lastName".equals(error.getInput().get(0).getField()))
			{
				lastNameFound = true;
			}
			else if ("primaryPhone".equals(error.getInput().get(0).getField()))
			{
				primaryPhoneFound = true;
			}
		}

		assertTrue(lastNameFound);
		assertTrue(emailAddressFound);
		assertTrue(primaryPhoneFound);
	}

	@Test
	public void testOptionalValidator()
	{
		List<ValidationError> errors;
		Contact acctContact = new Contact();
		acctContact.setContactInfo(new ContactInfo());

		acctContact.getContactInfo().setContactType("Lousy");
		acctContact.getContactInfo().setPrimaryPhone("3033333333");
		acctContact.getContactInfo().setEmailAddress("Lousy@junit.com");

		acctContact.setPerson(new Person());

		Map<String, String> errorMap = new HashMap<String, String>();
		errors = getValidatorTool().perform(acctContact);

		for (ValidationError error : errors)
		{
			// System.out.println(error);
			errorMap.put(error.getInput().get(0).getField(), error.getValidationCode());
			assertEquals("REQUIRED", error.getValidationCode());
			assertEquals(1, error.getInput().size());
			assertNull(error.getInput().get(0).getValue());
		}

		String error = errorMap.get("lastName");

		assertNotNull(error);

		error = errorMap.get("firstName");
		assertNotNull(error);
	}

	@Test
	public void testOptionalValidatorHappyPath()
	{
		List<ValidationError> errors = null;

		Contact acctContact = new Contact();
		acctContact.setContactInfo(new ContactInfo());

		acctContact.getContactInfo().setContactType("Lousy");
		acctContact.getContactInfo().setPrimaryPhone("3033333333");
		acctContact.getContactInfo().setEmailAddress("Lousy@junit.com");

		errors = getValidatorTool().perform(acctContact);

		// for (ValidationError error : errors)
		// {
		// System.out.println(error);
		// }

		assertTrue((errors != null) && errors.isEmpty());
		// assertNull("TestCase expected no errors,... but received: "
		// + parseValidationErrors(errors), parseValidationErrors(errors));
	}

	@Test
	public void testRequiredAndInRangeDoubleValidatorsHappyPath()
	{
		GeoPosition geoPosition = new GeoPosition();
		geoPosition.setLatitude(-90.0000);
		geoPosition.setLongitude(-180.0000);

		List<ValidationError> errors = getValidatorTool().perform(geoPosition);
		assertNull(
				"TestCase expected no errors,... but received: " + parseValidationErrors(errors),
				parseValidationErrors(errors));
	}

	@Test
	public void testRequiredAndZipCodeValidators()
	{
		Address address = new Address();
		address.setMunicipality("Denver");
		address.setRegion("CO");
		address.setPostalCode("999");

		List<ValidationError> validationErrors = getValidatorTool().perform(address);
		assertNotNull(validationErrors);

		Map<String, ValidationError> errorMap = new HashMap<String, ValidationError>();

		for (ValidationError error : validationErrors)
		{
			// System.out.println(error);
			assertEquals(1, error.getInput().size());
			errorMap.put(error.getInput().get(0).getField(), error);
		}

		ValidationError postalCodeError = errorMap.get("postalCode");
		assertNotNull(postalCodeError);
		assertEquals("INVALID_FORMAT", postalCodeError.getValidationCode());
		assertEquals("999", postalCodeError.getInput().get(0).getValue());

		ValidationError countryCodeError = errorMap.get("countryCode");
		assertNotNull(countryCodeError);
		assertEquals("REQUIRED", countryCodeError.getValidationCode());
		assertNull(countryCodeError.getInput().get(0).getValue());
	}

	@Test
	public void testRequiredConditional()
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
		assertEquals(errors.get(0).getValidationCode(), "INVALID_COMBINATION");

		assertEquals(errors.get(0).getInput().size(), 2);

		boolean someFieldFound = false;
		boolean someOtherFieldFound = false;

		for (Input input : errors.get(0).getInput())
		{
			if ("someField".equals(input.getField()))
			{
				someFieldFound = true;
				assertEquals("blahGinger", input.getValue());
			}
			else if ("someOtherField".equals(input.getField()))
			{
				assertNull(input.getValue());
				someOtherFieldFound = true;
			}
		}

		assertTrue(someFieldFound);
		assertTrue(someOtherFieldFound);

		bean.setField("someOtherField", "blahGinger");
		errors = getValidatorTool().perform(bean);
		assertTrue(errors.isEmpty());
	}

	@Test
	public void testRequiredUnlessHappyPath()
	{
		List<ValidationError> errors;
		ValidationBean bean = new ValidationBean("testRequiredUnless");
		bean.setField("someField", "BlahGinger");
		bean.setField("someOtherField", null);

		// Now it is "BlahGinger". Therefore someOtherField is not required

		errors = getValidatorTool().perform(bean);

		assertTrue(errors.isEmpty());
	}

	@Test
	public void testRequiredUnlessOne()
	{
		// someOtherField is required, unless someField is "BlahGinger"
		ValidationBean bean = new ValidationBean("testRequiredUnless");
		bean.setField("someField", null);
		bean.setField("someOtherField", null);

		List<ValidationError> errors = getValidatorTool().perform(bean);

		assertTrue(errors.size() == 1);
		// System.out.println(errors.get(0));

		assertEquals("REQUIRED", errors.get(0).getValidationCode());
		assertEquals(1, errors.get(0).getInput().size());
		assertEquals("someOtherField", errors.get(0).getInput().get(0).getField());
		assertNull(errors.get(0).getInput().get(0).getValue());
	}

	@Test
	public void testRequiredUnlessTwo()
	{
		ValidationBean bean = new ValidationBean("testRequiredUnless");
		bean.setField("someField", "blahGinger");
		bean.setField("someOtherField", null);

		List<ValidationError> errors = getValidatorTool().perform(bean);
		assertFalse(errors.isEmpty());
		assertTrue(errors.size() == 1);

		// Still isn't "BlahGinger"

		assertEquals("REQUIRED", errors.get(0).getValidationCode());
		assertEquals(1, errors.get(0).getInput().size());
		assertEquals("someOtherField", errors.get(0).getInput().get(0).getField());
		assertNull(errors.get(0).getInput().get(0).getValue());
	}
}
