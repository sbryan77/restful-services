package com.viasat.common.validator;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Locale;

import org.apache.commons.validator.Field;
import org.apache.commons.validator.Form;
import org.apache.commons.validator.ValidatorAction;
import org.junit.Before;

import com.viasat.common.fault.ValidationError;
import com.viasat.common.fault.ValidatorTool;

public abstract class BaseValidatorTest
{
	private ValidatorTool validatorTool;

	@Before
	public final void setUpInitValidatorTool() throws Exception
	{
		Throwable configurationException = null;

		if (validatorTool == null)
		{
			try
			{

				ValidatorTool.setConfigurationResources(new String[]
				{ "commonwsfault-validation-common.xml", "test-commonwsfault-validation.xml" });
				validatorTool = ValidatorTool.getInstance();
			}
			catch (Throwable e)
			{
				e.printStackTrace();
				configurationException = e;
			}
		}

		assertNull(
				"Validation configuration is not consumable... Exc: "
						+ String.valueOf(configurationException), configurationException);

		// Never could this happen?
		assertNotNull("Validation condfiguration is not initialized somehow!", validatorTool);
	}

	protected final void assertErrorMessageExists(List<ValidationError> validationErrors,
			String errorMsg)
	{
		boolean msgExists = false;

		for (ValidationError err : validationErrors)
		{
			if ((errorMsg != null) && errorMsg.equals(err.getValidationCode()))
			{
				msgExists = true;
				break;
			}
		}

		assertTrue("Expected error '" + errorMsg + "' is not included in validation errors.",
				msgExists);
	}

	protected final String getExpectedValidationError(String formName, String propertyName,
			String validatorName)
	{
		Form form = validatorTool.getResources().getForm(Locale.getDefault(), formName);
		Field field = form.getField(propertyName);
		ValidatorAction validator = validatorTool.getResources().getValidatorAction(validatorName);
		String msgKey = (validator != null) ? validator.getMsg() : null;

		String message = ((form != null) && (msgKey != null)) ? validatorTool.getMessage(msgKey,
				field.getArgs(validatorName)) : null;

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
				errorString += err.toString() + ", ";
			}

			errorString += "]";
		}

		return errorString;
	}
}
