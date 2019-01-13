package com.viasat.wildblue.common.validator;

import com.viasat.common.fault.AbstractValidatorTool;

import org.apache.commons.validator.Arg;
import org.apache.commons.validator.Field;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.ValidatorException;
import org.apache.commons.validator.util.ValidatorUtils;

import org.apache.cxf.common.util.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Validator class providing common validation that a value is found in a given
 * Enum classes set of values.
 */
public class EnumValidator
{
	private static Logger LOGGER = LoggerFactory.getLogger(EnumValidator.class);

	/**
	 * Copied in from account info, this checks to see if a value exists in an
	 * enum, ignoring case
	 * 
	 * @param bean
	 * @param action
	 * @param field
	 * @return
	 * @throws ValidatorException
	 */
	public boolean validateEnumValueExistsIgnoreCase(Object bean, ValidatorAction action,
			Field field) throws ValidatorException
	{
		boolean isValid = true;

		String fieldName = field.getProperty();
		String stringValue = ValidatorUtils.getValueAsString(bean, fieldName);

		if (!GenericValidator.isBlankOrNull(stringValue))
		{
			Object fieldValue = AbstractValidatorTool.getPropertyValue(bean, fieldName);

			isValid = false;

			String enumClassName = field.getVarValue("enumClass");

			if (!StringUtils.isEmpty(enumClassName))
			{
				try
				{
					Class<?> enumClass = Class.forName(enumClassName);

					if (enumClass.isEnum())
					{
						List<?> enums = Arrays.asList(enumClass.getEnumConstants());

						String accessorMethod = getEnumValueAccessorMethodName(field);

						for (Object o : enums)
						{
							Method m = o.getClass().getMethod(accessorMethod);
							Object enumValue = m.invoke(o, (Object[]) null);

							if (fieldValue.equals(enumValue))
							{
								isValid = true;
								break;
							}
							else if ((fieldValue instanceof String)
									&& (enumValue instanceof String))
							{
								String value1 = (String) fieldValue;
								String value2 = (String) enumValue;

								if (value1.equalsIgnoreCase(value2))
								{
									isValid = true;
									break;
								}
							}
						}

						// Check this, it's a bit of a hack to get the valid
						// values in the error
						// message,... should only ever do it once per
						// operation/field tho.
						if (!isValid && !isValidValuesArgSet(field, action))
						{
							setValidValuesArg(field, action, enums, accessorMethod);
						}
					}
					else
					{
						String errMsg = "Validator configuration error. EnumValidator, enumClass: "
								+ enumClassName + " is not an Enum.";
						LOGGER.error(errMsg);
						throw new ValidatorException(errMsg);
					}
				}
				catch (Exception e)
				{
					if (e instanceof ValidatorException)
					{
						throw (ValidatorException) e;
					}
					else
					{
						String errMsg = "Validator configuration error. Check for typos in your enumClass name in the validation xml file. EnumValidator caught exception: "
								+ e.getMessage();
						LOGGER.error(errMsg, e);
						throw new ValidatorException(errMsg);
					}
				}
			}
		}

		return isValid;
	}

	/**
	 * Validate enum value exists.
	 *
	 * @param bean
	 *            the bean
	 * @param action
	 *            the action
	 * @param field
	 *            the field
	 *
	 * @return true, if successful
	 *
	 * @throws ValidatorException
	 *             the validator exception
	 */
	public boolean validateEnumValueExists(Object bean, ValidatorAction action, Field field)
			throws ValidatorException
	{
		boolean isValid = true;

		String fieldName = field.getProperty();
		String stringValue = ValidatorUtils.getValueAsString(bean, fieldName);

		if (!GenericValidator.isBlankOrNull(stringValue))
		{
			Object fieldValue = AbstractValidatorTool.getPropertyValue(bean, fieldName);

			isValid = false;

			String enumClassName = field.getVarValue("enumClass");

			if (!StringUtils.isEmpty(enumClassName))
			{
				try
				{
					Class<?> enumClass = Class.forName(enumClassName);

					if (enumClass.isEnum())
					{
						List<?> enums = Arrays.asList(enumClass.getEnumConstants());

						String accessorMethod = getEnumValueAccessorMethodName(field);

						for (Object o : enums)
						{
							Method m = o.getClass().getMethod(accessorMethod);
							Object enumValue = m.invoke(o, (Object[]) null);

							if (fieldValue.equals(enumValue))
							{
								isValid = true;
								break;
							}
						}

						// Check this, it's a bit of a hack to get the valid
						// values in the error
						// message,... should only ever do it once per
						// operation/field tho.
						if (!isValid && !isValidValuesArgSet(field, action))
						{
							setValidValuesArg(field, action, enums, accessorMethod);
						}
					}
					else
					{
						String errMsg = "Validator configuration error. EnumValidator, enumClass: "
								+ enumClassName + " is not an Enum.";
						LOGGER.error(errMsg);
						throw new ValidatorException(errMsg);
					}
				}
				catch (Exception e)
				{
					if (e instanceof ValidatorException)
					{
						throw (ValidatorException) e;
					}
					else
					{
						String errMsg = "Validator configuration error. EnumValidator caught exception: "
								+ e.getMessage();
						LOGGER.error(errMsg, e);
						throw new ValidatorException(errMsg);
					}
				}
			}
		}

		return isValid;
	}

