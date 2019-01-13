package com.viasat.wildblue.common.simulator;

import com.viasat.wildblue.common.commondata.ValidationResult;

import org.junit.After;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.math.BigDecimal;

import java.net.URL;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.jws.WebService;

import javax.xml.ws.Service;


/**
 * Abstract base class to support simple testing of a deployed Web Service. This
 * class will verify that Simulator implementations exhibit the standard
 * fault/exception/validationError behavior. This class iterates over all
 * declared methods of a Web Service interface and executes a call and asserts
 * that the resulting Exception, or ValidationResult objects match what is
 * expected. Implementors of this class need only provide a service client
 * implementation class, service interface class, service name, and
 * WebServiceException class. All of these are generated with the typical CXF
 * Web Service classes generated using the wsdl2java utility (generating with
 * the '-client' flag is not necessary).
 */
public abstract class SimulatedFaultTestClient
{
    private Object endpoint;
    private int methodProcessedCount = 0;
    private int methodsInServiceApiCount = -1;
    private long startTime;
    private Set<Object> testedMethods = new HashSet<Object>();
    private Set<Object> testedValidationMethods = new HashSet<Object>();
    private Set<Object> untestedNoSuitableStringArg = new HashSet<Object>();
    private int validationMethodCount = 0;

    /**
     * Establish instance of the Port/Endpoint which will be tested.
     */
    @Before public final void setUp() throws Exception
    {
        try
        {
            startTime = System.currentTimeMillis();

            String wsdlLocation = getEndpointLocation();
            Class<?> serviceInterface = getServiceInterface();
            Class<?> serviceClient = getServiceClient();
            URL wsdlURL = new URL(wsdlLocation);
            Constructor<?> clientConstructor = serviceClient.getConstructor(
                    URL.class);
            Service service = (Service)clientConstructor.newInstance(wsdlURL);
            endpoint = service.getPort(serviceInterface);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Out put some basic statistics about what has been tested.
     */
    @After public final void tearDown() throws Exception
    {
        System.out.println("End: " + getServiceName() + "|"
            + getEndpointLocation());
        System.out.println("End: " + testedMethods.size() + " of "
            + methodProcessedCount + " methods ("
            + testedValidationMethods.size() + " of " + validationMethodCount
            + " validation methods) tested. ");
        System.out.println("End: " + untestedNoSuitableStringArg.size()
            + " methods not tested because no suitable String arg was located to hold Fault Command.");
        System.out.println("Runtime: "
            + (System.currentTimeMillis() - startTime) + " millis.");

        assertTrue("Some API Methods were not executed for unknown reason?!?",
            (methodProcessedCount
                == (untestedNoSuitableStringArg.size()
                    + testedMethods.size()))
            && (methodProcessedCount == methodsInServiceApiCount));
    }

    /**
     * Iterate through the methods declared in the service interface.
     */
    @Test public final void testAllMethodsForSimulatedFaults()
    {
        try
        {
            String[] faultCommandPrefixes = getFaultCommandPrefixes();
            assertNotNull("No service prefixes given.", faultCommandPrefixes);

            Method[] serviceMethods = getWebServiceMethodsFromEndpoint(
                    endpoint);
            assertNotNull("No WebService methods were found.", serviceMethods);
            methodsInServiceApiCount = serviceMethods.length;

            for (Method serviceMethod : serviceMethods)
            {
                invokeMethodTests(faultCommandPrefixes, serviceMethod);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            fail(e.getClass().toString() + ":" + e.getMessage());
        }
    }

    /**
     * Where is the WSDL published? This should return the address of the WSDL,
     * for example, the same URL which would be used in SOAP-UI.
     */
    protected abstract String getEndpointLocation();

    /**
     * What prefixes should the service pay attention too when generating the
     * simulated Faults and ValidationErrors?
     */
    protected abstract String[] getFaultCommandPrefixes();

    /**
     * The generated service client class (the class with the 'WebServiceClient'
     * annotation, typically with the same name as the service, for example:
     * BillingService.class, FulfillmentService.class).
     */
    protected abstract Class<?> getServiceClient();

    /**
     * The generated Interface which declares the service methods, this
     * interface will have the 'WebService' annotation.
     */
    protected abstract Class<?> getServiceInterface();

    /**
     * Return a simple String name for the Service which will be used in the
     * test output.
     */
    protected abstract String getServiceName();

    /**
     * The WebServiceException class specific to the WebService API which is
     * being tested (for example:
     * com.viasat.wildblue.facade.billing.WebServiceException.class).
     */
    protected abstract Class<?> getWebServiceExceptionClass();

    /**
     * Yuck... isn't there an easier way to do this these days?
     */
    private Object generateArgValue(Class<?> argType)
    {
        // This all seems archaic and old fashioned... oh well..
        Object argValue = null;

        if (String.class == argType)
        {
            argValue = "abc";
        }
        else if (Date.class == argType)
        {
            argValue = new Date();
        }
        else if (int.class == argType)
        {
            argValue = 1;
        }
        else if (long.class == argType)
        {
            argValue = 1L;
        }
        else if (boolean.class == argType)
        {
            argValue = false;
        }
        else if (boolean.class == argType)
        {
            argValue = false;
        }
        else if (BigDecimal.class == argType)
        {
            argValue = new BigDecimal("0");
        }
        else
        {
            try
            {
                argValue = argType.newInstance();
            }
            catch (Exception e)
            {
                // do nothing... oh well, maybe it's optional anyhow?
            }
        }

        return argValue;
    }

    /**
     * Look at the super-interfaces and find the one which defines the
     * WebService API.
     */
    private Class<?> getWebServiceClassFromEndpoint(Object port)
    {
        Class<?> wsClass = null;

        // Look for the superInterface with the WebService annotation.
        for (Class<?> clz : port.getClass().getInterfaces())
        {
            if (clz.getAnnotation(WebService.class) != null)
            {
                // This is the Web Service definition Interface.
                wsClass = clz;
            }
        }

        return wsClass;
    }

    /**
     * Get the declared Methods for the WebService.
     */
    private Method[] getWebServiceMethodsFromEndpoint(Object port)
    {
        Method[] methods = null;

        Class<?> wsClass = getWebServiceClassFromEndpoint(port);

        if (wsClass != null)
        {
            methods = wsClass.getDeclaredMethods();
        }

        return methods;
    }

    /**
     * Invokes the method, and asserts that any resulting Exception matches the
     * given expectedException.
     */
    private Object invokeMethod(String prefix, String faultCommand,
        Method serviceMethod, Class<?> expectedException)
        throws IllegalAccessException, InvocationTargetException
    {
        Object result = null;

        Class<?>[] args = serviceMethod.getParameterTypes();
        Object[] argValues = new Object[args.length];
        String methodName = serviceMethod.getName();
        boolean faultCommandSet = false;

        // look for any args of type String
        for (int i = 0; i < args.length; i++)
        {
            if (!faultCommandSet && (String.class == args[i]))
            {
                // Found one!
                argValues[i] = prefix + faultCommand;
                faultCommandSet = true;
            }
            else
            {
                // Do this in case there are required fields we need values in
                argValues[i] = generateArgValue(args[i]);
            }
        }

        if (faultCommandSet)
        {
            Throwable caughtException = null;

            try
            {
                System.out.println("[" + methodProcessedCount + "]Invoking "
                    + getServiceName() + ":" + methodName + "() with: " + prefix
                    + "/" + faultCommand);

                testedMethods.add(serviceMethod);

                if (ValidationResult.class.isAssignableFrom(
                        serviceMethod.getReturnType()))
                {
                    testedValidationMethods.add(serviceMethod);
                }

                result = serviceMethod.invoke(endpoint, argValues);
            }
            catch (InvocationTargetException e)
            {
                caughtException = e.getCause();
                result = caughtException;
            }

            if (expectedException != null)
            {
                assertNotNull(
                    "Expected Exception was not not received (nothing thrown)",
                    caughtException);

                assertTrue("Exception occured, but was not " + expectedException
                    + " as expected. (" + caughtException + ")",
                    expectedException.isAssignableFrom(
                        caughtException.getClass()));
            }
            else
            {
                assertNull("Exception was thrown, expected successful return.",
                    caughtException);
            }
        }
        else
        {
            System.out.println("FAULT NOT TESTED (no suitable args) ["
                + methodProcessedCount + "] " + getServiceName() + ":"
                + methodName + "() with: " + prefix + "/" + faultCommand);

            result = serviceMethod.invoke(endpoint, argValues);
            untestedNoSuitableStringArg.add(serviceMethod);
        }

        return result;
    }

    /**
     * Run the battery of tests on the given method. Included testing for
     * commands: Fault, ValidationError, ValidationWarning.
     */
    private void invokeMethodTests(String[] prefixes, Method serviceMethod)
        throws IllegalAccessException, InvocationTargetException
    {
        methodProcessedCount++;

        boolean isValidationMethod = false;

        if (ValidationResult.class.isAssignableFrom(
                serviceMethod.getReturnType()))
        {
            isValidationMethod = true;
            validationMethodCount++;
        }

        for (String prefix : prefixes)
        {
            simulatedFaultTest(endpoint, methodProcessedCount, serviceMethod,
                prefix);

            if (!untestedNoSuitableStringArg.contains(serviceMethod))
            {
                simulatedValidationErrorTest(endpoint, methodProcessedCount,
                    serviceMethod, isValidationMethod, prefix);

                simulatedValidationWarningTest(endpoint, methodProcessedCount,
                    serviceMethod, isValidationMethod, prefix);
            }
        }
    }

    /**
     * Test that the Fault command is followed correctly.
     */
    private void simulatedFaultTest(Object endpoint, int methodCount,
        Method serviceMethod, String prefix) throws IllegalAccessException,
        InvocationTargetException
    {
        invokeMethod(prefix, "Fault", serviceMethod,
            getWebServiceExceptionClass());
    }

    /**
     * Test that the ValidationError command is followed correctly.
     */
    private void simulatedValidationErrorTest(Object endpoint, int methodCount,
        Method serviceMethod, boolean isValidationMethod, String prefix)
        throws IllegalAccessException, InvocationTargetException
    {
        Class<?> expectedException = getWebServiceExceptionClass();

        if (isValidationMethod)
        {
            expectedException = null;
        }

        Object result = invokeMethod(prefix, "ValidationError", serviceMethod,
                expectedException);

        if (isValidationMethod)
        {
            assertNotNull(
                "Expected ValidationResult to be returned, instead received: null",
                result);
            assertTrue(
                "Expected ValidationResult to be returned, instead received: "
                + result.getClass().getName(),
                result instanceof ValidationResult);

            ValidationResult ve = (ValidationResult)result;
            assertNotNull(
                "Expected ValidationResult to contain Validation Errors, error list was null.",
                ve.getValidationError());
            assertFalse(
                "Expected ValidationResult to contain Validation Errors, error list is empty.",
                ve.getValidationError().isEmpty());
        }
    }

    /**
     * Test that the ValidationWarning command is followed correctly.
     */
    private void simulatedValidationWarningTest(Object endpoint,
        int methodCount, Method serviceMethod, boolean isValidationMethod,
        String prefix) throws IllegalAccessException, InvocationTargetException
    {
        Object result = invokeMethod(prefix, "ValidationWarning", serviceMethod,
                null);

        if (isValidationMethod)
        {
            assertNotNull(
                "Expected ValidationResult to be returned, instead received: null",
                result);
            assertTrue(
                "Expected ValidationResult to be returned, instead received: "
                + result.getClass().getName(),
                result instanceof ValidationResult);

            ValidationResult ve = (ValidationResult)result;
            assertNotNull(
                "Expected ValidationResult to contain Validation Warnings, warning list was null.",
                ve.getValidationWarning());
            assertFalse(
                "Expected ValidationResult to contain Validation Warnings, warning list is empty.",
                ve.getValidationWarning().isEmpty());
        }
    }
}
