package com.viasat.wildblue.common.validator;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class ValidatorConfigurationListenerTest
{
    @Test public void testParseConfigFiles() throws Exception
    {
        String[] configFiles = new String[]
            {
                "conf1.xml", "conf2.xml", "conf3.xml"
            };

        performParseConfigTest(configFiles, "conf1.xml,conf2.xml,conf3.xml");
        performParseConfigTest(configFiles, "conf1.xml\nconf2.xml\nconf3.xml");
        performParseConfigTest(configFiles,
            "\nconf1.xml\nconf2.xml\nconf3.xml\n");
        performParseConfigTest(configFiles,
            "\nconf1.xml,\nconf2.xml,\nconf3.xml,\n");
        performParseConfigTest(configFiles,
            "\n\t conf1.xml, \n\t conf2.xml , \n  \tconf3.xml,\t\n");
        performParseConfigTest(configFiles,
            "conf1.xml,conf2.xml,   conf3.xml   ");
        performParseConfigTest(configFiles,
            "\nconf1.xml\n,\nconf2.xml   ,   conf3.xml  ,,   ");
        performParseConfigTest(configFiles,
            "conf1.xml, \nconf2.xml,\n   conf3.xml \n,\n,\n    \n");
    }

    @Test public void testParseMessageBundleName() throws Exception
    {
        String bundleName = "testBundle";

        performParseMessageBundleTest(bundleName, "testBundle");
        performParseMessageBundleTest(bundleName, "testBundle.properties");
    }

    private void performParseConfigTest(String[] expectedResults,
        String testString)
    {
        String[] parseResults = ValidatorConfigurationListener
            .parseConfigurationParameter(testString);

        assertNotNull("No results parsed.", parseResults);
        assertTrue("Parse results size is not as expected.",
            parseResults.length == expectedResults.length);

        for (int i = 0; i < expectedResults.length; i++)
        {
            assertTrue("Result at index " + i + " does not match expected: "
                + parseResults[i] + " != " + expectedResults[i],
                parseResults[i].equals(expectedResults[i]));
        }
    }

    private void performParseMessageBundleTest(String expectedResult,
        String testString)
    {
        String parseResult = ValidatorConfigurationListener
            .parseMessageBundleName(testString);

        assertNotNull("No results parsed.", parseResult);

        assertTrue("Result does not match expected: " + parseResult + " != "
            + expectedResult, parseResult.equals(expectedResult));
    }
}
