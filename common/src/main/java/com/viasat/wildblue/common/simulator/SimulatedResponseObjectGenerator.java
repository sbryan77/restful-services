package com.viasat.wildblue.common.simulator;

import com.viasat.wildblue.common.commondata.ValidationResult;

import org.apache.commons.lang.StringUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * This class will generate/construct return Objects for a given method. It uses
 * reflection (including annotations created when the objects are generated from
 * the WSDL/XSD) to build the return objects. Using the class can provide return
 * objects for an entire WS API, using a Spring AOP / Interceptor
 * implementation.
 */
public class SimulatedResponseObjectGenerator
{
    /** The dont know how to construct set. */
    private Set<String> dontKnowHowToConstructSet = new HashSet<String>();

    /** The generate required fields only. */
    private boolean generateRequiredFieldsOnly = true;

    /**
     * Default constructor.
     */
    public SimulatedResponseObjectGenerator()
    {
    }

    /**
     * Constructor with argument for the generateRequiredFieldsOnly property
     * (when true, only required fields, as specified in the XSD definition,
     * will be populated in the return object).
     *
     * @param  generateRequiredFieldsOnly
     */
    public SimulatedResponseObjectGenerator(boolean generateRequiredFieldsOnly)
    {
        this.generateRequiredFieldsOnly = generateRequiredFieldsOnly;
    }

    /**
     * This method generates the return object for the given Method.
     *
     * @param   method  The Method Object to generate a return Object for.
     *
     * @return  A populated object matching the return type for the method.
     */
    public Object generateReturnValueObject(Method method)
    {
        return generateReturnValueObject(method, null);
    }

    /**
     * This method generates the return object for the given Method, including a
     * Map of input parameters (expected to have been the inputs to the Web
     * Service call) which will be substituted into the return object where any
     * field names match a param (for example, where and inputParam exist for
     * "accountReference", the associated value will be used in the return
     * object if the return object includes a field named "accountReference".
     *
     * @param   method  The Method Object to generate a return Object for.
     *
     * @return  A populated object matching the return type for the method.
     */
    public Object generateReturnValueObject(Method method,
        Map<String, Object> inputParams)
    {
        Object returnValue = null;
        Class<?> returnType = method.getReturnType();

        // Skip void, nothing to generate for those.
        if ((returnType != null) && !(void.class == returnType))
        {
            if (returnType.isAssignableFrom(List.class))
            {
                returnValue = createListResponseObject(method, inputParams);
            }
            else if (String.class.equals(returnType))
            {
                String returnString = method.getName();

                if (returnString.startsWith("get")
                    && (returnString.length() > 3))
                {
                    returnString = returnString.substring(3);
                }

                returnValue = returnString;
            }
            else
            {
                returnValue = createObjectInstance(returnType,
                        method.getName());

                // If its a ValidationResult, stop here... we dont want to
                // generate errors/warnings.
                // When desired they are created by the FaultSimulator logic.
                if ((returnValue != null)
                    && !ValidationResult.class.equals(returnType))
                {
                    generateFieldValues(returnType, returnValue, inputParams);
                }
            }
        }

        return returnValue;
    }

    /**
     * Provided for testing and reporting... if all is well this should always
     * return an empty Set.
     *
     * @return  A Set containing all the classes which were not able to be
     *          generated.
     */
    public Set<String> getClassesNotConstructed()
    {
        return dontKnowHowToConstructSet;
    }

    /**
     * The generateRequiredFieldsOnly property: When true, only required fields,
     * as specified in the XSD definition, will be populated in the return
     * object.
     *
     * @return  Value true/false for the generateRequiredFieldsOnly property.
     */
    public boolean isGenerateRequiredFieldsOnly()
    {
        return generateRequiredFieldsOnly;
    }

    /**
     * The generateRequiredFieldsOnly property: When true, only required fields,
     * as specified in the XSD definition, will be populated in the return
     * object.
     *
     * @param  generateRequiredFieldsOnly  Value true/false for the
     *                                     generateRequiredFieldsOnly property.
     */
    public void setGenerateRequiredFieldsOnly(
        boolean generateRequiredFieldsOnly)
    {
        this.generateRequiredFieldsOnly = generateRequiredFieldsOnly;
    }

