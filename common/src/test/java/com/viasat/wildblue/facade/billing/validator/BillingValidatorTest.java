package com.viasat.wildblue.facade.billing.validator;

import com.viasat.wildblue.common.commondata.ValidationError;
import com.viasat.wildblue.common.data.EFTPaymentTypeEnum;
import com.viasat.wildblue.common.validator.BaseValidatorTest;
import com.viasat.wildblue.common.validator.ValidationBean;
import com.viasat.wildblue.common.validator.ValidatorTool;
import com.viasat.wildblue.facade.billing.data.Contact;
import com.viasat.wildblue.facade.billing.data.EFT;
import com.viasat.wildblue.facade.billing.data.PaymentMethod;
import com.viasat.wildblue.facade.billing.data.Person;
import com.viasat.wildblue.facade.billing.rbm.util.RbmPaymentMethodEnum;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Date;
import java.util.List;


public class BillingValidatorTest extends BaseValidatorTest
{
    @BeforeClass public static void setTestValidationXml()
    {
        ValidatorTool.clearConfiguration();
        ValidatorTool.setConfigurationResources(
            new String[]
            {
                "test-validation-common.xml", "test-validation-billing.xml"
            });
    }

    @Test public void testAddCustomerAndAccountFailures()
    {
        try
        {
            String requestName = "addCustomerAndAccount";
            List<ValidationError> errors = null;

            ValidationBean valBean = new ValidationBean(requestName);

            Contact custContact = new Contact();
            custContact.setPerson(new Person());
            custContact.getPerson().setFirstName("Customer");

            valBean.setField("customerContact", custContact);

            PaymentMethod recurringPaymentMethod = new PaymentMethod();
            recurringPaymentMethod.setPaymentMethodType(
                RbmPaymentMethodEnum.ACH_RECURRING.getWbPaymentMethodName());
            recurringPaymentMethod.setEft(new EFT());
            recurringPaymentMethod.getEft().setBankAccountHolder("John Ya Ya");
            recurringPaymentMethod.getEft().setBankAccountNumber("123123123");
            recurringPaymentMethod.getEft().setBankCode("123123");
            recurringPaymentMethod.getEft().setPaymentType(EFTPaymentTypeEnum.C
                .getWildBlueName());

            valBean.setField("recurringPaymentMethod", recurringPaymentMethod);
            valBean.setField("isAddressWithinCityLimits", true);

            // transactionRef, accountContact, customerContact.lastName not set
            errors = getValidatorTool().perform(valBean);

            // Should get an error, Contact type has required children which are null.
            assertNotNull(
                "Required fields (accountContact, customerContact.lastName)"
                + " sent as null. Did not receive any validation error.",
                parseValidationErrors(errors));
            //            assertTrue("3 validation errors expected, received: "
            //                + errors.size(), errors.size() == 3);

            // Make sure we have precisely the error messages we expected.
            assertErrorMessageExists(errors,
                getExpectedValidationError(requestName, "accountContact",
                    "required"));

            // Make sure we have precisely the error messages we expected.
            assertErrorMessageExists(errors,
                getExpectedValidationError("Person", "lastName", "required"));

            Contact acctContact = new Contact();
            acctContact.setPerson(new Person());
            acctContact.getPerson().setFirstName("Ben");
            acctContact.getPerson().setLastName("A'CustB4'n DidntPay");

            valBean.setField("transactionReference", "1231231231");
            valBean.setField("accountContact", acctContact);

            // customerContact.lastName not set
            errors = getValidatorTool().perform(valBean);

            // Should get an error, Contact type has required children which are null.
            assertNotNull(
                "Required fields (customerContact.lastName) sent as null. Did not receive any validation error.",
                parseValidationErrors(errors));
            //            assertTrue("1 validation errors expected, received: "
            //                + errors.size(), errors.size() == 1);
            // Make sure we have precisely the error messages we expected.
            assertErrorMessageExists(errors,
                getExpectedValidationError("Person", "lastName", "required"));
        }
        catch (Exception e)
        {
            assertNull("Unexpected exception occured: " + String.valueOf(e), e);
        }
    }

