package com.viasat.wildblue.common.validator;

import com.viasat.common.validator.data.CorrectedAddress;
import com.viasat.common.validator.data.CorrectedContact;
import com.viasat.common.validator.data.UpdateContacts;

import com.viasat.wildblue.common.commondata.ValidationError;
import com.viasat.wildblue.common.exception.WebServiceException;
import com.viasat.wildblue.common.location.Address;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import javax.xml.ws.WebServiceContext;


/**
 */
public class ValidationOfNestedObjectsTest extends BaseValidatorTest
{
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LoggerFactory.getLogger(
            ValidationOfNestedObjectsTest.class.getName());

    @Resource WebServiceContext m_context;

    @BeforeClass public static void setTestValidationXml()
    {
        ValidatorTool.clearConfiguration();
        ValidatorTool.setConfigurationResource(
            "test-validationNestedTypes.xml");
    }

    @Test public void testValidateUpdateContactsAddressLineTooLong()
        throws WebServiceException
    {
        UpdateContacts updateContacts = new UpdateContacts();
        updateContacts.setCorrectedContact(new CorrectedContact());

        updateContacts.setExternalAccountReference(
            "externalAccountReferenceValue");
        updateContacts.setExternalSystemName("externalSystemNameValue");
        updateContacts.setExternalTransactionReference(
            "externalTransactionReferenceValue");
        // updateContacts.getCorrectedContact().setPrimaryPhone(
        // "primaryPhoneValue");

        updateContacts.getCorrectedContact().setBillingAddress(
            new CorrectedAddress());

        // This address has only one thing wrong with it. A line is too long
        updateContacts.getCorrectedContact().getBillingAddress().setAddress(
            new Address());

        String line = "0123456789";
        String tooLong = line + line + line;  // 30
        tooLong = tooLong + tooLong + tooLong + tooLong;  // 120
        updateContacts.getCorrectedContact().getBillingAddress().getAddress()
        .getAddressLine().add(tooLong);
        updateContacts.getCorrectedContact().getBillingAddress().getAddress()
        .getAddressLine().add(line);

        updateContacts.getCorrectedContact().getBillingAddress().getAddress()
        .setCountryCode("US");
        updateContacts.getCorrectedContact().getBillingAddress().getAddress()
        .setPostalCode("80123");
        updateContacts.getCorrectedContact().getBillingAddress()
        .setTaxJurisdictionCode("whatever");

        List<ValidationError> validationErrors = validateUpdateContacts(
                updateContacts);
        assertNotNull(validationErrors);

        assertEquals(validationErrors.size(), 1);

        assertEquals("VALIDATION_ERROR",
            validationErrors.get(0).getErrorCode());

        assertEquals("AddressLine exceeds max length of 102.",
            validationErrors.get(0).getMessage());
    }

    @Test public void testValidateUpdateContactsListSizeExceeded()
        throws WebServiceException
    {
        UpdateContacts updateContacts = new UpdateContacts();
        updateContacts.setCorrectedContact(new CorrectedContact());

        updateContacts.setExternalAccountReference(
            "externalAccountReferenceValue");
        updateContacts.setExternalSystemName("externalSystemNameValue");
        updateContacts.setExternalTransactionReference(
            "externalTransactionReferenceValue");
        // updateContacts.getCorrectedContact().setPrimaryPhone(
        // "primaryPhoneValue");

        updateContacts.getCorrectedContact().setBillingAddress(
            new CorrectedAddress());

        // This address has two things wrong with it ... 3 address lines, and
        // one is too long
        // However the CommonValidator will not move onto the second validator
        // if the first one reports an error
        // Therefore only one error of LIST_MAX_SIZE_EXCEEDED will be returned
        updateContacts.getCorrectedContact().getBillingAddress().setAddress(
            new Address());

        String line = "0123456789";
        String tooLong = line + line + line;  // 30
        tooLong = tooLong + tooLong + tooLong + tooLong;  // 120
        updateContacts.getCorrectedContact().getBillingAddress().getAddress()
        .getAddressLine().add(tooLong);
        updateContacts.getCorrectedContact().getBillingAddress().getAddress()
        .getAddressLine().add(line);
        updateContacts.getCorrectedContact().getBillingAddress().getAddress()
        .getAddressLine().add(line);

        updateContacts.getCorrectedContact().getBillingAddress().getAddress()
        .setCountryCode("US");
        updateContacts.getCorrectedContact().getBillingAddress().getAddress()
        .setPostalCode("80123");
        updateContacts.getCorrectedContact().getBillingAddress()
        .setTaxJurisdictionCode("whatever");

        List<ValidationError> validationErrors = validateUpdateContacts(
                updateContacts);
        assertNotNull(validationErrors);

        assertEquals(validationErrors.size(), 1);

        assertEquals("VALIDATION_ERROR",
            validationErrors.get(0).getErrorCode());

        assertEquals("The list of AddressLine has a maximum size of 2.",
            validationErrors.get(0).getMessage());
    }