    /**
     * @param  objectType
     * @param  objectInstance
     * @param  f
     * @param  substitutionParams
     */
    private void assignFieldValueOnObject(Class<?> objectType,
        Object objectInstance, Field f, Map<String, Object> substitutionParams)
    {
        Method setterMethod = getSetterMethod(objectType, f);

        // No point in proceeding if we don't have a setter method.
        if (setterMethod != null)
        {
            String fieldName = f.getName();
            Class<?> fieldType = f.getType();
            Object fieldValue = null;

            // Any input parameter value to recycle?
            if (substitutionParams != null)
            {
                Object substitutionValue = substitutionParams.get(fieldName);

                if ((substitutionValue != null)
                    && fieldType.isAssignableFrom(substitutionValue.getClass()))
                {
                    fieldValue = substitutionValue;
                }
            }

            // Any existing value (for example, parent object was substituted
            // early, and
            // we don't want to blow away it's attributes).
            Object existingValue = getExistingFieldValue(objectType,
                    objectInstance, f);

            // Otherwise, create a new object to assign to the field.
            if ((fieldValue == null) && (existingValue == null))
            {
                fieldValue = createObjectInstance(f.getType(), fieldName);
            }

            if (fieldValue != null)
            {
                invokeSetterMethod(objectInstance, setterMethod, fieldValue);

                // Don't recurse and try to set fields on an Enum class!
                if (!fieldType.isEnum() && isXmlType(fieldType))
                {
                    // Recurse and set fields on nested element.
                    generateFieldValues(fieldType, fieldValue,
                        substitutionParams);
                }
            }
            else if (existingValue != null)
            {
                // Don't recurse and try to set fields on an Enum class!
                if (!fieldType.isEnum() && isXmlType(fieldType))
                {
                    // Recurse and set fields on nested element.
                    generateFieldValues(fieldType, existingValue,
                        substitutionParams);
                }
            }
            else
            {
                dontKnowHowToConstructSet.add(f.getType().getName());
            }
        }
        else
        {
            dontKnowHowToConstructSet.add(f.getType().getName());
        }
    }

