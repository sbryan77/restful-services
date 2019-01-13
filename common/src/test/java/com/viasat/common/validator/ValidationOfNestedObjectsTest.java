package com.viasat.common.validator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.xml.ws.WebServiceContext;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.viasat.common.fault.ValidationError;
import com.viasat.common.fault.ValidatorTool;
import com.viasat.common.fault.WebServiceFault;
import com.viasat.common.validator.data.CorrectedAddress;
import com.viasat.common.validator.data.CorrectedContact;
import com.viasat.common.validator.data.UpdateContacts;
import com.viasat.wildblue.common.location.Address;
import com.viasat.wildblue.common.validator.ValidationBean;

/**
 */
public class ValidationOfNestedObjectsTest extends BaseValidatorTest
{
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ValidationOfNestedObjectsTest.class.getName());

	@Resource
	WebServiceContext m_context;
	private ValidatorTool m_validator = null;

	@BeforeClass
	public static void setTestValidationXml()
	{
		ValidatorTool.clearConfiguration();
		ValidatorTool.setConfigurationResource("test-validationNestedTypes.xml");
	}

	@Test
	public void testValidateUpdateContactsAddressLineTooLong() throws WebServiceFault
	{
		UpdateContacts updateContacts = new UpdateContacts();
		updateContacts.setCorrectedContact(new CorrectedContact());

		updateContacts.setExternalAccountReference("externalAccountReferenceValue");
		updateContacts.setExternalSystemName("externalSystemNameValue");
		updateContacts.setExternalTransactionReference("externalTransactionReferenceValue");
		// updateContacts.getCorrectedContact().setPrimaryPhone(
		// "primaryPhoneValue");

		updateContacts.getCorrectedContact().setBillingAddress(new CorrectedAddress());

		// This address has only one thing wrong with it. A line is too long
		updateContacts.getCorrectedContact().getBillingAddress().setAddress(new Address());

		String line = "0123456789";
		String tooLong = line + line + line; // 30
		tooLong = tooLong + tooLong + tooLong + tooLong; // 120
		updateContacts.getCorrectedContact().getBillingAddress().getAddress().getAddressLine()
				.add(tooLong);
		updateContacts.getCorrectedContact().getBillingAddress().getAddress().getAddressLine()
				.add(line);

		updateContacts.getCorrectedContact().getBillingAddress().getAddress().setCountryCode("US");
		updateContacts.getCorrectedContact().getBillingAddress().getAddress()
				.setPostalCode("80123");
		updateContacts.getCorrectedContact().getBillingAddress().setTaxJurisdictionCode("whatever");

		List<ValidationError> validationErrors = validateUpdateContacts(updateContacts);
		assertNotNull(validationErrors);

		assertEquals(validationErrors.size(), 1);

		assertEquals(validationErrors.get(0).getValidationCode(), "INVALID_LENGTH");
		assertEquals(validationErrors.get(0).getInput().size(), 1);
		assertEquals(validationErrors.get(0).getInput().get(0).getField(), "addressLine");
		assertTrue(validationErrors.get(0).getInput().get(0).getValue().length() > 102);
	}

	@Test
	public void testValidateUpdateContactsListSizeExceeded() throws WebServiceFault
	{
		UpdateContacts updateContacts = new UpdateContacts();
		updateContacts.setCorrectedContact(new CorrectedContact());

		updateContacts.setExternalAccountReference("externalAccountReferenceValue");
		updateContacts.setExternalSystemName("externalSystemNameValue");
		updateContacts.setExternalTransactionReference("externalTransactionReferenceValue");
		// updateContacts.getCorrectedContact().setPrimaryPhone(
		// "primaryPhoneValue");

		updateContacts.getCorrectedContact().setBillingAddress(new CorrectedAddress());

		// This address has two things wrong with it ... 3 address lines, and
		// one is too long
		// However the CommonValidator will not move onto the second validator
		// if the first one reports an error
		// Therefore only one error of LIST_MAX_SIZE_EXCEEDED will be returned
		updateContacts.getCorrectedContact().getBillingAddress().setAddress(new Address());

		String line = "0123456789";
		String tooLong = line + line + line; // 30
		tooLong = tooLong + tooLong + tooLong + tooLong; // 120
		updateContacts.getCorrectedContact().getBillingAddress().getAddress().getAddressLine()
				.add(tooLong);
		updateContacts.getCorrectedContact().getBillingAddress().getAddress().getAddressLine()
				.add(line);
		updateContacts.getCorrectedContact().getBillingAddress().getAddress().getAddressLine()
				.add(line);

		updateContacts.getCorrectedContact().getBillingAddress().getAddress().setCountryCode("US");
		updateContacts.getCorrectedContact().getBillingAddress().getAddress()
				.setPostalCode("80123");
		updateContacts.getCorrectedContact().getBillingAddress().setTaxJurisdictionCode("whatever");

		List<ValidationError> validationErrors = validateUpdateContacts(updateContacts);
		assertNotNull(validationErrors);

		assertEquals(validationErrors.size(), 1);

		assertEquals(validationErrors.get(0).getValidationCode(), "INVALID_LIST_SIZE");
		assertEquals(validationErrors.get(0).getInput().size(), 1);
		assertEquals(validationErrors.get(0).getInput().get(0).getField(), "addressLine");
	}

	@Test
	public void testValidateUpdateContactsMissingAddressAndTaxJurisdiction() throws WebServiceFault
	{
		UpdateContacts updateContacts = new UpdateContacts();
		updateContacts.setCorrectedContact(new CorrectedContact());

		updateContacts.setExternalAccountReference("externalAccountReferenceValue");
		updateContacts.setExternalSystemName("externalSystemNameValue");
		updateContacts.setExternalTransactionReference("externalTransactionReferenceValue");
		// updateContacts.getCorrectedContact().setPrimaryPhone(
		// "primaryPhoneValue");

		updateContacts.getCorrectedContact().setBillingAddress(new CorrectedAddress());

		List<ValidationError> validationErrors = validateUpdateContacts(updateContacts);
		assertNotNull(validationErrors);
		assertEquals(validationErrors.size(), 2);

		boolean addressErrorFound = false;
		boolean tjcErrorFound = false;

		for (ValidationError error : validationErrors)
		{
			assertEquals("REQUIRED", error.getValidationCode());
			assertNotNull(error.getInput());
			assertEquals(error.getInput().size(), 1);
			// System.out.println(error);

			if ("address".equals(error.getInput().get(0).getField()))
			{
				addressErrorFound = true;
				assertNull(error.getInput().get(0).getValue());
			}
			else if ("taxJurisdictionCode".equals(error.getInput().get(0).getField()))
			{
				tjcErrorFound = true;
				assertNull(error.getInput().get(0).getValue());
			}
		}

		assertTrue(addressErrorFound);
		assertTrue(tjcErrorFound);
	}

	@Test
	public void testValidateUpdateContactsMissingAddressTaxJurisdictionAndInvalidPhone()
			throws WebServiceFault
	{
		UpdateContacts updateContacts = new UpdateContacts();
		updateContacts.setCorrectedContact(new CorrectedContact());

		updateContacts.setExternalAccountReference("externalAccountReferenceValue");
		updateContacts.setExternalSystemName("externalSystemNameValue");
		updateContacts.setExternalTransactionReference("externalTransactionReferenceValue");
		updateContacts.getCorrectedContact().setPrimaryPhone("primaryPhoneValue");

		updateContacts.getCorrectedContact().setBillingAddress(new CorrectedAddress());

		List<ValidationError> validationErrors = validateUpdateContacts(updateContacts);

		assertEquals(validationErrors.size(), 3);

		boolean addressErrorFound = false;
		boolean tjcErrorFound = false;
		boolean phoneErrorFound = false;

		for (ValidationError error : validationErrors)
		{
			assertNotNull(error.getInput());
			assertEquals(error.getInput().size(), 1);

			if ("address".equals(error.getInput().get(0).getField()))
			{
				assertEquals("REQUIRED", error.getValidationCode());
				addressErrorFound = true;
				assertNull(error.getInput().get(0).getValue());
			}
			else if ("taxJurisdictionCode".equals(error.getInput().get(0).getField()))
			{
				assertEquals("REQUIRED", error.getValidationCode());
				tjcErrorFound = true;
				assertNull(error.getInput().get(0).getValue());
			}
			else if ("primaryPhone".equals(error.getInput().get(0).getField()))
			{
				assertEquals("INVALID_FORMAT", error.getValidationCode());
				phoneErrorFound = true;
				assertEquals("primaryPhoneValue", error.getInput().get(0).getValue());
			}

			// System.out.println(error);
		}

		assertTrue(addressErrorFound);
		assertTrue(tjcErrorFound);
		assertTrue(phoneErrorFound);
	}

	private ValidatorTool getValidator() throws WebServiceFault
	{
		if (m_validator == null)
		{
			m_validator = ValidatorTool.getInstance();
		}

		return m_validator;
	}

	private List<ValidationError> validateUpdateContacts(UpdateContacts parameter)
			throws WebServiceFault
	{
		List<ValidationError> errors = new ArrayList<ValidationError>();

		if (parameter != null)
		{
			ValidationBean valForm = new ValidationBean("UpdateContacts");
			valForm.setField("correctedContact", parameter.getCorrectedContact());
			valForm.setField("externalAccountReference", parameter.getExternalAccountReference());
			valForm.setField("externalSystemName", parameter.getExternalSystemName());
			valForm.setField("externalTransactionReference",
					parameter.getExternalTransactionReference());

			errors.addAll(getValidator().perform(valForm));
		}

		return errors;
	}
}