	/**
	 * Gets the enum valid values.
	 *
	 * @param enums
	 *            the enums
	 * @param accessorMethod
	 *            the accessor method
	 *
	 * @return the enum valid values
	 *
	 * @throws NoSuchMethodException
	 *             the no such method exception
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 * @throws InvocationTargetException
	 *             the invocation target exception
	 */
	private String getEnumValidValues(List<?> enums, String accessorMethod)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
	{
		StringBuilder sb = new StringBuilder("[");
		Iterator<?> iter = enums.iterator();

		while (iter.hasNext())
		{
			Object o = iter.next();
			Method m = o.getClass().getMethod(accessorMethod);
			Object enumValue = m.invoke(o, (Object[]) null);
			sb.append(String.valueOf(enumValue));

			if (iter.hasNext())
			{
				sb.append(", ");
			}
		}

		sb.append("]");
		return sb.toString();
	}

	/**
	 * Gets the enum value accessor method name.
	 *
	 * @param field
	 *            the field
	 *
	 * @return the enum value accessor method name
	 */
	private String getEnumValueAccessorMethodName(Field field)
	{
		String enumValueAccessor = field.getVarValue("enumValueAccessor");

		String enumValueAccessorMethod;

		if (enumValueAccessor != null)
		{
			enumValueAccessorMethod = enumValueAccessor;
		}
		else
		{
			enumValueAccessorMethod = "name";
		}

		return enumValueAccessorMethod;
	}

	/**
	 * Checks if is valid values action set.
	 *
	 * @param field
	 *            the field
	 * @param action
	 *            the action
	 *
	 * @return true, if is valid values action set
	 */
	private boolean isValidValuesArgSet(Field field, ValidatorAction action)
	{
		boolean isValidValuesArgSet = false;
		String actionName = action.getName();
		Arg[] enumArgs = field.getArgs(actionName);

		if (enumArgs != null)
		{
			for (Arg arg : enumArgs)
			{
				if (!arg.isResource() && actionName.equals(arg.getName()))
				{
					// This is lame,.. how else to recognize if we have it there
					// yet tho?
					if (arg.getKey().startsWith("["))
					{
						isValidValuesArgSet = true;
						break;
					}
				}
			}
		}

		return isValidValuesArgSet;
	}

	/**
	 * Sets the valid values arg.
	 *
	 * @param field
	 *            the field
	 * @param action
	 *            the action
	 * @param enums
	 *            the enums
	 * @param accessorMethod
	 *            the accessor method
	 *
	 * @throws NoSuchMethodException
	 *             the no such method exception
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 * @throws InvocationTargetException
	 *             the invocation target exception
	 */
	private void setValidValuesArg(Field field, ValidatorAction action, List<?> enums,
			String accessorMethod)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
	{
		synchronized (field)
		{
			if (!isValidValuesArgSet(field, action))
			{
				String validValues = getEnumValidValues(enums, accessorMethod);

				Arg arg = new Arg();
				arg.setResource(false);
				arg.setKey(validValues);
				arg.setName(action.getName());
				field.addArg(arg);
			}
		}
	}
}
