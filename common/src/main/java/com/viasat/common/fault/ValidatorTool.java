package com.viasat.common.fault;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.Field;
import org.apache.commons.validator.Form;
import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.ValidatorException;
import org.apache.commons.validator.ValidatorResources;
import org.apache.commons.validator.ValidatorResult;
import org.apache.commons.validator.ValidatorResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import com.viasat.wildblue.common.validator.AbstractValidator;
import com.viasat.wildblue.common.validator.ValidationBean;

/**
 * The Class ValidatorTool.
 */
public final class ValidatorTool extends AbstractValidatorTool
{
	private static Logger LOGGER = LoggerFactory.getLogger(ValidatorTool.class);

	/**
	 * Option prefix for resource bundle properties, nice if the props file is
	 * shared for more than validation properties to distinguish entries from
	 * other properties.
	 */
	private static final String MSG_PREFIX = "Validator.";

	private static boolean configurationInitialized = false;
	private static ValidatorResources resources;
	private static List<String> resourceList = new ArrayList<String>();
	private static final String DEFAULT_FIELD_SET_VAR_NAME = "fieldSet";

	/**
	 * Create an instance using the given Locale.
	 */
	private ValidatorTool(Locale locale)
	{
		if (locale == null)
		{
			m_locale = Locale.getDefault();
		}
		else
		{
			m_locale = locale;
		}
	}

	/**
	 * Clear the configuration, use to change the configuration files. Only
	 * seems to be used by testing classes
	 */
	public static synchronized void clearConfiguration()
	{
		configurationInitialized = false;
		resourceList.clear();
		resources = null;
	}

	/**
	 * Return an instance of a ValidatorTool initialized to use the default
	 * Locale.
	 */
	public static ValidatorTool getInstance() throws ValidatorToolException
	{
		return getInstance(null);
	}

	/**
	 * Return an instance of a ValidatorTool initialized to use the given
	 * Locale.
	 *
	 * @param loc
	 *            The Locale to use.
	 */
	public static ValidatorTool getInstance(Locale loc) throws ValidatorToolException
	{
		if (!configurationInitialized)
		{
			synchronized (ValidatorTool.class)
			{
				// looks weird to repeat this if clause, but protects against
				// two threads hitting this simultaneously and stepping on each
				// other.
				if (!configurationInitialized)
				{
					if (resourceList.isEmpty())
					{
						setDefaultResourceConfiguration();
					}

					configure();
				}
			}
		}

		if ((resourceList == null) || resourceList.isEmpty())
			throw new ValidatorToolException(
					"ValidatorTool is not configured. Call setConfigurationResource() "
							+ "before getInstance().");
		else
			// ENHANCEMENT OPPORTUNITY
			// Since ValidatorTool is stateless except for locale, store a map
			// of
			// "singletons" keyed by locale to avoid constructing new instances
			// on every request.
			return new ValidatorTool(loc);
	}

	/**
	 * Set a configuration file to the list of resources.
	 *
	 * @param resourceName
	 *            The resource (XML configuration), must be located on the
	 *            class-path as.
	 */
	public static synchronized void setConfigurationResource(String resourceName)
			throws ValidatorToolException
	{
		if (resourceName != null)
		{
			resourceList.add(resourceName);

			// if the configuration has been initialized, we need to reload it,
			// since
			// there is a new resource to process.
			if (configurationInitialized)
			{
				configure();
			}
		}
	}

	/**
	 * Sets a list of configuration files to the list of resources.
	 *
	 * @param resourceNames
	 *            The resources (XML configurations), each must be located on
	 *            the class-path.
	 *
	 * @throws SAXException
	 * @throws IOException
	 */
	public static synchronized void setConfigurationResources(String[] resourceNames)
			throws ValidatorToolException
	{
		if (resourceNames != null)
		{
			Collections.addAll(resourceList, resourceNames);

			// if the configuration has been initialized, we need to reload it,
			// since
			// there is a new resource to process.
			if (configurationInitialized)
			{
				configure();
			}
		}
	}

	/**
	 * Convenience method that performs multiple value replacement
	 *
	 * @param key
	 *            the key
	 * @param replacementValues
	 *            the replacement values
	 *
	 * @return the string
	 */
	public String getMessage(String messageKey)
	{
		return FaultKeyEnum.findCodeByKey(messageKey);
	}

	@Override
	public final ValidatorResources getResources()
	{
		return resources;
	}

	/**
	 * Performs the validation. As no form is specified, the bean class name is
	 * used. The message key retrieving works as follows:
	 *
	 * <ul>
	 * <li>If the validating field has a message, its key is returned</li>
	 * <li>Else, the validator action message is returned</li>
	 * <li>Otherwise, a default message like "validator.(form).(propertyname)"
	 * is returned</li>
	 * </ul>
	 *
	 * @param bean
	 *            the bean
	 *
	 * @return an array of messages or null if the validation gets passed
	 *
	 * @throws ValidatorException
	 */
	public final List<ValidationError> perform(Object bean) throws ValidatorToolException
	{
		List<ValidationError> errList = null;
		String form = getFormNameForObjectClass(bean);

		Collection<ValidationError> errCollection = performAndProcessResults(bean, form);

		if (errCollection instanceof List)
		{
			errList = (List<ValidationError>) errCollection;
		}
		else if (errCollection != null)
		{
			errList = new ArrayList<ValidationError>(errCollection.size());
			errList.addAll(errCollection);
		}

		return errList;
	}

