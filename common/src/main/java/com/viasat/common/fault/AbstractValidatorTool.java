package com.viasat.common.fault;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.validator.Arg;
import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorException;
import org.apache.commons.validator.ValidatorResources;
import org.apache.commons.validator.ValidatorResults;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;

import java.text.MessageFormat;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;


public abstract class AbstractValidatorTool
{
    private static Logger LOGGER = LoggerFactory.getLogger(
            AbstractValidatorTool.class);

    /**
     * The Constant NESTED_ERRORS, used for the recursive validation of nested
     * complex types
     */
    private static final String NESTED_ERRORS = "nested_validation_errors";

    protected Locale m_locale;
    protected ResourceBundle m_messageBundle;

    private boolean useActionMessages = true;

    public abstract ValidatorResources getResources();

    /**
     * Performs the validation against the specified validation form.
     *
     * @param   bean
     * @param   form
     *
     * @return  An array of messages or null if the validation gets passed
     *
     * @throws  ValidatorException
     */
    public abstract ValidatorResults perform(Object bean, String form,
        Validator parentValidator);

    /**
     * Strips the package declaration off a class name to get the form name
     * which will be used to look up the validation rules for the class.
     *
     * @param   clz  The Class instance.
     *
     * @return  String 'Form' name.
     */
    public static String getFormNameForObjectClass(Class<?> clz)
    {
        String form = null;

        String packageName = clz.getPackage().getName();
        String className = clz.getName();

        if (packageName.length() > 0)
        {
            form = className.substring(packageName.length() + 1);
        }
        else
        {
            form = className;
        }

        return form;
    }

    /**
     * Strips the package declaration off the bean's class name to get the form
     * name which will be used to look up the validation rules for the class.
     *
     * @param   bean  the bean
     *
     * @return  the form name
     */
    public static String getFormNameForObjectClass(Object bean)
    {
        String form = null;

        if (bean != null)
        {
            form = getFormNameForObjectClass(bean.getClass());
        }

        return form;
    }

    /**
     * Gets the property value from the given bean.
     *
     * @param   bean      the bean
     * @param   property  the property
     *
     * @return  the property value
     */
    public static Object getPropertyValue(Object bean, String property)
    {
        Object value = null;

        try
        {
            value = PropertyUtils.getProperty(bean, property);
        }
        catch (IllegalAccessException e)
        {
            LOGGER.error(e.getMessage(), e);
        }
        catch (InvocationTargetException e)
        {
            LOGGER.error(e.getMessage(), e);
        }
        catch (NoSuchMethodException e)
        {
            LOGGER.error(e.getMessage(), e);
        }

        return value;
    }

    /**
     * Gets the Locale.
     *
     * @return  Returns the Locale.
     */
    public final Locale getLocale()
    {
        return m_locale;
    }

    /**
     * Convenience method that performs multiple value replacement
     *
     * @param   key                the key
     * @param   replacementValues  the replacement values
     *
     * @return  the string
     */
    public String getMessage(String messageKey, Arg[] messageArgs)
    {
        if ((messageArgs != null) && (messageArgs.length > 0))
        {
            String[] replacementValues = new String[messageArgs.length];
            int i = 0;

            for (Arg arg : messageArgs)
            {
                if (arg.isResource())
                {
                    try
                    {
                        replacementValues[i] = m_messageBundle.getString(
                                arg.getKey());
                    }
                    catch (Exception e)
                    {
                        replacementValues[i] = "[" + arg.getKey() + "]";

                        LOGGER.error(
                            "Error in validation configuration, unable to find ["
                            + arg.getKey() + "] in resource bundle.", e);
                    }
                }
                else
                {
                    replacementValues[i] = arg.getKey();
                }

                i++;
            }

            return getMessage(messageKey, replacementValues);
        }
        else
        {
            return m_messageBundle.getString(messageKey);
        }
    }

    /**
     * Convenience method that performs multiple value replacement
     *
     * @param   key                the key
     * @param   replacementValues  the replacement values
     *
     * @return  the string
     */
    public String getMessage(String messageKey, String[] replacementValues)
    {
        return new MessageFormat(m_messageBundle.getString(messageKey)).format(
                replacementValues);
    }

    /**
     * @return  The ResourceBundle instance used for the validation messages.
     */
    public ResourceBundle getMessageBundle()
    {
        return m_messageBundle;
    }

    public Map<Object, ValidatorResults> getNestedValidatorResults(
        Validator validator)
    {
        @SuppressWarnings("unchecked")
        Map<Object, ValidatorResults> existingNestedErrors =
            (Map<Object, ValidatorResults>)validator.getParameterValue(
                NESTED_ERRORS);
        return existingNestedErrors;
    }

    /**
     * Checks if is use action messages.
     *
     * @return  the useActionMessages
     */
    public final boolean isUseActionMessages()
    {
        return useActionMessages;
    }

    /**
     * Sets the Locale.
     *
     * @param  locale  the new locale
     */
    public final void setLocale(Locale locale)
    {
        this.m_locale = locale;
    }

    public void setNestedValidatorResults(Validator validator,
        Validator parentValidator)
    {
        setNestedValidatorResults(validator,
            getNestedValidatorResults(parentValidator));
    }

    public void setNestedValidatorResults(Validator validator,
        Map<Object, ValidatorResults> results)
    {
        validator.setParameter(NESTED_ERRORS, results);
    }

    /**
     * Sets the use action messages.
     *
     * @param  useActionMessages  the new use action messages
     */
    public final void setUseActionMessages(boolean useActionMessages)
    {
        this.useActionMessages = useActionMessages;
    }

    /**
     * Do validation rules exist for the given form/bean name?
     *
     * @param   form
     *
     * @return  boolean true is rules exist.
     */
    public final boolean validationRulesExist(String form)
    {
        return getResources().getForm(m_locale, form) != null;
    }
}
