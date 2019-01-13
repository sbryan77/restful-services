package com.viasat.wildblue.common.validator;

import org.apache.commons.validator.ValidatorResources;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class ValidatorConfigurationTest  extends BaseValidatorTest
{
    @Test public void testValidatorConfigurationClass() throws Exception
    {
        Throwable configurationException = null;
        ValidatorTool tool = null;
        ValidatorResources resources = null;
        ResourceBundle bundle = null;
        String messageBundleName = null;

        try
        {
            ValidatorTool.setMessageBundleName(
                BaseValidatorTest.TEST_MESSAGES_PROPERTIES);

            List<String> configList = new ArrayList<String>();
            configList.add("validation-common.xml");
            ValidatorTool.setConfigurationResources(configList.toArray(
                    new String[configList.size()]));

            tool = ValidatorTool.getInstance();
            resources = tool.getResources();
            bundle = tool.getMessageBundle();
            messageBundleName = ValidatorTool.getMessageBundleName();
        }
        catch (Throwable e)
        {
            e.printStackTrace();
            configurationException = e;
        }

        assertNull("Validation condfiguration is not consumable... Exc: "
            + String.valueOf(configurationException), configurationException);

        assertNotNull("Validation configuration is not initialized somehow!",
            tool);

        assertNotNull("Validation condfiguration includes no resources.",
            resources);

        assertNotNull("Validation configuration includes no message bundle!",
            bundle);

        // Reset configuration... make sure previous stuff is gone.
        ValidatorTool.clearConfiguration();

        String messageBundleNameB = ValidatorTool.getMessageBundleName();
        assertTrue(
            "Configuration was cleared, should not match previous configuration",
            messageBundleName != messageBundleNameB);
    }

    @Test public void verifyConfigurationFiles() throws Exception
    {
        Throwable configurationException = null;
        ValidatorTool tool = null;
        ValidatorResources resources = null;
        ResourceBundle bundle = null;

        try
        {
            ValidatorTool.setMessageBundleName(
                BaseValidatorTest.TEST_MESSAGES_PROPERTIES);
            ValidatorTool.setConfigurationResource("validation-common.xml");
            tool = ValidatorTool.getInstance();
            resources = tool.getResources();
            bundle = tool.getMessageBundle();
        }
        catch (Throwable e)
        {
            e.printStackTrace();
            configurationException = e;
        }

        assertNull("Validation condfiguration is not consumable... Exc: "
            + String.valueOf(configurationException), configurationException);

        assertNotNull("Validation configuration is not initialized somehow!",
            tool);

        assertNotNull("Validation condfiguration includes no resources.",
            resources);

        assertNotNull("Validation configuration includes no message bundle!",
            bundle);
    }
}