	/**
	 * Performs validation on the given bean.
	 *
	 * @param bean
	 *            The ValidationBean instance to validate.
	 *
	 * @return an array of messages or null if the validation gets passed
	 */
	public final List<ValidationError> perform(ValidationBean bean) throws ValidatorToolException
	{
		List<ValidationError> errList = null;

		Collection<ValidationError> errCollection = performAndProcessResults(
				bean.getValidationFieldsMap(), bean.getFormName());

		if (errCollection instanceof List)
		{
			errList = (List<ValidationError>) errCollection;
		}
		else if (errCollection != null)
		{
			errList = new ArrayList<ValidationError>(errCollection.size());
			errList.addAll(errCollection);
		}

		return errList;
	}

	@Override
	public ValidatorResults perform(Object bean, String form, Validator parentValidator)
			throws ValidatorToolException
	{
		Validator validator = new Validator(resources, form);

		setNestedValidatorResults(validator, parentValidator);

		AbstractValidator.setValidatorTool(validator, this);
		validator.setParameter(Validator.BEAN_PARAM, bean);
		validator.setOnlyReturnErrors(true);

		ValidatorResults results = null;

		if (!validationRulesExist(form))
			throw new ValidatorToolException("Missing validation instructions for: " + form);

		try
		{
			results = validator.validate();
		}
		catch (ValidatorException e)
		{
			LOGGER.error("ValidtionException occured:" + e.getMessage(), e);
			throw new ValidatorToolException(e);
		}

		return results;
	}

	/**
	 * Sets the resource/config files to the default values of
	 * "validation-common.xml" and "validation.xml".
	 */
	protected static void setDefaultResourceConfiguration()
	{
		resourceList.add("validation.xml");
		resourceList.add("commonwsfault-validation-common.xml");
	}

