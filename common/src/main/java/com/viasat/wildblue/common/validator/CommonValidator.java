package com.viasat.wildblue.common.validator;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.Field;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorException;
import org.apache.commons.validator.util.ValidatorUtils;

import com.viasat.common.fault.AbstractValidatorTool;

/**
 * Validator class providing common validations intended to be used across the
 * Wild Blue OSS/BSS suite of Facade and Internal Web Services (the Private
 * API). Validation methods which operate on classes defined in the
 * WildBlue/Common project, or which otherwise would be useful across the suite
 * of Web Services (and which are not dependent on any service specific types)
 * may be put in this class.
 */
public class CommonValidator extends AbstractValidator
{
	// used to alter field set var name, if multiple validators for
	// same field use a field set, and the sets need to be different.
	private static final String DEFAULT_FIELD_SET_VAR_NAME = "fieldSet";

	/**
	 * Check if the given (optional) value is a DateTime according the given
	 * format.
	 */
	public boolean isDateTime(Object bean, Field field)
	{
		boolean isDateTime = true; // Null is valid for optional field
		String valueStr = ValidatorUtils.getValueAsString(bean, field.getProperty());
		if (valueStr != null)
		{
			isDateTime = GenericValidator.isDate(valueStr, field.getVarValue("format"), true);
		}
		return isDateTime;
	}

	/**
	 * Check if the given double value is within specified range. The xml
	 * configuration for the field validation must include a var named
	 * "minValue" with a double value, and a var named "maxValue" with a double
	 * value.
	 *
	 * @param bean
	 *            The Object having the field/property to check.
	 * @param field
	 *            The Field object specifying which field/property to check on
	 *            the given bean.
	 *
	 * @return A value of true/false indicating if the value is in the range.
	 */
	public boolean isInRangeDouble(Object bean, Field field)
	{
		boolean isInRange = true;
		String valueStr = ValidatorUtils.getValueAsString(bean, field.getProperty());
		if (valueStr != null)
		{
			try
			{
				double value = new Double(valueStr);
				double minValue = new Double(field.getVarValue("minValue"));
				double maxValue = new Double(field.getVarValue("maxValue"));
				isInRange = GenericValidator.isInRange(value, minValue, maxValue);
			}
			catch (Exception e)
			{
				isInRange = false;
			}
		}
		return isInRange;
	}

	/**
	 * Checks if is of type int.
	 *
	 * @param bean
	 *            the bean
	 * @param field
	 *            the field
	 *
	 * @return true, if is integer
	 */
	public boolean isInteger(Object bean, Field field)
	{
		boolean isValid;
		Object valueObject = AbstractValidatorTool.getPropertyValue(bean, field.getProperty());
		if (valueObject != null)
		{
			if ((valueObject instanceof String) && StringUtils.isEmpty((String) valueObject))
			{
				// This is not a required field check...
				isValid = true;
			}
			else if (valueObject instanceof Integer)
			{
				isValid = true;
			}
			else
			{
				String valueStr = ValidatorUtils.getValueAsString(bean, field.getProperty());
				try
				{
					new Integer(valueStr);
					// No Exception, it's an int!
					isValid = true;
				}
				catch (Exception e)
				{
					isValid = false;
				}
			}
		}
		else
		{
			// Null is valid, use validateRequired when field is also required.
			isValid = true;
		}
		return isValid;
	}

	/**
	 * Checks if the field is of type long.
	 *
	 * @param bean
	 *            the bean
	 * @param field
	 *            the field
	 *
	 * @return true, if is long
	 */
	public boolean isLong(Object bean, Field field)
	{
		boolean isValid;
		Object valueObject = AbstractValidatorTool.getPropertyValue(bean, field.getProperty());
		if (valueObject != null)
		{
			if ((valueObject instanceof String) && StringUtils.isEmpty((String) valueObject))
			{
				// This is not a required field check...
				isValid = true;
			}
			else if (valueObject instanceof Long)
			{
				isValid = true;
			}
			else
			{
				String valueStr = ValidatorUtils.getValueAsString(bean, field.getProperty());
				try
				{
					new Long(valueStr);
					// No Exception, it's a long!
					isValid = true;
				}
				catch (Exception e)
				{
					isValid = false;
				}
			}
		}
		else
		{
			// Null is valid, use validateRequired when field is also required.
			isValid = true;
		}
		return isValid;
	}

