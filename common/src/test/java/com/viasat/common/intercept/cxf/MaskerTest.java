package com.viasat.common.intercept.cxf;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

public class MaskerTest
{

	@Test
	public void testJsonMasker() throws Exception
	{
		String jsonString = "{\"userName\": \"wball\",\"password\":\"Wball01\",\"includeMembership\":\"true\"}";
		String[] elementNamesToMask =
		{ "password" };

		String response = Masker.maskJson(jsonString, elementNamesToMask);

		if (response.contains("Wball01"))
		{
			Assert.fail("Masked element found in payload.");
		}
	}

	@Test
	public void testFormMasker()
	{
		String formString = "userName=wball&password=Wball01&includeMembership=true";

		String[] elementNamesToMask =
		{ "password" };

		String response = Masker.maskFormElements(formString, elementNamesToMask);

		if (response.contains("Wball01"))
		{
			Assert.fail("Masked element found in payload.");
		}
	}

	@Test
	public void testMaskHeaderElements()
	{
		String[] elementNames = new String[]
		{ "Content-Type", "cache-control", "Accept" };
		String payload = "{Accept=[text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2], cache-control=[no-cache], connection=[keep-alive], Content-Type=[null], host=[fcd-catalog.sandbox.dev.wdc1.wildblue.net], ossxff=[10.67.162.171], pragma=[no-cache], user-agent=[Java/1.8.0_31], wl-proxy-client-ip=[10.67.162.171], xff=[10.67.162.171], xffp=[49012]}";
		assertEquals(
				"{Accept=[******], cache-control=[******], connection=[keep-alive], Content-Type=[******], host=[fcd-catalog.sandbox.dev.wdc1.wildblue.net], ossxff=[10.67.162.171], pragma=[no-cache], user-agent=[Java/1.8.0_31], wl-proxy-client-ip=[10.67.162.171], xff=[10.67.162.171], xffp=[49012]}",
				Masker.maskHeaderElements(payload, elementNames));
	}
}