    @Test public void testAddCustomerAndAccountSuccess()
    {
        try
        {
            String requestName = "addCustomerAndAccount";
            //            List<ValidationError> errors = null;

            ValidationBean valBean = new ValidationBean(requestName);

            Contact acctContact = new Contact();
            acctContact.setPerson(new Person());
            acctContact.setPrimaryPhone("3033330000x1234");
            acctContact.setEmailAddress("Cust@junit.com");
            acctContact.getPerson().setFirstName("Ben");
            acctContact.getPerson().setLastName("A'CustB4'n DidntPay");

            Contact custContact = new Contact();
            custContact.setPerson(new Person());
            custContact.setPrimaryPhone("3033330000");
            custContact.setEmailAddress("Cust@junit.com");
            custContact.getPerson().setFirstName("Customer");
            custContact.getPerson().setLastName("contactCust");

            PaymentMethod recurringPaymentMethod = new PaymentMethod();
            recurringPaymentMethod.setPaymentMethodType(
                RbmPaymentMethodEnum.ACH_RECURRING.getWbPaymentMethodName());
            recurringPaymentMethod.setEft(new EFT());
            recurringPaymentMethod.getEft().setBankAccountHolder("John Ya Ya");
            recurringPaymentMethod.getEft().setBankAccountNumber("123123123");
            recurringPaymentMethod.getEft().setBankCode("123123");
            recurringPaymentMethod.getEft().setPaymentType(EFTPaymentTypeEnum.C
                .getWildBlueName());

            valBean.setField("recurringPaymentMethod", recurringPaymentMethod);

            valBean.setField("transactionReference", "123123123");
            valBean.setField("accountContact", acctContact);
            valBean.setField("customerContact", custContact);

            valBean.setField("isAddressWithinCityLimits", true);

            //            errors = getValidatorTool().perform(valBean);
            //            assertNull("TestCase expected no errors,... but received: "
            //                + parseValidationErrors(errors), parseValidationErrors(errors));
        }
        catch (Exception e)
        {
            assertNull("Unexpected exception occured: " + String.valueOf(e), e);
        }
    }

    @Test public void testAddServiceAgreement()
    {
        try
        {
            String requestName = "addServiceAgreement";

            ValidationBean valBean = new ValidationBean(requestName);

            // Need this guy for the nested object/complexType validation.
            Contact serviceContact = new Contact();
            serviceContact.setPerson(new Person());

            valBean.setField("accountReference", "123123123");
            valBean.setField("transactionReference", "123123123");
            valBean.setField("soldBy", "bob");
            valBean.setField("serviceAgreementReference", "123123123");
            valBean.setField("equipmentPlan", "plan9");
            valBean.setField("startDate", new Date());
            valBean.setField("serviceContact", serviceContact);
            // valBean.setField("salesChannelType", "typeA");
            // valBean.setField("taxJurisdictionCode", "jcode");

            // Should get an error, Contact type has required children which are null.
            List<ValidationError> errors = getValidatorTool().perform(valBean);
            assertNotNull(
                "Required field (serviceContact.??) sent with null required children. Did not receive any validation error.",
                parseValidationErrors(errors));

            // Fill up everything which is required,... should then work.
            serviceContact.getPerson().setFirstName("Ben");
            serviceContact.getPerson().setLastName("A'CustB4'n DidntPay");
            serviceContact.setPrimaryPhone("3039992222");
            serviceContact.setEmailAddress("Lousy@lice.com");
            errors = getValidatorTool().perform(valBean);
            //            assertNull("TestCase expected no errors,... but received: "
            //                + parseValidationErrors(errors), parseValidationErrors(errors));

            // Check a null complex object causes error for required.
            valBean.setField("serviceContact", null);
            errors = getValidatorTool().perform(valBean);
            assertNotNull(
                "Required field (serviceContact) sent with null value. Did not receive any validation error.",
                parseValidationErrors(errors));
            // Make sure we have precisely the error messages we expected.
            assertErrorMessageExists(errors,
                getExpectedValidationError(requestName, "serviceContact",
                    "required"));
        }
        catch (Exception e)
        {
            assertNull("Unexpected exception occured: " + String.valueOf(e), e);
        }
    }