	/**
	 * Checks if the value of the field is positive. Assuming the value is
	 * integer or long. Use isInteger or isLong first.
	 *
	 * @param bean
	 *            the bean
	 * @param field
	 *            the field
	 *
	 * @return true, if is positive
	 */
	public boolean isPositive(Object bean, Field field)
	{
		boolean isValid;
		Object valueObject = AbstractValidatorTool.getPropertyValue(bean, field.getProperty());
		if (valueObject != null)
		{
			if ((valueObject instanceof Integer && (Integer) valueObject > 0)
					|| (valueObject instanceof Long && (Long) valueObject > 0))
			{
				isValid = true;
			}
			else
			{
				isValid = false;
			}
		}
		else
		{
			// Null is valid, use validateRequired when field is also required.
			isValid = true;
		}
		return isValid;
	}

	/**
	 * Checks if the given Field is a valid email address.
	 *
	 * @param bean
	 *            The Object having the field/property to check.
	 * @param field
	 *            The Field object specifying which field/property to check on
	 *            the given bean.
	 *
	 * @return A value of true/false indicating if the value is valid.
	 */
	public boolean validateEmail(Object bean, Field field)
	{
		String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
		if (!GenericValidator.isBlankOrNull(value))
			return GenericValidator.isEmail(value);
		else
			// Null is valid, this is not a required field check, just an email
			// validation.
			return true;
	}

	/**
	 * Validate list max size.
	 *
	 * @param bean
	 *            the bean
	 * @param field
	 *            the field
	 *
	 * @return true, if successful
	 */
	public boolean validateListMaxSize(Object bean, Field field)
	{
		boolean isValid = true;
		Object valueObject = AbstractValidatorTool.getPropertyValue(bean, field.getProperty());
		if (valueObject instanceof Collection<?>)
		{
			String maxSize = field.getVarValue("maxListSize");
			if (maxSize != null)
			{
				@SuppressWarnings("rawtypes")
				Collection collection = (Collection) valueObject;
				if (collection.size() > Integer.parseInt(maxSize))
				{
					isValid = false;
				}
			}
		}
		return isValid;
	}

	/**
	 * Checks if the given Field is within the specified maxLength. The xml
	 * configuration for the field validation must include a var named
	 * "maxLength" with an integer value.
	 *
	 * @param bean
	 *            The Object having the field/property to check.
	 * @param field
	 *            The Field object specifying which field/property to check on
	 *            the given bean.
	 *
	 * @return A value of true/false indicating if the value is valid.
	 */
	public boolean validateMaxLength(Object bean, Field field)
	{
		boolean isValid = true;
		Object valueObject = AbstractValidatorTool.getPropertyValue(bean, field.getProperty());
		if (valueObject instanceof Collection<?>)
		{
			@SuppressWarnings("rawtypes")
			Collection collection = (Collection) valueObject;
			isValid = validateMaxLengths(collection, field);
		}
		else
		{
			String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
			isValid = validateMaxLength(value, field);
		}
		return isValid;
	}

	/**
	 * Checks if the given Field is within the specified minLength. The xml
	 * configuration for the field validation must include a var named
	 * "minLength" with an integer value.
	 *
	 * @param bean
	 *            The Object having the field/property to check.
	 * @param field
	 *            The Field object specifying which field/property to check on
	 *            the given bean.
	 *
	 * @return A value of true/false indicating if the value is valid.
	 */
	public boolean validateMinLength(Object bean, Field field)
	{
		boolean isValid = true;
		Object valueObject = AbstractValidatorTool.getPropertyValue(bean, field.getProperty());
		if (valueObject instanceof Collection<?>)
		{
			@SuppressWarnings("rawtypes")
			Collection collection = (Collection) valueObject;
			isValid = validateMinLengths(collection, field);
		}
		else
		{
			String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
			isValid = validateMinLength(value, field);
		}
		return isValid;
	}

