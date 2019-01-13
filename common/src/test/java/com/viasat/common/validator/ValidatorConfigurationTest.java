package com.viasat.common.validator;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.validator.ValidatorResources;
import org.junit.Test;

import com.viasat.common.fault.ValidatorTool;

public class ValidatorConfigurationTest extends BaseValidatorTest
{
	@Test
	public void testValidatorConfigurationClass() throws Exception
	{
		Throwable configurationException = null;
		ValidatorTool tool = null;
		ValidatorResources resources = null;

		try
		{
			List<String> configList = new ArrayList<String>();
			configList.add("validation-common.xml");
			ValidatorTool
					.setConfigurationResources(configList.toArray(new String[configList.size()]));

			tool = ValidatorTool.getInstance();
			resources = tool.getResources();
		}
		catch (Throwable e)
		{
			e.printStackTrace();
			configurationException = e;
		}

		assertNull(
				"Validation condfiguration is not consumable... Exc: "
						+ String.valueOf(configurationException), configurationException);

		assertNotNull("Validation configuration is not initialized somehow!", tool);

		assertNotNull("Validation condfiguration includes no resources.", resources);

		// Reset configuration... make sure previous stuff is gone.
		ValidatorTool.clearConfiguration();

	}

	@Test
	public void verifyConfigurationFiles() throws Exception
	{
		Throwable configurationException = null;
		ValidatorTool tool = null;
		ValidatorResources resources = null;

		try
		{
			ValidatorTool.setConfigurationResource("validation-common.xml");
			tool = ValidatorTool.getInstance();
			resources = tool.getResources();
		}
		catch (Throwable e)
		{
			e.printStackTrace();
			configurationException = e;
		}

		assertNull(
				"Validation condfiguration is not consumable... Exc: "
						+ String.valueOf(configurationException), configurationException);

		assertNotNull("Validation configuration is not initialized somehow!", tool);

		assertNotNull("Validation condfiguration includes no resources.", resources);
	}
}