    @Test public void testValidateUpdateContactsMissingAddressAndTaxJurisdiction()
        throws WebServiceException
    {
        UpdateContacts updateContacts = new UpdateContacts();
        updateContacts.setCorrectedContact(new CorrectedContact());

        updateContacts.setExternalAccountReference(
            "externalAccountReferenceValue");
        updateContacts.setExternalSystemName("externalSystemNameValue");
        updateContacts.setExternalTransactionReference(
            "externalTransactionReferenceValue");
        // updateContacts.getCorrectedContact().setPrimaryPhone(
        // "primaryPhoneValue");

        updateContacts.getCorrectedContact().setBillingAddress(
            new CorrectedAddress());

        List<ValidationError> validationErrors = validateUpdateContacts(
                updateContacts);
        assertNotNull(validationErrors);

        assertEquals(2, validationErrors.size());

        assertEquals("VALIDATION_ERROR",
            validationErrors.get(0).getErrorCode());
        assertEquals("VALIDATION_ERROR",
            validationErrors.get(1).getErrorCode());

        boolean addressErrorFound = false;
        boolean tjcErrorFound = false;

        for (ValidationError error : validationErrors)
        {
            assertTrue(error.getMessage().endsWith(" field is required."));

            if (error.getMessage().startsWith("Address"))
            {
                addressErrorFound = true;
            }
            else if (error.getMessage().startsWith("taxJurisdictionCode"))
            {
                tjcErrorFound = true;
            }
        }

        assertTrue(addressErrorFound);
        assertTrue(tjcErrorFound);
    }

    @Test public void testValidateUpdateContactsMissingAddressTaxJurisdictionAndInvalidPhone()
        throws WebServiceException
    {
        UpdateContacts updateContacts = new UpdateContacts();
        updateContacts.setCorrectedContact(new CorrectedContact());

        updateContacts.setExternalAccountReference(
            "externalAccountReferenceValue");
        updateContacts.setExternalSystemName("externalSystemNameValue");
        updateContacts.setExternalTransactionReference(
            "externalTransactionReferenceValue");
        updateContacts.getCorrectedContact().setPrimaryPhone(
            "primaryPhoneValue");

        updateContacts.getCorrectedContact().setBillingAddress(
            new CorrectedAddress());

        List<ValidationError> validationErrors = validateUpdateContacts(
                updateContacts);

        assertEquals(validationErrors.size(), 3);

        boolean addressErrorFound = false;
        boolean tjcErrorFound = false;
        boolean phoneErrorFound = false;

        for (ValidationError error : validationErrors)
        {
            assertEquals("VALIDATION_ERROR", error.getErrorCode());

            if (error.getMessage().startsWith("Address"))
            {
                addressErrorFound = true;
                assertTrue(error.getMessage().endsWith(" field is required."));
            }
            else if (error.getMessage().startsWith("taxJurisdictionCode"))
            {
                tjcErrorFound = true;
                assertTrue(error.getMessage().endsWith(" field is required."));
            }
            else if (error.getMessage().startsWith("primaryPhone"))
            {
                phoneErrorFound = true;
                assertTrue(error.getMessage().endsWith(" is not valid."));
            }
        }

        assertTrue(addressErrorFound);
        assertTrue(tjcErrorFound);
        assertTrue(phoneErrorFound);
    }

    private List<ValidationError> validateUpdateContacts(
        UpdateContacts parameter) throws WebServiceException
    {
        List<ValidationError> errors = new ArrayList<ValidationError>();

        if (parameter != null)
        {
            ValidationBean valForm = new ValidationBean("UpdateContacts");
            valForm.setField("correctedContact",
                parameter.getCorrectedContact());
            valForm.setField("externalAccountReference",
                parameter.getExternalAccountReference());
            valForm.setField("externalSystemName",
                parameter.getExternalSystemName());
            valForm.setField("externalTransactionReference",
                parameter.getExternalTransactionReference());

            errors.addAll(ValidatorTool.getInstance().perform(valForm));
        }

        return errors;
    }
}