	/**
	 * If one, then not the other. Unlike requiredOneOfMany, each field needs to
	 * be configured. Validate mutually exclusive.
	 *
	 * @param bean
	 *            the bean
	 * @param field
	 *            the field
	 *
	 * @return true, if successful
	 *
	 * @throws ValidatorException
	 *             the validator exception
	 */
	public boolean validateMutuallyExclusive(Object bean, Field field) throws ValidatorException
	{
		boolean isValid = true;
		String fieldName = field.getProperty();
		String stringValue = ValidatorUtils.getValueAsString(bean, fieldName);
		// If this guy has a value, check others in set, they must be null.
		if (!GenericValidator.isBlankOrNull(stringValue))
		{
			String fieldSetString = field
					.getVarValue(getFieldSetVarName(field, "mutuallyExclusiveFieldSetVar"));
			if (!StringUtils.isEmpty(fieldSetString))
			{
				String[] fieldSet = fieldSetString.split(",");
				for (String anotherOfMany : fieldSet)
				{
					anotherOfMany = anotherOfMany.trim();
					// The listed set may include this field, so
					// don't check it again.
					if (anotherOfMany.equals(fieldName))
					{
						continue;
					}
					String anotherOfManyValue = ValidatorUtils.getValueAsString(bean,
							anotherOfMany);
					if (!GenericValidator.isBlankOrNull(anotherOfManyValue))
					{
						// Mutual Exclusive check, if we find another in the set
						// which
						// is non null/empty, validation fails.
						isValid = false;
						break;
					}
				}
			}
		}
		return isValid;
	}

	/**
	 * Does not fail on null objects (these are non-required fields).
	 * Recursively checks that all required fields on nested complex types are
	 * populated. This recursion into nested types can be turned off by setting
	 * a var in the xml configuration for the validation filed of (var-name:
	 * skipValidateNestedComplexTypes, var-value: true).
	 *
	 * @param bean
	 *            The Object having the field/property to check.
	 * @param field
	 *            The Field object specifying which field/property to check on
	 *            the given bean.
	 *
	 * @return A value of true/false indicating if the value is valid.
	 */
	public boolean validateOptional(Object bean, Field field, Validator validator)
			throws ValidatorException
	{
		String fieldName = field.getProperty();
		String stringValue = ValidatorUtils.getValueAsString(bean, fieldName);
		boolean isNotNull = !GenericValidator.isBlankOrNull(stringValue);
		if (isNotNull)
		{
			validateNestedTypes(bean, validator, field);
		}
		// Looks weird, but we are not validating this fields really, rather is
		// composition.
		// the recursive checks will set the NESTED_ERROR param, which is
		// checked up-stream.
		return true;
	}

	/**
	 * Validate the field is not empty if it is present.
	 */
	public boolean validateNotEmptyIfPresent(Object bean, Field field, Validator validator)
			throws ValidatorException
	{
		String fieldName = field.getProperty();
		String stringValue = ValidatorUtils.getValueAsString(bean, fieldName);
		boolean isValid = (stringValue == null || stringValue.trim().length() > 0)
				&& listValueHasItems(bean, fieldName);
		if (isValid)
		{
			validateNestedTypes(bean, validator, field);
		}
		return isValid;
	}

	/**
	 * Checks if the field is required. Also, recursively checks that all
	 * required fields on nested complex types are populated. This recursion
	 * into nested types can be turned off by setting a var in the xml
	 * configuration for the validation filed of (var-name:
	 * skipValidateNestedComplexTypes, var-value: true).
	 *
	 * @param bean
	 *            The Object having the field/property to check.
	 * @param field
	 *            The Field object specifying which field/property to check on
	 *            the given bean.
	 *
	 * @return A value of true/false indicating if the value is valid.
	 */
	public boolean validateRequired(Object bean, Field field, Validator validator)
			throws ValidatorException
	{
		String fieldName = field.getProperty();
		String stringValue = ValidatorUtils.getValueAsString(bean, fieldName);
		boolean isValid = (!GenericValidator.isBlankOrNull(stringValue))
				&& listValueHasItems(bean, fieldName);
		if (isValid)
		{
			validateNestedTypes(bean, validator, field);
		}
		return isValid;
	}