    /**
     * @param  objectType
     * @param  objectInstance
     * @param  f
     * @param  substitutionParams
     */
    private void assignListItemOnObject(Class<?> objectType,
        Object objectInstance, Field f, Map<String, Object> substitutionParams)
    {
        try
        {
            // Assumes these are all XML generated types, where there is no
            // setter for the List type fields, rather call the getter method
            // and add the item to the list.
            Method listAccessorMethod = getGetterMethod(objectType, f);

            @SuppressWarnings("unchecked")
            List<Object> list = (List<Object>)listAccessorMethod.invoke(
                    objectInstance);

            if ((listAccessorMethod != null) && (list != null))
            {
                populateListItem(listAccessorMethod, list, substitutionParams);
            }
            else
            {
                dontKnowHowToConstructSet.add(f.getType().getName());
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
     * @param   f
     *
     * @return
     */
    private boolean checkRequired(Field f)
    {
        boolean isRequired = false;

        // We are generating a response for our Web Service, so all of these
        // types are contract-first defined, generated from XSD, therefore we
        // can use the generated Annotations to determine which are required.
        Annotation xmlAnn = f.getAnnotation(XmlElement.class);

        // No Annotation? not a required field,... skip it.
        if (xmlAnn != null)
        {
            Method[] xmlAnnMethods = xmlAnn.annotationType().getMethods();

            for (Method m : xmlAnnMethods)
            {
                String mName = m.getName();

                if (mName.equals("required"))
                {
                    try
                    {
                        Object required = m.invoke(xmlAnn, (Object[])null);

                        if ((required != null) && (required instanceof Boolean)
                            && ((Boolean)required).booleanValue())
                        {
                            // The field is required!
                            isRequired = true;
                        }

                        break;
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
            }
        }

        return isRequired;
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
        else if (objectType.equals(Long.class) || objectType.equals(long.class))
        {
            newInstance = new Long("999");
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
    private Object createListResponseObject(Method serviceMethod,
        Map<String, Object> substitutionParams)
    {
        List<Object> responseList = new ArrayList<Object>();

        populateListItem(serviceMethod, responseList, substitutionParams);

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

            if (newInstance == null)
            {
                dontKnowHowToConstructSet.add(objectType.getName());
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
     * Generate field values.
     *
     * @param  objectType          the object type
     * @param  objectInstance      the object instance
     * @param  substitutionParams  the substitution params
     */
    private void generateFieldValues(Class<?> objectType, Object objectInstance,
        Map<String, Object> substitutionParams)
    {
        // Handle inheritance/sub-classing.
        if (isXmlType(objectType.getSuperclass()))
        {
            generateFieldValues(objectType.getSuperclass(), objectInstance,
                substitutionParams);
        }

        Field[] fields = objectType.getDeclaredFields();

        for (Field f : fields)
        {
            if (!generateRequiredFieldsOnly || checkRequired(f))
            {
                if (f.getType().isAssignableFrom(List.class))
                {
                    assignListItemOnObject(objectType, objectInstance, f,
                        substitutionParams);
                }
                else
                {
                    assignFieldValueOnObject(objectType, objectInstance, f,
                        substitutionParams);
                }
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
     * @param   objectType
     * @param   objectInstance
     * @param   f
     *
     * @return
     */
    private Object getExistingFieldValue(Class<?> objectType,
        Object objectInstance, Field f)
    {
        Object existingValue = null;

        try
        {
            Method getterMethod = getGetterMethod(objectType, f);

            if (getterMethod != null)
            {
                existingValue = getterMethod.invoke(objectInstance);
            }
        }
        catch (Exception e)
        {
            // ignore,... we really don't care, just checking for an existing
            // value.
        }

        return existingValue;
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

            // Note: CXF will generate setters using XML Element name, which may have different
            // case than standards derived from field name. ie: Element name 'CVV' will
            // generate field name 'cvv' and setter/getter 'getCVV/setCVV' not 'setCvv/getCvv'.

            String xmlElementName = getXmlElementName(f);

            if (xmlElementName != null)
            {
                fieldName = xmlElementName;
            }

            // Note: JAXB bindings may be in place to change the name of an operation,
            // or field, especially lists that have a plural name in the java object
            // and a singular xmlElementName, so this must use introspection to
            // obtain a reference to the "getter" instead of manually constructing
            // the get method name.
            try
            {
                BeanInfo beanInfo = Introspector.getBeanInfo(objectType);
                PropertyDescriptor[] beanPropertyDescriptors =
                    beanInfo.getPropertyDescriptors();

                for (PropertyDescriptor beanPropertyDescriptor
                    : beanPropertyDescriptors)
                {
                    if (beanPropertyDescriptor.getName().equals(f.getName()))
                    {
                        getter = beanPropertyDescriptor.getReadMethod();
                        break;
                    }
                }
            }
            catch (IntrospectionException e)
            {
                throw new RuntimeException(e);
            }

            if (getter == null)
            {
                // Try it the old way
                String readMethodName = "get"
                    + fieldName.substring(0, 1).toUpperCase()
                    + fieldName.substring(1);
                getter = objectType.getMethod(readMethodName);
            }

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

            // CXF will generate setters using XML Element name, which may have different
            // case than standards derived from field name. ie: Element name 'CVV' will
            // generate field name 'cvv' and setter/getter 'getCVV/setCVV' not 'setCvv/getCvv'.
            String xmlElementName = getXmlElementName(f);

            if (xmlElementName != null)
            {
                fieldName = xmlElementName;
            }

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

    private String getXmlElementName(Field f)
    {
        String xmlName = null;

        // Is it an XML type at all?
        Annotation xmlAnn = f.getAnnotation(XmlElement.class);

        // No Annotation? not a required field,... skip it.
        if (xmlAnn != null)
        {
            Method[] xmlAnnMethods = xmlAnn.annotationType().getMethods();

            for (Method m : xmlAnnMethods)
            {
                String mName = m.getName();

                if (mName.equals("name"))
                {
                    try
                    {
                        Object name = m.invoke(xmlAnn, (Object[])null);

                        if ((name != null) && (name instanceof String)
                            && !StringUtils.isEmpty((String)name))
                        {
                            // OK... very weird default value here... ignore it anyhow.
                            if (!"##default".equals(name))
                            {
                                xmlName = (String)name;
                            }
                        }

                        break;
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
            }
        }

        return xmlName;
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
    private void populateListItem(Method method, List<Object> returnList,
        Map<String, Object> substitutionParams)
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
                    generateFieldValues(genericTypeClass, genericTypeInstance,
                        substitutionParams);
                    break;
                }
            }
        }
    }
}
