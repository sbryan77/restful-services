package com.viasat.wildblue.common.validator;

import com.viasat.wildblue.common.commondata.ValidationError;

import org.apache.commons.validator.Field;
import org.apache.commons.validator.Form;
import org.apache.commons.validator.ValidatorAction;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;

import java.util.List;
import java.util.Locale;


public abstract class BaseValidatorTest
{
    public static final String TEST_MESSAGES_PROPERTIES =
        "test-applicationResources";
    private ValidatorTool validatorTool;

    @Before public final void setUpInitValidatorTool() throws Exception
    {
        Throwable configurationException = null;

        if (validatorTool == null)
        {
            try
            {
                ValidatorTool.setMessageBundleName(TEST_MESSAGES_PROPERTIES);
                ValidatorTool.setConfigurationResources(
                    new String[] { "test-validation-common.xml" });

                validatorTool = ValidatorTool.getInstance();
            }
            catch (Throwable e)
            {
                e.printStackTrace();
                configurationException = e;
            }
        }

        assertNull("Validation configuration is not consumable... Exc: "
            + String.valueOf(configurationException), configurationException);

        // Never could this happen?
        assertNotNull("Validation condfiguration is not initialized somehow!",
            validatorTool);
    }

    protected final void assertErrorMessageExists(
        List<ValidationError> validationErrors, String errorMsg)
    {
        boolean msgExists = false;

        for (ValidationError err : validationErrors)
        {
            if ((errorMsg != null) && errorMsg.equals(err.getMessage()))
            {
                msgExists = true;
                break;
            }
        }

        assertTrue("Expected error '" + errorMsg
            + "' is not included in validation errors.", msgExists);
    }

    protected final String getExpectedValidationError(String formName,
        String propertyName, String validatorName)
    {
        Form form = validatorTool.getResources().getForm(Locale.getDefault(),
                formName);
        Field field = form.getField(propertyName);
        ValidatorAction validator = validatorTool.getResources()
            .getValidatorAction(validatorName);
        String msgKey = (validator != null) ? validator.getMsg() : null;

        String message = ((form != null) && (msgKey != null))
            ? validatorTool.getMessage(msgKey, field.getArgs(validatorName))
            : null;

        return message;
    }

    protected final ValidatorTool getValidatorTool()
    {
        return validatorTool;
    }

    protected final String parseValidationErrors(List<ValidationError> errors)
    {
        String errorString = null;

        if ((errors != null) && !errors.isEmpty())
        {
            errorString = "" + errors.size() + " validation errors: [";

            for (ValidationError err : errors)
            {
                errorString += err.getErrorCode() + "|" + err.getMessage()
                    + ", ";
            }

            errorString += "]";
        }

        return errorString;
    }
}
