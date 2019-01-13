package com.viasat.wildblue.common.client;

import org.junit.After;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import java.math.BigDecimal;

import java.net.URL;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jws.WebService;

import javax.xml.bind.annotation.XmlType;
import javax.xml.ws.Service;


/**
 */
public abstract class AbstractWebServiceTestClient
{
    private Object endpoint;
    private Set<Object> failedMethods = new HashSet<Object>();
    private Set<Object> invokedMethods = new HashSet<Object>();
    private Method[] serviceMethods;
    private long startTime;

    /**
     * Iterate through the methods declared in the service interface.
     */
    @Test public final void invokeAllMethods()
    {
        try
        {
            for (Method serviceMethod : serviceMethods)
            {
                invokeMethod(serviceMethod);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            fail(e.getClass().toString() + ":" + e.getMessage());
        }
    }

    /**
     * Out put some basic statistics about what has been tested.
     */
    @After public final void report() throws Exception
    {
        System.out.println("------------------------------");
        System.out.println("End: " + getServiceName() + "|"
            + getEndpointLocation());

        if (!failedMethods.isEmpty())
        {
            System.out.println("Failed Methods:");

            for (Object failed : failedMethods)
            {
                System.out.println(">" + failed);
            }
        }

        System.out.println("Invoked: " + invokedMethods.size() + " of "
            + serviceMethods.length + " methods invoked. ");
        System.out.println("Failed: " + failedMethods.size() + " of "
            + invokedMethods.size() + " invoked methods failed. ");
        System.out.println("Total runtime: "
            + (System.currentTimeMillis() - startTime) + " millis.");

        assertTrue("Some API Methods Failed.", failedMethods.isEmpty());

        assertTrue("Some API Methods were not executed for unknown reason?!?",
            invokedMethods.size() == serviceMethods.length);
    }

    /**
     * Establish instance of the Port/Endpoint which will be tested.
     */
    @Before public final void setUp() throws Exception
    {
        try
        {
            startTime = System.currentTimeMillis();
            System.out.println("Start: " + getServiceName() + "|"
                + getEndpointLocation());
            System.out.println("------------------------------");

            String wsdlLocation = getEndpointLocation();
            Class<?> serviceInterface = getServiceInterface();
            Class<?> serviceClient = getServiceClient();
            URL wsdlURL = new URL(wsdlLocation);
            Constructor<?> clientConstructor = serviceClient.getConstructor(
                    URL.class);
            Service service = (Service)clientConstructor.newInstance(wsdlURL);
            endpoint = service.getPort(serviceInterface);

            serviceMethods = getWebServiceMethodsFromEndpoint(endpoint);
            assertNotNull("No WebService methods were found.", serviceMethods);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Where is the WSDL published? This should return the address of the WSDL,
     * for example, the same URL which would be used in SOAP-UI.
     */
    protected abstract String getEndpointLocation();

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
     * @param  objectType
     * @param  objectInstance
     * @param  f
     * @param  substitutionParams
     */
    private void assignFieldValueOnObject(Class<?> objectType,
        Object objectInstance, Field f)
    {
        Method setterMethod = getSetterMethod(objectType, f);

        // No point in proceeding if we don't have a setter method.
        if (setterMethod != null)
        {
            String fieldName = f.getName();
            Class<?> fieldType = f.getType();
            Object fieldValue = null;

            // Otherwise, create a new object to assign to the field.
            if (fieldValue == null)
            {
                fieldValue = createObjectInstance(f.getType(), fieldName);
            }

            if (fieldValue != null)
            {
                invokeSetterMethod(objectInstance, setterMethod, fieldValue);

                if (isXmlType(fieldType))
                {
                    generateFieldValues(fieldType, fieldValue);
                }
            }
        }
    }

    /**
     * @param  objectType
     * @param  objectInstance
     * @param  f
     * @param  substitutionParams
     */
    private void assignListItemOnObject(Class<?> objectType,
        Object objectInstance, Field f)
    {
        try
        {
            // Assumes these are all XML generated types, where there is no
            // setter for the List type fields, rather call the getter method
            // and add the item to the list.
            Method listAccessorMethod = getGetterMethod(objectType, f);

            List<?> unTypedlist = (List<?>)listAccessorMethod.invoke(
                    objectInstance);

            if ((listAccessorMethod != null) && (unTypedlist != null))
            {
                // Ok... looks goofy,... but gets compiler warning to go away
                // about generic declaration for the lise (<?> vs <Object>).
                List<Object> list = new ArrayList<Object>(unTypedlist.size());
                list.addAll(unTypedlist);

                populateListItem(listAccessorMethod, list);
            }
        }
        catch (Exception e)
        {
            // This is simulator code, the exception handling
            // does not need to be overly sophisticated.
            if (e instanceof RuntimeException)
            {
                throw (RuntimeException)e;
            }
            else
            {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * @param   objectType  the object type
     * @param   fieldName   the field name
     *
     * @return  the object
     */
    private Object createKnownType(Class<?> objectType, String fieldName)
    {
        Object newInstance = null;

        if (objectType.equals(String.class))
        {
            if (fieldName.startsWith("get"))
            {
                fieldName = fieldName.substring(3);
            }

            newInstance = new String(fieldName);
        }
        else if (objectType.equals(BigDecimal.class))
        {
            newInstance = new BigDecimal("100.00");
        }
        else if (objectType.equals(Integer.class)
            || objectType.equals(int.class))
        {
            newInstance = new Integer("99");
        }
        else if (objectType.equals(Double.class)
            || objectType.equals(double.class))
        {
            newInstance = new Double("5.5");
        }
        else if (objectType.equals(Boolean.class)
            || objectType.equals(boolean.class))
        {
            newInstance = new Boolean("true");
        }

        return newInstance;
    }

    /**
     * Creates the list response object.
     *
     * @param   serviceMethod       the service method
     * @param   substitutionParams  the substitution params
     *
     * @return  the object
     */
    private Object createListResponseObject(Method serviceMethod)
    {
        List<Object> responseList = new ArrayList<Object>();

        if (serviceMethod != null)
        {
            populateListItem(serviceMethod, responseList);
        }

        return responseList;
    }

    /**
     * Creates the object instance.
     *
     * @param   objectType  the object type
     * @param   fieldName   the field name
     *
     * @return  the object
     */
    private Object createObjectInstance(Class<?> objectType, String fieldName)
    {
        try
        {
            Object newInstance = createKnownType(objectType, fieldName);

            if (newInstance == null)
            {
                Constructor<?> defaultConstructor = getDefaultConstructor(
                        objectType);

                if (defaultConstructor != null)
                {
                    newInstance = objectType.newInstance();
                }
            }

            return newInstance;
        }
        catch (Exception e)
        {
            // This is simulator code, the exception handling
            // does not need to be overly sophisticated.
            if (e instanceof RuntimeException)
            {
                throw (RuntimeException)e;
            }
            else
            {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Yuck... isn't there an easier way to do this these days?
     */
    private Object generateArgValue(Class<?> argType)
    {
        //        // This all seems archaic and old fashioned... oh well..
        //        Object argValue = null;
        //
        //        if (String.class == argType)
        //        {
        //            argValue = "abc";
        //        }
        //        else if (Date.class == argType)
        //        {
        //            argValue = new Date();
        //        }
        //        else if (int.class == argType)
        //        {
        //            argValue = 1;
        //        }
        //        else if (long.class == argType)
        //        {
        //            argValue = 1L;
        //        }
        //        else if (boolean.class == argType)
        //        {
        //            argValue = false;
        //        }
        //        else if (boolean.class == argType)
        //        {
        //            argValue = false;
        //        }
        //        else if (BigDecimal.class == argType)
        //        {
        //            argValue = new BigDecimal("0");
        //        }
        //        else
        //        {
        //            try
        //            {
        //                argValue = argType.newInstance();
        //            }
        //            catch (Exception e)
        //            {
        //                throw new RuntimeException(e);
        //                // do nothing... oh well, maybe it's optional anyhow?
        //            }
        //        }

        Object argValue = null;

        if (argType.isAssignableFrom(List.class))
        {
            argValue = createListResponseObject(null);
        }
        else
        {
            argValue = createObjectInstance(argType,
                    argType.getName().substring(
                        argType.getName().lastIndexOf(".")));

            if (argValue != null)
            {
                generateFieldValues(argType, argValue);
            }
        }

        return argValue;
    }

    /**
     * Generate field values.
     *
     * @param  objectType          the object type
     * @param  objectInstance      the object instance
     * @param  substitutionParams  the substitution params
     */
    private void generateFieldValues(Class<?> objectType, Object objectInstance)
    {
        // Handle inheritance/sub-classing.
        if (isXmlType(objectType.getSuperclass()))
        {
            generateFieldValues(objectType.getSuperclass(), objectInstance);
        }

        Field[] fields = objectType.getDeclaredFields();

        for (Field f : fields)
        {
            if (f.getType().isAssignableFrom(List.class))
            {
                assignListItemOnObject(objectType, objectInstance, f);
            }
            else
            {
                assignFieldValueOnObject(objectType, objectInstance, f);
            }
        }
    }

    /**
     * Gets the default constructor.
     *
     * @param   returnType  the return type
     *
     * @return  the default constructor
     */
    private Constructor<?> getDefaultConstructor(Class<?> returnType)
    {
        Constructor<?> defaultConstructor = null;
        Constructor<?>[] constructors = returnType.getConstructors();

        for (Constructor<?> c : constructors)
        {
            if ((c.getParameterTypes() == null)
                || (c.getParameterTypes().length == 0))
            {
                defaultConstructor = c;
                break;
            }
        }

        return defaultConstructor;
    }

    /**
     * Gets the getter method.
     *
     * @param   objectType  the object type
     * @param   f           the f
     *
     * @return  the getter method
     */
    private Method getGetterMethod(Class<?> objectType, Field f)
    {
        Method getter = null;

        try
        {
            String fieldName = f.getName();
            String setterMethodName = "get"
                + fieldName.substring(0, 1).toUpperCase()
                + fieldName.substring(1);
            getter = objectType.getMethod(setterMethodName);

            if (!getter.getReturnType().equals(f.getType()))
            {
                getter = null;
            }
        }
        catch (SecurityException e)
        {
            throw new RuntimeException(e);
        }
        catch (NoSuchMethodException e)
        {
            throw new RuntimeException(e);
        }

        return getter;
    }

    /**
     * Gets the setter method.
     *
     * @param   objectType  the object type
     * @param   f           the f
     *
     * @return  the setter method
     */
    private Method getSetterMethod(Class<?> objectType, Field f)
    {
        Method setterMethod = null;

        try
        {
            String fieldName = f.getName();
            String setterMethodName = "set"
                + fieldName.substring(0, 1).toUpperCase()
                + fieldName.substring(1);
            setterMethod = objectType.getMethod(setterMethodName, f.getType());
        }
        catch (Exception e)
        {
            // This is simulator code, the exception handling
            // does not need to be overly sophisticated.
            if (e instanceof RuntimeException)
            {
                throw (RuntimeException)e;
            }
            else
            {
                throw new RuntimeException(e);
            }
        }

        return setterMethod;
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
    private Object invokeMethod(Method serviceMethod)
        throws IllegalAccessException, InvocationTargetException
    {
        Object result = null;

        Class<?>[] args = serviceMethod.getParameterTypes();
        Object[] argValues = new Object[args.length];
        String methodName = serviceMethod.getName();

        // look for any args of type String
        for (int i = 0; i < args.length; i++)
        {
            // Do this in case there are required fields we need values in
            argValues[i] = generateArgValue(args[i]);
        }

        long startTimeMillis = System.currentTimeMillis();

        try
        {
            invokedMethods.add(serviceMethod);

            System.out.print("[" + invokedMethods.size() + "]Invoking "
                + getServiceName() + ":" + methodName + "()");

            result = serviceMethod.invoke(endpoint, argValues);
        }
        catch (Exception e)
        {
            failedMethods.add(serviceMethod.getName() + ":" + e.getCause());
            System.out.print("-FAIL-");
        }

        System.out.println(" runtimeMillis:"
            + (System.currentTimeMillis() - startTimeMillis));

        return result;
    }

    /**
     * Invoke setter method.
     *
     * @param  objectInstance  the object instance
     * @param  setterMethod    the setter method
     * @param  fieldValue      the field value
     */
    private void invokeSetterMethod(Object objectInstance, Method setterMethod,
        Object fieldValue)
    {
        try
        {
            setterMethod.invoke(objectInstance, fieldValue);
        }
        catch (Exception e)
        {
            // This is simulator code, the exception handling
            // does not need to be overly sophisticated.
            if (e instanceof RuntimeException)
            {
                throw (RuntimeException)e;
            }
            else
            {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Checks if is xml type.
     *
     * @param   objectType  the object type
     *
     * @return  true, if is xml type
     */
    private boolean isXmlType(Class<?> objectType)
    {
        boolean isXmlType = objectType.getAnnotation(XmlType.class) != null;
        return isXmlType;
    }

    /**
     * Populate list item.
     *
     * @param  method              the method
     * @param  returnList          the return list
     * @param  substitutionParams  the substitution params
     */
    private void populateListItem(Method method, List<Object> returnList)
    {
        // Some voodoo to figure out the Generic type...
        Type returnTypeG = method.getGenericReturnType();

        if (returnTypeG instanceof ParameterizedType)
        {
            ParameterizedType type = (ParameterizedType)returnTypeG;
            Type[] typeArguments = type.getActualTypeArguments();

            for (Type typeArgument : typeArguments)
            {
                Class<?> genericTypeClass = (Class<?>)typeArgument;
                Object genericTypeInstance = createObjectInstance(
                        genericTypeClass, method.getName());
                returnList.add(genericTypeInstance);

                if (isXmlType(genericTypeClass))
                {
                    generateFieldValues(genericTypeClass, genericTypeInstance);
                }
            }
        }
    }
}