	/**
	 * Validate required conditional.
	 *
	 * @param bean
	 *            the bean
	 * @param field
	 *            the field
	 * @param validator
	 *            the validator
	 *
	 * @return true, if successful
	 *
	 * @throws ValidatorException
	 *             the validator exception
	 */
	public boolean validateRequiredConditional(Object bean, Field field, Validator validator)
			throws ValidatorException
	{
		boolean isValid = true;
		String fieldName = field.getProperty();
		String stringValue = ValidatorUtils.getValueAsString(bean, fieldName);
		// if this field is non-null, then we don't need to check the
		// requirement condition.
		if (GenericValidator.isBlankOrNull(stringValue))
		{
			// Its null, assume it's required until we test the condition.
			isValid = false;
			String conditionField = field.getVarValue("conditionField");
			// When "conditionValue" is null, interpret it to mean that ANY
			// value meets
			// the condition, meaning when the "conditionField" has any value,
			// then this
			// field is also required.
			String conditionValue = field.getVarValue("conditionFieldValue");
			if (!StringUtils.isEmpty(conditionField))
			{
				String testValue = ValidatorUtils.getValueAsString(bean, conditionField);
				if (StringUtils.isEmpty(conditionValue))
				{
					// When "conditionValue" is null, interpret it to mean that
					// ANY value meets
					// the condition, meaning when the "conditionField" is null,
					// this field is
					// not required.
					if (StringUtils.isEmpty(testValue))
					{
						// Null value is valid, field is not required.
						isValid = true;
					}
					else
					{
						// Null value is NOT valid, field is required.
						isValid = false;
					}
				}
				else
				{
					if (conditionValue.equals(testValue))
					{
						// Condition is true, therefore null value is NOT valid,
						// field is required.
						isValid = false;
					}
					else
					{
						// Condition is false, field is not required.
						isValid = true;
					}
				}
			}
		}
		if (isValid)
		{
			validateNestedTypes(bean, validator, field);
		}
		return isValid;
	}

	/**
	 * Checks if the field is required. Also, recursively checks that all
	 * required fields on nested complex types are populated. This recursion
	 * into nested types can be turned off by setting a var in the xml
	 * configuration for the validation filed of (var-name:
	 * skipValidateNestedComplexTypes, var-value: true).
	 *
	 * @param bean
	 *            The Object having the field/property to check.
	 * @param field
	 *            The Field object specifying which field/property to check on
	 *            the given bean.
	 *
	 * @return A value of true/false indicating if the value is valid.
	 */
	public boolean validateRequiredOneOfMany(Object bean, Field field, Validator validator)
			throws ValidatorException
	{
		boolean isValid = true;
		String fieldName = field.getProperty();
		String stringValue = ValidatorUtils.getValueAsString(bean, fieldName);
		// if this field is non-null, then we don't need to check the others
		// in the set.
		if (GenericValidator.isBlankOrNull(stringValue))
		{
			// Unless we find another field in the set to be populated,
			// validation
			// fails for all required fields in the set are null/empty.
			isValid = false;
			String fieldSetString = field
					.getVarValue(getFieldSetVarName(field, "requiredOneOfManyFieldSetVar"));
			if (!StringUtils.isEmpty(fieldSetString))
			{
				String[] fieldSet = fieldSetString.split(",");
				for (String anotherOfMany : fieldSet)
				{
					anotherOfMany = anotherOfMany.trim();
					// The listed set may include this field, so
					// don't check it again.
					if (anotherOfMany.equals(fieldName))
					{
						continue;
					}
					String anotherOfManyValue = ValidatorUtils.getValueAsString(bean,
							anotherOfMany);
					if (!GenericValidator.isBlankOrNull(anotherOfManyValue))
					{
						isValid = true;
						break;
					}
				}
			}
		}
		if (isValid)
		{
			validateNestedTypes(bean, validator, field);
		}
		return isValid;
	}

	/**
	 * Validate required unless.
	 *
	 * @param bean
	 *            the bean
	 * @param field
	 *            the field
	 * @param validator
	 *            the validator
	 *
	 * @return true, if successful
	 *
	 * @throws ValidatorException
	 *             the validator exception
	 */
	public boolean validateRequiredUnless(Object bean, Field field, Validator validator)
			throws ValidatorException
	{
		boolean isValid = true;
		String fieldName = field.getProperty();
		String stringValue = ValidatorUtils.getValueAsString(bean, fieldName);
		// if this field is non-null, then we don't need to check the
		// requirement condition.
		if (GenericValidator.isBlankOrNull(stringValue))
		{
			// Its null, assume it's required until we test the condition.
			isValid = false;
			String conditionField = field.getVarValue("conditionField");
			// When "conditionValue" is null, interpret it to mean that ANY
			// value meets
			// the condition, meaning when the "conditionField" has any value,
			// then this
			// field is also required.
			String conditionValue = field.getVarValue("conditionFieldValue");
			if (!StringUtils.isEmpty(conditionField))
			{
				String testValue = ValidatorUtils.getValueAsString(bean, conditionField);
				if (StringUtils.isEmpty(conditionValue))
				{
					// When "conditionValue" is null, interpret it to mean that
					// ANY value meets
					// the condition, meaning when the "conditionField" is null,
					// this field is
					// not required.
					if (StringUtils.isEmpty(testValue))
					{
						// Null value is valid, field is not required.
						isValid = true;
					}
					else
					{
						// Null value is NOT valid, field is required.
						isValid = false;
					}
				}
				else
				{
					if (conditionValue.equals(testValue))
					{
						// Condition is true, therefore null value is is valid,
						// field is not required.
						isValid = true;
					}
					else
					{
						// Condition is not met, field is required.
						isValid = false;
					}
				}
			}
		}
		if (isValid)
		{
			validateNestedTypes(bean, validator, field);
		}
		return isValid;
	}

