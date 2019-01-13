package com.viasat.common.bean;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class JavaBeanTester
{
	protected static final Logger LOGGER = LoggerFactory.getLogger(JavaBeanTester.class);

	/**
	 * Scans all classes accessible from the context class loader which belong
	 * to the given package and subpackages.
	 * 
	 * @param packageName
	 *            The base package
	 * @param excludeClassNames
	 *            The names of classes to exclude from test
	 * @return The classes
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static Class<?>[] getClassesInPackage(String packageName, List<String> excludeClassNames)
			throws ClassNotFoundException, IOException
	{
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		assert classLoader != null;
		String path = packageName.replace('.', '/');
		Enumeration<URL> resources = classLoader.getResources(path);
		List<File> dirs = new ArrayList<File>();
		while (resources.hasMoreElements())
		{
			URL resource = resources.nextElement();
			dirs.add(new File(resource.getFile()));
		}
		ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
		for (File directory : dirs)
		{
			classes.addAll(findClasses(directory, packageName, excludeClassNames));
		}
		return classes.toArray(new Class<?>[classes.size()]);
	}

	/**
	 * Recursive method used to find all classes in a given directory and
	 * subdirs.
	 * 
	 * @param directory
	 *            The base directory
	 * @param packageName
	 *            The package name for classes found inside the base directory
	 * @param excludeClassNames
	 *            The names of classes to exclude from list
	 * @return The classes
	 * @throws ClassNotFoundException
	 */
	private static List<Class<?>> findClasses(File directory, String packageName,
			List<String> excludeClassNames) throws ClassNotFoundException
	{
		List<Class<?>> classes = new ArrayList<Class<?>>();
		if (!directory.exists())
		{
			return classes;
		}
		File[] files = directory.listFiles();
		for (File file : files)
		{
			if (file.isDirectory())
			{
				assert !file.getName().contains(".");
				classes.addAll(findClasses(file, packageName + "." + file.getName(),
						excludeClassNames));
			}
			else if (file.getName().endsWith(".class"))
			{
				String className = file.getName().replace(".class", "");
				if (excludeClassNames == null || !excludeClassNames.contains(className))
					classes.add(Class.forName(packageName + '.'
							+ file.getName().substring(0, file.getName().length() - 6)));
			}
		}
		return classes;
	}

	private static <T> void testObjectFactory(final Class<T> clazz)
	{
		Method[] methods = clazz.getMethods();
		T bean;
		try
		{
			bean = clazz.newInstance();

			for (Method method : methods)
			{
				if (method.getName().startsWith("create") && method.getParameterTypes().length == 0)
				{

					try
					{
						Object o = method.invoke(bean);
						Assert.assertNotNull(o);
					}
					catch (Exception e)
					{
						Assert.fail(String.format("Error testing the method %s: %s",
								method.getName(), e.toString()));
					}

				}
			}
		}

		catch (Exception e1)
		{
			Assert.fail(String.format("Error creating instance of  %s: %s", clazz.getName(),
					e1.toString()));
		}
	}

	public static <T> void test(final Class<T> clazz, final boolean testObjectOverrides,
			final String... skipThese) throws IntrospectionException
	{
		if (clazz.getName().endsWith(".ObjectFactory"))
		{
			testObjectFactory(clazz);
		}
		final PropertyDescriptor[] props = Introspector.getBeanInfo(clazz).getPropertyDescriptors();
		nextProp: for (PropertyDescriptor prop : props)
		{
			// Check the list of properties that we don't want to test
			for (String skipThis : skipThese)
			{
				if (skipThis.equals(prop.getName()))
				{
					continue nextProp;
				}
			}
			final Method getter = prop.getReadMethod();
			final Method setter = prop.getWriteMethod();
			if (getter != null && setter != null)
			{
				final Class<?> returnType = getter.getReturnType();
				final Class<?>[] params = setter.getParameterTypes();
				if (params.length == 1 && params[0] == returnType)
				{
					try
					{
						final Object value = buildValue(returnType);
						final T bean = clazz.newInstance();
						if (testObjectOverrides)
						{
							testObjectOverrides(bean);
						}
						setter.invoke(bean, value);
						final Object expectedValue = value;
						final Object actualValue = getter.invoke(bean);
						Assert.assertEquals(
								String.format("Failed while testing property %s", prop.getName()),
								expectedValue, actualValue);
					}
					catch (Exception ex)
					{
						Assert.fail(String.format("Error testing the property %s: %s",
								prop.getName(), ex.toString()));
					}
				}
			}
		}
	}

	public static <T> void test(final Class<T> clazz) throws IntrospectionException
	{
		test(clazz, false, new String[0]);
	}

	public static <T> void test(final Class<T> clazz, final String... skipThese)
			throws IntrospectionException
	{
		test(clazz, false, skipThese);
	}

	@SuppressWarnings("unchecked")
	private static <T> void testObjectOverrides(final T bean) throws IllegalAccessException,
			InstantiationException
	{
		final T otherBean = (T) bean.getClass().newInstance();
		Assert.assertEquals("Failed equals()", bean, bean);
		Assert.assertEquals("Failed equals()", otherBean, bean);
		Assert.assertEquals("Failed hashCode()", bean.hashCode(), bean.hashCode());
		Assert.assertEquals("Failed hashCode()", otherBean.hashCode(), otherBean.hashCode());
		Assert.assertEquals("Failed full hashCode()", bean.hashCode(), otherBean.hashCode());
		Assert.assertEquals("Failed toString()", otherBean.toString(), bean.toString());
	}

	private static Object buildValue(Class<?> clazz) throws InstantiationException,
			IllegalAccessException, IllegalArgumentException, SecurityException,
			InvocationTargetException, DatatypeConfigurationException
	{
		// Try mocking framework first
		if (clazz == XMLGregorianCalendar.class)
		{
			return DatatypeFactory.newInstance().newXMLGregorianCalendar();
		}
		else if (clazz == BigInteger.class)
		{
			return new BigInteger("1");
		}
		else if (clazz == BigDecimal.class)
		{
			return new BigDecimal("1");
		}
		final Constructor<?>[] ctrs = clazz.getConstructors();
		for (Constructor<?> ctr : ctrs)
		{
			if (ctr.getParameterTypes().length == 0)
			{
				return ctr.newInstance();
			}
		}
		if (clazz == String.class)
			return "testvalue";
		else if (clazz.isArray())
			return Array.newInstance(clazz.getComponentType(), 1);
		else if (clazz == boolean.class || clazz == Boolean.class)
			return true;
		else if (clazz == int.class || clazz == Integer.class)
			return 1;
		else if (clazz == long.class || clazz == Long.class)
			return 1L;
		else if (clazz == double.class || clazz == Double.class)
			return 1.0D;
		else if (clazz == float.class || clazz == Float.class)
			return 1.0F;
		else if (clazz == char.class || clazz == Character.class)
			return 'Y';
		else if (clazz.isEnum())
			return clazz.getEnumConstants()[0];
		else if (clazz == List.class)
			return new ArrayList<Object>();
		else
		{
			LOGGER.error("Unable to build an instance of class " + clazz.getName());
			return null;
		}
	}

	protected abstract List<String> getPackagesToTest();

	protected List<String> getClassesToExclude()
	{
		return new ArrayList<String>();
	}

	@Test
	public void testDataBeans() throws IntrospectionException, ClassNotFoundException, IOException
	{

		for (String packageName : getPackagesToTest())
		{
			Class<?>[] classes = JavaBeanTester.getClassesInPackage(packageName,
					getClassesToExclude());
			for (Class<?> aClass : classes)
			{
				JavaBeanTester.test(aClass);
			}

		}
	}
}