	/**
	 * Configure/initialize the static properties of the Validation framework
	 * using the current configurations (validation xml resources). If none have
	 * been explicitly set, then the default values will be used.
	 */
	private static synchronized void configure() throws ValidatorToolException
	{
		InputStream[] ins = null;

		try
		{
			ins = new InputStream[resourceList.size()];

			Iterator<String> it = resourceList.iterator();

			for (int i = 0; i < resourceList.size(); i++)
			{
				String res = it.next();
				ins[i] = toInputStream(res);
			}

			if (ins.length > 0)
			{
				resources = new ValidatorResources(ins);
				configurationInitialized = true;
			}
		}
		catch (IOException e)
		{
			throw new ValidatorToolException(e);
		}
		catch (SAXException e)
		{
			throw new ValidatorToolException(e);
		}
		finally
		{
			if (ins != null)
			{
				for (InputStream is : ins)
				{
					try
					{
						if (is != null)
						{
							is.close();
						}
					}
					catch (IOException e)
					{
						LOGGER.warn("Excepting attempting to close inputStream: " + e.getMessage(),
								e);
					}
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private static Map<String, Object> getPropertyNameMap(Object nestedBean)
	{
		if (nestedBean instanceof Map<?, ?>)
			// The top level object for ValidationBeans is a Map<String,Object>
			return (Map<String, Object>) nestedBean;

		Map<String, Object> nestedBeanMap = null;

		try
		{
			nestedBeanMap = BeanUtils.describe(nestedBean);
		}
		catch (IllegalAccessException e)
		{
			return null;
		}
		catch (InvocationTargetException e)
		{
			return null;
		}
		catch (NoSuchMethodException e)
		{
			return null;
		}

		return nestedBeanMap;
	}

	private static void populateInputValue(Map<String, Object> nestedBeanMap, String propertyName,
			Input input)
	{
		if (nestedBeanMap != null)
		{
			Object inputValue = nestedBeanMap.get(propertyName);

			if (inputValue != null)
			{
				String value = String.valueOf(inputValue);
				input.setValue(value);
			}
		}
	}

	/**
	 * Get an InputStream for the resource named.
	 *
	 * @param res
	 *
	 * @return
	 */
	private static InputStream toInputStream(String res)
	{
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

		if (LOGGER.isInfoEnabled())
		{
			URL resurl = classLoader.getResource(res);

			if (resurl == null)
			{
				LOGGER.error("Missing Resource: " + res);
			}
			else
			{
				LOGGER.info("Resource: " + res + " found at: " + resurl.getPath());
			}
		}

		// URL resurl = classLoader.getResource(res);
		// System.out.println("Resource: " + res + " found at: "
		// + resurl.getPath());

		return classLoader.getResourceAsStream(res);
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

	/**
	 * Performs the validation against the specified validation form.
	 *
	 * @param bean
	 * @param form
	 *
	 * @return An array of messages or null if the validation gets passed
	 *
	 * @throws ValidatorException
	 */
	private Collection<ValidationError> performAndProcessResults(Object bean, String form)
			throws ValidatorToolException
	{
		Validator validator = new Validator(resources, form);
		AbstractValidator.setValidatorTool(validator, this);
		validator.setParameter(Validator.BEAN_PARAM, bean);
		validator.setOnlyReturnErrors(true);

		// Setting the nested error validator results on the top level bean
		// causes validation errors from all levels to be reported.

		Map<Object, ValidatorResults> nestedValidatorResults = new HashMap<Object, ValidatorResults>();
		setNestedValidatorResults(validator, nestedValidatorResults);

		ValidatorResults results = null;

		if (!validationRulesExist(form))
			throw new ValidatorToolException("Missing validation instructions for: " + form);

		try
		{
			results = validator.validate();
		}
		catch (ValidatorException e)
		{
			LOGGER.error("ValidatorException occured:" + e.getMessage(), e);
			throw new ValidatorToolException(e);
		}

		Collection<ValidationError> nestedErrors = processResults(results, bean, form);

		Map<Object, ValidatorResults> param = getNestedValidatorResults(validator);

		if (param != null)
		{
			for (Entry<Object, ValidatorResults> entry : param.entrySet())
			{
				nestedErrors.addAll(processResults(entry.getValue(), entry.getKey(), null));
			}
		}

		return nestedErrors;
	}

	private void populateConditionalFieldInInput(Map<String, Object> nestedBeanMap, Field field,
			ValidationError validationError)
	{
		// This is used in requiredUnless as well,
		// but in that case we don't want the conditionalField
		// to show up in the Input list because it won't make
		// sense.

		String conditionField = field.getVarValue("conditionField");

		if (!StringUtils.isEmpty(conditionField))
		{
			Input input = new Input();
			input.setField(conditionField);

			populateInputValue(nestedBeanMap, conditionField, input);

			validationError.getInput().add(input);
		}
	}

	/**
	 * Calculates the messages to be returned.
	 *
	 * @param results
	 * @param formName
	 * @param nestedErrors
	 *
	 * @return
	 */

	private Collection<ValidationError> processResults(ValidatorResults results, Object nestedBean,
			String formName)
	{
		Set<ValidationError> errors = new HashSet<ValidationError>();

		if (formName == null)
		{
			formName = AbstractValidatorTool.getFormNameForObjectClass(nestedBean);
		}

		Map<String, Object> nestedBeanMap = getPropertyNameMap(nestedBean);

		if ((results != null) && (results.getPropertyNames() != null)
				&& !results.getPropertyNames().isEmpty())
		{
			Form form = resources.getForm(Locale.getDefault(), formName);
			Iterator<?> propertyNames = results.getPropertyNames().iterator();
			List<String> fieldSetNames = new ArrayList<String>();

			while (propertyNames.hasNext())
			{
				String propertyName = (String) propertyNames.next();

				Field field = form.getField(propertyName);

				ValidatorResult result = results.getValidatorResult(propertyName);

				Iterator<?> actionKeys = result.getActions();

				while (actionKeys.hasNext())
				{
					String actName = (String) actionKeys.next();

					ValidatorAction action = resources.getValidatorAction(actName);

					if (!result.isValid(actName))
					{
						// First, the field message
						String msgKey = null;
						msgKey = field.getMsg(actName);

						// Else, the action message
						if ((msgKey == null) && isUseActionMessages())
						{
							msgKey = action.getMsg();
						}

						// Else the prefix,form name, property name and action
						// name
						if (msgKey == null)
						{
							msgKey = MSG_PREFIX + form.getName() + "." + propertyName + "."
									+ actName;
						}

						String message = getMessage(msgKey);

						ValidationError validationError = new ValidationError();
						validationError.setValidationCode(message);
						validationError.getInput().add(new Input());

						validationError.getInput().get(0).setField(propertyName);

						populateInputValue(nestedBeanMap, propertyName, validationError.getInput()
								.get(0));

						if ("requiredConditional".equals(actName) || "dateBefore".equals(actName))
						{
							populateConditionalFieldInInput(nestedBeanMap, field, validationError);
							errors.add(validationError);
						}
						else if ("requiredOneOfMany".equals(actName)
								|| "mutuallyExclusive".equals(actName))
						{
							String fieldSetName = getFieldSetVarName(field, actName + "FieldSetVar");

							String fieldSetString = field.getVarValue(fieldSetName);

							if (!fieldSetNames.contains(fieldSetName))
							{
								String[] fieldSet = fieldSetString.split(",");

								for (String theField : fieldSet)
								{
									theField = (theField == null) ? null : theField.trim();

									if ((theField != null) && (theField.length() > 0)
											&& !theField.equals(propertyName))
									{
										Input input = new Input();
										input.setField(theField);
										populateInputValue(nestedBeanMap, theField, input);

										validationError.getInput().add(input);
									}
								}

								fieldSetNames.add(fieldSetName);
								errors.add(validationError);
							}
						}
						else
						{
							errors.add(validationError);
						}
					}
				}
			}
		}

		return errors;
	}
}
