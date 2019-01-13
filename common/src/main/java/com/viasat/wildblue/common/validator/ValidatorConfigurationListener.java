package com.viasat.wildblue.common.validator;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


/**
 * This class is a ServletContextListener implementation to provide
 * ValidatorTool configuration associated to the start (context loading event)
 * of a Web Application. It provides the ability to specify the resource bundle
 * name(s) and xml configurations file(s) with the ValidatorTool will use.
 */
public class ValidatorConfigurationListener implements ServletContextListener
{
    public static final String VALIDATOR_CONFIGURATION_PARAM =
        "validatorConfiguration";
    public static final String VALIDATOR_MESSAGE_BUNDLE_PARAM =
        "validatorMessageBundle";

    /**
     * This class provides no functionality in this method. It is here because
     * the ServletConextListemer interface requires it.
     */
    @Override public void contextDestroyed(ServletContextEvent sce)
    {
        // nothing to do here.
    }

    @Override public void contextInitialized(ServletContextEvent sce)
    {
        ServletContext ctx = sce.getServletContext();
        getMessageBundleParameter(ctx);

        String messageBundleParam = getMessageBundleParameter(ctx);

        if ((messageBundleParam != null)
            && !messageBundleParam.trim().isEmpty())
        {
            ValidatorTool.setMessageBundleName(messageBundleParam);
        }

        String[] configFiles = getConfigurationParameter(ctx);

        if ((configFiles != null) && (configFiles.length > 0))
        {
            ValidatorTool.setConfigurationResources(configFiles);
        }
    }

    /**
     * Parse the array of configuration file names from a whitespace and/or
     * comma delimited String.
     *
     * @param   validationConfigsRaw  The raw unparsed String which needs to be
     *                                split into the array of file names.
     *
     * @return  String array of file names.
     */
    static String[] parseConfigurationParameter(String validationConfigsRaw)
    {
        String[] configFiles = null;
        String[] rawConfigFiles = null;

        if ((validationConfigsRaw != null)
            && !validationConfigsRaw.trim().isEmpty())
        {
            rawConfigFiles = validationConfigsRaw.split(",|\\s");
        }

        if (rawConfigFiles != null)
        {
            List<String> configFilesList = new ArrayList<String>();

            for (String maybeConfigFile : rawConfigFiles)
            {
                if ((maybeConfigFile != null)
                    && !maybeConfigFile.trim().isEmpty())
                {
                    configFilesList.add(maybeConfigFile);
                }
            }

            if (!configFilesList.isEmpty())
            {
                configFiles = configFilesList.toArray(
                        new String[configFilesList.size()]);
            }
        }

        return configFiles;
    }

    /**
     * Removes the ".properties" extension from the resource bundle name
     * (ResourceBundle takes an unsuffixed file name).
     *
     * @param   validationMessageBundleRaw
     *
     * @return  valid resource name to use when creating a ResourceBundle
     *          instance.
     */
    static String parseMessageBundleName(String validationMessageBundleRaw)
    {
        String validationMessageBundleName = validationMessageBundleRaw;

        if (validationMessageBundleName != null)
        {
            if (validationMessageBundleName.trim().endsWith(".properties"))
            {
                validationMessageBundleName =
                    validationMessageBundleName.substring(0,
                        validationMessageBundleName.indexOf(".properties"))
                    .trim();
            }
        }

        return validationMessageBundleName;
    }

    /**
     * Reads a parameter from the given Context.
     *
     * @param   ctx  The ServletContext
     *
     * @return  The processed parameter value, and array of file names.
     */
    private static String[] getConfigurationParameter(ServletContext ctx)
    {
        String validationConfigsRaw = ctx.getInitParameter(
                VALIDATOR_CONFIGURATION_PARAM);

        return parseConfigurationParameter(validationConfigsRaw);
    }

    /**
     * Reads a parameter from the given Context.
     *
     * @param   ctx  The ServletContext
     *
     * @return  The processed parameter value, a resource name with any
     *          ".properties" extension stripped.
     */
    private static String getMessageBundleParameter(ServletContext ctx)
    {
        String validationMessageBundleRaw = ctx.getInitParameter(
                VALIDATOR_MESSAGE_BUNDLE_PARAM);
        return parseMessageBundleName(validationMessageBundleRaw);
    }
}
