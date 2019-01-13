package com.viasat.wildblue.common.validator;

import com.viasat.common.fault.AbstractValidatorTool;
import com.viasat.common.fault.ValidatorToolException;

import com.viasat.wildblue.common.commondata.ValidationError;

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

import java.io.IOException;
import java.io.InputStream;

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
import java.util.ResourceBundle;
import java.util.Set;


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

    /** The default resource bundle name which will be used. */
    public static final String MESSAGE_BUNDLE_DEFAULT = "applicationResources";

    private static boolean configurationInitialized = false;
    private static String messageBundleName = MESSAGE_BUNDLE_DEFAULT;
    private static ValidatorResources resources;
    private static List<String> resourceList = new ArrayList<String>();

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

        m_messageBundle = ResourceBundle.getBundle(messageBundleName, m_locale);
    }

    /**
     * Clear the configuration, use to change the configuration files.
     */
    public static synchronized void clearConfiguration()
    {
        configurationInitialized = false;
        resourceList.clear();
        resources = null;
        messageBundleName = MESSAGE_BUNDLE_DEFAULT;
    }

    /**
     * Return an instance of a ValidatorTool initialized to use the default
     * Locale.
     */
    public static ValidatorTool getInstance()
    {
        return getInstance(null);
    }

    /**
     * Return an instance of a ValidatorTool initialized to use the given
     * Locale.
     *
     * @param  loc  The Locale to use.
     */
    public static ValidatorTool getInstance(Locale loc)
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
        {
            throw new RuntimeException(
                "ValidatorTool is not configured. Call setConfigurationResource() "
                + "before getInstance().");
        }
        else
        {
            // ENHANCEMENT OPPORTUNITY
            // Since ValidatorTool is stateless except for locale, store a map
            // of
            // "singletons" keyed by locale to avoid constructing new instances
            // on every request.
            return new ValidatorTool(loc);
        }
    }

    /**
     * @return  The message bundle (file) name which is being used.
     */
    public static String getMessageBundleName()
    {
        return messageBundleName;
    }

    /**
     * Set a configuration file to the list of resources.
     *
     * @param  resourceName  The resource (XML configuration), must be located
     *                       on the class-path as.
     */
    public static synchronized void setConfigurationResource(
        String resourceName)
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
     * @param  resourceNames  The resources (XML configurations), each must be
     *                        located on the class-path.
     */
    public static synchronized void setConfigurationResources(
        String[] resourceNames)
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
     * Set the message bundle (properties file) name.
     *
     * @param  messageBundleName
     */
    public static void setMessageBundleName(String messageBundleName)
    {
        ValidatorTool.messageBundleName = messageBundleName;
    }

    /**
     * Gets the messages.
     *
     * @param   keys  the keys
     *
     * @return  the messages
     */
    public final String[] getMessages(String[] keys)
    {
        if (keys == null)
        {
            return null;
        }

        String[] messages = new String[keys.length];

        for (int i = 0; i < keys.length; i++)
        {
            messages[i] = m_messageBundle.getString(keys[i]);
        }

        return messages;
    }

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
     * @param   bean  the bean
     *
     * @return  an array of messages or null if the validation gets passed
     */
    public final List<ValidationError> perform(Object bean)
    {
        List<ValidationError> errList = null;
        String form = getFormNameForObjectClass(bean);

        Collection<ValidationError> errCollection = performAndProcessResults(
                bean, form);

        if (errCollection instanceof List)
        {
            errList = (List<ValidationError>)errCollection;
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
     * @param   bean  The ValidationBean instance to validate.
     *
     * @return  an array of messages or null if the validation gets passed
     */
    public final List<ValidationError> perform(ValidationBean bean)
    {
        List<ValidationError> errList = null;

        Collection<ValidationError> errCollection = performAndProcessResults(
                bean.getValidationFieldsMap(), bean.getFormName());

        if (errCollection instanceof List)
        {
            errList = (List<ValidationError>)errCollection;
        }
        else if (errCollection != null)
        {
            errList = new ArrayList<ValidationError>(errCollection.size());
            errList.addAll(errCollection);
        }

        return errList;
    }

    @Override public ValidatorResults perform(Object bean, String form,
        Validator parentValidator) throws ValidatorToolException
    {
        Validator validator = new Validator(resources, form);

        setNestedValidatorResults(validator, parentValidator);

        AbstractValidator.setValidatorTool(validator, this);
        validator.setParameter(Validator.BEAN_PARAM, bean);
        validator.setOnlyReturnErrors(true);

        ValidatorResults results = null;

        if (!validationRulesExist(form))
        {
            throw new RuntimeException("Missing validation instructions for: "
                + form);
        }

        try
        {
            results = validator.validate();
        }
        catch (ValidatorException e)
        {
            //TODO jkent not good to swallow error, but throwing it may cause problems

            LOGGER.error("ValidatorException occured:" + e.getMessage(), e);
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
        resourceList.add("validation-common.xml");
    }

    /**
     * Configure/initialize the static properties of the Validation framework
     * using the current configurations (validation xml resources, and resource
     * bundle name). If none have been explicitly set, then the default values
     * will be used.
     */
    private static synchronized void configure()
    {
        InputStream[] ins = null;

        try
        {
            ins = new InputStream[resourceList.size()];

            Iterator<String> it = resourceList.iterator();

            for (int i = 0; i < resourceList.size(); i++)
            {
                String res = (String)it.next();
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
            throw new RuntimeException(e);
        }
        catch (SAXException e)
        {
            throw new RuntimeException(e);
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
                        LOGGER.warn(
                            "Excepting attempting to close inputStream: "
                            + e.getMessage(), e);
                    }
                }
            }
        }
    }

    /**
     * Get an InputStream for the resource named.
     *
     * @param   res
     *
     * @return
     */
    private static InputStream toInputStream(String res)
    {
        ClassLoader classLoader = Thread.currentThread()
            .getContextClassLoader();

        if (LOGGER.isInfoEnabled())
        {
            URL resurl = classLoader.getResource(res);

            if (resurl == null)
            {
                LOGGER.error("Missing Resource: " + res);
            }
            else
            {
                LOGGER.info("Resource: " + res + " found at: "
                    + resurl.getPath());
            }
        }

        // URL resurl = classLoader.getResource(res);
        // System.out.println("Resource: " + res + " found at: "
        // + resurl.getPath());

        return classLoader.getResourceAsStream(res);
    }

    /**
     * Performs the validation against the specified validation form.
     *
     * @param   bean
     * @param   form
     *
     * @return  An array of messages or null if the validation gets passed
     */
    private Collection<ValidationError> performAndProcessResults(Object bean,
        String form)
    {
        Validator validator = new Validator(resources, form);
        AbstractValidator.setValidatorTool(validator, this);
        validator.setParameter(Validator.BEAN_PARAM, bean);
        validator.setOnlyReturnErrors(true);

        Map<Object, ValidatorResults> nestedValidatorResults =
            new HashMap<Object, ValidatorResults>();
        setNestedValidatorResults(validator, nestedValidatorResults);

        ValidatorResults results = null;
        ValidatorException validatorErr = null;

        if (!validationRulesExist(form))
        {
            throw new RuntimeException("Missing validation instructions for: "
                + form);
        }

        try
        {
            results = validator.validate();
        }
        catch (ValidatorException e)
        {
            validatorErr = e;
            LOGGER.error("ValidtionException occured:" + e.getMessage(), e);
        }

        Collection<ValidationError> nestedErrors = processResults(results, form,
                validatorErr);

        Map<Object, ValidatorResults> param = getNestedValidatorResults(
                validator);

        if (param != null)
        {
            for (Entry<Object, ValidatorResults> entry : param.entrySet())
            {
                String formName = AbstractValidatorTool
                    .getFormNameForObjectClass(entry.getKey());
                nestedErrors.addAll(processResults(entry.getValue(), formName,
                        null));
            }
        }

        return nestedErrors;
    }

    /**
     * Calculates the messages to be returned.
     *
     * @param   results
     * @param   formName
     * @param   nestedErrors
     *
     * @return
     */
    private Collection<ValidationError> processResults(ValidatorResults results,
        String formName, ValidatorException validatorExc)
    {
        Set<ValidationError> errors = new HashSet<ValidationError>();

        if ((results != null) && (results.getPropertyNames() != null)
            && !results.getPropertyNames().isEmpty())
        {
            Form form = resources.getForm(Locale.getDefault(), formName);
            Iterator<?> propertyNames = results.getPropertyNames().iterator();

            while (propertyNames.hasNext())
            {
                String propertyName = (String)propertyNames.next();
                Field field = form.getField(propertyName);
                ValidatorResult result = results.getValidatorResult(
                        propertyName);

                Iterator<?> actionKeys = result.getActions();

                while (actionKeys.hasNext())
                {
                    String actName = (String)actionKeys.next();
                    ValidatorAction action = resources.getValidatorAction(
                            actName);

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
                            msgKey = MSG_PREFIX + form.getName() + "."
                                + propertyName + "." + actName;
                        }

                        String message = getMessage(msgKey,
                                field.getArgs(actName));

                        ValidationError validationError = new ValidationError();
                        validationError.setMessage(message);

                        validationError.setErrorCode(
                            WebServiceValidationException.VALIDATION_ERROR);

                        // Add the message to the list
                        errors.add(validationError);
                    }
                }
            }
        }

        if (validatorExc != null)
        {
            ValidationError valErr = new ValidationError();
            valErr.setMessage(validatorExc.getMessage());
            valErr.setErrorCode(WebServiceValidationException.VALIDATION_ERROR);
            errors.add(valErr);
        }

        return errors;
    }
}