	/**
	 * Gets the field set var name.
	 *
	 * @param field
	 *            the field
	 *
	 * @return the field set var name
	 */
	private String getFieldSetVarName(Field field, String fieldSetNameVar)
	{
		String fieldSetVarName = field.getVarValue(fieldSetNameVar);
		if (StringUtils.isEmpty(fieldSetVarName))
		{
			fieldSetVarName = DEFAULT_FIELD_SET_VAR_NAME;
		}
		return fieldSetVarName;
	}

	private boolean listValueHasItems(Object bean, String fieldName)
	{
		// Some logic here to check for empty Lists, or lists full of empty
		// items.
		boolean isValid = true;
		Object fieldValue = AbstractValidatorTool.getPropertyValue(bean, fieldName);
		if (fieldValue instanceof Collection)
		{
			if (((Collection<?>) fieldValue).isEmpty())
			{
				isValid = false;
			}
			else
			{
				boolean listHasValidValues = false;
				for (Object listBeanItem : ((Collection<?>) fieldValue))
				{
					if ((listBeanItem != null) && (String.valueOf(listBeanItem) != null)
							&& (String.valueOf(listBeanItem).trim().length() > 0))
					{
						listHasValidValues = true;
						break;
					}
				}
				if (!listHasValidValues)
				{
					isValid = false;
				}
			}
		}
		return isValid;
	}

	/**
	 * Validate max length.
	 *
	 * @param value
	 *            the value
	 * @param field
	 *            the field
	 *
	 * @return true, if successful
	 */
	private boolean validateMaxLength(String value, Field field)
	{
		boolean isValid = true;
		if (!GenericValidator.isBlankOrNull(value))
		{
			String max = field.getVarValue("maxLength");
			if (max != null)
			{
				if (value.length() > Integer.parseInt(max))
				{
					isValid = false;
				}
			}
		}
		return isValid;
	}

	/**
	 * Validate max lengths.
	 *
	 * @param collection
	 *            the collection
	 * @param field
	 *            the field
	 */
	@SuppressWarnings("rawtypes")
	private boolean validateMaxLengths(Collection collection, Field field)
	{
		boolean isValid = true;
		for (Object o : collection)
		{
			if (o instanceof Collection)
			{
				isValid = validateMaxLengths(collection, field);
			}
			else
			{
				isValid = validateMaxLength(String.valueOf(o), field);
			}
			if (!isValid)
			{
				break;
			}
		}
		return isValid;
	}

	/**
	 * Validate min length.
	 *
	 * @param value
	 *            the value
	 * @param field
	 *            the field
	 *
	 * @return true, if successful
	 */
	private boolean validateMinLength(String value, Field field)
	{
		boolean isValid = true;
		if (!GenericValidator.isBlankOrNull(value))
		{
			String min = field.getVarValue("minLength");
			if (min != null)
			{
				if (value.length() < Integer.parseInt(min))
				{
					isValid = false;
				}
			}
		}
		return isValid;
	}

	/**
	 * Validate min lengths.
	 *
	 * @param collection
	 *            the collection
	 * @param field
	 *            the field
	 */
	@SuppressWarnings("rawtypes")
	private boolean validateMinLengths(Collection collection, Field field)
	{
		boolean isValid = true;
		for (Object o : collection)
		{
			if (o instanceof Collection)
			{
				isValid = validateMinLengths(collection, field);
			}
			else
			{
				isValid = validateMinLength(String.valueOf(o), field);
			}
			if (!isValid)
			{
				break;
			}
		}
		return isValid;
	}
}