    @Test public void testGetAccount()
    {
        try
        {
            String requestName = "getAccount";

            // Use something valid... account ref is required, so just stick anything in there.
            ValidationBean valBean = new ValidationBean(requestName);
            valBean.setField("accountReference", "123123123");

            List<ValidationError> errors = getValidatorTool().perform(valBean);
            assertNull("TestCase expected no errors,... but received: "
                + parseValidationErrors(errors), parseValidationErrors(errors));

            // Now lets test the required field is empty...
            valBean.setField("accountReference", "");
            errors = getValidatorTool().perform(valBean);
            assertNotNull(
                "Required field sent as empty string. Did not receive any validation error.",
                parseValidationErrors(errors));
            // Make sure we have precisely the error messages we expected.
            assertErrorMessageExists(errors,
                getExpectedValidationError(requestName, "accountReference",
                    "required"));

            // Now lets test the required field is null...
            valBean.setField("accountReference", null);
            errors = getValidatorTool().perform(valBean);
            assertNotNull(
                "Required field sent with null value. Did not receive any validation error.",
                parseValidationErrors(errors));
            // Make sure we have precisely the error messages we expected.
            assertErrorMessageExists(errors,
                getExpectedValidationError(requestName, "accountReference",
                    "required"));

            // Now lets test the required field too big... 1 char to big, good test
            String accountRef =
                "1234567-10_234567-20_234567-30_234567-40_234567-50_234567-60_234567-70_234567-80x";
            assertTrue("accountRef should be 81 chars, it is: "
                + accountRef.length(), accountRef.length() == 81);
            valBean.setField("accountReference", accountRef);
            errors = getValidatorTool().perform(valBean);
            assertNotNull(
                "Required field sent with null value. Did not receive any validation error.",
                parseValidationErrors(errors));
            // Make sure we have precisely the error messages we expected.
            assertErrorMessageExists(errors,
                getExpectedValidationError(requestName, "accountReference",
                    "maxLength"));

            // Exactly 80, should be okie dokie
            accountRef =
                "1234567-10_234567-20_234567-30_234567-40_234567-50_234567-60_234567-70_234567-80";
            assertTrue("accountRef should be 80 chars, it is: "
                + accountRef.length(), accountRef.length() == 80);
            valBean.setField("accountReference", accountRef);
            errors = getValidatorTool().perform(valBean);
            assertNull("TestCase expected no errors,... but received: "
                + parseValidationErrors(errors), parseValidationErrors(errors));
        }
        catch (Exception e)
        {
            assertNull("Unexpected exception occured: " + String.valueOf(e), e);
        }
    }

    @Test public void testSuspendAllServiceAgreements()
    {
        try
        {
            String requestName = "suspendAllServiceAgreements";

            // Use something valid... account ref is required, so just stick anything in there.
            ValidationBean valBean = new ValidationBean(requestName);
            valBean.setField("transactionReference", "123123123");
            valBean.setField("accountReference", "123123123");
            valBean.setField("reason", "bogus");
            valBean.setField("suspendDate", new Date());

            List<ValidationError> errors = getValidatorTool().perform(valBean);
            assertNull("TestCase expected no errors,... but received: "
                + parseValidationErrors(errors), parseValidationErrors(errors));
        }
        catch (Exception e)
        {
            assertNull("Unexpected exception occured: " + String.valueOf(e), e);
        }
    }

    @Test public void testUpdateAccountContactPhoneError()
    {
        try
        {
            String requestName = "updateAccountContactPhone";
            ValidationBean valBean;
            List<ValidationError> errors;

            // We expect phone validation error (prim or sec is required)
            valBean = new ValidationBean(requestName);
            valBean.setField("transactionReference", "123123123");
            valBean.setField("accountReference", "123123123");
            errors = getValidatorTool().perform(valBean);
            assertNotNull("TestCase expected errors,... but none received: ",
                parseValidationErrors(errors));
            //System.out.println(parseValidationErrors(errors));

            // We expect phone validation error (tn format not valid)
            valBean = new ValidationBean(requestName);
            valBean.setField("transactionReference", "123123123");
            valBean.setField("accountReference", "123123123");
            valBean.setField("phoneSecondary", "(22)123-3");
            errors = getValidatorTool().perform(valBean);
            assertNotNull("TestCase expected errors,... but none received: ",
                parseValidationErrors(errors));
            //            assertTrue("TestCase expected 1 error,... but received: "
            //                + errors.size(), errors.size() == 1);
            //System.out.println(parseValidationErrors(errors));

            // We expect phone validation error (tn format not valid)
            valBean = new ValidationBean(requestName);
            valBean.setField("transactionReference", "123123123");
            valBean.setField("accountReference", "123123123");
            valBean.setField("phonePrimary", "(22)123-3");
            valBean.setField("phoneSecondary", "3033333333");
            errors = getValidatorTool().perform(valBean);
            assertNotNull("TestCase expected errors,... but none received: ",
                parseValidationErrors(errors));
            //            assertTrue("TestCase expected 1 error,... but received: "
            //                + errors.size(), errors.size() == 1);
            //System.out.println(parseValidationErrors(errors));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            assertNull("Unexpected exception occured: " + String.valueOf(e), e);
        }
    }

    @Test public void testUpdateAccountContactPhoneSuccess()
    {
        try
        {
            String requestName = "updateAccountContactPhone";

            ValidationBean valBean = new ValidationBean(requestName);
            valBean.setField("transactionReference", "123123123");
            valBean.setField("accountReference", "123123123");
            valBean.setField("phonePrimary", "(303)333-3333x1234");

            List<ValidationError> errors = getValidatorTool().perform(valBean);
            assertNull("TestCase expected no errors,... but received: "
                + parseValidationErrors(errors), parseValidationErrors(errors));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            assertNull("Unexpected exception occured: " + String.valueOf(e), e);
        }
    }
}
