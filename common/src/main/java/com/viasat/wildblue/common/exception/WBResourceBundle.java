package com.viasat.wildblue.common.exception;

import java.util.Collections;
import java.util.Enumeration;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;

public class WBResourceBundle extends ResourceBundle
{

	WBResourceBundle(String baseResourceBundle, String appResourceBundle)
	{
		super();
		try
		{
			m_BaseBundle = ResourceBundle.getBundle(baseResourceBundle);
		}
		catch (MissingResourceException e)
		{

		}
		try
		{
			m_WebServiceBundle = ResourceBundle.getBundle(appResourceBundle);
		}
		catch (MissingResourceException e)
		{

		}
	}

	@Override
	public Enumeration<String> getKeys()
	{
		Enumeration<String> keys = null;
		if (m_BaseBundle != null)
		{
			keys = m_BaseBundle.getKeys();
		}
		if (keys == null && m_WebServiceBundle != null)
		{
			keys = m_BaseBundle.getKeys();
		}
		else if (keys != null && m_WebServiceBundle != null)
		{
			Set<String> keyset = m_BaseBundle.keySet();
			keyset.addAll(m_WebServiceBundle.keySet());
			keys = Collections.enumeration(keyset);
		}
		return keys;
	}

	@Override
	protected Object handleGetObject(String key)
	{
		Object o = null;
		if (m_BaseBundle != null && m_BaseBundle.containsKey(key))
		{
			o = m_BaseBundle.getObject(key);
		}
		if (o == null && m_WebServiceBundle != null && m_WebServiceBundle.containsKey(key))
		{
			o = m_WebServiceBundle.getObject(key);
		}
		return o;
	}

	ResourceBundle m_BaseBundle;
	ResourceBundle m_WebServiceBundle;
}
